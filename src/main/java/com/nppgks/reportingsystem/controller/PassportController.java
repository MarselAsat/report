package com.nppgks.reportingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/passport")
public class PassportController {
    @GetMapping("/oilQuality")
    public String getOilQualityPassportPage(ModelMap modelMap){
        modelMap.put("passportNumber", "139/2");
        modelMap.put("date", "23.05.2019");
        modelMap.put("oilAcceptancePoint", "ПСП \"Марковское\" ООО \"ИНК\"");
        modelMap.put("labOrg", "ООО \"ИНК\"");
        modelMap.put("accreditationNumber", "№ RA.RU.518198");
        modelMap.put("SIKN", "№ 1516");
        modelMap.put("dateTimeSampling", "23.05.2019 12:00 − 24:00");

        modelMap.put("tempMethod", "МИ ФР.1.29.2016.24410");
        modelMap.put("tempResult", "17,5");

        modelMap.put("pressureMethod", "МИ ФР.1.29.2016.24410");
        modelMap.put("pressureResult", "3,72");

        modelMap.put("initialDensityMethod", "МИ ФР.1.29.2016.24410");
        modelMap.put("initialDensityResult", "821,1");

        modelMap.put("density20Method", "МИ ФР.1.29.2016.24410");
        modelMap.put("density20Result", "816,7");

        modelMap.put("density15Method", "МИ ФР.1.29.2016.24410");
        modelMap.put("density15Result", "820,5");

        modelMap.put("waterFractionMethod", "ГОСТ 2477");
        modelMap.put("waterFractionResult", "0,03");

        modelMap.put("chlorideSaltFractionMethod", "ГОСТ 21534 (Метод А)");
        modelMap.put("chlorideSaltFractionResult", "30,2 (0,0037)");

        modelMap.put("admixtureFractionMethod", "ГОСТ 6370");
        modelMap.put("admixtureFractionResult", "0,0217");

        modelMap.put("sulfurFractionMethod", "ГОСТ Р 51947");
        modelMap.put("sulfurFractionResult", "0,19");

        modelMap.put("steamPressureMethod", "ГОСТ 1756");
        modelMap.put("steamPressureResult", "63,0(473)");

        modelMap.put("fractionOutMethod", "ГОСТ 2177 (Метод Б)");
        modelMap.put("fractionOut200Result", "29,0");
        modelMap.put("fractionOut300Result", "49,0");

        modelMap.put("paraffinFractionMethod", "ГОСТ 11851 (Метод А)");
        modelMap.put("paraffinFractionResult", "1,3");

        modelMap.put("hydroSulfideFractionMethod", "ГОСТ Р 50802");
        modelMap.put("hydroSulfideFractionResult", "менее 2,0");

        modelMap.put("methFractionMethod", "ГОСТ Р 50802");
        modelMap.put("methFractionResult", "16,1");

        modelMap.put("organicFractionMethod", "ГОСТ Р 52247 (Метод А)");
        modelMap.put("organicFractionResult", "менее 1,0");

        modelMap.put("GOST", "1.1э.1.1");
        modelMap.put("labName", "И.Н. Бирюкова");
        modelMap.put("deliverPosition", "оператор товарный");
        modelMap.put("deliverOrg", "ООО \"ИНК\"");
        modelMap.put("deliverName", "Е.А. Романов");
        modelMap.put("receiverPosition", "оператор товарный");
        modelMap.put("receiverOrg", "ООО \"Транснефть-Восток\"");
        modelMap.put("receiverName", "Т.А. Данькина");

        // Этот параметр нужен для отображения кнопок "Сохранить в БД" и "Печать" после генерации отчета
        modelMap.put("printSaveButtonsRequired", true);

        return "report_pages/acts/oil-quality-passport";
    }
}
