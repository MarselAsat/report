package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki;

import java.util.Arrays;

public class CommonFunctions {

    public static double getMaxInArray(double[] array){
        return Arrays.stream(array).max().orElseThrow();
    }

    public static double getMinInArray(double[] array){
        return Arrays.stream(array).min().orElseThrow();
    }

    public static double[] getAverageForEachColumn(double[][] array){
        int columnsCount = array[0].length;
        int rowsCount = array.length;
        double[] averageForEachColumn = new double[columnsCount];
        for (int j = 0; j < columnsCount; j++) {
            double sum = 0;
            for (double[] doubles : array) {
                sum = sum + doubles[j];
            }
            averageForEachColumn[j] = sum / rowsCount;
        }
        return averageForEachColumn;
    }

    public static double[][] getDivisionOfTwoArrays(double[][] dividendArr, double[][] divisorArr){
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
