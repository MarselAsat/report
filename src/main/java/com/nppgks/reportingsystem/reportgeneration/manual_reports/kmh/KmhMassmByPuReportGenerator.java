package com.nppgks.reportingsystem.reportgeneration.manual_reports.kmh;

import com.nppgks.reportingsystem.constants.ManualReportTypesEnum;
import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.ManualReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.SaveReportStrategy;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.kmh.massmbypu.KmhMassmByPuCalculator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.kmh.massmbypu.KmhMassmByPuFinalData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.kmh.massmbypu.KmhMassmByPuInitialData;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportTypeService;
import com.nppgks.reportingsystem.util.DataRounder;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.MI3272TprInitData;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.util.ArrayParser;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KmhMassmByPuReportGenerator extends ManualReportGenerator {

    private final OpcServiceRequests opcServiceRequests;
    private final ManualReportTypeService manualReportTypeService;
    private final ManualTagService manualTagService;
    public KmhMassmByPuReportGenerator(@Qualifier("saveManyTimesADayStrategy") SaveReportStrategy saveReportStrategy, OpcServiceRequests opcServiceRequests, ManualReportTypeService manualReportTypeService, ManualTagService manualTagService) {
        super(saveReportStrategy);
        this.opcServiceRequests = opcServiceRequests;
        this.manualReportTypeService = manualReportTypeService;
        this.manualTagService = manualTagService;
    }

    @Override
    protected List<ReportData> generateReportDataList() {
        List<ManualTagForOpc> initialTags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypesEnum.kmhMassmByPu.name());
        List<String> initialTagsForOpc = DataConverter.convertTagsToListOfAddresses(initialTags);
        Map<String, String> initialDataFromOpc = opcServiceRequests.getTagValuesFromOpc(initialTagsForOpc);

        Map<String, String> initialTagsMap = DataConverter.createPermanentNameToAddressMap(initialTags);
        DataConverter.putInOrder2DArraysInOpcData(initialDataFromOpc, initialTagsMap, MI3272TprInitData.class);

        KmhMassmByPuInitialData initialData = DataConverter.convertMapToInitialData(initialDataFromOpc, initialTagsMap, KmhMassmByPuInitialData.class, true);

        KmhMassmByPuFinalData finalData = KmhMassmByPuCalculator.calculate(initialData);
        DataRounder.roundPojo(finalData);

        List<ManualTagForOpc> finalTags = manualTagService.getTagsByInitialAndReportType(false, ManualReportTypesEnum.kmhMassmByPu.name());
        Map<String, String> finalTagsMap = DataConverter.createPermanentNameToAddressMap(finalTags);
        Map<String, Object> finalDataForOpc = DataConverter.convertFinalDataToMap(finalData, finalTagsMap);

        return createListOfReportData(initialTags, finalTags, initialDataFromOpc, finalDataForOpc, report);
    }

    private List<ReportData> createListOfReportData(List<ManualTagForOpc> initialTags, List<ManualTagForOpc> finalTags, Map<String, String> initialDataFromOpc, Map<String, Object> finalDataForOpc, Report report) {
        Map<String, ManualTagForOpc> addressToinitialTagMap = DataConverter.convertTagListToMapWithAddressKey(initialTags);
        Map<String, ManualTagForOpc> addressTofinalTagMap = DataConverter.convertTagListToMapWithAddressKey(finalTags);

        List<ReportData> reportDataList = new ArrayList<>();

        for (Map.Entry<String, String> entry : initialDataFromOpc.entrySet()) {
            String value = entry.getValue();
            ReportData reportData = new ReportData(
                    null,
                    value,
                    ManualTagForOpc.toTag(addressToinitialTagMap.get(entry.getKey())),
                    report
            );
            reportDataList.add(reportData);
        }

        for (Map.Entry<String, Object> entry : finalDataForOpc.entrySet()) {
            String value = ArrayParser.fromObjectToJson(entry.getValue());
            ReportData reportData = new ReportData(
                    null,
                    value,
                    ManualTagForOpc.toTag(addressTofinalTagMap.get(entry.getKey())),
                    report
            );
            reportDataList.add(reportData);
        }
        return reportDataList;

    }

    @Override
    protected Report createReport(LocalDateTime currentDt) {
        ReportType reportType = manualReportTypeService.findById(ManualReportTypesEnum.kmhMassmByPu.name());
        return new Report(
                null,
                "КМХ контрольного МПР с помощью ПУ "+ SingleDateTimeFormatter.formatToSinglePattern(currentDt),
                currentDt,
                reportType
        );
    }
}
