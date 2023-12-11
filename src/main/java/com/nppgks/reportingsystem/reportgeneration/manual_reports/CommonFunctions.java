package com.nppgks.reportingsystem.reportgeneration.manual_reports;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CommonFunctions {

    public static double getMaxInArray(double[] array){
        return Arrays.stream(array).max().orElseThrow();
    }

    public static double getMinInArray(double[] array){
        return Arrays.stream(array).min().orElseThrow();
    }

    public static double[] getAverageForEachColumn(double[][] array){
        final int rowsCount = array.length;
        final int columnsCount = array[0].length;
        return Arrays.stream(array).reduce((sumArr, arr) ->
                        IntStream.range(0, columnsCount)
                                .mapToDouble(i -> sumArr[i] + arr[i])
                                .toArray())
                .stream()
                .flatMapToDouble(Arrays::stream)
                .map(sum -> sum / rowsCount)
                .toArray();
    }

    public static double[] getAverageForEachRow(double[][] array){
        return Arrays.stream(array)
                .mapToDouble(arr -> Arrays.stream(arr)
                        .average().orElseThrow())
                .toArray();
    }

    public static double[][] divide2DimArrayByNumber(double[][] array, double number){
        int n = array.length;
        int m = array[0].length;
        double[][] result = new double[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j<m; j++){
                result[i][j] = array[i][j]/number;
            }
        }
        return result;
    }

    public static double[][] divide2DimArrayBy2DimArray(double[][] dividendArr, double[][] divisorArr){
        int columnsCount = dividendArr[0].length;
        int rowsCount = dividendArr.length;
        double[][] result = new double[rowsCount][columnsCount];
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                result[i][j] = dividendArr[i][j] / divisorArr[i][j];
            }
        }
        return result;
    }

    public static double[] multiplyArrayByArray(double[] array1, double[] array2){
        return IntStream.range(0, array1.length).mapToDouble(i -> array1[i]*array2[i]).toArray();
    }

    public static double[][] linearInterpolation(double[][] x, double[] xKnown, double[] yKnown){
        int columnsCount = x[0].length;
        int rowsCount = x.length;
        int intervalsCount = yKnown.length;
        double[][] y = new double[rowsCount][columnsCount];

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                // если x выходит за пределы интервалов
                if (x[i][j] <= xKnown[0]) {
                    y[i][j] = yKnown[0];
                } else if (x[i][j] >= xKnown[intervalsCount - 1]) {
                    y[i][j] = yKnown[intervalsCount - 1];
                } else {
                    int index1;
                    int index2;
                    for (int k = 0; k < intervalsCount; k++) {
                        if (x[i][j] == xKnown[k]) {
                            y[i][j] = yKnown[k];
                            break;
                        }
                        if (x[i][j] < xKnown[k]) {
                            index2 = k;
                            index1 = k - 1;

                            // f(x) = f(X1)+(f(X2) - f(X1))*(X - X1)/(X2 - X1)
                            y[i][j] = yKnown[index1] + (yKnown[index2] - yKnown[index1]) *
                                    (x[i][j] - xKnown[index1]) / (xKnown[index2] - xKnown[index1]);
                            break;
                        }
                    }
                }
            }
        }
        return y;
    }
}
