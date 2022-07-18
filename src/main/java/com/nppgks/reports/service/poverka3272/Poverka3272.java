package com.nppgks.reports.service.poverka3272;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
//@Component
public class Poverka3272 {

    // Дано:
    private boolean usedTPR;
    private OperatingFluid opFluid;
    private double[][] T_ij;
    private double V_0_KP;
    private double[][] ro_PP_ij, ro_BIK_ij;
    private double[][] Q_ij;
    private double[][] N_TPR_ij, N_mas_ij;
    private double[][] t_KP_ij, t_PP_ij, t_st_ij;;
    private double[][] N2_TPR_ij;
    private double[][] t2_KP_ij;
    private double[][] P2_KP_ij;
    private double[][] P_KP_ij, P_PP_ij;
    private double alpha_CYL_t_sq;
    private double alpha_ST_t;
    private double KFconf;
    private double K_PEP_gr;
    private double MF_setRange;
    private double delta_KP, delta_PP;
    private double delta_UOI_K;
    private double delta_t_KP, delta_t_PP;
    private double ZS;
    private double D, E, s;
    int measureCount;
    int pointsCount;

    @Autowired
    Appendix appendix;

    public Poverka3272(InitialData initialData){
        this.usedTPR = initialData.isUsedTPR();
        this.opFluid =  initialData.getOpFluid();
        this.T_ij =  initialData.getT_ij();
        this.V_0_KP =  initialData.getV_0_KP();
        this.ro_PP_ij =  initialData.getRo_PP_ij();
        this.ro_BIK_ij = initialData.getRo_BIK_ij();
        this.Q_ij = initialData.getQ_ij();
        this.N_TPR_ij = initialData.getN_TPR_ij();
        this.N_mas_ij = initialData.getN_mas_ij();
        this.t_KP_ij =  initialData.getT_KP_ij();
        this.t_PP_ij = initialData.getT_PP_ij();
        this.N2_TPR_ij = initialData.getN2_TPR_ij();
        this.P2_KP_ij = initialData.getP2_KP_ij();
        this.t2_KP_ij = initialData.getT2_KP_ij();
        this.P_PP_ij = initialData.getP_PP_ij();
        this.P_KP_ij = initialData.getP_KP_ij();
        this.alpha_CYL_t_sq = initialData.getAlpha_CYL_t_sq();
        this.alpha_ST_t = initialData.getAlpha_ST_t();
        this.KFconf = initialData.getKFconf();
        this.K_PEP_gr = initialData.getK_PEP_gr();
        this.MF_setRange = initialData.getMF_setRange();
        this.delta_KP =  initialData.getDelta_KP();
        this.delta_PP = initialData.getDelta_PP();
        this.delta_UOI_K = initialData.getDelta_UOI_K();
        this.delta_t_KP = initialData.getDelta_t_KP();
        this.delta_t_PP = initialData.getDelta_t_PP();
        this.ZS = initialData.getZS();
        this.D =  initialData.getD();
        this.s = initialData.getS();
        this.E = initialData.getE();
        this.pointsCount = Q_ij[0].length;
        this.measureCount = Q_ij.length;
    }

    public double[][] calculateQ_ij(){
        log.info("Расчет поверочного расхода, согласно п.8.3.1.4/5 формуле(3а) по МИ3272-2011");
        log.debug("Вместимость калиброванного участка КП {}", V_0_KP);
        log.debug("Время {}", Arrays.deepToString(T_ij));
        log.debug("Плотность рабочей жидкости {}", Arrays.deepToString(ro_PP_ij));
        double[][] Q_ij = new double[measureCount][pointsCount];
        for (int i = 0; i < measureCount; i++) {
            for (int j = 0; j < pointsCount; j++) {
                Q_ij[i][j] = (V_0_KP * 3600 / T_ij[i][j]) * ro_PP_ij[i][j] * 0.001;
            }
        }
        log.info("Q_ij = {}", Arrays.deepToString(Q_ij));
        return Q_ij;
    }

    public double[][] calculateK_TPR_ij(boolean repeatMeasure){
        double[][] K_TPR_ij = new double[measureCount][pointsCount];
        double[][] V_KP_pr_ij = calculateV_KP_pr_ij(repeatMeasure);
        for(int i=0; i<measureCount; i++){
            for(int j = 0; j<pointsCount; j++){
                if(repeatMeasure){
                    K_TPR_ij[i][j] = N2_TPR_ij[i][j]/V_KP_pr_ij[i][j];
                }
                else{
                    K_TPR_ij[i][j] = N_TPR_ij[i][j]/V_KP_pr_ij[i][j];
                }

            }
        }
        return K_TPR_ij;
    }

