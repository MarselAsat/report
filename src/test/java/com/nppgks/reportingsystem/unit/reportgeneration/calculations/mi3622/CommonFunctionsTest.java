package com.nppgks.reportingsystem.unit.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.CommonFunctions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonFunctionsTest {

    @Test
    public void testDivide2DimArrayByNumber_PositiveCase() {
        double[][] array = {{2.0, 4.0}, {6.0, 8.0}};
        double number = 2.0;
        double[][] expected = {{1.0, 2.0}, {3.0, 4.0}};
        double[][] actual = CommonFunctions.divide2DimArrayByNumber(array, number);
        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[0].length; j++) {
                assertThat(actual[i][j]).isCloseTo(expected[i][j], Offset.offset(0.001));
            }
        }
    }


    @Test
    public void multiplyArrayByArray() {
        double[] array1 = {1.0, 2.0, 3.0};
        double[] array2 = {2.0, 3.0, 4.0};
        double[] expected = {2.0, 6.0, 12.0};

        double[] actual = CommonFunctions.multiplyArrayByArray(array1, array2);

        IntStream.range(0, actual.length).forEach(i ->
                assertThat(actual[i]).
                        isCloseTo(expected[i], Offset.offset(0.001)));
        ;
    }

    @Test
    public void getAverageForEachRow() {
        double[][] array = {{1, 2, 3}, {4, 5, 6}};
        double[] expected = {2, 5};
        double[] actual = CommonFunctions.getAverageForEachRow(array);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getAverageForEachColumn() {
        double[][] array = {{1, 2, 3}, {4, 5, 6}};
        double[] expected = {2.5, 3.5, 4.5};
        double[] actual = CommonFunctions.getAverageForEachColumn(array);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void linearInterpolation() {
        double[][] x = new double[][]{{0, 1, 2, 3}, {0.5, 1.5, 2.5, 6}};
        double[] xKnown = new double[]{1, 2, 3, 4};
        double[] yKnown = new double[]{20, 40, 55, 56};
        double[][] y = CommonFunctions.linearInterpolation(x, xKnown, yKnown);
        double[][] expected = new double[][]{{20, 20, 40, 55}, {20, 30, 47.5, 56}};
        assertThat(y).isDeepEqualTo(expected);
    }

    @Test
    void getDivisionOfTwoArrays() {
        double[][] arr1 = new double[][]{{2, 4, 6, 8}, {16, 25, 10, 23}};
        double[][] arr2 = new double[][]{{2, 2, 2, 3}, {2, 5, 2, 23}};
        double[][] y = CommonFunctions.divide2DimArrayBy2DimArray(arr1, arr2);
        double[][] expected = new double[][]{{1, 2, 3, 2.66666}, {8, 5, 5, 1}};

        for (int i = 0; i < y.length; i++) {
            for (int j = 0; j < y[0].length; j++) {
                assertThat(y[i][j]).isCloseTo(expected[i][j], Offset.offset(0.001));
            }
        }
    }
}
