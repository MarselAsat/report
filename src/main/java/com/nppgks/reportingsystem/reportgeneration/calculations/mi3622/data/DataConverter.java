package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data;

import com.nppgks.reportingsystem.exception.MissingDbDataException;
import com.nppgks.reportingsystem.exception.NotValidTagDataException;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.MI3622Calculation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.nppgks.reportingsystem.util.ArrayParser.*;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@Slf4j
public class DataConverter {

    private static final String ARRAY_REGEX = "\\[.*]";
    private static final String ARRAY_2DIM_REGEX = "\\[\\[.*]]";

    private static final String CALCULATE_METHOD_PREFIX = "calculate";

    /**
     * Возвращается объект FinalData, поля которого высчитываются по формулам из MI3622Calculation
     */
    public static FinalData calculateFinalData(MI3622Calculation calc) {
        FinalData finalData = new FinalData();
        Field[] finalDataFields = FinalData.class.getDeclaredFields();
        Class<? extends MI3622Calculation> calcClass = calc.getClass();
        for (Field field : finalDataFields) {
            try {
                field.setAccessible(true);
                String methodName = CALCULATE_METHOD_PREFIX + StringUtils.capitalize(field.getName());
                var value = calcClass.getMethod(methodName).invoke(calc);
                field.set(finalData, value);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return finalData;
    }

    /**
     * Возвращается объект Map<String, Object>, построенный на основе значений полей finalData.
     * Каждому полю в FinalData соответствует имя тега, которое знает OPC.
     * Соответствия имен тегов и имен полей FinalData содержатся в параметре tagNamesMap.
     * Ключ в map это имя тега из OPC, а значение это значение этого тега,
     * которое берется из соответствующего поля finalData
     */
    public static Map<String, Object> convertFinalDataToMap(FinalData finalData, Map<String, String> tagNamesMap) {

        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = FinalData.class.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            try {
                declaredField.setAccessible(true);
                Object data = declaredField.get(finalData);
                if (data != null) {
                    if (declaredField.getType().equals(double[][].class)) {
                        data = transpose((double[][]) data);
                    }
                    String tagName = tagNamesMap.get(declaredField.getName());
                    if (tagName == null) {
                        throw new MissingDbDataException("There is no " + declaredField.getName() + " (initial = FALSE, type = MI_3622) tag in calculations.tag_name table");
                    }
                    map.put(tagNamesMap.get(declaredField.getName()), data);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return map;
    }

    /**
     * Возвращается объект InitialData, значения которого берутся из dataFromOpc.
     * В dataFromOpc ключ - это имя тега, а значение - это значение тега.
     * В tagNamesMap содержатся соответствия имени тега и имени полей в InitialData.
     * C использованием tagNamesMap и dataFromOpc заполняются поля InitialData.
     */
    public static InitialData convertMapToInitialData(Map<String, String> dataFromOpc, Map<String, String> tagNamesMap) {
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
                            // из OPC сервера значения идут в таком виде: каждая строка(!) это множество измерений в 1 точке
                            // а формулы для вычислений предполагают, что каждый столбец(!) это множество измерений в 1 точке
                            // поэтому, нужно транспонировать двумерный массив
                            double[][] array2Dim = transpose(to2DArray(value));
                            declaredField.set(initialData, array2Dim);
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

    /**
     * Двумерные массивы из OPC приходят в виде одномерного массива: [x, x, x, x, ...]
     * Этот метод меняет вид двумерных массивов (value из dataFromOpc) на [[x, x, ...],[x, x, ...]...]
     */
    public static void putInOrder2DArraysInOpcData(Map<String, String> dataFromOpc, Map<String, String> initialTagNamesMap) {
        int pointsCount = parseInt(dataFromOpc.get(initialTagNamesMap.get("pointsCount")));
        int measureCount = parseInt(dataFromOpc.get(initialTagNamesMap.get("measureCount")));
        Field[] initialDataFields = InitialData.class.getDeclaredFields();
        for (Map.Entry<String, String> entry : dataFromOpc.entrySet()) {
            String value = entry.getValue();

            String permanentTagName = initialTagNamesMap
                    .entrySet()
                    .stream()
                    .filter(en -> en.getValue().equals(entry.getKey()))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElseThrow();
            Optional<Field> dfOptional= Arrays.stream(initialDataFields)
                    .filter(df -> df.getName().equals(permanentTagName))
                    .findFirst();
            Class<?> dfType;
            if(dfOptional.isPresent()){
                dfType = dfOptional.get().getType();
            }
            else{
                continue;
            }
            if (value.matches(ARRAY_REGEX) && !value.matches(ARRAY_2DIM_REGEX) && dfType.equals(double[][].class)) {
                double[] valueArr = toArray(value);
                if (valueArr.length == pointsCount * measureCount) {
                    double[][] array2D = fromArrayTo2DArray(valueArr, pointsCount, measureCount);
                    String newValue = fromObjectToJson(array2D);
                    dataFromOpc.put(entry.getKey(), newValue);
                }
                else {
                    throw new NotValidTagDataException("Длина массива %s должна быть равна pointsCount*measureCount = %s"
                            .formatted(value, pointsCount*measureCount));
                }
            }
        }
    }

    private static double[][] transpose(double[][] matrix) {
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
}
