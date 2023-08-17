package com.nppgks.reportingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kmh")
public class KMHController {

    @GetMapping("/viscometer")
    public String getKmhViscometerPage(ModelMap modelMap){
        modelMap.put("protocolNumber", "018-19");
        modelMap.put("siknNumber", "1516");
        modelMap.put("place_name", "ПСП Марковское ООО «ИНК»");
        modelMap.put("transmitter_type", "Solartron 7829");
        modelMap.put("viscometer_type", "ВНЖ");
        modelMap.put("transmitter_number", "14462515");
        modelMap.put("viscometer_number", "32");
        modelMap.put("transmitter_date", "12.02.2018");
        modelMap.put("viscometer_date", "25.03.2015");
        modelMap.put("delta_v_PVz", "0.2");
        modelMap.put("delta_v_il", "0.7");
        modelMap.put("t_bik", new double[]{15.0, 15.0, 15.0});
        modelMap.put("ro_bik", new double[]{824.7, 824.7, 824.7});
        modelMap.put("eta_PVz", new double[]{6.08, 6.08, 6.08});
        modelMap.put("v_PVz", new double[]{7.36, 7.36, 7.363});
        modelMap.put("v_il", new double[]{7.34, 7.37, 7.306});
        modelMap.put("v_PVz_minus_v_il", new double[]{0.02, 0.01, 0.057});
        modelMap.put("conclusion", "годен");
        modelMap.put("kmh_date", "06.02.2019");
        modelMap.put("deliverOrg", "ООО \"ИНК\"");
        modelMap.put("metrologistName", "Титов А.В.");
        modelMap.put("chemistName", "Рабинович Ю.Д.");
        modelMap.put("receiverOrg", "ООО \"Транснефть-Восток\"");
        modelMap.put("receiverPosition", "Мастер ПСП НПС-7");
        modelMap.put("receiverName", "Ермолаев К.В.");
        modelMap.put("serviceOrg", "ЗАО НИЦ \"Инкомсистем\"");
        modelMap.put("servicePosition", "Инженер ТО и С");
        modelMap.put("serviceName", "Рахматуллин Р.Ф.");
        modelMap.put("printSaveButtonsRequired", true);
        return "report_pages/KMHViscometer-report-page.html";
    }
}
