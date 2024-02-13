package com.nppgks.reportingsystem.unit.util;

import com.nppgks.reportingsystem.util.ArrayParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayParserTest {

    @Test
    void toListOfArrays() {
        List<double[][]> actual = ArrayParser.toListOfArrays("[[[1, 2, 3],[4, 5, 6]],[[7, 8, 9],[10, 11, 12]]]");
        assertThat(actual).hasSize(2).contains(new double[][]{{1.0,2.0,3.0},{4.0,5.0,6.0}});
    }

    @Test
    void shouldReturnDoublesWithoutScientificNotation() {
        // во время сериализации этих объектов, должны вызываться кастомные сериалайзеры
        POJOTestClass testPojo = new POJOTestClass(0.000000078, 87978089879d);
        double[] array = new double[]{0.000000000098d, 87978089879d};
        Double dobObj = 87978089879d;
        double dobPrim = 87978089879d;
        double[][] arr2Dim = new double[][]{{1, 2, 3},{4, 5, 6}};
        double[][][] arr3Dim = new double[][][]{{{1, 2, 3},{4, 5, 6}}, {{1, 2, 3},{4, 5, 6}}};
        String[][] arr2DimStr = new String[][]{{"a", "b", "c"}, {"d", "e", "f"}};
        String actualPojo = ArrayParser.fromObjectToJson(testPojo);
        String actualArray = ArrayParser.fromObjectToJson(array);
        String actual2DimArray = ArrayParser.fromObjectToJson(arr2Dim);
        String actual2DimArrayStr = ArrayParser.fromObjectToJson(arr2DimStr);
        String actual3DimArray = ArrayParser.fromObjectToJson(arr3Dim);
        String actualDoubleObject = ArrayParser.fromObjectToJson(dobObj);
        String actualDoublePrimitive = ArrayParser.fromObjectToJson(dobPrim);

        assertThat(actualPojo).isEqualTo("{\"doubleField\":\"0.000000078\",\"doubleObjectField\":\"87978089879\"}");
        assertThat(actualArray).isEqualTo("[0.000000000098,87978089879]");
        assertThat(actual2DimArray).isEqualTo("[[1,2,3],[4,5,6]]");
        assertThat(actual2DimArrayStr).isEqualTo("[[\"a\",\"b\",\"c\"],[\"d\",\"e\",\"f\"]]");
        assertThat(actual3DimArray).isEqualTo("[[[1,2,3],[4,5,6]],[[1,2,3],[4,5,6]]]");
        assertThat(actualDoubleObject).isEqualTo("87978089879");
        assertThat(actualDoublePrimitive).isEqualTo("87978089879");
    }

    @Test
    void to2DimArray() {
        String jsonArray1 = "[[\"1\", \"2\", \"3\"], [\"4\", \"5\", \"6\"]]";
        String jsonArray2 = "[[1, 2, 3], [4, 5, 6]]";
        double[][] result1 = ArrayParser.to2DArray(jsonArray1);
        double[][] result2 = ArrayParser.to2DArray(jsonArray2);
        assertThat(result1).isDeepEqualTo(new double[][]{{1, 2, 3}, {4, 5, 6}});
        assertThat(result2).isDeepEqualTo(new double[][]{{1, 2, 3}, {4, 5, 6}});
    }

    @Test
    void returnNulIfStringIsNull() {
        double[][] actual2DimArr = ArrayParser.to2DArray(null);
        double[] actualArr = ArrayParser.toArray(null);
        assertThat(actual2DimArr).isNull();
        assertThat(actualArr).isNull();
    }

    @Test
    void fromArrayTo2DimArray() {
        double[] array1Dim = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        double[][] array2Dim = ArrayParser.fromArrayTo2DArray(array1Dim, 3, 5);
        assertThat(array2Dim).isDeepEqualTo(new double[][]
                {{1, 2, 3, 4, 5},
                        {6, 7, 8, 9, 10},
                        {11, 12, 13, 14, 15}});
    }

    @Test
    void from2DArrayToJson() {
        String json1 = ArrayParser.fromObjectToJson(new double[][]{{1.0, 2.0, 3.0}, {1.0, 2.0, 3.0}});
        assertThat(json1).isEqualTo("[[1,2,3],[1,2,3]]");

        String json2 = ArrayParser.fromObjectToJson(1);
        assertThat(json2).isEqualTo("1");

        String json3 = ArrayParser.fromObjectToJson(new double[]{1, 2, 3, 7.0});
        assertThat(json3).isEqualTo("[1,2,3,7]");
    }

    @Test
    void fromJsonToObject() {
        Object o = ArrayParser.fromJsonToObject("[1,2,3,4,5]");
        Object o1 = ArrayParser.fromJsonToObject("[[1,2,3],[1,2,3]]");
        Object o2 = ArrayParser.fromJsonToObject("hello");
        Object o3 = ArrayParser.fromJsonToObject("hello \"Company\"");
        System.out.println();
    }
}
