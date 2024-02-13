package com.nppgks.reportingsystem.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nppgks.reportingsystem.constants.Regexes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ArrayParser {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule("DoubleSerializer");
        module.addSerializer(Double.class, new DoubleSerializer());
        module.addSerializer(double.class, new DoubleSerializer());
        module.addSerializer(double[].class, new DoubleArraySerializer());
        module.addSerializer(double[][].class, new Double2DimArraySerializer());
        objectMapper.registerModule(module);
    }

    public static double[] toArray(String json) {
        if (json == null) return null;
        try {
            return objectMapper.readValue(json, double[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static double[][] to2DArray(String json) {
        if (json == null) return null;
        try {
            return objectMapper.readValue(json, double[][].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static double[][] fromArrayTo2DArray(double[] array, int rowsCount, int columnsCount) {
        double[][] resultArr = new double[rowsCount][columnsCount];
        try {
            for (int i = 0; i < rowsCount; i++) {
                resultArr[i] = Arrays.copyOfRange(array, columnsCount * i, columnsCount * (i + 1));
            }
            return resultArr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object fromJsonToObject(String json) {
        if (json == null || json.isBlank()) return null;
        try {
            // если это не массив, а просто строка
            if (!json.matches(Regexes.ARRAY_3DIM_REGEX) && !json.matches(Regexes.ARRAY_2DIM_REGEX) && !json.matches(Regexes.ARRAY_REGEX)) {
                return json;
            }
            return objectMapper.readValue(json, Object.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fromObjectToJson(Object object) {
        try {
            String result = objectMapper.writeValueAsString(object);
            if (result.matches("\".*\"")) {
                StringBuilder sb = new StringBuilder(result);
                sb.deleteCharAt(0);
                sb.deleteCharAt(sb.length()-1);
                return sb.toString();
            }
            if(result.matches("\\[\"\\[.*]\"]")){
                result = result.replaceAll("\"\\[", "[");
                result = result.replaceAll("]\"", "]");
                return result;
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<double[][]> toListOfArrays(String json) {
        try {
            double[][][] list = objectMapper.readValue(json, double[][][].class);
            return Arrays.stream(list).toList();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
