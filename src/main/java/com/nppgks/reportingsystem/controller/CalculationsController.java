package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.db.calculations.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.MI3622Generator;
import com.nppgks.reportingsystem.util.ArrayParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/calc")
public class CalculationsController {
    private final MI3622Generator MI3622Generator;
    private final String MI_3622 = "/MI3622";

    @GetMapping(MI_3622)
    public String calcMI3622(ModelMap modelMap) {
        List<ReportData> reportDataList = MI3622Generator.generateMI3622Report();
        reportDataList.forEach(td -> {
            Object value = ArrayParser.fromJsonToObject(td.getData());
            modelMap.put(
                    td.getTag().getPermanentName(), value);
        });
        modelMap.put("printSaveButtonsRequired", true);
        return "report_pages/MI3622-report-page";
    }

    @ResponseBody
    @GetMapping(MI_3622 + "/save")
    public String saveMI3622Data() {
        return MI3622Generator.saveInDb();
    }
}
