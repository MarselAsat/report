package com.nppgks.reports;

import com.nppgks.reports.opc.ArrayParser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayParserTest {

    @Test
    void to2DimArray(){
        String jsonArray1 = "[[\"1\", \"2\", \"3\"], [\"4\", \"5\", \"6\"]]";
        String jsonArray2 = "[[1, 2, 3], [4, 5, 6]]";
        double[][] result1 = ArrayParser.to2dimArray(jsonArray1);
        double[][] result2 = ArrayParser.to2dimArray(jsonArray2);
        assertThat(result1).isDeepEqualTo(new double[][]{{1, 2, 3},{4, 5, 6}});
        assertThat(result2).isDeepEqualTo(new double[][]{{1, 2, 3},{4, 5, 6}});
    }

    @Test
    void returnNulIfStringIsNull(){
        double[][] actual2DimArr = ArrayParser.to2dimArray(null);
        double[] actualArr = ArrayParser.toArray(null);
        assertThat(actual2DimArr).isNull();
        assertThat(actualArr).isNull();
    }

    @Test
    void fromArrayTo2DimArray(){
        double[] array1Dim = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        double[][] array2Dim = ArrayParser.fromArrayTo2DimArray(array1Dim, 3, 5);
        assertThat(array2Dim).isDeepEqualTo(new double[][]
                {{1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15}});


    }
}
