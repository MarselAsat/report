package com.nppgks.reports.service.poverka3622.data;

import com.nppgks.reports.service.poverka3622.Poverka3622;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DataConverterTest {

    @Test
    void setFinalDataFieldsFromPoverka(){
        double[] arr1Dim = new double[]{2, 2, 2, 2, 2};
        double[][] arr2Dim =  new double[][]{{1, 1, 1, 1, 1}, {2, 2, 2, 2, 2}, {3, 3, 3, 3, 3}};
        double singleVal = 99.99;
        Poverka3622 poverkaMock = Mockito.mock(Poverka3622.class);
        Mockito.doReturn(arr1Dim).when(poverkaMock).calculateS_0j();
        Mockito.doReturn(arr1Dim).when(poverkaMock).calculateEps_j();
        Mockito.doReturn(arr2Dim).when(poverkaMock).calculateM_e_ij();
        Mockito.doReturn(singleVal).when(poverkaMock).calculateK();

        FinalData finalData = new FinalData();
        DataConverter.setFinalDataFieldsFromPoverka(finalData, poverkaMock);

        assertThat(finalData.getS_0j()).isEqualTo(arr1Dim);
        assertThat(finalData.getEps_j()).isEqualTo(arr1Dim);
        assertThat(finalData.getM_e_ij()).isEqualTo(arr2Dim);
        assertThat(finalData.getK()).isEqualTo(singleVal);
    }

    @Test
    void convertFinalDataToMap() {
        FinalData finalData = new FinalData();
        double[] arr1Dim = new double[]{2, 2, 2, 2, 2};
        double[][] arr2Dim =  new double[][]{{1, 1, 1, 1, 1}, {2, 2, 2, 2, 2}, {3, 3, 3, 3, 3}};
        System.out.println(Arrays.deepToString(arr2Dim));
        System.out.println(Arrays.toString(arr1Dim));
        finalData.setDelta_j(arr1Dim);
        finalData.setF_ij(arr2Dim);
        finalData.setEps_PDk(arr1Dim);
        Map<String, String> tagNamesMap = Map.of("delta_j", "tagDelta_j", "f_ij", "tagFij", "eps_PDk", "tagEpsPdk");
        Map<String, Object> actualResult = DataConverter.convertFinalDataToMap(finalData, tagNamesMap);
        assertThat(actualResult).hasSize(3);
    }

    @Test
    void convertMapToInitialData() {
        String array2Dim = "[[1.0, 1.0, 1.0, 1.0, 1.0], [2.0, 2.0, 2.0, 2.0, 2.0], [3.0, 3.0, 3.0, 3.0, 3.0]]";
        String singleValue = "45.89";
        Map<String, String> valuesMap = Map.of("tagQij", array2Dim, "tagNeij", array2Dim, "tagFPMAx", singleValue);
        Map<String, String> tagNamesMap = Map.of("Q_ij","tagQij", "N_e_ij", "tagNeij", "f_p_max", "tagFPMAx");
        InitialData initialData = DataConverter.convertMapToInitialData(valuesMap, tagNamesMap);
        InitialData expectedInitialData = new InitialData();

        double[][] arr2Dim =  new double[][]{{1, 1, 1, 1, 1}, {2, 2, 2, 2, 2}, {3, 3, 3, 3, 3}};
        double value = 45.89;
        expectedInitialData.setQ_ij(arr2Dim);
        expectedInitialData.setN_e_ij(arr2Dim);
        expectedInitialData.setF_p_max(value);
        assertThat(initialData).isEqualTo(expectedInitialData);
    }
}