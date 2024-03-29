package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622.calculations;

import com.nppgks.reportingsystem.constants.MI3622Constants;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.CommonFunctions;
import com.nppgks.reportingsystem.exception.NotValidTagValueException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@SuppressWarnings("unused")
@Slf4j
public class MI3622Calculation {
    private final double[][] Q_i_j;
    private final double[][] N_e_i_j;
    private final double[][] N_p_i_j;
    private final double[][] T_i_j;
    private final double f_p_max;
    private final double Q_p_max;
    private final Double MF_p;
    private final double[] K_e_arr;
    private final double[] Q_e_arr;
    private final int measureCount;
    private final int pointsCount;
    private final double ZS;
    private final double theta_e;
    private final double theta_t;
    private final double theta_p;
    private final double theta_N;
    private final double theta_Dt;
    private final double theta_Dp;
    private final double theta_PDt;
    private final double theta_PDp;

    /**
     * Способ реализации градуировочной характеристики СРМ: при определении коэффициента коррекции {MF} (по умолчанию), при определении коэффициента преобразования {K}
     */
    private final String MFOrK;

    /**
     * С коррекцией стабильности нуля {true} или без коррекции стабильности нуля {false} (по умолчанию)
     */
    private final boolean zeroStabilityCorr;

    /**
     * СРМ применяется в качестве контрольного {контрольный} или рабочего {рабочий} (по умолчанию)
     */
    private final String operatingOrControlCPM;


    /**
     * Вид реализации градуировочной характеристики СРМ: в рабочем диапазоне измерений {рабочий диапазон} (по умолчанию), в поддиапазонах рабочего диапазона измерений {поддиапазон}
     */
    private final String rangeType;

    public MI3622Calculation(MI3622InitialData data) {
        this.Q_i_j = data.getQ_ij();
        this.N_e_i_j = data.getN_e_ij();
        this.N_p_i_j = data.getN_p_ij();
        this.T_i_j = data.getT_ij();
        this.f_p_max = data.getF_p_max();
        this.Q_p_max = data.getQ_p_max();
        this.K_e_arr = data.getK_e_arr();
        this.MF_p = data.getMF_p();
        this.ZS = data.getZS();
        this.theta_e = data.getTheta_e();
        this.theta_t = data.getTheta_t();
        this.theta_p = data.getTheta_p();
        this.theta_N = data.getTheta_N();
        this.theta_PDt = data.getTheta_PDt();
        this.theta_PDp = data.getTheta_PDp();
        this.theta_Dt = data.getTheta_Dt();
        this.theta_Dp = data.getTheta_Dp();
        measureCount = data.getMeasureCount();
        pointsCount = data.getPointsCount();
        Q_e_arr = data.getQ_e_arr();
        this.MFOrK = (data.getMFOrK() == null || data.getMFOrK().isBlank()) ? MI3622Constants.MF : data.getMFOrK();
        this.zeroStabilityCorr = data.isZeroStabilityCorr();
        this.operatingOrControlCPM = (data.getOperatingOrControlCPM() == null || data.getOperatingOrControlCPM().isBlank()) ? MI3622Constants.OPERATING : data.getOperatingOrControlCPM();
        this.rangeType = (data.getRangeType() == null || data.getRangeType().isBlank()) ? MI3622Constants.OPERATING_RANGE : data.getRangeType();
    }

    public double calculateK_pm() {
        log.info("Рассчет коэффициента преобразования поверяемого СРМ (K_pm, имп/т) согласно п.7.1 по формуле (1) МИ3622-2020");

        double Kpm = f_p_max * 3.6 / Q_p_max;

        log.debug("Максимальное значение частоты, установленное в СРМ (f_r_max, Гц) {}", f_p_max);
        log.debug("Максимальное значение расхода (Q_r_max, т/ч) {}", Q_p_max);
        log.info("K_pm = {}", Kpm);

        return Kpm;
    }

    public double[][] calculateK_e_ij() {
        double[][] K_e_ij = new double[measureCount][pointsCount];
        int eLen = K_e_arr.length;
        if (eLen == 1) {
            for (int i = 0; i < measureCount; i++) {
                Arrays.fill(K_e_ij[i], K_e_arr[0]);
            }
        } else {
            if (eLen != Q_e_arr.length)
                throw new NotValidTagValueException("Длины массивов Q_e_arr=%s и K_e_arr=%s должны совпадать"
                        .formatted(Arrays.toString(Q_e_arr), Arrays.toString(K_e_arr)));
            K_e_ij = CommonFunctions.linearInterpolation(Q_i_j, Q_e_arr, K_e_arr);
        }
        return K_e_ij;
    }

