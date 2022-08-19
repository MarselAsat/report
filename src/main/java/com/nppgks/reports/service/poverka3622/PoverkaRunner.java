package com.nppgks.reports.service.poverka3622;

import com.nppgks.reports.service.poverka3622.data.DataConverter;
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
        DataConverter.setFinalDataFieldsFromPoverka(finalData, poverka3622);
        return finalData;
    }
}
