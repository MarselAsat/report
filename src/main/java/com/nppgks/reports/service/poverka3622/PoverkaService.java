package com.nppgks.reports.service.poverka3622;

import com.nppgks.reports.dto.TagNameForOpc;
import com.nppgks.reports.opc.OpcRequests;
import com.nppgks.reports.service.PoverkaType;
import com.nppgks.reports.service.db_services.ManualTagNameService;
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
    private final ManualTagNameService manualTagNameService;

    public void doPoverka3622(){
        List<TagNameForOpc> initialTagNamesForOpc = manualTagNameService.getTagNamesByInitialAndType(true, PoverkaType._3622.name());
        List<String> initialTagNamesStr = initialTagNamesForOpc
                .stream()
                .map(TagNameForOpc::name)
                .toList();

        Map<String, String> initialTagNamesMap = initialTagNamesForOpc.stream()
                .collect(Collectors.toMap(TagNameForOpc::name, TagNameForOpc::permanentName));

        Map<String, String> initialDataFromOpc = opcRequests.getTagDataFromOpc(initialTagNamesStr);
        InitialData initialData = DataConverter.convertMapToInitialData(initialDataFromOpc, initialTagNamesMap);
        PoverkaRunner poverkaRunner = new PoverkaRunner(initialData);
        FinalData finalData = poverkaRunner.run();
        Map<String, Object> finalDataForOpc = DataConverter.convertFinalDataToMap(finalData, initialTagNamesMap);
        opcRequests.sendTagDataToOpc(finalDataForOpc);

    }
}
