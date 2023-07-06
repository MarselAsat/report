package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
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
@RequestMapping("/mi3622")
public class MI3622Controller {
    private final MI3622Generator MI3622Generator;

    @GetMapping
    public String calculateMI3622(ModelMap modelMap) {
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
    @GetMapping("/save")
    public String saveMI3622Data() {
        return MI3622Generator.saveInDb();
    }
}
