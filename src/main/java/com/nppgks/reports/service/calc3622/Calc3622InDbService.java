package com.nppgks.reports.service.calc3622;

import com.nppgks.reports.constants.DataType;
import com.nppgks.reports.constants.CalcMethod;
import com.nppgks.reports.db.calculations.entity.ReportName;
import com.nppgks.reports.db.calculations.entity.TagData;
import com.nppgks.reports.db.calculations.repository.ReportNameRepository;
import com.nppgks.reports.db.calculations.repository.TagDataRepository;
import com.nppgks.reports.dto.calc.CalcTagNameForOpc;
import com.nppgks.reports.opc.ArrayParser;
import com.nppgks.reports.service.time_services.SingleDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Setter
@RequiredArgsConstructor
public class Calc3622InDbService {
    private List<CalcTagNameForOpc> initialTagNames; // (id, tag name, permanent tag name)
    private Map<String, String> initialDataFromOpc; // (tag name, tag data)
    private Map<String, Object> finalDataForOpc;
    private List<CalcTagNameForOpc> finalTagNames;
    private final ReportNameRepository reportNameRepository;
    private final TagDataRepository tagDataRepository;

    @Transactional
    public void saveCalculations(){
        ReportName reportName = createReportNameCalc();

        Map<String, CalcTagNameForOpc> initialTagNamesMap = convertListToMap(initialTagNames);
        Map<String, CalcTagNameForOpc> finalTagNamesMap = convertListToMap(finalTagNames);
        List<TagData> tagDataList = createListOfTagDataCalc3622(
                reportName,
                initialTagNamesMap,
                finalTagNamesMap);

        reportNameRepository.save(reportName);
        tagDataRepository.saveAll(tagDataList);
    }

    private Map<String, CalcTagNameForOpc> convertListToMap(List<CalcTagNameForOpc> initialTagNames) {
        return initialTagNames.stream()
                .map(tn -> Map.entry(tn.name(), tn))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1));
    }

    private List<TagData> createListOfTagDataCalc3622(
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

    private ReportName createReportNameCalc() {
        LocalDateTime dt = LocalDateTime.now();
        return new ReportName(
                null,
                "Поверка МИ3622 от "+ SingleDateTimeFormatter.formatToSinglePattern(dt),
                dt,
                CalcMethod.MI_3622.name());
    }
}