package com.nppgks.reports.service.poverka3622.data;

import com.nppgks.reports.service.poverka3622.Poverka3622;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.nppgks.reports.opc.ArrayParser.*;
import static java.lang.Double.parseDouble;

@Slf4j
public class DataConverter {

    public static void setFinalDataFieldsFromPoverka(FinalData finalData, Poverka3622 poverka){
        Field[] declaredFields = FinalData.class.getDeclaredFields();
        Class<? extends Poverka3622> poverkaClass = poverka.getClass();
        for(Field field : declaredFields){
            try{
                field.setAccessible(true);
                String methodName = "calculate"+ StringUtils.capitalize(field.getName());
                var value = poverkaClass.getMethod(methodName).invoke(poverka);
                field.set(finalData, value);
            }
            catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
                throw new RuntimeException(e);
            }
        }
    }

    public static Map<String, Object> convertFinalDataToMap(FinalData finalData, Map<String, String> tagNamesMap){

        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = FinalData.class.getDeclaredFields();

        for(Field declaredField : declaredFields){
            try {
                declaredField.setAccessible(true);
                if(declaredField.get(finalData) != null){
                    map.put(tagNamesMap.get(declaredField.getName()), declaredField.get(finalData));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return map;
    }

    public static InitialData convertMapToInitialData(Map<String, String> dataFromOpc, Map<String, String> tagNamesMap){
        InitialData initialData = new InitialData();

        Field[] declaredFields = InitialData.class.getDeclaredFields();

        for(Field declaredField : declaredFields){
            try {
                declaredField.setAccessible(true);
                String tagName = tagNamesMap.get(declaredField.getName());
                if(tagName!=null){
                    String value = dataFromOpc.get(tagNamesMap.get(declaredField.getName()));
                    if(value != null){
                        if(value.matches("\\[\\[.*")){
                            declaredField.set(initialData, to2DArray(value));
                        }
                        else if(value.matches("\\[.*")){
                            declaredField.set(initialData, toArray(value));
                        }
                        else{
                            if(declaredField.getType().equals(int.class)){
                                declaredField.set(initialData, Integer.parseInt(value));
                            }
                            else{
                                declaredField.set(initialData, parseDouble(value));
                            }
                        }
                    }
                    else{
                        log.warn("В полученных данных от OPC нет имя тега {}", declaredField.getName());
                    }

                }
                else{
                    log.error("В переданной tagNamesMap не нашлось соответствия с полем {} в InitialData ", declaredField.getName());
                    log.error("Возможно, в таблице manual_tag_name нет нужной записи с permanent_name = {} и initial = true", declaredField.getName());
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return initialData;
    }

    public static void putInOrder2DArraysInOpcData(Map<String, String> dataFromOpc, Map<String, String> tagNamesMap) {
        int pointsCount = Integer.parseInt(dataFromOpc.get(tagNamesMap.get("pointsCount")));
        int measureCount = Integer.parseInt(dataFromOpc.get(tagNamesMap.get("measureCount")));
        for(Map.Entry<String, String> entry: dataFromOpc.entrySet()){
            String value = entry.getValue();
            if(value.matches("\\[.*")){
                double[] valueArr = toArray(value);
                if(valueArr.length == pointsCount*measureCount){
                    double[][] array2D = fromArrayTo2DArray(valueArr, pointsCount, measureCount);
                    String newValue = fromObjectToJson(array2D);
                    dataFromOpc.put(entry.getKey(), newValue);
                }
            }
        }
    }
}
