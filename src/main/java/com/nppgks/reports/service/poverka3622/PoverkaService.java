package com.nppgks.reports.service.poverka3622;

import com.nppgks.reports.dto.TagNameForOpc;
import com.nppgks.reports.opc.OpcRequests;
import com.nppgks.reports.constants.PoverkaType;
import com.nppgks.reports.service.db_services.poverka.PoverkaTagNameService;
import com.nppgks.reports.service.poverka3622.data.DataConverter;
import com.nppgks.reports.service.poverka3622.data.FinalData;
import com.nppgks.reports.service.poverka3622.data.InitialData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PoverkaService {

    private final OpcRequests opcRequests;
    private final PoverkaTagNameService poverkaTagNameService;

    private final Poverka3622InDbService poverka3622InDbService;

    public void doPoverka3622() {
        List<TagNameForOpc> initialTagNames = poverkaTagNameService.getTagNamesByInitialAndType(true, PoverkaType.MI_3622.name());
        Map<String, String> initialTagNamesMap = createTagNamesMap(initialTagNames);

        List<String> initialTagNamesForOpc = initialTagNames
                .stream()
                .map(TagNameForOpc::name)
                .toList();

        Map<String, String> initialDataFromOpc = opcRequests.getTagDataFromOpc(initialTagNamesForOpc);

        DataConverter.putInOrder2DArraysInOpcData(initialDataFromOpc, initialTagNamesMap);

        InitialData initialData = DataConverter.convertMapToInitialData(initialDataFromOpc, initialTagNamesMap);
        PoverkaRunner poverkaRunner = new PoverkaRunner(initialData);
        FinalData finalData = poverkaRunner.run();

        List<TagNameForOpc> finalTagNames = poverkaTagNameService.getTagNamesByInitialAndType(false, PoverkaType.MI_3622.name());

        Map<String, String> finalTagNamesMap = createTagNamesMap(finalTagNames);

        Map<String, Object> finalDataForOpc = DataConverter.convertFinalDataToMap(finalData, finalTagNamesMap);
        opcRequests.sendTagDataToOpc(finalDataForOpc);

        prepareAllDataForDB(initialTagNames, initialDataFromOpc, finalTagNames, finalDataForOpc);
    }

    private void prepareAllDataForDB(List<TagNameForOpc> initialTagNames,
                                     Map<String, String> initialDataFromOpc,
                                     List<TagNameForOpc> finalTagNames,
                                     Map<String, Object> finalDataForOpc) {
        poverka3622InDbService.setInitialDataFromOpc(initialDataFromOpc);
        poverka3622InDbService.setInitialTagNames(initialTagNames);
        poverka3622InDbService.setFinalTagNames(finalTagNames);
        poverka3622InDbService.setFinalDataForOpc(finalDataForOpc);
    }

    private Map<String, String> createTagNamesMap(List<TagNameForOpc> tagNamesForOpc){
        return tagNamesForOpc.stream()
                .collect(Collectors.toMap(TagNameForOpc::permanentName, TagNameForOpc::name));
    }

    public void saveInDb() {
        poverka3622InDbService.savePoverka();
    }
}
