package com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.CommonFunctions;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.Appendix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class MI3313Formulas {

    // формула (2) для одного ЭСРМ
    public static double[][] calculateM_eji_oneEsrm(double[][] N_eji, double K_PME) {
        return CommonFunctions.divide2DimArrayByNumber(N_eji, K_PME);
    }

    // формула (2) для нескольких ЭСРМ
    public static double[][] calculateM_eji_multipleEsrm(List<double[][]> M_ejik) {
        int n = M_ejik.get(0).length;
        int m = M_ejik.get(0)[0].length;
        double[][] M_eji = new double[n][m];
        for (double[][] array : M_ejik) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    M_eji[i][j] += array[i][j];
                }
            }
        }
        return M_eji;
    }

    // формула (3)
    public static List<double[][]> calculateM_ejik(List<double[][]> N_ejik, double[] K_PMEk) {
        int esrmCount = N_ejik.size();
        List<double[][]> M_ejik = new ArrayList<>();
        for (int k = 0; k < esrmCount; k++) {
            M_ejik.add(CommonFunctions.divide2DimArrayByNumber(N_ejik.get(k), K_PMEk[k]));
        }
        return M_ejik;
    }

    // формула 4
    public static List<double[][]> calculateQ_jik(List<double[][]> M_ejik, double[][] T_ji) {
        List<double[][]> Q_jik = new ArrayList<>();
        for (double[][] M_eji : M_ejik) {
            Q_jik.add(calculateQ_ji(M_eji, T_ji));
        }
        return Q_jik;
    }

    // формула (5)
    public static double[][] calculateQ_ji(double[][] M_eji, double[][] T_ji) {
        int n = T_ji.length;
        int m = T_ji[0].length;
        double[][] Q_ji = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Q_ji[i][j] = M_eji[i][j] * 3600 / T_ji[i][j];
            }
        }
        return Q_ji;
    }

    // формула (6)
    public static double[] calculateQ_j(double[][] Q_ji) {
        return CommonFunctions.getAverageForEachRow(Q_ji);
    }

    // формула (7)
    public static double calculateQ_min(double[] Q_j) {
        return CommonFunctions.getMinInArray(Q_j);
    }

    // формула (8)
    public static double calculateQ_max(double[] Q_j) {
        return CommonFunctions.getMaxInArray(Q_j);
    }

    // формула (9)
    public static double[][] calculateM_ji(double[][] N_ji, double K_PM) {
        return CommonFunctions.divide2DimArrayByNumber(N_ji, K_PM);
    }

    // формула (10) и (13)
    public static double calculateMForK(double[] MForK_j) {
        return Arrays.stream(MForK_j).average().orElseThrow();
    }

    // формула (11) и (14)
    public static double[] calculateMForK_j(double[][] K_Mji) {
        return CommonFunctions.getAverageForEachRow(K_Mji);
    }

    // формула (12) и (15)
    public static double[][] calculateMForK_ji(double[][] M_eji, double[][] M_ji, double MForK_set) {
        double[][] temp = CommonFunctions.divide2DimArrayBy2DimArray(M_eji, M_ji);
        return CommonFunctions.divide2DimArrayByNumber(temp, 1 / MForK_set);
    }

    // формула (16)
    public static double[] calculateS_j(double[][] MForK_ji, double[] MForK_j) {
        int m = MForK_ji.length;
        int n = MForK_ji[0].length;
        double[] S_j = new double[m];
        for (int j = 0; j < m; j++) {
            double sum = 0;
            for (int i = 0; i < n; i++) {
                sum += Math.pow(MForK_ji[j][i] - MForK_j[j], 2);
            }
            S_j[j] = Math.sqrt(sum / (n - 1)) * (100 / MForK_j[j]);
        }
        return S_j;
    }

    // формула (18)
    public static double calculateTheta_sigma(double theta_M, double theta_IVK, double theta_A, double theta_Z, double theta_Mt, double theta_MP) {
        return 1.1 * Math.sqrt(
                Math.pow(theta_M, 2) +
                        Math.pow(theta_IVK, 2) +
                        Math.pow(theta_A, 2) +
                        Math.pow(theta_Z, 2) +
                        Math.pow(theta_Mt, 2) +
                        Math.pow(theta_MP, 2));
    }

    // формула (21)
    public static double calculateTheta_A(double[] MForK_j, double MForK) {
        return Arrays.stream(MForK_j).map(el -> Math.abs(
                        (el - MForK) / MForK) * 100)
                .max().orElseThrow();
    }

    // формула (22)
    public static double calculateTheta_Z(double ZS, double Q_min){
        return ZS/Q_min*100;
    }

    // формула (23)
    public static double calculateTheta_Mt(double delta_tdop, double Q_t, double delta_t, double Q_min){
        return (delta_tdop*Q_t*delta_t)/Q_min;
    }

    // формула (25) и (27)
    public static double calculateDelta_tOrP(double tOrP_max, double tOrP_P, double tOrP_min){
        return Math.max(tOrP_max-tOrP_P, tOrP_P-tOrP_min);
    }

    // формула (26)
    public static double calculateTheta_MP(double delta_Pdop, double deltaP){
        return 10*delta_Pdop*deltaP;
    }

    // формула (28)
    public static double[] calculateS_0j(double[] S_j, int measureCount){
        return Arrays.stream(S_j).map(s -> s/Math.sqrt(measureCount)).toArray();
    }

    // формула (29)
    public static double calculateEpsilon(double[] epsilon_j){
        return CommonFunctions.getMaxInArray(epsilon_j);
    }

    // формула (30)
    public static double[] calculateEpsilon_j(double[] t_095, double[] S_0j){
        return CommonFunctions.multiplyArrayByArray(t_095, S_0j);
    }

    // формула (31)
    public static double calculateDelta(double epsilon, double theta_sigma, double S_0, double t_sigma, double S_sigma){
        double ratio = theta_sigma/S_0;
        if(ratio < 0.8){
            return epsilon;
        }
        else if(ratio >= 0.8 && ratio <= 8){
            return t_sigma*S_sigma;
        }
        else{
            return theta_sigma;
        }
    }

    public static double calculateS_0(double[] epsilon_j, double[] S_0j){
        int maxIndex = 0;
        double maxEpsilon = epsilon_j[0];
        for (int i = 0; i < S_0j.length; i++) {
            if(epsilon_j[i] > maxEpsilon){
                maxIndex = i;
            }
        }
        return S_0j[maxIndex];
    }

    // формула (32)
    public static double calculateT_sigma(double epsilon, double theta_sigma, double S_0, double S_theta){
        return (epsilon+theta_sigma)/(S_0+S_theta);
    }

    // формула (33)
    public static double calculateS_sigma(double S_theta, double S_0){
        return Math.sqrt(Math.pow(S_theta, 2)+Math.pow(S_0, 2));
    }

    // формула (34)
    public static double calculateS_theta(double theta_M, double theta_IVK, double theta_A, double theta_Z, double theta_Mt, double theta_MP){
        return Math.sqrt(
                (Math.pow(theta_M, 2)+
                        Math.pow(theta_IVK, 2)+
                        Math.pow(theta_A, 2)+
                        Math.pow(theta_Z, 2)+
                        Math.pow(theta_Mt, 2)+
                        Math.pow(theta_MP, 2))/3);
    }

    public static double[] calculateT_095j(int measureCount, int pointsCount) {
        return IntStream.range(0, pointsCount).mapToDouble(el -> Appendix.get_t_P_n(measureCount)).toArray();
    }

}