    public double[][] calculateM_e_ij() {
        log.info("Рассчет массы измеряемой среды, измеренная преобразователем массового расхода (M_e, т) согласно п.7.2 по формуле (3) МИ3622-2020");

        double[][] K_e = calculateK_e_ij();
        double[][] M_e = CommonFunctions.getDivisionOfTwoArrays(N_e_i_j, K_e);

        log.debug("Кол-во импульсов, поступившее с ПР (N_e, имп) {}", Arrays.deepToString(N_e_i_j));
        log.debug("Коэффициент преобразования ПР, вычисленный по градуировочной характеристике (K_e, имп/т) {}", Arrays.deepToString(K_e));
        log.info("M_e = {}", Arrays.deepToString(M_e));

        return M_e;
    }

    public double[][] calculateMF_ij() {
        log.info("Рассчет коэффициента коррекции поверяемого СРМ (MF_ij) согласно п.7.2 по формуле (2) МИ3622-2020");

        double[][] MF = null;
        if (MFOrK.equalsIgnoreCase(MI3622Constants.MF)) {
            double Kpm = calculateK_pm();
            double[][] M_e = calculateM_e_ij();
            double[][] M = new double[measureCount][pointsCount];
            for (int i = 0; i < measureCount; i++) {
                for (int j = 0; j < pointsCount; j++) {
                    M[i][j] = N_p_i_j[i][j] * MF_p / Kpm;
                }
            }
            MF = new double[measureCount][pointsCount];
            for (int i = 0; i < measureCount; i++) {
                for (int j = 0; j < pointsCount; j++) {
                    MF[i][j] = M_e[i][j] / M[i][j];
                }
            }

            log.debug("Масса измеряемой среды, измеренная преобразователем массового расхода (M_e, т) {}", Arrays.deepToString(M_e));
            log.debug("Масса измеряемой среды, измеренная поверяемым СРМ (M_ij, т) {}", Arrays.deepToString(M));
            log.info("MF_ij = {}", Arrays.deepToString(MF));
        }

        return MF;
    }

    public Double calculateMF() {
        log.info("Рассчет среднего значения коэффициента коррекции поверяемого СРМ (MF) согласно п.7.4 по формуле (7) МИ3622-2020");

        Double MF = null;

        if (MFOrK.equalsIgnoreCase(MI3622Constants.MF)) {
            double[] MF_j = calculateMF_j();
            MF = 0d;

            for (int j = 0; j < pointsCount; j++) {
                MF = MF + MF_j[j];
            }

            MF = MF / pointsCount;

            log.debug("Значения коэффициентов коррекции поверяемого СРМ (MF_j) {}", Arrays.toString(MF_j));
            log.debug("Количество точек (m) {}", pointsCount);
            log.info("MF = {}", MF);
        }

        return MF;
    }

    public double[] calculateMF_j() {
        log.info("Рассчет значения коэффициента коррекции поверяемого СРМ в j-й точке (MF) согласно п.7.3 по формуле (6) МИ3622-2020");

        double[] MF_j = null;
        if (MFOrK.equalsIgnoreCase(MI3622Constants.MF)) {
            double[][] MF_ij = calculateMF_ij();
            MF_j = CommonFunctions.getAverageForEachColumn(MF_ij);

            log.debug("Значения коэффициентов коррекции поверяемого СРМ (MF) {}", Arrays.deepToString(MF_ij));
            log.debug("Количество измерений (n) {}", measureCount);
            log.info("MF_j = {}", Arrays.toString(MF_j));
        }

        return MF_j;
    }

    public Double calculateK() {
        Double K = null;
        if (MFOrK.equalsIgnoreCase(MI3622Constants.K)) {
            double[] Kj = calculateK_j();
            K = 0d;
            for (int j = 0; j < pointsCount; j++) {
                K = K + Kj[j];
            }
            K = K / pointsCount;
        }

        return K;
    }

    public double[] calculateK_j() {
        double[] Kj = null;
        if (MFOrK.equalsIgnoreCase(MI3622Constants.K)) {
            double[][] K_ij = calculateK_ij();
            Kj = CommonFunctions.getAverageForEachColumn(K_ij);
        }

        return Kj;
    }