    public double[][] calculateV_KP_pr_ij(boolean repeatMeasure){
        double alpha_CYL_t = calculateAlpha_CYL_t();
        double[][] V_KP_pr_ij = new double[measureCount][pointsCount];
        for(int i=0; i< measureCount; i++){
            for(int j = 0; j< pointsCount; j++){
                if(repeatMeasure){
                    V_KP_pr_ij[i][j] = V_0_KP
                            * (1 + 2 * alpha_CYL_t * (t2_KP_ij[i][j] - 20) + alpha_ST_t * (t_st_ij[i][j] - 20))
                            * (1 + 0.95 * D / (E * s) * P2_KP_ij[i][j]);
                }
                else{
                    V_KP_pr_ij[i][j] = V_0_KP
                            * (1 + 2 * alpha_CYL_t * (t_KP_ij[i][j] - 20) + alpha_ST_t * (t_st_ij[i][j] - 20))
                            * (1 + 0.95 * D / (E * s) * P_KP_ij[i][j]);
                }

            }
        }
        return V_KP_pr_ij;
    }

    public double calculateAlpha_CYL_t(){
        return 0.5*alpha_CYL_t_sq;
    }

    public double[] calculatePi_j(){
        double[] Pi_j = new double[pointsCount];
        double[] K_TPR_j_max = new double[pointsCount];
        double[] K_TPR_j_min = new double[pointsCount];
        double[][] K_TPR_ij = calculateK_TPR_ij(false);
        for(int j=0; j<pointsCount; j++){
            double max =K_TPR_ij[0][j];
            double min = K_TPR_ij[0][j];
            for(int i =0; i< measureCount; i++){
                if(K_TPR_ij[i][j]>max){
                    max = K_TPR_ij[i][j];
                }
                if(K_TPR_ij[i][j]<min){
                    min = K_TPR_ij[i][j];
                }
            }
            K_TPR_j_max[j] = max;
            K_TPR_j_min[j] = min;
        }
        for (int j=0; j<pointsCount; j++){
            Pi_j[j] = (K_TPR_j_max[j] - K_TPR_j_min[j])/K_TPR_j_min[j]*100;
        }
        return Pi_j;
    }
    public double[] calculateK_TPR_j(boolean repeatMeasure){
        double[][] K_TPR_ij = calculateK_TPR_ij(repeatMeasure);
        double[] K_TPR_j = new double[pointsCount];
        for(int j =0; j<pointsCount; j++){
            double sum = 0;
            for(int i=0; i<measureCount; i++){
                sum = sum + K_TPR_ij[i][j];
            }
            K_TPR_j[j] = sum/measureCount;
        }
        return K_TPR_j;
    }

    public double[] calculateDelta_K_j(){
        double[] delta_K_j = new double[pointsCount];
        double[] K2_TPR_j = calculateK_TPR_j(true);
        double[] K_TPR_j = calculateK_TPR_j(false);
        for(int j=0; j< pointsCount; j++){
            delta_K_j[j] = (K2_TPR_j[j] - K_TPR_j[j])/(K_TPR_j[j])*100;
        }
        return delta_K_j;
    }

    private double[][] calculateM_TPR_re_ij(double[][] ro_PP_pr_ij){
        double[][] M_re_ij =  new double[measureCount][pointsCount];
        double[] K_TPR_j = calculateK_TPR_j(false);
        for(int i=0; i< measureCount; i++){
            for(int j=0; j<pointsCount; j++){
                M_re_ij[i][j] = N_TPR_ij[i][j]/K_TPR_j[j]*ro_PP_pr_ij[i][j]*0.001;
            }
        }
        return M_re_ij;
    }

    public double[][] calculateM_re_ij(){
        double[][] M_re_ij =  new double[measureCount][pointsCount];
        double[][] V_KP_pr_ij =  calculateV_KP_pr_ij(false);
        double[][] ro_PP_pr_ij = calculateRo_PP_pr_ij();
        if(usedTPR){
            M_re_ij = calculateM_TPR_re_ij(ro_PP_pr_ij);
        }
        else{
            for(int i = 0; i<measureCount; i++){
                for(int j =0; j< pointsCount; j++){
                    M_re_ij[i][j] = V_KP_pr_ij[i][j]*ro_PP_pr_ij[i][j]*0.001;
                }
            }
        }
        return M_re_ij;
    }

