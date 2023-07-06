package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.constants.ManualReportTypes;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.FinalData;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.InitialData;
import com.nppgks.reportingsystem.util.ArrayParser;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MI3622Generator {

    private final OpcServiceRequests opcServiceRequests;
    private final ManualTagService manualTagService;
    private final MI3622DbService MI3622DbService;
    private boolean isSaved = false;
    private List<ReportData> reportDataList = new ArrayList<>();
    private Report report;

    public List<ReportData> generateMI3622Report() {
        List<ManualTagForOpc> initialTags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypes.MI3622.name());
        Map<String, String> initialTagsMap = createTagsMap(initialTags);

        List<String> initialTagsForOpc = initialTags
                .stream()
                .map(ManualTagForOpc::address)
                .toList();

        Map<String, String> initialDataFromOpc = opcServiceRequests.getTagValuesFromOpc(initialTagsForOpc);

        DataConverter.putInOrder2DArraysInOpcData(initialDataFromOpc, initialTagsMap);

        InitialData initialData = DataConverter.convertMapToInitialData(initialDataFromOpc, initialTagsMap);
        MI3622Runner MI3622Runner = new MI3622Runner(initialData);
        FinalData finalData = MI3622Runner.run();

        List<ManualTagForOpc> finalTags = manualTagService.getTagsByInitialAndReportType(false, ManualReportTypes.MI3622.name());

        Map<String, String> finalTagsMap = createTagsMap(finalTags);

        Map<String, Object> finalDataForOpc = DataConverter.convertFinalDataToMap(finalData, finalTagsMap);
        opcServiceRequests.sendTagValuesToOpc(finalDataForOpc);

        prepareAllDataForDB(initialTags, initialDataFromOpc, finalTags, finalDataForOpc);
        isSaved = false;
        return reportDataList;
    }

    public String saveInDb() {
        if (isSaved) {
            return "Эти результаты уже сохранены в базу данных";
        }
        String response = MI3622DbService.saveCalculations(reportDataList, report);
        isSaved = true;
        return response;
    }

    private void prepareAllDataForDB(List<ManualTagForOpc> initialTags,
                                     Map<String, String> initialDataFromOpc,
                                     List<ManualTagForOpc> finalTags,
                                     Map<String, Object> finalDataForOpc) {

        report = createReport();
        Map<String, ManualTagForOpc> initialTagsMap = convertListToMap(initialTags);
        Map<String, ManualTagForOpc> finalTagsMap = convertListToMap(finalTags);

        reportDataList = createListOfReportDataMI3622(
                initialDataFromOpc,
                finalDataForOpc,
                report,
                initialTagsMap,
                finalTagsMap);
    }

    /**
     * конвертация списка объектов CalcTagForOpc (id, address, permanentName)
     * в map (key = CalcTagForOpc.address, value = calcTagForOpc)
     */
    private Map<String, ManualTagForOpc> convertListToMap(List<ManualTagForOpc> tags) {
        return tags.stream()
                .map(t -> Map.entry(t.address(), t))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1));
    }

    private List<ReportData> createListOfReportDataMI3622(
            Map<String, String> initialDataFromOpc,
            Map<String, Object> finalDataForOpc,
            Report report,
            Map<String, ManualTagForOpc> initialTagsMap,
            Map<String, ManualTagForOpc> finalTagsMap) {
        List<ReportData> reportDataList = new ArrayList<>();
        for (Map.Entry<String, String> entry : initialDataFromOpc.entrySet()) {
            String value = entry.getValue();
            ReportData reportData = new ReportData(
                    null,
                    value,
                    ManualTagForOpc.toTag(initialTagsMap.get(entry.getKey())),
                    report
            );
            reportDataList.add(reportData);
        }

        for (Map.Entry<String, Object> entry : finalDataForOpc.entrySet()) {
            String value = ArrayParser.fromObjectToJson(entry.getValue());

            ReportData reportData = new ReportData(
                    null,
                    value,
                    ManualTagForOpc.toTag(finalTagsMap.get(entry.getKey())),
                    report
            );
            reportDataList.add(reportData);
        }
        return reportDataList;
    }

    private Report createReport() {
        LocalDateTime dt = LocalDateTime.now();
        return new Report(
                null,
                "Поверка МИ3622 от " + SingleDateTimeFormatter.formatToSinglePattern(dt),
                dt,
                ManualReportTypes.MI3622.name());
    }

    private Map<String, String> createTagsMap(List<ManualTagForOpc> tagsForOpc) {
        return tagsForOpc.stream()
                .collect(Collectors.toMap(ManualTagForOpc::permanentName, ManualTagForOpc::address));
    }
}
