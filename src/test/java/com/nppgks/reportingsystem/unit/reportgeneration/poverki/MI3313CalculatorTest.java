package com.nppgks.reportingsystem.unit.reportgeneration.poverki;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313.MI3313Calculator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313.MI3313OneEsrmInitialData;

public class MI3313CalculatorTest {

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

        MI3313Calculator calculator = new MI3313Calculator(initialData);
    }


}
