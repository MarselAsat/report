package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.DataRounder;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.FinalData;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.InitialData;

public class MI3622Runner {
    private final InitialData initialData;

    public MI3622Runner(InitialData initialData) {
        this.initialData = initialData;
    }

    public FinalData run(){
        MI3622Calculation MI3622Calculation = new MI3622Calculation(initialData);
        FinalData finalData = DataConverter.calculateFinalData(MI3622Calculation);
        DataRounder.round(finalData);
        return finalData;
    }
}
