package com.nppgks.reportingsystem.reportgeneration.manual_reports;

import com.nppgks.reportingsystem.constants.Regexes;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.exception.MissingOpcTagException;
import com.nppgks.reportingsystem.exception.NotValidTagValueException;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622.calculations.MI3622Calculation;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622.calculations.MI3622FinalData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static com.nppgks.reportingsystem.util.ArrayParser.*;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@Slf4j
public class DataConverter {
    private static final String CALCULATE_METHOD_PREFIX = "calculate";

    /**
     * Возвращается объект FinalData, поля которого высчитываются по формулам из MI3622Calculation
     */
    public static MI3622FinalData calculateMI3622FinalData(MI3622Calculation calc) {
        MI3622FinalData MI3622FinalData = new MI3622FinalData();
        Field[] finalDataFields = MI3622FinalData.class.getDeclaredFields();
        Class<? extends MI3622Calculation> calcClass = calc.getClass();
        for (Field field : finalDataFields) {
            try {
                field.setAccessible(true);
                String methodName = CALCULATE_METHOD_PREFIX + StringUtils.capitalize(field.getName());
                var value = calcClass.getMethod(methodName).invoke(calc);
                field.set(MI3622FinalData, value);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return MI3622FinalData;
    }

    /**
     * Конвертирует объект List<ManualTagForOpc> в List<String>,
     * где хранятся только адреса тегов - manualTagForOpc.address
     */
    public static List<String> convertTagsToListOfAddresses(List<ManualTagForOpc> tags){
        return tags
                .stream()
                .map(ManualTagForOpc::address)
                .toList();
    }

    /**
     * конвертация списка объектов ManualTagForOpc (id, address, permanentName)
     * в map (key = ManualTagForOpc.address, value = manualTagForOpc)
     */
    public static Map<String, ManualTagForOpc> convertTagListToMapWithAddressKey(List<ManualTagForOpc> tags) {
        return tags.stream()
                .map(t -> Map.entry(t.address(), t))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1));
    }