    public double[][] calculateK_ij() {
        double[][] K_ij = null;
        if (MFOrK.equalsIgnoreCase(MI3622Constants.K)) {
            K_ij = new double[measureCount][pointsCount];
            double[][] M_e_ij = calculateM_e_ij();
            for (int i = 0; i < measureCount; i++) {
                for (int j = 0; j < pointsCount; j++) {
                    K_ij[i][j] = N_p_i_j[i][j] / M_e_ij[i][j];
                }
            }
        }

        return K_ij;
    }

    public Double calculateMF_prime() {
        Double MF_prime = null;
        if (MFOrK.equalsIgnoreCase(MI3622Constants.MF) && MF_p != null) {
            return MF_p * calculateMF();
        }
        return MF_prime;
    }

    public double[][] calculateF_ij() {
        log.info("Рассчет частоты выходного сигнала поверяемого СРМ (f, Гц) согласно п.7.9 по формуле (12) МИ3622-2020");

        double[][] f_ij = CommonFunctions.getDivisionOfTwoArrays(N_p_i_j, T_i_j);

        log.debug("кол-во импульсов, поступившее с поверяемого СРМ (N_r, имп) {}", Arrays.deepToString(N_p_i_j));
        log.debug("время измерения (T, с) {}", Arrays.deepToString(T_i_j));
        log.info("f_ij = {}", Arrays.deepToString(f_ij));

        return f_ij;
    }

    public double[] calculateF_j() {
        double[][] f_ij = calculateF_ij();
        return CommonFunctions.getAverageForEachColumn(f_ij);
    }

    public double[] calculateS_j() {
        log.info("Рассчет СКО результатов измерений в j-й точке (S_j) согласно п.7.11 по формуле (14) МИ3622-2020");

        double[][] MFOrK_ij;
        double[] MFOrK_j;
        if (MFOrK.equalsIgnoreCase(MI3622Constants.MF)) {
            MFOrK_ij = calculateMF_ij();
            MFOrK_j = calculateMF_j();
        } else if (MFOrK.equalsIgnoreCase(MI3622Constants.K)) {
            MFOrK_ij = calculateK_ij();
            MFOrK_j = calculateK_j();
        } else {
            throw new NotValidTagValueException("Значение поля MForK=%s, но оно должно быть либо '%s', либо '%s'"
                    .formatted(MFOrK, MI3622Constants.MF, MI3622Constants.K));
        }

        double[] S_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            double sum = 0;
            for (int i = 0; i < measureCount; i++) {
                sum = sum + (Math.pow(MFOrK_ij[i][j] - MFOrK_j[j], 2));
            }
            S_j[j] = 1 / MFOrK_j[j] * Math.sqrt(sum / (measureCount - 1)) * 100;
        }

        log.debug("Значения коэффициентов преобразования/коррекции поверяемого СРМ (MF_ij или K_ij) {}", Arrays.deepToString(MFOrK_ij));
        log.debug("Среднее значение коэффициента преобразования/коррекции поверяемого СРМ в j-й точке (MF_j или K_j) {}", Arrays.toString(MFOrK_j));
        log.debug("Кол-во измерений (n) {}", measureCount);
        log.info("S_j = {}", Arrays.toString(S_j));

