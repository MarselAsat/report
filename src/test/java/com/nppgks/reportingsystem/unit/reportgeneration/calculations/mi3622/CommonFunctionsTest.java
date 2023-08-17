package com.nppgks.reportingsystem.unit.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.reportgeneration.poverki.CommonFunctions;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

public class CommonFunctionsTest {

    @Test
    void linearInterpolation() {
        double[][] x = new double[][]{{0, 1, 2, 3}, {0.5, 1.5, 2.5, 6}};
        double[] xKnown = new double[]{1, 2, 3, 4};
        double[] yKnown = new double[]{20, 40, 55, 56};
        double[][] y = CommonFunctions.linearInterpolation(x, xKnown, yKnown);
        double[][] expected = new double[][]{{20, 20, 40, 55}, {20, 30, 47.5, 56}};
        Assertions.assertThat(y).isDeepEqualTo(expected);
    }

    @Test
    void getDivisionOfTwoArrays() {
        double[][] arr1 = new double[][]{{2, 4, 6, 8}, {16, 25, 10, 23}};
        double[][] arr2 = new double[][]{{2, 2, 2, 3}, {2, 5, 2, 23}};
        double[][] y = CommonFunctions.getDivisionOfTwoArrays(arr1, arr2);
        double[][] expected = new double[][]{{1, 2, 3, 2.66666}, {8, 5, 5, 1}};

        for (int i = 0; i < y.length; i++) {
            for (int j = 0; j < y[0].length; j++) {
                Assertions.assertThat(y[i][j]).isCloseTo(expected[i][j], Offset.offset(0.001));
            }
        }
    }
}