    /**
     * конвертация списка объектов ManualTagForOpc (id, address, permanentName)
     * в map (key = ManualTagForOpc.permanentName, value = ManualTagForOpc.address)
     */
    public static Map<String, String> createPermanentNameToAddressMap(List<ManualTagForOpc> tagsForOpc) {
        return tagsForOpc.stream()
                .collect(Collectors.toMap(ManualTagForOpc::permanentName, ManualTagForOpc::address));
    }
    /**
     * Возвращается объект Map<String, Object>, построенный на основе значений полей finalData.
     * Каждому полю в FinalData соответствует имя тега, которое знает OPC.
     * Соответствия имен тегов и имен полей FinalData содержатся в параметре tagsMap.
     * Ключ в map это имя тега из OPC, а значение это значение этого тега,
     * которое берется из соответствующего поля finalData
     */
    public static <F> Map<String, Object> convertFinalDataToMap(F finalData, Map<String, String> tagsMap, boolean transposeArr) {

        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = finalData.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            try {
                declaredField.setAccessible(true);
                Object data = declaredField.get(finalData);
                if (data != null) {
                    if (declaredField.getType().equals(double[][].class) && transposeArr) {
                        data = transpose2DimArray((double[][]) data);
                    }
                    String tag = tagsMap.get(declaredField.getName());
                    if (tag == null) {
                        log.error("Не существует тега с закрепленным именем {} (initial = FALSE) в таблице manual_reports.tag", declaredField.getName());
                    }
                    map.put(tagsMap.get(declaredField.getName()), data);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return map;
    }

    /**
     * Возвращается объект InitialData, значения которого берутся из dataFromOpc.
     * В dataFromOpc ключ - это адрес тега, а значение - это значение тега.
     * В tagsMap содержатся соответствия адреса тега и имени полей в InitialData.
     * C использованием tagsMap и dataFromOpc заполняются поля InitialData.
     */
    public static <I> I convertMapToInitialData(Map<String, String> dataFromOpc, Map<String, String> tagsMap, Class<I> type, boolean transposeArr) {
        I initialData;
        try {
            initialData = type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        Field[] declaredFields = type.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            try {
                declaredField.setAccessible(true);
                String tag = tagsMap.get(declaredField.getName());
                if (tag != null) {
                    String value = dataFromOpc.get(tagsMap.get(declaredField.getName()));

                    if (value != null && !value.isBlank()) {
                        Class<?> fieldType = declaredField.getType();

                        if(fieldType.equals(List.class)){
                            declaredField.set(initialData, toListOfArrays(value));
                        }
                        else if (fieldType.equals(double[][].class)) {
                            // из OPC сервера значения идут в таком виде: каждая строка(!) это множество измерений в 1 точке
                            // а формулы для вычислений предполагают, что каждый столбец(!) это множество измерений в 1 точке
                            // поэтому, нужно транспонировать двумерный массив
                            double[][] array2Dim;
                            try {
                                if(transposeArr){
                                    array2Dim = transpose2DimArray(to2DArray(value));
                                }
                                else{
                                    array2Dim = to2DArray(value);
                                }
                            } catch (Exception e) {
                                String message = """
                                        Произошла ошибка во время обработки массива %s.
                                        Проверьте, что массив соответствует одному из шаблонов
                                        [a1, a2, a3, ...] или [[a11, a12, a13, ...],[a21, a22, a23, ...], ...]
                                        и что его длина соответствует количеству точек и измерений
                                        """.formatted(value);
                                throw new NotValidTagValueException(message, e);
                            }
                            declaredField.set(initialData, array2Dim);
                        } else if (fieldType.equals(double[].class)) {
                            declaredField.set(initialData, toArray(value));
                        } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                            declaredField.set(initialData, parseInt(value));
                        } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
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
                    log.error("В переданной tagsMap не нашлось соответствия с полем {} в InitialData ", declaredField.getName());
                    log.error("Возможно, в таблице manual_reports.tag нет нужной записи с permanent_name = {} и initial = true", declaredField.getName());
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return initialData;
    }

    /**
     * Двумерные массивы из OPC могут приходить в виде одномерного массива: [x, x, x, x, ...]
     * Этот метод меняет вид двумерных массивов (value из dataFromOpc) на [[x, x, ...],[x, x, ...]...]
     */
    public static <I> void putInOrder2DArraysInOpcData(Map<String, String> dataFromOpc, Map<String, String> initialTagsMap, Class<I> initialDataType) {
        String pointsCountStr = dataFromOpc.get(initialTagsMap.get("pointsCount"));
        String measureCountStr = dataFromOpc.get(initialTagsMap.get("measureCount"));
        if (pointsCountStr == null || measureCountStr == null) {
            throw new MissingOpcTagException("Теги pointsCount и/или measureCount не инициализированы, либо не существуют на OPC сервере");
        }
        int pointsCount = parseInt(pointsCountStr);
        int measureCount = parseInt(measureCountStr);

        Field[] initialDataFields = initialDataType.getDeclaredFields();
        for (Map.Entry<String, String> entry : dataFromOpc.entrySet()) {
            String value = entry.getValue();

            String permanentTagName = initialTagsMap
                    .entrySet()
                    .stream()
                    .filter(en -> en.getValue().equals(entry.getKey()))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElseThrow();
            Optional<Field> dfOptional = Arrays.stream(initialDataFields)
                    .filter(df -> df.getName().equals(permanentTagName))
                    .findFirst();
            Class<?> dfType;
            if (dfOptional.isPresent()) {
                dfType = dfOptional.get().getType();
            } else {
                continue;
            }
            if (value.matches(Regexes.ARRAY_REGEX) && !value.matches(Regexes.ARRAY_2DIM_REGEX) && dfType.equals(double[][].class)) {
                double[] valueArr = toArray(value);
                if (valueArr.length == pointsCount * measureCount) {
                    double[][] array2D = fromArrayTo2DArray(valueArr, pointsCount, measureCount);
                    String newValue = fromObjectToJson(array2D);
                    dataFromOpc.put(entry.getKey(), newValue);
                } else {
                    throw new NotValidTagValueException("Длина массива %s должна быть равна pointsCount*measureCount = %s"
                            .formatted(value, pointsCount * measureCount));
                }
            }
        }
    }

    public static double[][] transpose2DimArray(double[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        double[][] transposeMatrix = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                transposeMatrix[i][j] = matrix[j][i];
            }
        }
        return transposeMatrix;
    }

    public static Object[][][] rearrangeListOfArrays(List<List<List<Object>>> array){
        int n = array.size();
        int m = array.get(0).size();
        int l = array.get(0).get(0).size();
        Object[][][] newArray = new Object[m][l][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                for(int k=0; k<l;k++){
                    newArray[j][k][i] = array.get(i).get(j).get(k);
                }
            }
        }
        return newArray;
    }

}
