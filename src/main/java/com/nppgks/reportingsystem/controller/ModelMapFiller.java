package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.constants.Regexes;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.util.ArrayParser;
import org.springframework.ui.ModelMap;

import java.util.List;

public class ModelMapFiller {

    public static void fillForAcceptanceAct(ModelMap modelMap, List<ReportData> reportDataList){
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

                switch (repData.getTag().getPermanentName()) {
                    case "accAct_grossOilVol_shiftn" -> oilVolSum = sumValuesInList(values);
                    case "accAct_grossOilMass_shiftn" -> oilMassSum = sumValuesInList(values);
                    case "accAct_ballastMass_shiftn" -> ballastMassSum = sumValuesInList(values);
                    case "accAct_oilNetMass_shiftn" -> oilNetMassSum = sumValuesInList(values);
                }
            }
            modelMap.put("oilVolSum", oilVolSum);
            modelMap.put("oilMassSum", oilMassSum);
            modelMap.put("ballastMassSum", ballastMassSum);
            modelMap.put("oilNetMassSum", oilNetMassSum);

            modelMap.put(repData.getTag().getPermanentName(), value);
        }
        modelMap.put("colNum", colNum);
    }

    private static double sumValuesInList(List<String> values){
        return values.stream()
                .mapToDouble(Double::parseDouble)
                .sum();
    }

    public static void fillForMI3272(ModelMap modelMap, List<ReportData> reportDataList, boolean usedTpr){
        reportDataList.forEach(rd -> {
            String json = rd.getData();
            String separator = "#";
            if(json.contains(separator)){
                json = json.split(separator)[0];
            }
            Object value = ArrayParser.fromJsonToObject(json);
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
        modelMap.put("usedTPR", usedTpr);
    }
    public static void fillForMI3272(ModelMap modelMap, List<ReportData> reportDataList){
        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(rd.getTag().getPermanentName(), value);
        });
        modelMap.put("usedTPR", "false");
    }
}
