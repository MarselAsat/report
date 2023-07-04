package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.db.calculations.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.acts.AcceptanceActGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/act")
public class ActsController {

    private final AcceptanceActGenerator acceptanceActGenerator;

    @GetMapping("/acceptanceOilAct/generate")
    public String getAcceptanceAct(ModelMap modelMap) {
        List<ReportData> reportDataList = acceptanceActGenerator.generateActOfAcceptance();
        ModelMapFiller.fillForAcceptanceAct(modelMap, reportDataList);
        modelMap.put("printSaveButtonsRequired", true);
        return "report_pages/acceptance-oil-act";
    }

    @ResponseBody
    @GetMapping("/acceptanceOilAct/save")
    public String saveAcceptanceActData(@RequestParam List<String> dtStartShift, @RequestParam List<String> dtEndShift) {
        acceptanceActGenerator.updateShiftsDateTimeInReportData(dtStartShift, dtEndShift);
        return acceptanceActGenerator.saveInDb();
    }
}