    private double[][] calculateRo_PP_pr_ij(){
        double[][] ro_PP_pr_ij = new double[measureCount][pointsCount];
        double[][] betta = appendix.calculateBetta_fluid(opFluid, ro_PP_ij, t_PP_ij);
        double[][] gamma = appendix.calculateGamma_fluid(ro_PP_ij, t_PP_ij);
        for(int i=0; i< measureCount; i++){
            for(int j = 0; j< pointsCount; j++){
                ro_PP_pr_ij[i][j] = ro_BIK_ij[i][j]*
                        (1+betta[i][j]*(t_PP_ij[i][j]-t_KP_ij[i][j]))*
                        (1+gamma[i][j]*(P_KP_ij[i][j]-P_PP_ij[i][j]));
            }
        }
        return ro_PP_pr_ij;
    }
    public double[][] calculateM_mas_ij(){
        double[][] M_mas_ij = new double[measureCount][pointsCount];
        for(int i=0; i<measureCount; i++){
            for(int j=0; j< pointsCount; j++){
                M_mas_ij[i][j] = N_mas_ij[i][j]/KFconf;
            }
        }
        return M_mas_ij;
    }

    public double[][] calculateMF_ij(){
        double[][] M_re_ij =  calculateM_re_ij();
        double [][] M_mas_ij = calculateM_mas_ij();
        double[][] MF_ij =  new double[measureCount][pointsCount];
        for(int i=0; i< measureCount; i++){
            for(int j=0; j< pointsCount; j++){
                MF_ij[i][j] = M_re_ij[i][j]*MF_setRange/M_mas_ij[i][j];
            }
        }
        return MF_ij;
    }

    public double[] calculateMF_j(){
        double[][] MF_ij =  calculateMF_ij();
        double[] MF_j =  new double[pointsCount];
        for(int j=0; j<pointsCount; j++){
            double sum = 0;
            for(int i =0; i< measureCount; i++){
                sum+= MF_ij[i][j];
            }
            MF_j[j] = sum/measureCount;
        }
        return MF_j;
    }

    public double calculateS_MF_range(){
        double[][] MF_ij = calculateMF_ij();
        double[] MF_j = calculateMF_j();
        int sigma_n_j = measureCount*pointsCount;
        double sum = 0;
        for(int j=0; j<pointsCount; j++){
            double sum1 = 0;
            for(int i=0; i<measureCount; i++){
                sum1 += Math.pow((MF_ij[i][j]-MF_j[j])/MF_j[j], 2);
            }
            sum += sum1;
        }
        double S_MF_range = Math.sqrt(sum/(sigma_n_j-1))*100;
        return S_MF_range;
    }

    public double calculateMF_range(){
        double[] MF_j = calculateMF_j();
        double sum =0;
        for(int j=0; j<pointsCount; j++){
            sum += MF_j[j];
        }
        double MF_range = sum/pointsCount;
        return MF_range;
    }

    public double calculateK_gr(){
        double MF_range = calculateMF_range();
        double K_gr = K_PEP_gr*MF_range;
        return K_gr;
    }

    public double[][] calculateKF_ij(){
        double[][] KF_ij =  new double[measureCount][pointsCount];
        double[][] M_re_ij =  calculateM_re_ij();
        for(int i = 0; i<measureCount; i++){
            for(int j =0; j<pointsCount; j++){
                KF_ij[i][j] = N_mas_ij[i][j]/M_re_ij[i][j];
            }
        }
        return KF_ij;
    }

    public double[] calculateKF_j(){
        double[] KF_j = new double[pointsCount];
        double[][] KF_ij = calculateKF_ij();
        for(int j = 0; j<pointsCount; j++){
            double sum = 0;
            for(int i=0; i<measureCount; i++){
                sum+=KF_ij[i][j];
            }
            KF_j[j] = sum/measureCount;
        }
        return KF_j;
    }

    public double calculateS_KF_range(){
        double[][] KF_ij = calculateKF_ij();
        double[] KF_j = calculateKF_j();
        double sum = 0;
        double sigma_n_j = pointsCount*measureCount;
        for(int j=0; j< pointsCount; j++){
            double sum1 = 0;
            for(int i=0; i< measureCount; i++){
                sum1+=Math.pow((KF_ij[i][j]-KF_j[j])/KF_j[j], 2);
            }
            sum += sum1;
        }
        double S_KF_range = Math.sqrt(sum/sigma_n_j)*100;
        return S_KF_range;
    }

    public double[] calculateS_KF_k(){
        double[] S_KF_k =  new double[pointsCount-1];
        double[][] KF_ij = calculateKF_ij();
        double[] KF_j = calculateKF_j();
        double sigma_n_j = measureCount*2;
        double sum = 0;
        for(int k = 0; k<pointsCount-1; k++){
            for(int j = k; j<=k+1; j++){
                double sum1 = 0;
                for(int i = 0; i<measureCount; j++){
                    sum1+=Math.pow((KF_ij[i][j]-KF_j[j])/KF_j[j], 2);
                }
                sum += sum1;
            }
            S_KF_k[k] = Math.sqrt(sum/sigma_n_j)*100;
        }
        return S_KF_k;
    }

