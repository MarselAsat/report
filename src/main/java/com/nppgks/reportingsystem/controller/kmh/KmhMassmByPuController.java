package com.nppgks.reportingsystem.controller.kmh;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.ManualReportGenerator;
import com.nppgks.reportingsystem.util.ArrayParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/kmh")
public class KmhMassmByPuController {
    private final ManualReportGenerator reportGenerator;

    public KmhMassmByPuController(@Qualifier("kmhMassmByPuReportGenerator") ManualReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @GetMapping("/massmByPu")
    public String generateKmhMassmByPuReport(ModelMap modelMap){
        List<ReportData> reportDataList = reportGenerator.generateReport();
        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
        modelMap.put("printSaveButtonsRequired", true);

//        modelMap.put("deliver_org", "ООО \"ИНК\"");
//        modelMap.put("siknNumber", "1516");
//        modelMap.put("protocolNumber", "052-19");
//        modelMap.put("il_number", "1");
//        modelMap.put("massmeterModel", "CMFHC2");
//        modelMap.put("place_name", "ПСП \"Марковское\"");
//        modelMap.put("place_owner", "ООО \"Иркутская нефтяная компания\"");
//        modelMap.put("massmeterSensor", "CMFHC2");
//        modelMap.put("massmeterDu", "200");
//        modelMap.put("massmeterNumber", "12110976");
//        modelMap.put("pepModel", "2700R");
//        modelMap.put("pepNumber", "3847397");
//        modelMap.put("installedOn", "СИКН");
//        modelMap.put("ilNumber", "1 контр-рез");
//        modelMap.put("cpType", "поверочная установка CP");
//        modelMap.put("cpGrade", "первый");
//        modelMap.put("cpNumber", "1505-10020479-1.1-1");
//        modelMap.put("cpDate", "03.10.2015");
//        modelMap.put("tprType", "HELIFLU TZN 200-800");
//        modelMap.put("tprRange", "80-800");
//        modelMap.put("tprNumber", "9022106");
//        modelMap.put("ppType", "7835B");
//        modelMap.put("ppNumber", "8000596");
//        modelMap.put("ppDate", "09.10.2015");
//
//        modelMap.put("V_KP_0", "0.119454");
//        modelMap.put("delta_KP", "0.05");
//        modelMap.put("D", "444.5");
//        modelMap.put("E", "196500");
//        modelMap.put("s", "31.75");
//        modelMap.put("delta_t_KP", "0.2");
//        modelMap.put("delta_PP", "0.03");
//        modelMap.put("delta_t_PP", "0.2");
//        modelMap.put("delta_UOI_K", "0.001");
//        modelMap.put("KF_conf", "65454.5");
//        modelMap.put("ZS", "0.0682");
//
//        String Q_TPR_ij =
//                "[[439.667547,438.898177,439.119283,439.208443,439.057520]]";
//
//        String N_TPR_ij_avg =
//                "[[46.143475,46.141003,46.143757,46.147202,46.149117]]";
//
//        String t_TPR_ij_avg =
//                "[[16.020216,16.023512,16.025393,16.031071,16.039322]]";
//
//        String P_TPR_ij_avg =
//                "[[3.237774,3.238343,3.237746,3.237154,3.237032]]";
//
//        String t_KP_ij_avg =
//                "[[16.020216,16.023512,16.025393,16.031071,16.039322]]";
//
//        String P_KP_ij_avg =
//                "[[3.237774,3.238343,3.237746,3.237154,3.237032]]";
//
//        String t_st_ij =
//                "[[18.187569,18.187569,18.187569,18.187569,18.187569]]";
//
//        modelMap.put("Q_TPR_ij", ArrayParser.fromJsonToObject(Q_TPR_ij));
//        modelMap.put("N_TPR_ij_avg", ArrayParser.fromJsonToObject(N_TPR_ij_avg));
//        modelMap.put("t_TPR_ij_avg", ArrayParser.fromJsonToObject(t_TPR_ij_avg));
//        modelMap.put("P_TPR_ij_avg", ArrayParser.fromJsonToObject(P_TPR_ij_avg));
//        modelMap.put("t_KP_ij_avg", ArrayParser.fromJsonToObject(t_KP_ij_avg));
//        modelMap.put("P_KP_ij_avg", ArrayParser.fromJsonToObject(P_KP_ij_avg));
//        modelMap.put("t_st_ij", ArrayParser.fromJsonToObject(t_st_ij));
//
//        String Q_ij =
//                "[[419.494659, 419.543671, 419.470978]]";
//
//        String t_il =
//                "[[16.026829, 16.033859, 16.036207]]";
//
//        String P_il =
//        "[[3.157158, 3.155969, 3.154463]]";
//
//        String t_TPR =
//                "[[16.041616, 16.048931, 16.056810]]";
//
//        String P_TPR =
//                "[[3.242779, 3.241624, 3.240480]]";
//
//        String rho_TPR =
//                "[[822.680725, 822.688354, 822.695496]]";
//
//        String M_il =
//                "[[25.068800, 25.037479, 25.046595]]";
//
//        String M_TPR =
//                "[[25.050127, 25.020527, 25.031399]]";
//
//        String delta_M =
//                "[[0.074541, 0.067759, 0.060704]]";
//
//        modelMap.put("Q_ij", ArrayParser.fromJsonToObject(Q_ij));
//        modelMap.put("t_il", ArrayParser.fromJsonToObject(t_il));
//        modelMap.put("P_il", ArrayParser.fromJsonToObject(P_il));
//        modelMap.put("t_TPR", ArrayParser.fromJsonToObject(t_TPR));
//        modelMap.put("P_TPR", ArrayParser.fromJsonToObject(P_TPR));
//        modelMap.put("rho_TPR", ArrayParser.fromJsonToObject(rho_TPR));
//        modelMap.put("M_il", ArrayParser.fromJsonToObject(M_il));
//        modelMap.put("M_TPR", ArrayParser.fromJsonToObject(M_TPR));
//        modelMap.put("delta_M", ArrayParser.fromJsonToObject(delta_M));
//
//        modelMap.put("delta_max", 0.075);
//        modelMap.put("conclusion", "годен");
//        modelMap.put("deliver_name", "Данькин Н.Ф.");
//        modelMap.put("receiver_org", "ООО \"Транснефть-Восток\"");
//        modelMap.put("receiver_name", "Хабибуллин И.И.");
//        modelMap.put("service_org", "ООО \"СНГ\"");
//        modelMap.put("service_name", "Шакиров Р.З.");


        return "report_pages/kmh-massm-by-pu-report-page";
    }
}
