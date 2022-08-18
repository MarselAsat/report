package com.nppgks.reports.service.poverka3622;

import com.nppgks.reports.service.poverka3622.data.FinalData;
import com.nppgks.reports.service.poverka3622.data.InitialData;

public class PoverkaRunner {
    private final InitialData initialData;

    public PoverkaRunner(InitialData initialData) {
        this.initialData = initialData;
    }

    public FinalData run(){
        FinalData finalData = new FinalData();
        Poverka3622 poverka3622 = new Poverka3622(initialData);
        finalData.setF_ij(poverka3622.calculateF_ij());
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
}
