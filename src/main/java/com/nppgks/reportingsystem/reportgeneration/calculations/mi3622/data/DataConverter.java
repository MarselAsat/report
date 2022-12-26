package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data;

import com.nppgks.reportingsystem.exception.MissingDbDataException;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.MI3622Calculation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.nppgks.reportingsystem.opc.ArrayParser.*;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@Slf4j
public class DataConverter {

    private static final String ARRAY_REGEX = "\\[.*]";

    private static final String CALCULATE_METHOD_PREFIX = "calculate";


    public static void setCalcFinalDataFields(FinalData finalData, MI3622Calculation calc){
        Field[] declaredFields = FinalData.class.getDeclaredFields();
        Class<? extends MI3622Calculation> calcClass = calc.getClass();
        for(Field field : declaredFields){
            try{
                field.setAccessible(true);
                String methodName = CALCULATE_METHOD_PREFIX + StringUtils.capitalize(field.getName());
                var value = calcClass.getMethod(methodName).invoke(calc);
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
                    String tagName = tagNamesMap.get(declaredField.getName());
                    if(tagName==null){
                        throw new MissingDbDataException("There is no "+declaredField.getName()+" (initial = FALSE, type = MI_3622) tag in calculations.tag_name table");
                    }
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

        for (Field declaredField : declaredFields) {
            try {
                declaredField.setAccessible(true);
                String tagName = tagNamesMap.get(declaredField.getName());
                if (tagName != null) {
                    String value = dataFromOpc.get(tagNamesMap.get(declaredField.getName()));

                    if (value != null && !value.isBlank()) {
                        Class<?> fieldType = declaredField.getType();

                        if (fieldType.equals(double[][].class)) {
                            declaredField.set(initialData, to2DArray(value));
                        } else if (fieldType.equals(double[].class)) {
                            declaredField.set(initialData, toArray(value));
                        } else if (fieldType.equals(int.class)) {
                            declaredField.set(initialData, parseInt(value));
                        } else if (fieldType.equals(double.class)) {
                            declaredField.set(initialData, parseDouble(value));
                        } else if (fieldType.equals(boolean.class)) {
                            declaredField.set(initialData, parseBoolean(value));
                        } else if (fieldType.equals(String.class)) {
                            declaredField.set(initialData, value);
                        }
                    } else {
                        log.warn("В полученных данных от OPC нет имя тега {}", declaredField.getName());
                    }

                } else {
                    log.error("В переданной tagNamesMap не нашлось соответствия с полем {} в InitialData ", declaredField.getName());
                    log.error("Возможно, в таблице calculations.tag_name нет нужной записи с permanent_name = {} и initial = true", declaredField.getName());
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return initialData;
    }

    public static void putInOrder2DArraysInOpcData(Map<String, String> dataFromOpc, Map<String, String> tagNamesMap) {
        int pointsCount = parseInt(dataFromOpc.get(tagNamesMap.get("pointsCount")));
        int measureCount = parseInt(dataFromOpc.get(tagNamesMap.get("measureCount")));
        for(Map.Entry<String, String> entry: dataFromOpc.entrySet()){
            String value = entry.getValue();
            if(value.matches(ARRAY_REGEX)){
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
