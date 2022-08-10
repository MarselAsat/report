package com.nppgks.reports.service.poverka3622;

import com.nppgks.reports.service.poverka3622.data.FinalData;
import com.nppgks.reports.service.poverka3622.data.InitialData;

import java.util.Map;

import static com.nppgks.reports.opc.ArrayParser.to2dimArray;
import static com.nppgks.reports.service.poverka3622.data.InitialData.Fields.*;
import static java.lang.Double.parseDouble;

public class PoverkaRunner {
    private final Map<String, String> valuesFromOpc;
    private final Map<String, String> tagNamesMap;

    public PoverkaRunner(Map<String, String> valuesFromOpc, Map<String, String> tagNamesMap) {
        this.valuesFromOpc = valuesFromOpc;
        this.tagNamesMap = tagNamesMap;
    }

    public FinalData run(){
        InitialData initialData = parseInitialData();
        FinalData finalData = new FinalData();
        Poverka3622 poverka3622 = new Poverka3622(initialData);
        finalData.setF_ij(poverka3622.calculatef_ij());
        finalData.setM_e_ij(poverka3622.calculateM_e_ij());
        finalData.setMF_ij(poverka3622.calculateMF_ij());
        finalData.setQ_j(poverka3622.calculateQ_j());
        finalData.setF_j(poverka3622.calculatef_j());
        finalData.setMF_j(poverka3622.calculateMF_j(finalData.getMF_ij()));
        finalData.setS_j(poverka3622.calculateS_j());
        finalData.setS_0j(poverka3622.calculateS_0j());
        finalData.setEps_j(poverka3622.calculateEps_j(finalData.getS_0j()));

        return finalData;
    }

    private InitialData parseInitialData(){
        InitialData initialData = new InitialData();
        initialData.setF_p_max(parseDouble(valuesFromOpc.get(tagNamesMap.get(f_p_max))));
        initialData.setK_e_ij(to2dimArray(valuesFromOpc.get(tagNamesMap.get(K_e_ij))));
        initialData.setQ_ij(to2dimArray(valuesFromOpc.get(tagNamesMap.get(Q_ij))));
        initialData.setN_e_ij(to2dimArray(valuesFromOpc.get(tagNamesMap.get(N_e_ij))));
        initialData.setN_p_ij(to2dimArray(valuesFromOpc.get(tagNamesMap.get(N_p_ij))));
        initialData.setT_ij(to2dimArray(valuesFromOpc.get(tagNamesMap.get(T_ij))));
        initialData.setM_ij(to2dimArray(valuesFromOpc.get(tagNamesMap.get(M_ij))));
        initialData.setQ_p_max(parseDouble(valuesFromOpc.get(tagNamesMap.get(Q_p_max))));
        initialData.setMF_p(parseDouble(valuesFromOpc.get(tagNamesMap.get(MF_p))));
        initialData.setTheta_e(parseDouble(valuesFromOpc.get(tagNamesMap.get(theta_e))));
        initialData.setTheta_N(parseDouble(valuesFromOpc.get(tagNamesMap.get(theta_N))));
        initialData.setTheta_p(parseDouble(valuesFromOpc.get(tagNamesMap.get(theta_p))));
        initialData.setTheta_PDp(parseDouble(valuesFromOpc.get(tagNamesMap.get(theta_PDp))));
        initialData.setTheta_PDt(parseDouble(valuesFromOpc.get(tagNamesMap.get(theta_PDt))));
        initialData.setTheta_t(parseDouble(valuesFromOpc.get(tagNamesMap.get(theta_t))));
        initialData.setZS(parseDouble(valuesFromOpc.get(tagNamesMap.get(ZS))));
        return initialData;
    }
}
