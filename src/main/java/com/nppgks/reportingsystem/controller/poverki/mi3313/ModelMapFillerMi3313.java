package com.nppgks.reportingsystem.controller.poverki.mi3313;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.DataConverter;
import com.nppgks.reportingsystem.util.ArrayParser;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

public class ModelMapFillerMi3313 {

    public static void fillForMI3313ManyEsrm(ModelMap modelMap, List<ReportData> reportDataList){
        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            if(value instanceof List<?>){
                try {
                    List<List<List<Object>>> arr3D = (List<List<List<Object>>>) value;
                    modelMap.put(rd.getTag().getPermanentName(), DataConverter.rearrangeListOfArrays(arr3D));
                    if(!modelMap.containsKey("n")){
                        int n = arr3D.get(0).get(0).size();
                        modelMap.put("n", n);
                    }
                }
                catch(Exception e){
                    modelMap.put(rd.getTag().getPermanentName(), value);
                }
            }
            else{
                modelMap.put(rd.getTag().getPermanentName(), value);
            }
        });
    }

    public static void fillForMI3313OneEsrm(ModelMap modelMap, List<ReportData> reportDataList){
        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            if(!modelMap.containsKey("n") && value instanceof ArrayList<?>){
                int n = ((ArrayList<?>)(((ArrayList<?>) value).get(0))).size();
                modelMap.put("n", n);
            }
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
    }
}
