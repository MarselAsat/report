package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3272.MI3272ReportGenerator;
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
@RequestMapping("/mi3272")
public class MI3272Controller {
    private final MI3272ReportGenerator mi3272ReportGenerator;

    @GetMapping
    public String getMI3272Page(ModelMap modelMap){
        List<ReportData> reportDataList = mi3272ReportGenerator.generateMI3272Report();
        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
        modelMap.put("printSaveButtonsRequired", true);
        return "report_pages/MI3272-report-page";
    }

    @ResponseBody
    @GetMapping("/save")
    public String saveMI3272Data() {
        return mi3272ReportGenerator.saveInDb();
    }
}
