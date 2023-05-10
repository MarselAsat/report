package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.db.calculations.entity.ReportName;
import com.nppgks.reportingsystem.db.calculations.entity.TagData;
import com.nppgks.reportingsystem.dto.calc.CalcTagNameForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.constants.CalcMethod;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcTagNameService;
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
public class MI3622Service {

    private final OpcServiceRequests opcServiceRequests;
    private final CalcTagNameService calcTagNameService;

    private final MI3622DbService MI3622DbService;

    private boolean isSaved = false;

    private List<TagData> tagDataList = new ArrayList<>();
    private ReportName reportName;

    public List<TagData> doCalc3622() {
        List<CalcTagNameForOpc> initialTagNames = calcTagNameService.getTagNamesByInitialAndType(true, CalcMethod.MI_3622.name());
        Map<String, String> initialTagNamesMap = createTagNamesMap(initialTagNames);

        List<String> initialTagNamesForOpc = initialTagNames
                .stream()
                .map(CalcTagNameForOpc::name)
                .toList();

        Map<String, String> initialDataFromOpc = opcServiceRequests.getTagDataFromOpc(initialTagNamesForOpc);

        DataConverter.putInOrder2DArraysInOpcData(
                initialDataFromOpc,
                initialTagNamesMap.get("pointsCount"),
                initialTagNamesMap.get("measureCount"));

        InitialData initialData = DataConverter.convertMapToInitialData(initialDataFromOpc, initialTagNamesMap);
        MI3622Runner MI3622Runner = new MI3622Runner(initialData);
        FinalData finalData = MI3622Runner.run();

        List<CalcTagNameForOpc> finalTagNames = calcTagNameService.getTagNamesByInitialAndType(false, CalcMethod.MI_3622.name());

        Map<String, String> finalTagNamesMap = createTagNamesMap(finalTagNames);

        Map<String, Object> finalDataForOpc = DataConverter.convertFinalDataToMap(finalData, finalTagNamesMap);
        opcServiceRequests.sendTagDataToOpc(finalDataForOpc);

        prepareAllDataForDB(initialTagNames, initialDataFromOpc, finalTagNames, finalDataForOpc);
        isSaved = false;
        return tagDataList;
    }

    public String saveInDb() {
        if (isSaved) {
            return "Эти результаты уже сохранены в базу данных";
        }
        String response = MI3622DbService.saveCalculations(tagDataList, reportName);
        isSaved = true;
        return response;
    }

    private void prepareAllDataForDB(List<CalcTagNameForOpc> initialTagNames,
                                     Map<String, String> initialDataFromOpc,
                                     List<CalcTagNameForOpc> finalTagNames,
                                     Map<String, Object> finalDataForOpc) {

        reportName = createReportName();
        Map<String, CalcTagNameForOpc> initialTagNamesMap = convertListToMap(initialTagNames);
        Map<String, CalcTagNameForOpc> finalTagNamesMap = convertListToMap(finalTagNames);

        tagDataList = createListOfTagDataMI3622(
                initialDataFromOpc,
                finalDataForOpc,
                reportName,
                initialTagNamesMap,
                finalTagNamesMap);
    }

    /**
     * конвертация списка объектов CalcTagNameForOpc (id, name, permanentTagName)
     * в map (key = CalcTagNameForOpc.name, value = calcTagNameForOpc)
     */
    private Map<String, CalcTagNameForOpc> convertListToMap(List<CalcTagNameForOpc> tagNames) {
        return tagNames.stream()
                .map(tn -> Map.entry(tn.name(), tn))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1));
    }

    private List<TagData> createListOfTagDataMI3622(
            Map<String, String> initialDataFromOpc,
            Map<String, Object> finalDataForOpc,
            ReportName reportName,
            Map<String, CalcTagNameForOpc> initialTagNamesMap,
            Map<String, CalcTagNameForOpc> finalTagNamesMap) {
        List<TagData> tagDataList = new ArrayList<>();
        for (Map.Entry<String, String> entry : initialDataFromOpc.entrySet()) {
            String value = entry.getValue();
            TagData tagData = new TagData(
                    null,
                    value,
                    CalcTagNameForOpc.toTagName(initialTagNamesMap.get(entry.getKey())),
                    reportName
            );
            tagDataList.add(tagData);
        }

        for (Map.Entry<String, Object> entry : finalDataForOpc.entrySet()) {
            String value = ArrayParser.fromObjectToJson(entry.getValue());

            TagData tagData = new TagData(
                    null,
                    value,
                    CalcTagNameForOpc.toTagName(finalTagNamesMap.get(entry.getKey())),
                    reportName
            );
            tagDataList.add(tagData);
        }
        return tagDataList;
    }

    private ReportName createReportName() {
        LocalDateTime dt = LocalDateTime.now();
        return new ReportName(
                null,
                "Поверка МИ3622 от " + SingleDateTimeFormatter.formatToSinglePattern(dt),
                dt,
                CalcMethod.MI_3622.name());
    }

    private Map<String, String> createTagNamesMap(List<CalcTagNameForOpc> tagNamesForOpc) {
        return tagNamesForOpc.stream()
                .collect(Collectors.toMap(CalcTagNameForOpc::permanentName, CalcTagNameForOpc::name));
    }
}
