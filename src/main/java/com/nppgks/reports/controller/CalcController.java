package com.nppgks.reports.controller;

import com.nppgks.reports.service.calc3622.CalcService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalcController {
    private final CalcService calcService;
    private final String CALC_3622 = "3622";

    @GetMapping("/calc"+ CALC_3622)
    public void doCalc3622(){
        calcService.doCalc3622();
    }

    @GetMapping("/calc"+ CALC_3622 +"/save")
    public void saveDataCalc3622(){
        calcService.saveInDb();
    }
}
