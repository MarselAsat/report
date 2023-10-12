package com.nppgks.reportingsystem.reportgeneration.manual_reports.kmh;

import com.nppgks.reportingsystem.constants.ManualReportTypesEnum;
import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.ManualReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.SaveReportStrategy;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportTypeService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.util.ArrayParser;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class KmhMassmByMassmReportGenerator extends ManualReportGenerator {

    private final OpcServiceRequests opcServiceRequests;
    private final ManualReportTypeService manualReportTypeService;
    private final ManualTagService manualTagService;
    public KmhMassmByMassmReportGenerator(@Qualifier("saveManyTimesADayStrategy") SaveReportStrategy saveReportStrategy, OpcServiceRequests opcServiceRequests, ManualReportTypeService manualReportTypeService, ManualTagService manualTagService) {
        super(saveReportStrategy);
        this.opcServiceRequests = opcServiceRequests;
        this.manualReportTypeService = manualReportTypeService;
        this.manualTagService = manualTagService;
    }

    @Override
    protected List<ReportData> generateReportDataList() {
        List<ReportData> reportDataList = new ArrayList<>();
        List<ManualTagForOpc> tags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypesEnum.kmhMassmByMassm.name());
        List<String> tagAddresses = DataConverter.convertTagsToListOfAddresses(tags);
        Map<String, String> tagValuesFromOpc = opcServiceRequests.getTagValuesFromOpc(tagAddresses);
        Map<String, ManualTagForOpc> addressToTagMap = DataConverter.convertTagListToMapWithAddressKey(tags);
        Map<String, String> permanentNameToAddressMap = DataConverter.createPermanentNameToAddressMap(tags);

        for(Map.Entry<String, String> addressToValue: tagValuesFromOpc.entrySet()){
            reportDataList.add(new ReportData(
                    null,
                    addressToValue.getValue(),
                    ManualTagForOpc.toTag(addressToTagMap.get(addressToValue.getKey())),
                    report
            ));
        }

        String delta_MStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("delta_M"));
        double[] delta_M = ArrayParser.toArray(delta_MStr);

        double delta_max = Arrays.stream(delta_M).map(Math::abs)
                .max().orElseThrow();

        String conclusion = "годен";

        if(delta_max >= 0.25) conclusion = "не годен";

        Tag delta_maxTag = manualTagService.getTagByNameAndReportType("delta_max", ManualReportTypesEnum.kmhMassmByMassm.name());
        Tag conclusionTag = manualTagService.getTagByNameAndReportType("conclusion", ManualReportTypesEnum.kmhMassmByMassm.name());

        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(delta_max),
                delta_maxTag,
                report
        ));

        reportDataList.add(new ReportData(
                null,
                conclusion,
                conclusionTag,
                report
        ));

        return reportDataList;
    }

    @Override
    protected Report createReport(LocalDateTime currentDt) {
        ReportType reportType = manualReportTypeService.findById(ManualReportTypesEnum.kmhMassmByMassm.name());
        return new Report(
                null,
                "КМХ рабочего МПР по контрольному от "+ SingleDateTimeFormatter.formatToSinglePattern(currentDt),
                currentDt,
                reportType
        );
    }
}
