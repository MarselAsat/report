package com.nppgks.reportingsystem.util;

import org.decimal4j.util.DoubleRounder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

public class DataRounder {

    private final static int MIN_PRECISION = 6;
    private final static int MAX_PRECISION = 8;

    // округляет double, double[] и double[][] поля объекта
    public static void roundPojo(Object o) {
        Field[] declaredFields = o.getClass().getDeclaredFields();
        try {
            for (Field field : declaredFields) {
                field.setAccessible(true);

                if (field.getType().equals(double[][].class)) {
                    double[][] array2D = (double[][]) field.get(o);
                    double[][] roundedArray2D = roundDouble2DArray(array2D);
                    field.set(o, roundedArray2D);
                } else if (field.getType().equals(double[].class)) {
                    double[] array = (double[]) field.get(o);
                    double[] roundedArray = roundDoubleArray(array);
                    field.set(o, roundedArray);
                } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                    Double value = (Double) field.get(o);
                    Double roundedValue = roundDouble(value);
                    field.set(o, roundedValue);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Double roundDouble(Double value) {
        if (value != null) {
            if (value < 0.0009) {
                return DoubleRounder.round(value, MAX_PRECISION);
            } else {
                return DoubleRounder.round(value, MIN_PRECISION);
            }
        }
        return value;
    }

    public static double[] roundDoubleArray(double[] array) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                array[i] = roundDouble(array[i]);
            }
        }
        return array;
    }

    private static double[][] roundDouble2DArray(double[][] array2D) {
        if (array2D != null) {
            for (int i = 0; i < array2D.length; i++) {
                for (int j = 0; j < array2D[0].length; j++) {
                    array2D[i][j] = roundDouble(array2D[i][j]);
                }
            }
        }
        return array2D;
    }

    public static double roundDouble(double number, int precision){
        return DoubleRounder.round(number, precision);
    }
    public static double[] roundArray(double[] numbers, int precision){
        return Arrays.stream(numbers)
                .map(num -> DoubleRounder.round(num, precision))
                .toArray();
    }

    public static double[][] round2DimArray(double[][] numbers, int precision){
        return Arrays.stream(numbers)
                .map(arr -> Arrays.stream(arr)
                        .map(num -> DoubleRounder.round(num, precision))
                        .toArray())
                .toArray(double[][]::new);
    }

    public static double roundToSignificantDigits(double number, int signDigNum){
        int roundNumber = (int) Math.round(number);
        int numberLen = (String.valueOf(roundNumber)).length();
        if(signDigNum < numberLen){
            return roundNumber;
        }
        BigDecimal bd = new BigDecimal(number);
        bd = bd.round(new MathContext(signDigNum));
        return bd.doubleValue();
    }
    public static double[] roundArrayToSignDig(double[] numbers, int signDigNum){
        return Arrays.stream(numbers)
                .map(num -> roundToSignificantDigits(num, signDigNum))
                .toArray();
    }

    public static double[][] round2DimArrayToSignDig(double[][] numbers, int signDigNum){
        return Arrays.stream(numbers)
                .map(arr -> Arrays.stream(arr)
                        .map(num -> roundToSignificantDigits(num, signDigNum))
                        .toArray())
                .toArray(double[][]::new);
    }
}
