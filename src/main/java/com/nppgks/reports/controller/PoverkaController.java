package com.nppgks.reports.controller;

import com.nppgks.reports.dto.TagNameForOpc;
import com.nppgks.reports.opc.OpcRequests;
import com.nppgks.reports.service.ManualTagNameService;
import com.nppgks.reports.service.poverka3622.PoverkaRunner;
import com.nppgks.reports.service.poverka3622.PoverkaService;
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
    private final PoverkaService poverkaService;
    private final OpcRequests opcRequests;
    private final String POVERKA_3622 = "3622";

    @GetMapping("/poverka"+ POVERKA_3622)
    public void poverka3622(){
        poverkaService.doPoverka3622();
    }
}