    public double calculateKF_range(){
        double[] KF_j = calculateKF_j();
        double sum = 0;
        for(int j = 0; j<pointsCount; j++){
            sum+=KF_j[j];
        }
        double KF_range = sum/pointsCount;
        return KF_range;
    }

    public double calculateEpsilon_PEP(){
        double S_MF_range = calculateS_MF_range();
        double epsilon = Appendix.get_t_P_n(measureCount)*S_MF_range;
        return epsilon;
    }
    public double calculateTheta_sigma_PEP(){
        double theta_t = calculateTheta_t();
        double theta_MF_range = calculateTheta_MF_range();
        double delta_mas_0 = calculateDeltaMas_0();
        double theta_sigma = 1.1*Math.sqrt(Math.pow(delta_KP, 2)+
                Math.pow(delta_PP, 2)+
                Math.pow(theta_t, 2)+
                Math.pow(delta_UOI_K, 2)+
                Math.pow(theta_MF_range, 2)+
                Math.pow(delta_mas_0, 2));
        return theta_sigma;
    }

    private double calculateDeltaMas_0() {
        double Q_max = Arrays.stream(Q_ij).map(Q_j -> Arrays.stream(Q_j).max().getAsDouble())
                .max(Double::compare).get();
        double Q_min = Arrays.stream(Q_ij).map(Q_j -> Arrays.stream(Q_j).min().getAsDouble())
                .min(Double::compare).get();
        double delta_mas_0 = 2*ZS/(Q_min+Q_max)*100;
        return delta_mas_0;
    }

    private double calculateTheta_MF_range() {
        double[] MF_j = calculateMF_j();
        double MF_range = calculateMF_range();
        double max = 0;
        for(int j = 0; j<pointsCount; j++){
            max = Math.max(max, Math.abs((MF_j[j] - MF_range)/MF_range));
        }
        double theta_MF_range = max*100;
        return theta_MF_range;
    }

    private double calculateTheta_t() {
        double betta_fluid_max = appendix.getBetta_fluid_max();
        double theta_t = betta_fluid_max*Math.sqrt(Math.pow(delta_t_KP, 2)+Math.pow(delta_t_PP, 2))*100;
        return theta_t;
    }

    public double calculateDelta_PEP(){

        double theta_sigma = calculateTheta_sigma_PEP();
        double S_MF_range = calculateS_MF_range();
        double epsilon = calculateEpsilon_PEP();
        double ratio = theta_sigma/S_MF_range;
        double Z_P = appendix.getZ_P(ratio);
        double delta = -1;
        if(ratio>=0.8 && ratio<=8){
            delta = Z_P*(theta_sigma+epsilon);
        }
        else if(ratio > 0.8){
            delta = theta_sigma;
        }
        return delta;
    }

    public double calculateEpsilon_SOI_linear(){
        double t_P_n = Appendix.get_t_P_n(pointsCount*measureCount);
        double S_KF_range = calculateS_KF_range();
        double epsilon = t_P_n*S_KF_range;
        return epsilon;
    }

    public double calculateTheta_sigma_SOI_linear(){
        double theta_t =  calculateTheta_t();
        double theta_KF_range = calculateTheta_KF_range();
        double delta_mas_0 = calculateDeltaMas_0();
        double theta_sigma = 1.1*Math.sqrt(
                Math.pow(delta_KP, 2)+
                        Math.pow(delta_PP, 2)+
                        Math.pow(theta_t, 2)+
                        Math.pow(delta_UOI_K, 2)+
                        Math.pow(theta_KF_range, 2)+
                        Math.pow(delta_mas_0, 2));
        return theta_sigma;
    }

    public double calculateDelta_SOI_linear(){
        double theta_sigma = calculateTheta_sigma_SOI_linear();
        double S_KF_range = calculateS_KF_range();
        double epsilon = calculateEpsilon_SOI_linear();
        double ratio = theta_sigma/S_KF_range;
        double Z_P = appendix.getZ_P(ratio);
        double delta = -1;
        if(ratio>=0.8 && ratio<=8){
            delta = Z_P*(theta_sigma+epsilon);
        }
        else if(ratio > 0.8){
            delta = theta_sigma;
        }
        return delta;
    }

