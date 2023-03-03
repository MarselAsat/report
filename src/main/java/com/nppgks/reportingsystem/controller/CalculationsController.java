package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.MI3622Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CalculationsController {
    private final MI3622Service MI3622Service;
    private final String MI_3622 = "MI3622";

    @ResponseBody
    @GetMapping("/calc" + MI_3622)
    public void doCalc3622() {
        MI3622Service.doCalc3622();
    }

    @ResponseBody
    @GetMapping("/calc" + MI_3622 + "/save")
    public void saveDataCalc3622() {
        MI3622Service.saveInDb();
    }
}
