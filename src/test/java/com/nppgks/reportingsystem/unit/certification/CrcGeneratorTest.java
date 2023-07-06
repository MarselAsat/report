package com.nppgks.reportingsystem.unit.certification;

import com.nppgks.reportingsystem.certification.CrcGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CrcGeneratorTest {

    @Test
    void generate(){
        Assertions.assertThat(CrcGenerator.getCertificationAlgorithmsCrc("1.0.0")).isEqualTo(-1);
    }
}
