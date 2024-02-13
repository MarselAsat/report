package com.nppgks.reportingsystem.unit.util;

import com.nppgks.reportingsystem.util.TableDisplay;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class TableDisplayTest {

    @Test
    void display2DimArray(){
        Double[][] arr = new Double[][]{{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}};
        StringBuilder actual = TableDisplay.display2DimArray(arr);
        String expected = """
                1.0\t2.0\t3.0\t
                4.0\t5.0\t6.0\t
                """;
        Assertions.assertThat(actual.toString()).isEqualTo(expected);
    }
}
