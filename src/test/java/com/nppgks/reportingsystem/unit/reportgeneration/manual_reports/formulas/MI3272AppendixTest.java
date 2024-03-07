package com.nppgks.reportingsystem.unit.reportgeneration.manual_reports.formulas;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.Appendix;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MI3272AppendixTest {

    @ParameterizedTest
    @MethodSource
    void getT_P_n(int n, double expected){
        double actual = Appendix.get_t_P_n(n);
        Assertions.assertThat(actual).isCloseTo(expected, Offset.offset(0.01));
    }

    private static Stream<Arguments> getT_P_n(){
        return Stream.of(
                Arguments.of(2, 12.706),
                Arguments.of(7, 2.447),
                Arguments.of(12, 2.203),
                Arguments.of(15, 2.145),
                Arguments.of(17, 2.120),
                Arguments.of(19, 2.101),
                Arguments.of(31, 2.0423)
        );
    }
}
