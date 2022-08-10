package com.nppgks.reports.opc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

public class ArrayParser {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static double[] toArray(String json){
        try {
            return objectMapper.readValue(json, double[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static double[][] to2dimArray(String json){
        try {
            return objectMapper.readValue(json, double[][].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
