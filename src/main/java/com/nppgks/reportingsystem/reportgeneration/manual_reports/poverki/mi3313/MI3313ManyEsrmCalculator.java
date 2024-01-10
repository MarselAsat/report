package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313;

import com.nppgks.reportingsystem.exception.NotValidTagValueException;
import com.nppgks.reportingsystem.util.DataRounder;

import java.util.List;

import static com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.MI3313Formulas.*;

public class MI3313ManyEsrmCalculator {
    private final MI3313ManyEsrmInitialData initialData;

    public MI3313ManyEsrmCalculator(MI3313ManyEsrmInitialData initialData) {
        this.initialData = initialData;
    }

    private void validate() {
        if(initialData.getN_ji() == null || initialData.getT_ji() == null || initialData.getN_ejik() == null || initialData.getWorkingOrControl() == null){
            throw new NotValidTagValueException("Значения тегов N_eji, T_ji, N_ji и workingOrControl не могут быть null");
        }
        int measureCount = initialData.getT_ji()[0].length;
        int pointsCount = initialData.getT_ji().length;
        boolean incorrectArrLen = false;

        for(double[][] N_eji:initialData.getN_ejik()) {
            incorrectArrLen = measureCount != N_eji[0].length || pointsCount != N_eji.length;
        }

        incorrectArrLen = incorrectArrLen || measureCount != initialData.getN_ji()[0].length || pointsCount != initialData.getN_ji().length;

        if(incorrectArrLen){
            throw new NotValidTagValueException("Неверная длина массивов входных данных: N_ejik, T_ji или N_ji");
        }
        if((!initialData.getWorkingOrControl().equalsIgnoreCase("рабочий")) && (!initialData.getWorkingOrControl().equalsIgnoreCase("контрольный"))){
            throw new NotValidTagValueException("Значение параметра workingOrControl: \"%s\", но может быть только \"рабочий\" или \"контрольный\"".formatted(initialData.getWorkingOrControl()));
        }
        for(double K_PME:initialData.getK_PMEk()){
            if(K_PME == 0 || initialData.getMForK_set() == 0){
                throw new NotValidTagValueException("Значения в массиве K_PMEk не могут быть равны 0");
            }
        }
        if(initialData.getMForK_set() == 0){
            throw new NotValidTagValueException("Значение параметра MForK_set не могжет быть равно 0");
        }
    }

