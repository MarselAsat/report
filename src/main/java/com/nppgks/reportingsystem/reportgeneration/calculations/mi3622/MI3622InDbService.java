package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.constants.DataType;
import com.nppgks.reportingsystem.constants.CalcMethod;
import com.nppgks.reportingsystem.db.calculations.entity.ReportName;
import com.nppgks.reportingsystem.db.calculations.entity.TagData;
import com.nppgks.reportingsystem.db.calculations.repository.ReportNameRepository;
import com.nppgks.reportingsystem.db.calculations.repository.TagDataRepository;
import com.nppgks.reportingsystem.dto.calc.CalcTagNameForOpc;
import com.nppgks.reportingsystem.opc.ArrayParser;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс, отвечающий за сохранение данных МИ3622 (исходные данные + результаты вычислений) в базу данных
 */
@Service
@Setter
@RequiredArgsConstructor
public class MI3622InDbService {
    private List<CalcTagNameForOpc> initialTagNames; // (id, tag name, permanent tag name)
    private Map<String, String> initialDataFromOpc; // (tag name, tag data)
    private Map<String, Object> finalDataForOpc;
    private List<CalcTagNameForOpc> finalTagNames;
    private final ReportNameRepository reportNameRepository;
    private final TagDataRepository tagDataRepository;

    @Transactional
    public void saveCalculations() {
        ReportName reportName = createReportName();

        Map<String, CalcTagNameForOpc> initialTagNamesMap = convertListToMap(initialTagNames);
        Map<String, CalcTagNameForOpc> finalTagNamesMap = convertListToMap(finalTagNames);

        ReportName savedReportName = reportNameRepository.save(reportName);

        List<TagData> tagDataList = createListOfTagDataMI3622(
                savedReportName,
                initialTagNamesMap,
                finalTagNamesMap);
        tagDataRepository.saveAll(tagDataList);
    }

    /** конвертация списка объектов CalcTagNameForOpc (id, tagName, permanentTagName)
     *  в map (key = CalcTagNameForOpc.name, value = calcTagNameForOpc)
     */
    private Map<String, CalcTagNameForOpc> convertListToMap(List<CalcTagNameForOpc> tagNames) {
        return tagNames.stream()
                .map(tn -> Map.entry(tn.name(), tn))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1));
    }

    private List<TagData> createListOfTagDataMI3622 (
            ReportName reportName,
            Map<String, CalcTagNameForOpc> initialTagNamesMap,
            Map<String, CalcTagNameForOpc> finalTagNamesMap) {
        List<TagData> tagDataList = new ArrayList<>();
        for (Map.Entry<String, String> entry : initialDataFromOpc.entrySet()) {
            String value = entry.getValue();
            TagData tagData = new TagData(
                    null,
                    value,
                    getDataType(value),
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
                    getDataType(value),
                    CalcTagNameForOpc.toTagName(finalTagNamesMap.get(entry.getKey())),
                    reportName
            );
            tagDataList.add(tagData);
        }
        return tagDataList;
    }

    private String getDataType(String value) {
        if (value.matches("\\[\\[.*")) {
            return DataType.array2D.name();
        } else if (value.matches("\\[.*")) {
            return DataType.array.name();
        } else {
            return DataType.singleValue.name();
        }
    }

    private ReportName createReportName() {
        LocalDateTime dt = LocalDateTime.now();
        return new ReportName(
                null,
                "Поверка МИ3622 от "+ SingleDateTimeFormatter.formatToSinglePattern(dt),
                dt,
                CalcMethod.MI_3622.name());
    }
}
