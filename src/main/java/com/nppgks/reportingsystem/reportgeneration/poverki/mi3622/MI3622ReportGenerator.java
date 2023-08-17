package com.nppgks.reportingsystem.reportgeneration.poverki.mi3622;

import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.constants.ManualReportTypes;
import com.nppgks.reportingsystem.reportgeneration.poverki.ManualReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.poverki.SaveReportStrategy;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.reportgeneration.poverki.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.poverki.mi3622.calculations.MI3622FinalData;
import com.nppgks.reportingsystem.reportgeneration.poverki.mi3622.calculations.MI3622InitialData;
import com.nppgks.reportingsystem.util.ArrayParser;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MI3622ReportGenerator extends ManualReportGenerator {

    private final OpcServiceRequests opcServiceRequests;
    private final ManualTagService manualTagService;

    public MI3622ReportGenerator(@Qualifier("saveOnceADayStrategy") SaveReportStrategy saveReportStrategy, OpcServiceRequests opcServiceRequests, ManualTagService manualTagService) {
        super(saveReportStrategy);
        this.opcServiceRequests = opcServiceRequests;
        this.manualTagService = manualTagService;
    }

    @Override
    protected List<ReportData> generateReportDataList() {
        Tag isFinishedTag = manualTagService.getTagByNameAndReportType("isFinished", ManualReportTypes.MI3622.name());
        opcServiceRequests.sendTagValuesToOpc(Map.of(isFinishedTag.getAddress(), false));

        List<ManualTagForOpc> initialTags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypes.MI3622.name());
        List<String> initialTagsForOpc = initialTags
                .stream()
                .map(ManualTagForOpc::address)
                .toList();

        Map<String, String> initialDataFromOpc = opcServiceRequests.getTagValuesFromOpc(initialTagsForOpc);

        Map<String, String> initialTagsMap = createTagsMap(initialTags);
        DataConverter.putInOrder2DArraysInOpcData(initialDataFromOpc, initialTagsMap, MI3622InitialData.class);

        MI3622InitialData MI3622InitialData = DataConverter.convertMapToInitialData(initialDataFromOpc, initialTagsMap, MI3622InitialData.class);
        MI3622Runner MI3622Runner = new MI3622Runner(MI3622InitialData);
        MI3622FinalData MI3622FinalData = MI3622Runner.run();

        List<ManualTagForOpc> finalTags = manualTagService.getTagsByInitialAndReportType(false, ManualReportTypes.MI3622.name());

        Map<String, String> finalTagsMap = createTagsMap(finalTags);

        Map<String, Object> finalDataForOpc = DataConverter.convertFinalDataToMap(MI3622FinalData, finalTagsMap);
        finalDataForOpc.put(isFinishedTag.getAddress(), true);
        opcServiceRequests.sendTagValuesToOpc(finalDataForOpc);

        prepareAllDataForDB(initialTags, initialDataFromOpc, finalTags, finalDataForOpc);

        return reportDataList;
    }

    @Override
    protected Report createReport(LocalDateTime currentDt) {
        return new Report(
                null,
                "Поверка МИ3622 от " + SingleDateTimeFormatter.formatToSinglePattern(currentDt),
                currentDt,
                ManualReportTypes.MI3622.name());
    }

    private void prepareAllDataForDB(List<ManualTagForOpc> initialTags,
                                     Map<String, String> initialDataFromOpc,
                                     List<ManualTagForOpc> finalTags,
                                     Map<String, Object> finalDataForOpc) {

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
     * конвертация списка объектов ManualTagForOpc (id, address, permanentName)
     * в map (key = ManualTagForOpc.address, value = manualTagForOpc)
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

    private Map<String, String> createTagsMap(List<ManualTagForOpc> tagsForOpc) {
        return tagsForOpc.stream()
                .collect(Collectors.toMap(ManualTagForOpc::permanentName, ManualTagForOpc::address));
    }
}
