package com.nppgks.reports.opc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static double[][] to2dimArray(String json){
        if (json == null) return null;
        try {
            return objectMapper.readValue(json, double[][].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static double[][] fromArrayTo2DimArray(double[] array, int rowsCount, int columnsCount){
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
}
