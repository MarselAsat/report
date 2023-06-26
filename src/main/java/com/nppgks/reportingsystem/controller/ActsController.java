package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.service.dbservices.ReportDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/act")
public class ActsController {

    private final ReportDataService reportDataService;

    @GetMapping("/actOfAcceptanceOil/generate")
    public String getActOfAcceptance(ModelMap modelMap) {
//        List<ReportData> reportDataList = MI3622Service.calcMI3622();
//        reportDataList.forEach(td -> {
//            Object value = ArrayParser.fromJsonToObject(td.getData());
//            modelMap.put(
//                    td.getTag().getPermanentName(), value);
//        });
        return "report_pages/MI3622-report-page";
    }
}
