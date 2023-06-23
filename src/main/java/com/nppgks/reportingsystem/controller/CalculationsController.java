package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.db.calculations.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.MI3622Service;
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
    private final MI3622Service MI3622Service;
    private final String MI_3622 = "/MI3622";

    @GetMapping(MI_3622)
    public String calcMI3622(ModelMap modelMap) {
        List<ReportData> reportDataList = MI3622Service.calcMI3622();
        reportDataList.forEach(td -> {
            Object value = ArrayParser.fromJsonToObject(td.getData());
            modelMap.put(
                    td.getTag().getPermanentName(), value);
        });
        return "report_pages/MI3622-report-page";
    }

    @ResponseBody
    @GetMapping(MI_3622 + "/save")
    public String saveMI3622Data() {
        return MI3622Service.saveInDb();
    }
}
