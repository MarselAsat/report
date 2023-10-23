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
import com.nppgks.reportingsystem.util.DataRounder;
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

        String M_ilStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("M_il"));
        double[] M_il = ArrayParser.toArray(M_ilStr);

        String M_ilkStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("M_ilk"));
        double[] M_ilk = ArrayParser.toArray(M_ilkStr);

        double[] delta_M = new double[M_il.length];

        for (int i = 0; i < M_il.length; i++) {
            delta_M[i] = (M_il[i] - M_ilk[i])/M_ilk[i]*100;
        }

        DataRounder.roundDoubleArray(delta_M);

        double delta_max = Arrays.stream(delta_M).map(Math::abs)
                .max().orElseThrow();

        String conclusion = "годен";

        if(delta_max >= 0.25) conclusion = "не годен";

        Tag delta_MTag = manualTagService.getTagByNameAndReportType("delta_M", ManualReportTypesEnum.kmhMassmByMassm.name());
        Tag delta_maxTag = manualTagService.getTagByNameAndReportType("delta_max", ManualReportTypesEnum.kmhMassmByMassm.name());
        Tag conclusionTag = manualTagService.getTagByNameAndReportType("conclusion", ManualReportTypesEnum.kmhMassmByMassm.name());

        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(delta_M),
                delta_MTag,
                report
        ));

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
