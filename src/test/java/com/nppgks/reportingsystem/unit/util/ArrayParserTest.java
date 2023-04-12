package com.nppgks.reportingsystem.unit.util;

import com.nppgks.reportingsystem.util.ArrayParser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayParserTest {

    @Test
    void to2DimArray(){
        String jsonArray1 = "[[\"1\", \"2\", \"3\"], [\"4\", \"5\", \"6\"]]";
        String jsonArray2 = "[[1, 2, 3], [4, 5, 6]]";
        double[][] result1 = ArrayParser.to2DArray(jsonArray1);
        double[][] result2 = ArrayParser.to2DArray(jsonArray2);
        assertThat(result1).isDeepEqualTo(new double[][]{{1, 2, 3},{4, 5, 6}});
        assertThat(result2).isDeepEqualTo(new double[][]{{1, 2, 3},{4, 5, 6}});
    }

    @Test
    void returnNulIfStringIsNull(){
        double[][] actual2DimArr = ArrayParser.to2DArray(null);
        double[] actualArr = ArrayParser.toArray(null);
        assertThat(actual2DimArr).isNull();
        assertThat(actualArr).isNull();
    }

    @Test
    void fromArrayTo2DimArray(){
        double[] array1Dim = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        double[][] array2Dim = ArrayParser.fromArrayTo2DArray(array1Dim, 3, 5);
        assertThat(array2Dim).isDeepEqualTo(new double[][]
                {{1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15}});
    }

    @Test
    void from2DArrayToJson(){
        String json1 = ArrayParser.fromObjectToJson(new double[][]{{1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}});
        assertThat(json1).isEqualTo("[[1.0,2.0,3.0],[1.0,2.0,3.0]]");

        String json2 = ArrayParser.fromObjectToJson(1);
        assertThat(json2).isEqualTo("1");

        String json3 = ArrayParser.fromObjectToJson(new double[]{1, 2, 3, 7.0});
        assertThat(json3).isEqualTo("[1.0,2.0,3.0,7.0]");
    }

    @Test
    void fromJsonToObject() {
        Object o = ArrayParser.fromJsonToObject("[1,2,3,4,5]");
        Object o1 = ArrayParser.fromJsonToObject("[[1,2,3],[1,2,3]]");
        Object o2 = ArrayParser.fromJsonToObject("hello");
        System.out.println();
    }
}
