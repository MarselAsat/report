package com.nppgks.reports.service.poverka3622;

import com.nppgks.reports.constants.DataType;
import com.nppgks.reports.constants.PoverkaType;
import com.nppgks.reports.db.entity.ReportNamePoverka;
import com.nppgks.reports.db.entity.TagDataPoverka3622;
import com.nppgks.reports.db.repository.ReportNamePoverkaRepository;
import com.nppgks.reports.db.repository.TagDataPoverka3622Repository;
import com.nppgks.reports.dto.TagNameForOpc;
import com.nppgks.reports.opc.ArrayParser;
import com.nppgks.reports.service.time_services.SingleDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Setter
@RequiredArgsConstructor
public class Poverka3622InDbService {
    private List<TagNameForOpc> initialTagNames; // (id, tag name, permanent tag name)
    private Map<String, String> initialDataFromOpc; // (tag name, tag data)
    private Map<String, Object> finalDataForOpc;
    private List<TagNameForOpc> finalTagNames;
    private final ReportNamePoverkaRepository reportNamePoverkaRepository;
    private final TagDataPoverka3622Repository tagDataPoverka3622Repository;

    public void savePoverka(){
        ReportNamePoverka reportName = createReportNamePoverka();

        Map<String, TagNameForOpc> initialTagNamesMap = convertListToMap(initialTagNames);
        Map<String, TagNameForOpc> finalTagNamesMap = convertListToMap(finalTagNames);
        List<TagDataPoverka3622> tagDataList = createListOfTagDataPoverka3622(
                reportName,
                initialTagNamesMap,
                finalTagNamesMap);

        reportNamePoverkaRepository.save(reportName);
        tagDataPoverka3622Repository.saveAll(tagDataList);
    }

    private Map<String, TagNameForOpc> convertListToMap(List<TagNameForOpc> initialTagNames) {
        return initialTagNames.stream()
                .map(tn -> Map.entry(tn.name(), tn))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1));
    }

    private List<TagDataPoverka3622> createListOfTagDataPoverka3622(
            ReportNamePoverka reportName,
            Map<String, TagNameForOpc> initialTagNamesMap,
            Map<String, TagNameForOpc> finalTagNamesMap) {
        List<TagDataPoverka3622> tagDataPoverka3622List = new ArrayList<>();
        for (Map.Entry<String, String> entry : initialDataFromOpc.entrySet()) {
            String value = entry.getValue();
            TagDataPoverka3622 tagDataPoverka3622 = new TagDataPoverka3622(
                    null,
                    value,
                    getDataType(value),
                    TagNameForOpc.toManualTagName(initialTagNamesMap.get(entry.getKey())),
                    reportName
            );
            tagDataPoverka3622List.add(tagDataPoverka3622);
        }

        for (Map.Entry<String, Object> entry : finalDataForOpc.entrySet()) {
            String value = ArrayParser.fromObjectToJson(entry.getValue());

            TagDataPoverka3622 tagDataPoverka3622 = new TagDataPoverka3622(
                    null,
                    value,
                    getDataType(value),
                    TagNameForOpc.toManualTagName(finalTagNamesMap.get(entry.getKey())),
                    reportName
            );
            tagDataPoverka3622List.add(tagDataPoverka3622);
        }
        return tagDataPoverka3622List;
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

    private ReportNamePoverka createReportNamePoverka() {
        LocalDateTime dt = LocalDateTime.now();
        return new ReportNamePoverka(
                null,
                "Поверка МИ3622 от "+ SingleDateTimeFormatter.formatToSinglePattern(dt),
                dt,
                PoverkaType.MI_3622.name());
    }

}