    public MI3313ManyEsrmFinalData calculate() {
        validate();
        List<double[][]> M_ejik = calculateM_ejik(initialData.getN_ejik(), initialData.getK_PMEk());
        double[][] M_eji = calculateM_eji_multipleEsrm(M_ejik);
        List<double[][]> Q_jik = calculateQ_jik(M_ejik, initialData.getT_ji());
        double[][] Q_ji = calculateQ_ji(M_eji, initialData.getT_ji());
        double[] Q_j = calculateQ_j(Q_ji);
        double Q_min = calculateQ_min(Q_j);
        double Q_max = calculateQ_max(Q_j);
        double[][] M_ji = calculateM_ji(initialData.getN_ji(), initialData.getK_PM());
        double[][] MForK_ji = calculateMForK_ji(M_eji, M_ji, initialData.getMForK_set());
        double[] MForK_j = calculateMForK_j(MForK_ji);
        double MForK = calculateMForK(MForK_j);
        double[] S_j = calculateS_j(MForK_ji, MForK_j);
        double theta_A = calculateTheta_A(MForK_j, MForK);

        double theta_Z = 0;
        if (initialData.getZS() != null) {
            theta_Z = calculateTheta_Z(initialData.getZS(), Q_min);
        }

        double Q_t = initialData.getQ_Mmax();
        if (initialData.getQ_nom() != null) {
            Q_t = initialData.getQ_nom();
        }

        double theta_Mt = 0;
        if (initialData.getDelta_tdop() != null && initialData.getT_min() != null && initialData.getT_max() != null) {
            double delta_t = calculateDelta_tOrP(initialData.getT_max(), initialData.getT_P(), initialData.getT_min());
            theta_Mt = calculateTheta_Mt(initialData.getDelta_tdop(), Q_t, delta_t, Q_min);
        }

        double theta_MP = 0;
        if (initialData.getDelta_Pdop() != null && initialData.getP_min() != null && initialData.getP_max() != null) {
            double delta_P = calculateDelta_tOrP(initialData.getP_max(), initialData.getP_P(), initialData.getP_min());
            theta_MP = calculateTheta_MP(initialData.getDelta_Pdop(), delta_P);
        }
        double theta_sigma = calculateTheta_sigma(initialData.getDelta_e(), initialData.getDelta_ivk(), theta_A, theta_Z, theta_Mt, theta_MP);

        double[] S_0j = calculateS_0j(S_j, M_ji[0].length);
        double[] t_095j = calculateT_095j(M_ji[0].length, S_0j.length);
        double[] epsilon_j = calculateEpsilon_j(t_095j, S_0j);
        double epsilon = calculateEpsilon(epsilon_j);
        double S_0 = calculateS_0(epsilon_j, S_0j);
        double S_theta = calculateS_theta(initialData.getDelta_e(), initialData.getDelta_ivk(), theta_A, theta_Z, theta_Mt, theta_MP);
        double t_sigma = calculateT_sigma(epsilon, theta_sigma, S_0, S_theta);
        double S_sigma = calculateS_sigma(S_theta, S_0);
        double delta = calculateDelta(epsilon, theta_sigma, S_0, t_sigma, S_sigma);

        boolean valid = (initialData.getWorkingOrControl().equals("рабочий") && delta <= 0.25) || (initialData.getWorkingOrControl().equals("контрольный") && delta <= 0.2);

        Q_ji = DataRounder.round2DimArray(Q_ji, 1);
        Q_j = DataRounder.roundArray(Q_j, 1);
        Q_max = DataRounder.roundDouble(Q_max, 1);
        Q_min = DataRounder.roundDouble(Q_min, 1);

        S_j = DataRounder.roundArray(S_j, 3);
        S_0j = DataRounder.roundArray(S_0j, 3);
        epsilon_j = DataRounder.roundArray(epsilon_j, 3);
        S_0 = DataRounder.roundDouble(S_0, 3);
        epsilon = DataRounder.roundDouble(epsilon, 3);
        theta_A = DataRounder.roundDouble(theta_A, 3);
        theta_Z = DataRounder.roundDouble(theta_Z, 3);
        theta_Mt = DataRounder.roundDouble(theta_Mt, 3);
        theta_MP = DataRounder.roundDouble(theta_MP, 3);
        theta_sigma = DataRounder.roundDouble(theta_sigma, 3);
        delta = DataRounder.roundDouble(delta, 3);
        MForK_ji = DataRounder.round2DimArrayToSignDig(MForK_ji, 5);
        MForK_j = DataRounder.roundArrayToSignDig(MForK_j, 5);
        MForK = DataRounder.roundToSignificantDigits(MForK, 5);
        M_eji = DataRounder.round2DimArrayToSignDig(M_eji, 6);
        M_ji = DataRounder.round2DimArrayToSignDig(M_ji, 6);

        MI3313ManyEsrmFinalData finalData = new MI3313ManyEsrmFinalData();
        finalData.setM_ejik(M_ejik);
        finalData.setM_eji(M_eji);
        finalData.setQ_jik(Q_jik);
        finalData.setQ_ji(Q_ji);
        finalData.setQ_j(Q_j);
        finalData.setQ_min(Q_min);
        finalData.setQ_max(Q_max);
        finalData.setM_ji(M_ji);
        finalData.setMForK_ji(MForK_ji);
        finalData.setMForK_j(MForK_j);
        finalData.setMForK(MForK);
        finalData.setS_j(S_j);
        finalData.setTheta_sigma(theta_sigma);
        finalData.setTheta_A(theta_A);
        finalData.setTheta_Z(theta_Z);
        finalData.setTheta_Mt(theta_Mt);
        finalData.setTheta_MP(theta_MP);
        finalData.setS_0j(S_0j);
        finalData.setEpsilon_j(epsilon_j);
        finalData.setEpsilon(epsilon);
        finalData.setT_095j(t_095j);
        finalData.setDelta(delta);
        finalData.setS_0(S_0);
        finalData.setConclusion(valid ? "годен" : "не годен");
        return finalData;
    }
}
