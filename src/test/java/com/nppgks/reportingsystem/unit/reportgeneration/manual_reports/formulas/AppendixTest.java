package com.nppgks.reportingsystem.unit.reportgeneration.manual_reports.formulas;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.Appendix;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppendixTest {

    @Test
    void calculateBeta_fluid_forCrudeOilLess5Perc(){
        double rho15 = 800;
        double t = 10;
        double W_w = 2;
        double actual = Appendix.calculateBeta_fluid_forCrudeOilLess5Perc("нефть", rho15, t, W_w);
        double expected = 0.000938;
        assertThat(actual).isCloseTo(expected, Offset.offset(0.00001));
    }

    @Test
    void calculateBeta_fluid_forCrudeOilMore5Perc(){
        double rho15 = 800;
        double t = 10;
        double W_w = 10;
        double beta_w = 0.005;
        double actual = Appendix.calculateBeta_fluid_forCrudeOilMore5Perc("нефть", rho15, t, W_w, beta_w);
        double expected = 0.001356;
        assertThat(actual).isCloseTo(expected, Offset.offset(0.00001));
    }

    @Test
    void calculateB_w(){
        double t_TPR = 800;
        double t_KP = 700;
        double W_xc = 8;

        double actual = Appendix.calculateB_w(t_TPR, t_KP, W_xc);
        double expected = -0.00384133667;
        assertThat(actual).isCloseTo(expected, Offset.offset(0.00001));
    }
}
