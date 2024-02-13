package com.nppgks.reportingsystem.controller.poverki;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313.MI3313ReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313.MI3313Type;
import com.nppgks.reportingsystem.util.ArrayParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mi3313")
public class MI3313Controller {

    private final MI3313ReportGenerator reportGenerator;

    public MI3313Controller(MI3313ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @GetMapping("/multipleEsrm")
    public String generateMI3313ManyEsrm(ModelMap modelMap){
        reportGenerator.setReportType(MI3313Type.MULTIPLE_ESRMS);
        List<ReportData> reportDataList = reportGenerator.generateReport();
        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            if(!modelMap.containsKey("n") && value instanceof ArrayList<?>){
                int n = ((ArrayList<?>)(((ArrayList<?>) value).get(0))).size();
                modelMap.put("n", n);
            }
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });

        // Этот параметр нужен для сохранения отчета
        // Этот параметр используется в обработчике нажатия на кнопку "Сохранить в БД" в save-report-in-db.js
        modelMap.put("saveUrl", "/mi3313/save");

        // Этот параметр нужен для отображения кнопок "Сохранить в БД" и "Печать" после генерации отчета
        modelMap.put("printSaveButtonsRequired", true);

        return "report_pages/poverki/MI3313-many-esrm";
    }

    @GetMapping
    public String generateMI3313ReportPage(ModelMap modelMap){
        reportGenerator.setReportType(MI3313Type.ONE_ESRM);
        List<ReportData> reportDataList = reportGenerator.generateReport();
        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            if(!modelMap.containsKey("n") && value instanceof ArrayList<?>){
                int n = ((ArrayList<?>)(((ArrayList<?>) value).get(0))).size();
                modelMap.put("n", n);
            }
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });

        // Этот параметр нужен для сохранения отчета
        // Этот параметр используется в обработчике нажатия на кнопку "Сохранить в БД" в save-report-in-db.js
        modelMap.put("saveUrl", "/mi3313/save");

        // Этот параметр нужен для отображения кнопок "Сохранить в БД" и "Печать" после генерации отчета
        modelMap.put("printSaveButtonsRequired", true);

        return "report_pages/poverki/MI3313-one-esrm";
    }

    @ResponseBody
    @GetMapping("/save")
    public String saveMI3313Data() {
        return reportGenerator.saveReportInDb();
    }
}
