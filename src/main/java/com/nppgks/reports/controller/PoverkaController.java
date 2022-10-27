package com.nppgks.reports.controller;

import com.nppgks.reports.service.poverka3622.PoverkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PoverkaController {
    private final PoverkaService poverkaService;
    private final String POVERKA_3622 = "3622";

    @GetMapping("/poverka"+ POVERKA_3622)
    public void doPoverka3622(){
        poverkaService.doPoverka3622();
    }

    @GetMapping("/poverka"+ POVERKA_3622+"/save")
    public void saveDataPoverka3622(){
        poverkaService.saveInDb();
    }
}
