package com.nppgks.reportingsystem.unit.reportgeneration.manual_reports.poverki;

import com.nppgks.reportingsystem.exception.NotValidTagValueException;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313.MI3313OneEsrmCalculator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313.MI3313OneEsrmFinalData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313.MI3313OneEsrmInitialData;
import com.nppgks.reportingsystem.unit.DoubleArrayAssert;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MI3313OneEsrmCalculatorTest {

    @Test
    public void calculate(){
        MI3313OneEsrmInitialData initialData = new MI3313OneEsrmInitialData();
        initialData.setK_PME(45000);
        double[][] T_ji = new double[][]{{15, 15, 15, 15}, {15, 15, 15, 15}, {15, 15, 15, 15}};
        double[][] N_eji = new double[][]{{6697, 6696, 6727, 6813}, {6912, 6941, 7036, 7077}, {7041, 7014, 6974, 6947}};
        double[][] N_ji = new double[][]{{6677, 6694, 6717, 6773}, {6911, 6967, 7004, 7037}, {7057, 7040, 7017, 6991}};
        initialData.setT_ji(T_ji);
        initialData.setN_eji(N_eji);
        initialData.setN_ji(N_ji);
        initialData.setK_PM(45000);
        initialData.setMForK_set(1);
        initialData.setDelta_e(0.1);
        initialData.setDelta_ivk(0.01);
        initialData.setZS(0.032);
        initialData.setDelta_tdop(0.008);
        initialData.setDelta_Pdop(0.0);
        initialData.setQ_nom(0.0);
        initialData.setQ_Mmax(0);
        initialData.setT_max(0.0);
        initialData.setT_min(0.0);
        initialData.setP_max(0.0);
        initialData.setP_min(0.0);
        initialData.setP_P(2.41);
        initialData.setT_P(37.59);
        initialData.setWorkingOrControl("рабочий");

        MI3313OneEsrmCalculator calculator = new MI3313OneEsrmCalculator(initialData);
        MI3313OneEsrmFinalData finalData = calculator.calculate();
        assertThat(finalData.getDelta()).isCloseTo(0.857, Offset.offset(0.001));
        assertThat(finalData.getMForK()).isCloseTo(0.99992, Offset.offset(0.001));
        DoubleArrayAssert.assertThat(finalData.getEpsilon_j()).isCloseTo(new double[]{0.384, 0.686, 0.312}, 0.001);
        DoubleArrayAssert.assertThat(finalData.getS_j()).isCloseTo(new double[]{0.242, 0.431, 0.196}, 0.001);
        DoubleArrayAssert.assertThat(finalData.getQ_j()).isCloseTo(new double[]{35.9, 37.3, 37.3}, 0.001);
    }

    @Test
    public void calculate_withoutZS(){
        MI3313OneEsrmInitialData initialData = new MI3313OneEsrmInitialData();
        initialData.setK_PME(45000);
        double[][] T_ji = new double[][]{{15, 15, 15, 15}, {15, 15, 15, 15}, {15, 15, 15, 15}};
        double[][] N_eji = new double[][]{{6697, 6696, 6727, 6813}, {6912, 6941, 7036, 7077}, {7041, 7014, 6974, 6947}};
        double[][] N_ji = new double[][]{{6677, 6694, 6717, 6773}, {6911, 6967, 7004, 7037}, {7057, 7040, 7017, 6991}};
        initialData.setT_ji(T_ji);
        initialData.setN_eji(N_eji);
        initialData.setN_ji(N_ji);
        initialData.setK_PM(45000);
        initialData.setMForK_set(1);
        initialData.setDelta_e(0.1);
        initialData.setDelta_ivk(0.01);
        initialData.setDelta_tdop(0.008);
        initialData.setDelta_Pdop(0.0);
        initialData.setQ_nom(10.0);
        initialData.setQ_Mmax(5.0);
        initialData.setT_max(20.0);
        initialData.setT_min(0.0);
        initialData.setP_max(2.0);
        initialData.setP_min(0.0);
        initialData.setP_P(2.41);
        initialData.setT_P(37.59);
        initialData.setWorkingOrControl("рабочий");

        MI3313OneEsrmCalculator calculator = new MI3313OneEsrmCalculator(initialData);
        MI3313OneEsrmFinalData finalData = calculator.calculate();
        assertThat(finalData.getTheta_Z()).isEqualTo(0);
        assertThat(finalData.getDelta()).isCloseTo(0.856, Offset.offset(0.001));
        assertThat(finalData.getMForK()).isCloseTo(0.99991, Offset.offset(0.001));
        DoubleArrayAssert.assertThat(finalData.getEpsilon_j()).isCloseTo(new double[]{0.384, 0.686, 0.312}, 0.001);
        DoubleArrayAssert.assertThat(finalData.getS_j()).isCloseTo(new double[]{0.242, 0.431, 0.196}, 0.001);
        DoubleArrayAssert.assertThat(finalData.getQ_j()).isCloseTo(new double[]{35.9, 37.3, 37.3}, 0.001);
    }

    @Test
    public void calculate_withoutDelta_tdop_And_ZS(){
        MI3313OneEsrmInitialData initialData = new MI3313OneEsrmInitialData();
        initialData.setK_PME(45000);
        double[][] T_ji = new double[][]{{15, 15, 15, 15}, {15, 15, 15, 15}, {15, 15, 15, 15}};
        double[][] N_eji = new double[][]{{6697, 6696, 6727, 6813}, {6912, 6941, 7036, 7077}, {7041, 7014, 6974, 6947}};
        double[][] N_ji = new double[][]{{6677, 6694, 6717, 6773}, {6911, 6967, 7004, 7037}, {7057, 7040, 7017, 6991}};
        initialData.setT_ji(T_ji);
        initialData.setN_eji(N_eji);
        initialData.setN_ji(N_ji);
        initialData.setK_PM(45000);
        initialData.setMForK_set(1);
        initialData.setDelta_e(0.1);
        initialData.setDelta_ivk(0.01);
        initialData.setDelta_Pdop(0.0);
        initialData.setQ_nom(10.0);
        initialData.setQ_Mmax(5.0);
        initialData.setT_max(20.0);
        initialData.setT_min(0.0);
        initialData.setP_max(2.0);
        initialData.setP_min(0.0);
        initialData.setP_P(2.41);
        initialData.setT_P(37.59);
        initialData.setWorkingOrControl("рабочий");

        MI3313OneEsrmCalculator calculator = new MI3313OneEsrmCalculator(initialData);
        MI3313OneEsrmFinalData finalData = calculator.calculate();
        assertThat(finalData.getTheta_Z()).isEqualTo(0);
        assertThat(finalData.getTheta_Mt()).isEqualTo(0);
        assertThat(finalData.getDelta()).isCloseTo(0.849, Offset.offset(0.001));
        assertThat(finalData.getMForK()).isCloseTo(0.99991, Offset.offset(0.001));
        DoubleArrayAssert.assertThat(finalData.getEpsilon_j()).isCloseTo(new double[]{0.384, 0.686, 0.312}, 0.001);
        DoubleArrayAssert.assertThat(finalData.getS_j()).isCloseTo(new double[]{0.242, 0.431, 0.196}, 0.001);
        DoubleArrayAssert.assertThat(finalData.getQ_j()).isCloseTo(new double[]{35.9, 37.3, 37.3}, 0.001);
    }

    @Test
    public void calculate_withoutDelta_tdop_And_ZS_And_P_max(){
        MI3313OneEsrmInitialData initialData = new MI3313OneEsrmInitialData();
        initialData.setK_PME(45000);
        double[][] T_ji = new double[][]{{15, 15, 15, 15}, {15, 15, 15, 15}, {15, 15, 15, 15}};
        double[][] N_eji = new double[][]{{6697, 6696, 6727, 6813}, {6912, 6941, 7036, 7077}, {7041, 7014, 6974, 6947}};
        double[][] N_ji = new double[][]{{6677, 6694, 6717, 6773}, {6911, 6967, 7004, 7037}, {7057, 7040, 7017, 6991}};
        initialData.setT_ji(T_ji);
        initialData.setN_eji(N_eji);
        initialData.setN_ji(N_ji);
        initialData.setK_PM(45000);
        initialData.setMForK_set(1);
        initialData.setDelta_e(0.1);
        initialData.setDelta_ivk(0.01);
        initialData.setDelta_Pdop(0.0);
        initialData.setQ_nom(10.0);
        initialData.setQ_Mmax(5.0);
        initialData.setT_max(20.0);
        initialData.setT_min(0.0);
        initialData.setP_min(0.0);
        initialData.setP_P(2.41);
        initialData.setT_P(37.59);
        initialData.setWorkingOrControl("рабочий");

        MI3313OneEsrmCalculator calculator = new MI3313OneEsrmCalculator(initialData);
        MI3313OneEsrmFinalData finalData = calculator.calculate();
        assertThat(finalData.getTheta_Z()).isEqualTo(0);
        assertThat(finalData.getTheta_Mt()).isEqualTo(0);
        assertThat(finalData.getTheta_MP()).isEqualTo(0);
        assertThat(finalData.getDelta()).isCloseTo(0.849, Offset.offset(0.001));
        assertThat(finalData.getMForK()).isCloseTo(0.99991, Offset.offset(0.001));
        DoubleArrayAssert.assertThat(finalData.getEpsilon_j()).isCloseTo(new double[]{0.384, 0.686, 0.312}, 0.001);
        DoubleArrayAssert.assertThat(finalData.getS_j()).isCloseTo(new double[]{0.242, 0.431, 0.196}, 0.001);
        DoubleArrayAssert.assertThat(finalData.getQ_j()).isCloseTo(new double[]{35.9, 37.3, 37.3}, 0.001);
    }

    @Test
    public void calculate_shouldThrowExcWhenIncorrectArrLen(){
        MI3313OneEsrmInitialData initialData = new MI3313OneEsrmInitialData();
        initialData.setK_PME(45000);
        double[][] T_ji = new double[][]{{15, 15, 15}, {15, 15, 15}, {15, 15, 15}};
        double[][] N_eji = new double[][]{{6697, 6696, 6727, 6813}, {6912, 6941, 7036, 7077}, {7041, 7014, 6974, 6947}};
        double[][] N_ji = new double[][]{{6677, 6694, 6717, 6773}, {6911, 6967, 7004, 7037}, {7057, 7040, 7017, 6991}};
        initialData.setT_ji(T_ji);
        initialData.setN_eji(N_eji);
        initialData.setN_ji(N_ji);
        initialData.setWorkingOrControl("рабочий");
        MI3313OneEsrmCalculator calculator = new MI3313OneEsrmCalculator(initialData);
        assertThatThrownBy(calculator::calculate)
                .isInstanceOf(NotValidTagValueException.class)
                .hasMessageContaining("Неверная длина массивов входных данных");
    }

    @Test
    public void calculate_shouldThrowExcWhenArraysNull(){
        MI3313OneEsrmInitialData initialData = new MI3313OneEsrmInitialData();
        initialData.setK_PME(45000);
        double[][] T_ji = null;
        double[][] N_eji = new double[][]{{6697, 6696, 6727, 6813}, {6912, 6941, 7036, 7077}, {7041, 7014, 6974, 6947}};
        double[][] N_ji = new double[][]{{6677, 6694, 6717, 6773}, {6911, 6967, 7004, 7037}, {7057, 7040, 7017, 6991}};
        initialData.setT_ji(T_ji);
        initialData.setN_eji(N_eji);
        initialData.setN_ji(N_ji);
        initialData.setWorkingOrControl("рабочий");
        MI3313OneEsrmCalculator calculator = new MI3313OneEsrmCalculator(initialData);
        assertThatThrownBy(calculator::calculate)
                .isInstanceOf(NotValidTagValueException.class)
                .hasMessageContaining("Значения тегов N_eji, T_ji, N_ji и workingOrControl не могут быть null");
    }

    @Test
    public void calculate_shouldThrowExcWhenWorkingOrControlIncorrect(){
        MI3313OneEsrmInitialData initialData = new MI3313OneEsrmInitialData();
        initialData.setK_PME(45000);
        double[][] T_ji = new double[][]{{6697, 6696, 6727, 6813}, {6912, 6941, 7036, 7077}, {7041, 7014, 6974, 6947}};
        double[][] N_eji = new double[][]{{6697, 6696, 6727, 6813}, {6912, 6941, 7036, 7077}, {7041, 7014, 6974, 6947}};
        double[][] N_ji = new double[][]{{6677, 6694, 6717, 6773}, {6911, 6967, 7004, 7037}, {7057, 7040, 7017, 6991}};
        initialData.setT_ji(T_ji);
        initialData.setN_eji(N_eji);
        initialData.setN_ji(N_ji);
        initialData.setWorkingOrControl("blabla");
        MI3313OneEsrmCalculator calculator = new MI3313OneEsrmCalculator(initialData);
        assertThatThrownBy(calculator::calculate)
                .isInstanceOf(NotValidTagValueException.class)
                .hasMessageContaining("Значение тега workingOrControl: \"blabla\", но может быть только \"рабочий\" или \"контрольный\"");
    }

    @Test
    public void calculate_shouldThrowExcWhenK_PMEZero(){
        MI3313OneEsrmInitialData initialData = new MI3313OneEsrmInitialData();
        double[][] T_ji = new double[][]{{6697, 6696, 6727, 6813}, {6912, 6941, 7036, 7077}, {7041, 7014, 6974, 6947}};
        double[][] N_eji = new double[][]{{6697, 6696, 6727, 6813}, {6912, 6941, 7036, 7077}, {7041, 7014, 6974, 6947}};
        double[][] N_ji = new double[][]{{6677, 6694, 6717, 6773}, {6911, 6967, 7004, 7037}, {7057, 7040, 7017, 6991}};
        initialData.setT_ji(T_ji);
        initialData.setN_eji(N_eji);
        initialData.setN_ji(N_ji);
        initialData.setWorkingOrControl("рабочий");
        MI3313OneEsrmCalculator calculator = new MI3313OneEsrmCalculator(initialData);
        assertThatThrownBy(calculator::calculate)
                .isInstanceOf(NotValidTagValueException.class)
                .hasMessageContaining("Значения тегов K_PME и MForK_set не могут быть равны 0");
    }
}
