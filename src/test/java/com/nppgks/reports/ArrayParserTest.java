package com.nppgks.reports;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nppgks.reports.opc.ArrayParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArrayParserTest {

    @Test
    void to2DimArray(){
        String jsonArray1 = "[[\"1\", \"2\", \"3\"], [\"4\", \"5\", \"6\"]]";
        String jsonArray2 = "[[1, 2, 3], [4, 5, 6]]";
        double[][] result1 = ArrayParser.to2dimArray(jsonArray1);
        double[][] result2 = ArrayParser.to2dimArray(jsonArray2);
        Assertions.assertThat(result1).isDeepEqualTo(new double[][]{{1, 2, 3},{4, 5, 6}});
        Assertions.assertThat(result2).isDeepEqualTo(new double[][]{{1, 2, 3},{4, 5, 6}});
    }
}
