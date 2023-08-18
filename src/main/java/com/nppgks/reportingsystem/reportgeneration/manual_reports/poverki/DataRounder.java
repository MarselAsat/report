package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki;

import org.decimal4j.util.DoubleRounder;

import java.lang.reflect.Field;

public class DataRounder {

    private final static int MIN_PRECISION = 4;
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
}
