package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.acts_passport.AcceptanceActGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/act")
public class ActsController {

    private final AcceptanceActGenerator actReportGenerator;

    @GetMapping("/acceptanceOilAct")
    public String getAcceptanceAct(ModelMap modelMap) {
        List<ReportData> reportDataList = actReportGenerator.generateReport();
        ModelMapFiller.fillForAcceptanceAct(modelMap, reportDataList);

        // Этот параметр нужен для сохранения отчета
        // Этот параметр используется в обработчике нажатия на кнопку "Сохранить в БД" в acceptance-oil-act.js
        modelMap.put("saveUrl", "/act/acceptanceOilAct/save");

        // Этот параметр нужен для отображения кнопок "Сохранить в БД" и "Печать" после генерации отчета
        modelMap.put("printSaveButtonsRequired", true);
        return "report_pages/acts/acceptance-oil-act";
    }

    @ResponseBody
    @GetMapping("/acceptanceOilAct/save")
    public String saveAcceptanceActData(@RequestParam List<String> dtStartShift, @RequestParam List<String> dtEndShift) {
        actReportGenerator.updateShiftsDateTimeInReportData(dtStartShift, dtEndShift);
        return actReportGenerator.saveReportInDb();
    }
}
