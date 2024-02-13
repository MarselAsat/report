package com.nppgks.reportingsystem.util;

import java.lang.reflect.Array;

public class TableDisplay {
    public static StringBuilder display2DimArray(Object[] arr){
        int len = arr.length;
        StringBuilder sb = new StringBuilder();
        if(len > 0){
            for (Object o : arr) {
                displayArray(o, sb);
                sb.append("\n");
            }
        }
        return sb;
    }
    private static void displayArray(Object arr, StringBuilder sb){
        int len = Array.getLength(arr);
        if(len > 0){
            for (int i = 0; i < len; i++) {
                sb.append(Array.get(arr, i)).append("\t");
            }
        }
    }
}
