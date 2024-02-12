package com.nppgks.reportingsystem.util;

import com.nppgks.reportingsystem.exception.NotValidTagValueException;

import java.util.List;

public class TagValueValidator {

    public static void notNull(Object obj, String objName) {
        if (obj == null) {
            throw new NotValidTagValueException(objName + " не может быть null");
        }
    }

    public static void haveSameLen2DimArr(List<double[][]> arrs, List<String> arrNames) {
        int n = arrs.get(0).length;
        int m = arrs.get(0)[0].length;
        String refArrName = arrNames.get(0);
        for (int i = 0; i < arrs.size(); i++) {
            double[][] arr = arrs.get(i);
            if (arr.length != n || arr[0].length != m) {
                throw new NotValidTagValueException(
                        "Длины массивов %s ([%s][%s]) и %s ([%s][%s]) должны совпадать"
                                .formatted(arrNames.get(i), arr.length, arr[0].length, refArrName, n, m));
            }
        }
    }

    public static void haveSameLen(List<double[]> arrs, List<String> arrNames) {
        int len = arrs.get(0).length;
        String refArrName = arrNames.get(0);
        for (int i = 0; i < arrs.size(); i++) {
            double[] arr = arrs.get(i);
            if (arr.length != len) {
                throw new NotValidTagValueException(
                        "Длины массивов %s ([%s] и %s ([%s]) должны совпадать"
                                .formatted(arrNames.get(i), arr.length, refArrName, len));
            }
        }
    }

    public static void hasOneOfValues(Object actualValue, List<Object> possibleValues, String objName) {
        boolean containsValue = false;
        for (Object possibleValue : possibleValues) {
            if (actualValue.equals(possibleValue)) {
                containsValue = true;
                break;
            }
        }
        if (!containsValue) {
            throw new NotValidTagValueException(
                    "Значение %s может быть только одно из %s, но было передано значение %s"
                            .formatted(objName, possibleValues.toString(), actualValue));
        }
    }

    public static void notZero(double num, String objName){
        if(num == 0){
            throw new NotValidTagValueException(
                    "Значение %s не может быть равным нулю"
                            .formatted(objName));
        }
    }

    public static void notZero(double[][] arr2Dim, String objName){
        for (double[] arr: arr2Dim) {
            for (double num: arr) {
                notZero(num, objName);
            }
        }
    }
}
