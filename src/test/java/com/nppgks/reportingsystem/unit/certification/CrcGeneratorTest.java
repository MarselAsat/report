package com.nppgks.reportingsystem.unit.certification;

import com.nppgks.reportingsystem.certification.CRCGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CrcGeneratorTest {

    @Test
    void generate(){
        Assertions.assertThat(CRCGenerator.getCertificationAlgorithmsCrc()).isEqualTo(-1);
    }
}
