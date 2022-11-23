package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data;

import org.decimal4j.util.DoubleRounder;
import java.lang.reflect.Field;

public class DataRounder {

    public static void round(Object o) {
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
                } else if (field.getType().equals(Double.class)) {
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
        return DoubleRounder.round(value, 4);
    }

    private static double[] roundDoubleArray(double[] array) {
        for (int i = 0; i < array.length; i++) {
                array[i] = DoubleRounder.round(array[i], 4);
        }
        return array;
    }

    private static double[][] roundDouble2DArray(double[][] array2D) {
        for (int i = 0; i < array2D.length; i++) {
            for (int j = 0; j < array2D[0].length; j++) {
                array2D[i][j] = DoubleRounder.round(array2D[i][j], 4);
            }
        }
        return array2D;
    }
}