    public double[] calculateEpsilon_SOI_pwLinear(){
        double t_P_n = Appendix.get_t_P_n(measureCount*2);
        double[] S_KF_k = calculateS_KF_k();
        double[] epsilon_k =  new double[S_KF_k.length];
        for(int k =0; k< S_KF_k.length; k++){
            epsilon_k[k] = t_P_n*S_KF_k[k];
        }
        return epsilon_k;
    }

    public double[] calculateTheta_sigma_k(){
        double[] theta_KF_k = calculateTheta_KF_k();
        double theta_t =  calculateTheta_t();
        double[] delta_mas_0_k = calculateDeltaMas_0_k();
        int length = theta_KF_k.length;
        double[] theta_sigma_k =  new double[length];
        for(int k =0; k<length; k++){
            theta_sigma_k[k] = 1.1*Math.sqrt(
                    Math.pow(delta_KP, 2)+
                    Math.pow(delta_PP, 2)+
                    Math.pow(theta_t, 2)+
                    Math.pow(delta_UOI_K, 2)+
                    Math.pow(theta_KF_k[k], 2)+
                    Math.pow(delta_mas_0_k[k], 2));
        }
        return theta_sigma_k;
    }

    private double[] calculateDeltaMas_0_k() {
        int length = pointsCount-1;
        double[] delta_mas_0k = new double[length];
        for(int k = 0; k< length; k++){
            double Q_max1 = Arrays.stream(Q_ij[k]).max().getAsDouble();
            double Q_max2 = Arrays.stream(Q_ij[k+1]).max().getAsDouble();
            double Q_max = Math.max(Q_max1, Q_max2);

            double Q_min1 = Arrays.stream(Q_ij[k]).min().getAsDouble();
            double Q_min2 = Arrays.stream(Q_ij[k+1]).min().getAsDouble();
            double Q_min = Math.max(Q_min1, Q_min2);

            delta_mas_0k[k] = 2*ZS/(Q_min+Q_max)*100;
        }

        return delta_mas_0k;
    }

    private double[] calculateTheta_KF_k() {
        double[] KF_j =  calculateKF_j();
        int length = pointsCount-1;
        double[] theta_KF_k =  new double[length];
        double[] absKF = new double[length];
        for(int j = 0; j< length; j++){
            absKF[j] =Math.abs((KF_j[j] - KF_j[j+1])/(KF_j[j] +KF_j[j+1]));
        }
        Arrays.sort(absKF);
        for(int k =0; k<length; k++){
            theta_KF_k[k] = 0.5*absKF[k]*100;
        }
        return theta_KF_k;
    }
    public double[] calculateDelta_k(){
        int length = pointsCount-1;
        double[] delta_k = new double[length];
        double[] theta_sigma_k = calculateTheta_sigma_k();
        double[] S_KF_k = calculateS_KF_k();
        double[] epsilon_k = calculateEpsilon_SOI_pwLinear();
        for(int k =0; k< length; k++){

            double ratio = theta_sigma_k[k]/S_KF_k[k];
            double Z_P = appendix.getZ_P(ratio);
            if(ratio>=0.8 && ratio<=8){
                delta_k[k] = Z_P*(theta_sigma_k[k]+epsilon_k[k]);
            }
            else if(ratio > 0.8){
                delta_k[k] = theta_sigma_k[k];
            }
        }
        return delta_k;
    }

    private double calculateTheta_KF_range() {
        double[] KF_j = calculateKF_j();
        double KF_range = calculateKF_range();
        double max = 0;
        for(int j = 0; j<pointsCount; j++){
            max = Math.max(max, Math.abs((KF_j[j] - KF_range)/KF_range));
        }
        double theta_KF_range = max*100;
        return theta_KF_range;
    }

    public boolean checkDelta_PEP(String typeOfMassomer){
        double val = 0.2;
        if(typeOfMassomer.equals("рабочий")){
            val = 0.25;
        }
        double delta = calculateDelta_PEP();
        if(Math.abs(delta)<=val){
            return true;
        }
        return false;
    }

    public boolean checkDelta_SOI_linear(String typeOfMassomer){
        double val = 0.2;
        if(typeOfMassomer.equals("рабочий")){
            val = 0.25;
        }
        double delta = calculateDelta_SOI_linear();
        if(Math.abs(delta)<=val){
            return true;
        }
        return false;
    }

    public boolean checkDelta_SOI_pwLinear(String typeOfMassomer){
        double val = 0.2;
        if(typeOfMassomer.equals("рабочий")){
            val = 0.25;
        }
        double delta_max = Arrays.stream(calculateDelta_k()).map(delta -> Math.abs(delta)).max().getAsDouble();
        if(Math.abs(delta_max)<=val){
            return true;
        }
        return false;
    }

}
