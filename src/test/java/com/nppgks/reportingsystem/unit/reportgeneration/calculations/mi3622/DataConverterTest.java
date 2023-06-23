package com.nppgks.reportingsystem.unit.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.MI3622Calculation;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.FinalData;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.InitialData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DataConverterTest {

    @Test
    void setCalcFinalDataFields() {
        double[] arr1Dim = new double[]{2, 2, 2, 2, 2};
        double[][] arr2Dim = new double[][]{{1, 1, 1, 1, 1}, {2, 2, 2, 2, 2}, {3, 3, 3, 3, 3}};
        double singleVal = 99.99;
        MI3622Calculation poverkaMock = Mockito.mock(MI3622Calculation.class);
        Mockito.doReturn(arr1Dim).when(poverkaMock).calculateS_0j();
        Mockito.doReturn(arr1Dim).when(poverkaMock).calculateEps_j();
        Mockito.doReturn(arr2Dim).when(poverkaMock).calculateM_e_ij();
        Mockito.doReturn(singleVal).when(poverkaMock).calculateK();

        FinalData finalData = DataConverter.calculateFinalData(poverkaMock);

        assertThat(finalData.getS_0j()).isEqualTo(arr1Dim);
        assertThat(finalData.getEps_j()).isEqualTo(arr1Dim);
        assertThat(finalData.getM_e_ij()).isEqualTo(arr2Dim);
        assertThat(finalData.getK()).isEqualTo(singleVal);
    }

    @Test
    void convertFinalDataToMap() {
        FinalData finalData = new FinalData();
        double[] arr1Dim = new double[]{2, 2, 2, 2, 2};
        double[][] arr2Dim = new double[][]{{1, 1, 1, 1, 1}, {2, 2, 2, 2, 2}, {3, 3, 3, 3, 3}};
        System.out.println(Arrays.deepToString(arr2Dim));
        System.out.println(Arrays.toString(arr1Dim));
        finalData.setDelta_j(arr1Dim);
        finalData.setF_ij(arr2Dim);
        finalData.setEps_PDk(arr1Dim);
        Map<String, String> tagsMap = new HashMap<>();
        fillInTagsMap(tagsMap);
        Map<String, Object> actualResult = DataConverter.convertFinalDataToMap(finalData, tagsMap);
        assertThat(actualResult).hasSize(4);
    }

    void fillInTagsMap(Map<String, String> tagsMap) {
        tagsMap.put("K_pm", "tagK_pm");
        tagsMap.put("M_e_ij", "tagM_e_ij");
        tagsMap.put("MF_ij", "tagMF_ij");
        tagsMap.put("MF", "tagMF");
        tagsMap.put("MF_j", "tagMF_j");
        tagsMap.put("K", "tagK");
        tagsMap.put("K_j", "tagK_j");
        tagsMap.put("K_ij", "tagK_ij");
        tagsMap.put("MF_prime", "tagMF_prime");
        tagsMap.put("f_ij", "tagf_ij");
        tagsMap.put("f_j", "tagf_j");
        tagsMap.put("S_j", "tagS_j");
        tagsMap.put("S_0j", "tagS_0j");
        tagsMap.put("eps_j", "tageps_j");
        tagsMap.put("t_095", "tagt_095");
        tagsMap.put("eps_D", "tageps_D");
        tagsMap.put("Q_j", "tagQ_j");
        tagsMap.put("theta_sigma_j", "tagtheta_sigma_j");
        tagsMap.put("eps_PDk", "tageps_PDk");
        tagsMap.put("theta_sigma_D", "tagtheta_sigma_D");
        tagsMap.put("theta_sigma_PDk", "tagtheta_sigma_PDk");
        tagsMap.put("delta_j", "tagdelta_j");
        tagsMap.put("t_sigma_j", "tagt_sigma_j");
        tagsMap.put("S_sigma_j", "tagS_sigma_j");
        tagsMap.put("S_theta_j", "tagS_theta_j");
        tagsMap.put("delta_D", "tagdelta_D");
        tagsMap.put("delta_PDk", "tagdelta_PDk");
        tagsMap.put("t_sigma_PDk", "tagt_sigma_PDk");
        tagsMap.put("S_sigma_PDk", "tagS_sigma_PDk");
        tagsMap.put("S_theta_PDk", "tagS_theta_PDk");
        tagsMap.put("S_PDk", "tagS_PDk");
        tagsMap.put("theta_zj", "tagtheta_zj");
        tagsMap.put("theta_Dz", "tagtheta_Dz");
        tagsMap.put("Q_min", "tagQ_min");
        tagsMap.put("Q_max", "tagQ_max");
        tagsMap.put("Q_min_k", "tagQ_min_k");
        tagsMap.put("Q_max_k", "tagQ_max_k");
        tagsMap.put("S_D", "tagS_D");
        tagsMap.put("theta_D", "tagtheta_D");
        tagsMap.put("theta_PDz", "tagtheta_PDz");
        tagsMap.put("theta_PDk", "tagtheta_PDk");
        tagsMap.put("S_theta_D", "tagS_theta_D");
        tagsMap.put("S_sigma_D", "tagS_sigma_D");
        tagsMap.put("t_sigma_D", "tagt_sigma_D");
        tagsMap.put("conclusion", "tagconclusion");
    }

    @Test
    void convertMapToInitialData() {
        String array2Dim = "[[1.0, 1.0, 1.0, 1.0, 1.0], [2.0, 2.0, 2.0, 2.0, 2.0], [3.0, 3.0, 3.0, 3.0, 3.0]]";
        String singleValue = "45.89";
        Map<String, String> valuesMap = Map.of("tagNp", array2Dim, "tagNeij", array2Dim, "tagFPMAx", singleValue,
                "WinCC_pointsCount", "5",
                "WinCC_measureCount", "3",
                "WinCC_Q", array2Dim);
        Map<String, String> tagsMap = Map.of("N_p_ij", "tagNp", "N_e_ij", "tagNeij", "f_p_max", "tagFPMAx",
                "pointsCount", "WinCC_pointsCount",
                "measureCount", "WinCC_measureCount",
                "Q_ij", "WinCC_Q");
        InitialData initialData = DataConverter.convertMapToInitialData(valuesMap, tagsMap);
        InitialData expectedInitialData = new InitialData();

        double[][] arr2Dim = new double[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {1, 2, 3}};
        double value = 45.89;
        expectedInitialData.setPointsCount(5);
        expectedInitialData.setMeasureCount(3);
        expectedInitialData.setN_p_ij(arr2Dim);
        expectedInitialData.setQ_ij(arr2Dim);
        expectedInitialData.setN_e_ij(arr2Dim);
        expectedInitialData.setF_p_max(value);
        assertThat(initialData).isEqualTo(expectedInitialData);
    }

    @Test
    void putInOrder2DArray() {
        Map<String, String> dataFromOpc = new HashMap<>();
        dataFromOpc.put("tag.pointsCount", "2");
        dataFromOpc.put("tag.measureCount", "3");
        dataFromOpc.put("tag.Q_ij", "[1, 2, 3, 4, 5, 6]");

        Map<String, String> tagsMap = new HashMap<>();
        tagsMap.put("pointsCount", "tag.pointsCount");
        tagsMap.put("measureCount", "tag.measureCount");
        tagsMap.put("Q_ij", "tag.Q_ij");
        DataConverter.putInOrder2DArraysInOpcData(dataFromOpc, tagsMap);
        assertThat(dataFromOpc.get("tag.Q_ij")).isEqualTo("[[1.0,2.0,3.0],[4.0,5.0,6.0]]");
    }

    @Test
    void testReflectionApi() {
//        FinalData finalData = new FinalData();
//        finalData.setK_pm(89.987989);
//        finalData.setDelta_D(678.45672);
//        finalData.setF_ij(new double[][]{{1, 2, 3},{4, 5, 6}});
//
//        try {
//            Field f_ij = finalData.getClass().getDeclaredField("f_ij");
//            f_ij.getType()
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        }
    }


}