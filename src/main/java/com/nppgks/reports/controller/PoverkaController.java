package com.nppgks.reports.controller;

import com.nppgks.reports.dto.TagNameForOpc;
import com.nppgks.reports.opc.OpcRequests;
import com.nppgks.reports.service.ManualTagNameService;
import com.nppgks.reports.service.poverka3622.PoverkaRunner;
import com.nppgks.reports.service.poverka3622.data.FinalData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PoverkaController {

    private final ManualTagNameService manualTagNameService;
    private final OpcRequests opcRequests;
    private final String POVERKA_3622 = "3622";

    @GetMapping("/poverka"+ POVERKA_3622)
    public void poverka3622(){
        List<TagNameForOpc> tagNames = manualTagNameService.getTagNamesByInitialAndType(true, POVERKA_3622);

        List<String> tagNamesStr = tagNames
                .stream()
                .map(TagNameForOpc::getName)
                .toList();

        Map<String, String> tagNamesMap = tagNames.stream()
                .collect(Collectors.toMap(TagNameForOpc::getName, TagNameForOpc::getPermanentName));
        Map<String, String> initialDataMap = opcRequests.getTagDataFromOpc(tagNamesStr);

        PoverkaRunner poverkaRunner = new PoverkaRunner(initialDataMap, tagNamesMap);
        FinalData finalData = poverkaRunner.run();

        Map<String, String> finalTagNames = manualTagNameService.getTagNamesByInitialAndType(false, POVERKA_3622)
                .stream()
                .collect(Collectors.toMap(TagNameForOpc::getName, TagNameForOpc::getPermanentName));
        opcRequests.sendTagDataToOpc(finalData.convertToMap(finalTagNames));
    }
}
