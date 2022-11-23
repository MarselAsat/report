package com.nppgks.reportingsystem.opc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ArrayParser {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static double[] toArray(String json){
        if (json == null) return null;
        try {
            return objectMapper.readValue(json, double[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static double[][] to2DArray(String json){
        if (json == null) return null;
        try {
            return objectMapper.readValue(json, double[][].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static double[][] fromArrayTo2DArray(double[] array, int rowsCount, int columnsCount){
        double[][] resultArr = new double[rowsCount][columnsCount];
        try{
            for(int i = 0; i < rowsCount; i++){
                resultArr[i] = Arrays.copyOfRange(array, columnsCount*i, columnsCount*(i+1));
            }
            return resultArr;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static Object fromJsonToObject(String json){
        if (json == null) return null;
        try {
            if(!json.matches("\\[\\[.+]") && !json.matches("\\[.+]")){
                return objectMapper.readValue("\""+json+"\"", Object.class);
            }
            return objectMapper.readValue(json, Object.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fromObjectToJson(Object object){
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(out, object);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
