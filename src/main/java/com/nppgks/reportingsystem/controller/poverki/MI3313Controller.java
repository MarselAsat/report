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
        reportGenerator.generateReport();
//        modelMap.put("esrm_sensor_type_k", new String[]{"one type", "two type", "three type"});
//        modelMap.put("esrm_sensor_number", new String[]{"one num", "two num", "three num"});
//        modelMap.put("esrm_converter_type_k", new String[]{"one type", "two type", "three type"});
//        modelMap.put("esrm_converter_number_k", new String[]{"one type", "two type", "three type"});
//        modelMap.put("Q_jik", List.of(new double[][]{{1, 7},{2, 8}, {3, 9}}, new double[][]{{4, 10}, {5, 11}, {6, 12}}));
//        modelMap.put("N_ejik", List.of(new double[][]{{1, 7},{2, 8}, {3, 9}}, new double[][]{{4, 10}, {5, 11}, {6, 12}}));
//        modelMap.put("M_ejik", List.of(new double[][]{{1, 7},{2, 8}, {3, 9}}, new double[][]{{4, 10}, {5, 11}, {6, 12}}));
//        modelMap.put("K_PMEk", new double[]{99, 88});
//        modelMap.put("Q_ji", new double[][]{{1, 2, 3}, {4, 5, 6}});
//        modelMap.put("T_ji", new double[][]{{1, 2, 3}, {4, 5, 6}});
//        modelMap.put("N_ji", new double[][]{{1, 2, 3}, {4, 5, 6}});
//        modelMap.put("MF_ji", new double[][]{{1, 2, 3}, {4, 5, 6}});
//        modelMap.put("Q_j", new double[]{1, 2, 3});
//        modelMap.put("MF_j", new double[]{1, 2, 3});
//        modelMap.put("S_j", new double[]{1, 2, 3});
//        modelMap.put("S_0j", new double[]{1, 2, 3});
//        modelMap.put("epsilon_j", new double[]{1, 2, 3});
//        modelMap.put("Q_max", 3);
//        modelMap.put("MF", 3);
//        modelMap.put("S_0", 3);
//        modelMap.put("t_P", 3);
//        modelMap.put("P_P", 3);


        // Этот параметр нужен для сохранения отчета
        // Этот параметр используется в обработчике нажатия на кнопку "Сохранить в БД" в save-report-in-db.js
        modelMap.put("saveUrl", "/mi3313/save");

        // Этот параметр нужен для отображения кнопок "Сохранить в БД" и "Печать" после генерации отчета
        modelMap.put("printSaveButtonsRequired", true);

        return "report_pages/poverki/MI3313-many-esrm";
    }

    @GetMapping
    public String generateMI3313ReportPage(ModelMap modelMap){
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
    public String saveMI3272Data() {
        return reportGenerator.saveReportInDb();
    }
}
