package com.nppgks.reports.service.poverka3622.data;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.nppgks.reports.opc.ArrayParser.to2dimArray;
import static com.nppgks.reports.opc.ArrayParser.toArray;
import static java.lang.Double.parseDouble;

@Slf4j
public class DataConverter {

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
                            declaredField.set(initialData, to2dimArray(value));
                        }
                        else if(value.matches("\\[.*")){
                            declaredField.set(initialData, toArray(value));
                        }
                        else{
                            declaredField.set(initialData, parseDouble(value));
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
}
