package com.nppgks.reportingsystem.controller.poverki;

import com.nppgks.reportingsystem.controller.ModelMapFiller;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.MI3272ReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.MI3272WithTprReportGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/mi3272")
public class MI3272Controller {

    private final MI3272WithTprReportGenerator reportGeneratorWithTpr;
    private final MI3272ReportGenerator reportGenerator;

    public MI3272Controller(MI3272WithTprReportGenerator reportGeneratorWithTpr, MI3272ReportGenerator reportGenerator) {
        this.reportGeneratorWithTpr = reportGeneratorWithTpr;
        this.reportGenerator = reportGenerator;
    }

    @GetMapping("/tprCoeff")
    @ResponseBody
    public String calculateTprCoeff(){
        return "Коэффициент преобразования ТПР расчитан: %s"
                .formatted(reportGeneratorWithTpr.generateTprCoeff());
    }

    @GetMapping
    public String getMI3272Page(ModelMap modelMap, @RequestParam boolean usedTpr){
        List<ReportData> reportDataList;
        if(usedTpr){
            reportDataList = reportGeneratorWithTpr.generateReport();
        }
        else{
            reportDataList = reportGenerator.generateReport();
        }

        ModelMapFiller.fillForMI3272(modelMap, reportDataList, usedTpr);

        // Этот параметр нужен для сохранения отчета
        // Этот параметр используется в обработчике нажатия на кнопку "Сохранить в БД" в save-report-in-db.js
        modelMap.put("saveUrl", "/mi3272/save?usedTpr="+usedTpr);

        // Этот параметр нужен для отображения кнопок "Сохранить в БД" и "Печать" после генерации отчета
        modelMap.put("printSaveButtonsRequired", true);

        return "report_pages/poverki/MI3272-report-page";
    }

    @ResponseBody
    @GetMapping("/save")
    public String saveMI3272Data(@RequestParam boolean usedTpr) {
        if(usedTpr){
            return reportGeneratorWithTpr.saveReportInDb();
        }
        else{
            return reportGenerator.saveReportInDb();
        }
    }
}
