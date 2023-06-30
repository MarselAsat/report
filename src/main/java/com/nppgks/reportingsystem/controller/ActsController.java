package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.constants.Regexes;
import com.nppgks.reportingsystem.db.calculations.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.acts.AcceptanceActGenerator;
import com.nppgks.reportingsystem.util.ArrayParser;
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
    public String getActOfAcceptance(ModelMap modelMap) {
        List<ReportData> reportDataList = acceptanceActGenerator.generateActOfAcceptance();
        int colNum = 1;
        Double oilVolSum = null;
        Double oilMassSum = null;
        Double ballastMassSum = null;
        Double oilNetMassSum = null;
        for (ReportData repData : reportDataList) {
            Object value = ArrayParser.fromJsonToObject(repData.getData());
            if (value != null && value.toString().matches(Regexes.ARRAY_REGEX)) {
                List<String> values = (List<String>) value;
                int valuesNum = values.size();
                if (valuesNum > colNum) colNum = valuesNum;

                double sum = values.stream()
                        .mapToDouble(Double::parseDouble)
                        .sum();
                switch (repData.getTag().getPermanentName()) {
                    case "accAct_grossOilVol_shiftn" -> oilVolSum = sum;
                    case "accAct_grossOilMass_shiftn" -> oilMassSum = sum;
                    case "accAct_ballastMass_shiftn" -> ballastMassSum = sum;
                    case "accAct_oilNetMass_shiftn" -> oilNetMassSum = sum;
                }
            }
            modelMap.put("oilVolSum", oilVolSum);
            modelMap.put("oilMassSum", oilMassSum);
            modelMap.put("ballastMassSum", ballastMassSum);
            modelMap.put("oilNetMassSum", oilNetMassSum);

            modelMap.put(repData.getTag().getPermanentName(), value);
        }
        modelMap.put("colNum", colNum);

        return "report_pages/acceptance-oil-act";
    }

    @ResponseBody
    @GetMapping("/acceptanceOilAct/save")
    public String saveAcceptanceActData(@RequestParam List<String> dtStartShift, @RequestParam List<String> dtEndShift) {
        acceptanceActGenerator.updateShiftsDateTimeInReportData(dtStartShift, dtEndShift);
        return acceptanceActGenerator.saveInDb();
    }
}