        return S_j;
    }

    public boolean checkS_j() {
        double[] S_j = calculateS_j();
        return CommonFunctions.getMaxInArray(S_j) <= 0.03;
    }

    public String calculateConclusion() {
        String isValid = "годен";
        String isNotValid = "не годен";
        if (operatingOrControlCPM.equalsIgnoreCase(MI3622Constants.OPERATING)) {
            if (rangeType.equalsIgnoreCase(MI3622Constants.OPERATING_RANGE)) {
                double delta_D = calculateDelta_D();
                if (delta_D <= 0.25) return isValid;
                else return isNotValid;
            } else if (rangeType.equalsIgnoreCase(MI3622Constants.SUBRANGE)) {
                double[] delta_PDk = calculateDelta_PDk();
                if (delta_PDk == null) {
                    return "Не может быть вычислено delta_PDk, т.к. кол-во точек < 2";
                } else {
                    if (CommonFunctions.getMaxInArray(delta_PDk) <= 0.25) return isValid;
                    else return isNotValid;
                }
            } else {
                throw new NotValidTagValueException("Значение поля rangeType=%s, но оно должно быть либо '%s', либо '%s'".
                        formatted(rangeType, MI3622Constants.OPERATING_RANGE, MI3622Constants.SUBRANGE));
            }
        } else if (operatingOrControlCPM.equalsIgnoreCase(MI3622Constants.CONTROL)) {
            double[] delta_j = calculateDelta_j();
            if (CommonFunctions.getMaxInArray(delta_j) <= 0.2) return isValid;
            else return isNotValid;
        } else {
            throw new NotValidTagValueException("Значение поля operatingOrControl=%s, но оно должно быть либо '%s', либо '%s'"
                    .formatted(operatingOrControlCPM, MI3622Constants.OPERATING, MI3622Constants.CONTROL));
        }
    }

    public double[] calculateS_0j() {
        log.info("Рассчет СКО среднего значения результатов измерений в j-й точке (S0) согласно п.7.12 по формуле (16) МИ3622-2020");

        double[] S_0j = new double[pointsCount];
        double[] S_j = calculateS_j();

        for (int j = 0; j < pointsCount; j++) {
            S_0j[j] = S_j[j] / Math.sqrt(pointsCount);
        }

        log.debug("Значение СКО в j-й точке (S_j) {}", Arrays.toString(S_j));
        log.debug("Кол-во измерений (n) {}", measureCount);
        log.info("S_0j = {}", Arrays.toString(S_0j));

        return S_0j;
    }

    public double[] calculateEps_j() {
        log.info("Рассчет границы случайной составляющей погрешности СРМ в j-й точке (eps_j) согласно п.7.13 по формуле (17) МИ3622-2020");

        double[] S_0j = calculateS_0j();
        double[] t095Arr = calculateT_095();
        double t095 = t095Arr[measureCount - 5];
        if (S_0j == null) {
            S_0j = calculateS_0j();
        }
        double[] eps_j = new double[pointsCount];

        for (int j = 0; j < pointsCount; j++) {
            eps_j[j] = t095 * S_0j[j];
        }

        log.debug("СКО среднего значения результатов измерений в j-й точке (S_0j) {}", Arrays.toString(S_0j));
        log.debug("Квантиль распределения Стъюдента при доверительной вероятности Р=0.95 (t_0_95) {}", t095);
        log.info("eps_j = {}", Arrays.toString(eps_j));

        return eps_j;
    }

    public double[] calculateT_095() {
        return new double[]{2.776, 2.571, 2.447, 2.365, 2.306, 2.262};
    }

    public double calculateEps_D() {
        log.info("Рассчет границы случайной составляющей погрешности СРМ в диапазоне измерений (E_d) согласно п.7.14 по формуле (18) МИ3622-2020");

        double[] eps_j = calculateEps_j();
        double eps_d = CommonFunctions.getMaxInArray(eps_j);

        log.debug("Значения границ случайной составляющей погрешности СРМ (eps_j) {}", eps_j);
        log.info("eps_d = {}", eps_d);

        return eps_d;
    }

    public double[] calculateQ_j() {
        return CommonFunctions.getAverageForEachColumn(Q_i_j);
    }

    public double[] calculateTheta_sigma_j() {
        log.info("Рассчет границы неисключенной систематической погрешности в j-й точке (Theta_sigma_j) согласно п.7.16 по формуле (20) МИ3622-2020");

        double[] theta_sigma_j = new double[pointsCount];
        double[] theta_zj = new double[pointsCount];
        if (!zeroStabilityCorr) {
            theta_zj = calculateTheta_zj();
        }
        for (int j = 0; j < pointsCount; j++) {
            theta_sigma_j[j] = 1.1 * Math.sqrt(
                    Math.pow(theta_e, 2) +
                            Math.pow(theta_t, 2) +
                            Math.pow(theta_p, 2) +
                            Math.pow(theta_N, 2) +
                            Math.pow(theta_zj[j], 2));
        }

        log.debug("Граница составляющей неисключенной систематической погрешности, обусловленной нестабильностью нуля СРМ в j-ой точке (Theta_jz, %) {}", Arrays.toString(theta_zj));
        log.debug("Другие параметры: ZS = {}, theta_e = {}, theta_t = {}, theta_P = {}, theta_N = {}", ZS, theta_e, theta_t, theta_p, theta_N);
        log.info("theta_sigma_j = {}", Arrays.toString(theta_sigma_j));

        return theta_sigma_j;
    }

    public double[] calculateEps_PDk() {
        log.info("Рассчет границы случайной составляющей погрешности СРМ в к-м поддиапазоне рабочего диапазона измерений (E_PD) согласно п.7.15 по формуле (19) МИ3622-2020");

        double[] eps_pdk = null;
        if (pointsCount > 1) {
            double[] eps_j = calculateEps_j();
            int subrangeCount = pointsCount - 1;
            eps_pdk = new double[subrangeCount];
            for (int k = 0; k < subrangeCount; k++) {
                eps_pdk[k] = Math.max(eps_j[k], eps_j[k + 1]);
            }

            log.debug("значения границ случайной составляющей погрешности СРМ (eps_j) {}", Arrays.toString(eps_j));
            log.info("eps_pdk = {}", Arrays.toString(eps_pdk));
        }

        return eps_pdk;
    }

    public double calculateTheta_sigma_D() {
        log.info("Рассчет границы неисключенной систематической погрешности (Theta_sigma_D, %) согласно п.7.17 по формуле (22) МИ3622-2020");

        double theta_Dz = calculateTheta_Dz();
        double theta_D = calculateTheta_D();
        double theta_sigma_D = 1.1 * Math.sqrt(
                Math.pow(theta_e, 2) +
                        Math.pow(theta_Dt, 2) +
                        Math.pow(theta_Dp, 2) +
                        Math.pow(theta_N, 2) +
                        Math.pow(theta_Dz, 2) +
                        Math.pow(theta_D, 2));

        log.debug("Граница составляющей неисключенной систематической погрешности, обусловленной нестабильностью нуля СРМ (theta_D_z, %) {}", theta_Dz);
        log.debug("Граница составляющей неисключенной систематической погрешности, обусловленной погрешностью аппроксимации градуировочной характеристики (theta_D, %) {}", theta_D);
        log.debug("Дргуие параметры: ZS = {}, theta_e = {}, theta_Dt = {}, theta_Dp ={}, theta_N = {}", ZS, theta_e, theta_Dt, theta_Dp, theta_N);
        log.info("theta_sigma_D = {}", theta_sigma_D);

        return theta_sigma_D;
    }

    public double[] calculateTheta_sigma_PDk() {
        log.info("Рассчет границы неисключенной систематической погрешности в к-м поддиапазоне (theta_sigma_PDk) согласно п.7.17 по формуле (22) МИ3622-2020");

        double[] theta_sigma_PDk = null;
        if (pointsCount > 1) {
            int subrangeCount = pointsCount - 1;
            theta_sigma_PDk = new double[subrangeCount];
            double[] theta_PDz = new double[subrangeCount];
            if (!zeroStabilityCorr) theta_PDz = calculateTheta_PDz();
            double[] theta_PDk = calculateTheta_PDk();
            for (int k = 0; k < subrangeCount; k++) {
                theta_sigma_PDk[k] = 1.1 * Math.sqrt(
                        Math.pow(theta_e, 2) +
                                Math.pow(theta_PDt, 2) +
                                Math.pow(theta_PDp, 2) +
                                Math.pow(theta_N, 2) +
                                Math.pow(theta_PDz[k], 2) +
                                Math.pow(theta_PDk[k], 2));
            }

            log.debug("Используемые данные: ZS = {}, theta_e = {}, theta_PDt = {}, theta_PDp = {}, theta_N = {}", ZS, theta_e, theta_PDt, theta_PDp, theta_N);
            log.info("theta_sigma_PDk = {}", Arrays.toString(theta_sigma_PDk));
        }

        return theta_sigma_PDk;
    }

    public double[] calculateDelta_j() {
        log.info("Рассчет относительной погрешности СРМ (контрольного) в j-й точке диапазона измерений (delta_j) согласно п.7.21 по формуле (28) МИ3622-2020");

        double[] delta_j = new double[pointsCount];
        double[] theta_sigma_j = calculateTheta_sigma_j();
        double[] S_0j = calculateS_0j();
        double[] eps_j = calculateEps_j();

        double[] S_theta_j = calculateS_theta_j();
        double[] S_sigma_j = calculateS_sigma_j();
        double[] t_sigma_j = calculateT_sigma_j();

        for (int j = 0; j < pointsCount; j++) {
            double ratio = theta_sigma_j[j] / S_0j[j];
            if (ratio <= 8 && ratio >= 0.8) {
                t_sigma_j[j] = (eps_j[j] + theta_sigma_j[j]) / (S_theta_j[j] + S_0j[j]);
                delta_j[j] = t_sigma_j[j] * S_sigma_j[j];
            } else if (ratio > 8) {
                delta_j[j] = theta_sigma_j[j];
            }
        }

        log.debug("Данные: theta_sigma_j = {}, S_0j = {}", Arrays.toString(theta_sigma_j), Arrays.toString(S_0j));
        log.info("delta_j = {}", delta_j);

        return delta_j;
    }

    public double[] calculateT_sigma_j() {
        double[] theta_sigma_j = calculateTheta_sigma_j();
        double[] S_0j = calculateS_0j();
        double[] eps_j = calculateEps_j();
        double[] S_theta_j = calculateS_theta_j();
        double[] t_sigma_j = new double[pointsCount];

        for (int j = 0; j < pointsCount; j++) {
            t_sigma_j[j] = (eps_j[j] + theta_sigma_j[j]) / (S_theta_j[j] + S_0j[j]);
        }
        return t_sigma_j;
    }

    public double[] calculateS_sigma_j() {
        double[] S_0j = calculateS_0j();
        double[] S_theta_j = calculateS_theta_j();
        double[] S_sigma_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            S_sigma_j[j] = Math.sqrt(
                    Math.pow(S_theta_j[j], 2) +
                            Math.pow(S_0j[j], 2));
        }
        return S_sigma_j;
    }

    public double[] calculateS_theta_j() {
        double[] theta_zj = new double[pointsCount];
        if (!zeroStabilityCorr) theta_zj = calculateTheta_zj();
        double[] S_theta_j = new double[pointsCount];
        for (int j = 0; j < pointsCount; j++) {
            S_theta_j[j] = Math.sqrt(
                    Math.pow(theta_e, 2) +
                            Math.pow(theta_t, 2) +
                            Math.pow(theta_p, 2) +
                            Math.pow(theta_N, 2) +
                            Math.pow(theta_zj[j], 2));
        }
        return S_theta_j;
    }

    public double calculateDelta_D() {
        log.info("Рассчет относительной погрешности СРМ (рабочего) в рабочем диапазоне измерений (delta_D) согласно п.7.22 по формуле (32) МИ3622-2020");

        double delta_D = 0;
        double theta_sigma_D = 0;
        if (!zeroStabilityCorr) theta_sigma_D = calculateTheta_sigma_D();
        double S_D = calculateS_D();
        double ratio = theta_sigma_D / S_D;
        if (ratio <= 8 && ratio >= 0.8) {
            double t_sigma_D = calculateT_sigma_D();
            double S_sigma_D = calculateS_sigma_D();
            delta_D = t_sigma_D * S_sigma_D;
        } else if (ratio > 8) {
            delta_D = theta_sigma_D;
        }

        log.info("delta_D = {}", delta_D);

        return delta_D;
    }

    public double calculateT_sigma_D() {
        double S_theta_D = calculateS_theta_D();
        double S_D = calculateS_D();
        double theta_sigma_D = 0;
        if (!zeroStabilityCorr) theta_sigma_D = calculateTheta_sigma_D();
        return (calculateEps_D() + theta_sigma_D) / (S_D + S_theta_D);
    }

    public double calculateS_D() {
        return CommonFunctions.getMaxInArray(calculateS_0j());
    }

    public double calculateS_sigma_D() {
        double S_D = calculateS_D();
        double S_theta_D = calculateS_theta_D();
        return Math.sqrt(
                Math.pow(S_theta_D, 2) +
                        Math.pow(S_D, 2));
    }

    public double calculateS_theta_D() {
        double theta_Dz = calculateTheta_Dz();
        double theta_D = calculateTheta_D();
        return Math.sqrt(
                Math.pow(theta_e, 2) +
                        Math.pow(theta_Dt, 2) +
                        Math.pow(theta_Dp, 2) +
                        Math.pow(theta_N, 2) +
                        Math.pow(theta_Dz, 2) +
                        Math.pow(theta_D, 2));
    }

    public double[] calculateDelta_PDk() {
        log.info("Рассчет относительной погрешности СРМ (рабочего) в к-м поддиапазоне рабочего диапазона измерений (delta_PDk, %) согласно п.7.23 по формуле (36) МИ3622-2020");

        double[] delta_PDk = null;
        if (pointsCount > 1) {
            int subrangeCount = pointsCount - 1;
            delta_PDk = new double[subrangeCount];
            double[] theta_sigma_PDk = calculateTheta_sigma_PDk();
            double[] S_0_j = calculateS_0j();
            double[] S_PDk = calculateS_PDk();
            double[] S_sigma_PDk = calculateS_sigma_PDk();

            double[] t_sigma_PDk = calculateT_sigma_PDk();

            for (int k = 0; k < subrangeCount; k++) {
                S_PDk[k] = Math.max(S_0_j[k], S_0_j[k + 1]);
                double ratio = theta_sigma_PDk[k] / S_PDk[k];
                if (ratio <= 8 && ratio >= 0.8) {
                    delta_PDk[k] = t_sigma_PDk[k] * S_sigma_PDk[k];
                } else if (ratio > 8) {
                    delta_PDk[k] = theta_sigma_PDk[k];
                }
            }

            log.info("delta_PDk = {}", Arrays.toString(delta_PDk));
        }

        return delta_PDk;
    }

    public double[] calculateT_sigma_PDk() {
        double[] t_sigma_PDk = null;
        if (pointsCount > 1) {
            double[] theta_sigma_PDk = calculateTheta_sigma_PDk();
            double[] eps_PDk = calculateEps_PDk();
            double[] S_PDk = calculateS_PDk();
            double[] S_theta_PDk = calculateS_theta_PDk();
            int subrangeCount = pointsCount - 1;
            t_sigma_PDk = new double[subrangeCount];
            for (int k = 0; k < subrangeCount; k++) {
                t_sigma_PDk[k] = (eps_PDk[k] + theta_sigma_PDk[k]) / (S_theta_PDk[k] + S_PDk[k]);
            }
        }

        return t_sigma_PDk;
    }

    public double[] calculateS_sigma_PDk() {
        double[] S_sigma_PDk = null;
        if (pointsCount > 1) {
            double[] S_PDk = calculateS_PDk();
            double[] S_theta_PDk = calculateS_theta_PDk();
            int subrangeCount = pointsCount - 1;
            S_sigma_PDk = new double[subrangeCount];
            for (int k = 0; k < subrangeCount; k++) {
                S_sigma_PDk[k] = Math.sqrt(
                        Math.pow(S_theta_PDk[k], 2) +
                                Math.pow(S_PDk[k], 2));
            }
        }
        return S_sigma_PDk;
    }

    public double[] calculateS_theta_PDk() {
        double[] S_theta_PDk = null;
        if (pointsCount > 1) {
            double[] theta_PDk = calculateTheta_PDk();
            double[] theta_PDz = new double[theta_PDk.length];
            if (!zeroStabilityCorr) {
                theta_PDz = calculateTheta_PDz();
            }
            int subrangeCount = pointsCount - 1;
            S_theta_PDk = new double[subrangeCount];

            for (int k = 0; k < subrangeCount; k++) {
                S_theta_PDk[k] = Math.sqrt(
                        Math.pow(theta_e, 2) +
                                Math.pow(theta_PDt, 2) +
                                Math.pow(theta_PDp, 2) +
                                Math.pow(theta_N, 2) +
                                Math.pow(theta_PDz[k], 2) +
                                Math.pow(theta_PDk[k], 2));
            }
        }

        return S_theta_PDk;
    }

    public double[] calculateS_PDk() {
        double[] S_PDk = null;
        if (pointsCount > 1) {
            double[] S_0_j = calculateS_0j();
            int subrangeCount = pointsCount - 1;
            S_PDk = new double[subrangeCount];
            for (int k = 0; k < subrangeCount; k++) {
                S_PDk[k] = Math.max(S_0_j[k], S_0_j[k + 1]);
            }
        }

        return S_PDk;
    }

    public boolean checkIfWorkingSRMIsFit() {
        double delta_D = calculateDelta_D();
        double[] delta_Pdk = calculateDelta_PDk();
        return Math.max(CommonFunctions.getMaxInArray(delta_Pdk), delta_D) <= 0.25;
    }

    public boolean checkIfControlSRMIsFit(boolean theta_zjIsRequired) {
        double[] delta_j = calculateDelta_j();
        return CommonFunctions.getMaxInArray(delta_j) <= 0.2;
    }

    public double[] calculateTheta_zj() {
        double[] theta_zj = null;
        if (!zeroStabilityCorr) {
            double[] Q_j = calculateQ_j();
            theta_zj = new double[pointsCount];
            for (int j = 0; j < pointsCount; j++) {
                theta_zj[j] = ZS / Q_j[j] * 100;
            }
        }

        return theta_zj;
    }

    public double calculateTheta_Dz() {
        double Q_min = calculateQ_min();
        return ZS / Q_min * 100;
    }

    public double calculateQ_min() {
        double[] Q_j = calculateQ_j();
        return CommonFunctions.getMinInArray(Q_j);
    }

    public double[] calculateQ_min_k() {
        double[] Q_min_k = null;
        if (pointsCount > 1) {
            double[] Q_j = calculateQ_j();
            int subrangeCount = pointsCount - 1;
            Q_min_k = new double[subrangeCount];

            for (int k = 0; k < subrangeCount; k++) {
                Q_min_k[k] = Math.min(Q_j[k], Q_j[k + 1]);
            }
        }

        return Q_min_k;
    }

    public double calculateQ_max() {
        double[] Q_j = calculateQ_j();
        return Arrays.stream(Q_j).max().orElseThrow();
    }

    public double[] calculateQ_max_k() {
        double[] Q_max_k = null;
        if (pointsCount > 1) {
            double[] Q_j = calculateQ_j();
            int subrangeCount = pointsCount - 1;
            Q_max_k = new double[subrangeCount];

            for (int k = 0; k < subrangeCount; k++) {
                Q_max_k[k] = Math.max(Q_j[k], Q_j[k + 1]);
            }
        }

        return Q_max_k;
    }

    public double calculateTheta_D() {
        double[] MFOrK_j;
        double MFOrKValue;
        if (this.MFOrK.equalsIgnoreCase(MI3622Constants.MF)) {
            MFOrK_j = calculateMF_j();
            MFOrKValue = calculateMF();
        } else if (this.MFOrK.equalsIgnoreCase(MI3622Constants.K)) {
            MFOrK_j = calculateK_j();
            MFOrKValue = calculateK();
        } else {
            throw new NotValidTagValueException("Значение поля MForK=%s, но оно должно быть либо '%s', либо '%s'"
                    .formatted(this.MFOrK, MI3622Constants.MF, MI3622Constants.K));
        }

        return Arrays.stream(MFOrK_j)
                .map(mf_j -> Math.abs((mf_j - MFOrKValue) / MFOrKValue))
                .max().orElseThrow() * 100;
    }

    public double[] calculateTheta_PDz() {
        double[] theta_PDz = null;
        if (pointsCount > 1) {
            int subrangeCount = pointsCount - 1;
            theta_PDz = new double[subrangeCount];
            double[] Q_j = calculateQ_j();
            for (int k = 0; k < subrangeCount; k++) {
                double Q_PDmin = Math.min(Q_j[k], Q_j[k + 1]);
                theta_PDz[k] = ZS / Q_PDmin * 100;
            }
        }

        return theta_PDz;
    }

    public double[] calculateTheta_PDk() {
        double[] theta_PDk = null;
        if (pointsCount > 1) {
            double[] MFOrK_j;
            if (this.MFOrK.equalsIgnoreCase(MI3622Constants.MF)) {
                MFOrK_j = calculateMF_j();
            } else if (this.MFOrK.equalsIgnoreCase(MI3622Constants.K)) {
                MFOrK_j = calculateK_j();
            } else {
                throw new NotValidTagValueException("Значение поля MForK=%s, но оно должно быть либо '%s', либо '%s'"
                        .formatted(this.MFOrK, MI3622Constants.MF, MI3622Constants.K));
            }

            int subrangeCount = pointsCount - 1;
            theta_PDk = new double[subrangeCount];
            for (int k = 0; k < subrangeCount; k++) {
                theta_PDk[k] = 0.5 * Math.abs((MFOrK_j[k] - MFOrK_j[k + 1]) / (MFOrK_j[k] + MFOrK_j[k + 1])) * 100;
            }
        }
        return theta_PDk;
    }
}
