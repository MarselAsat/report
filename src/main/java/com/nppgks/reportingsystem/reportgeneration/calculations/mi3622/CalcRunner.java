package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.DataRounder;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.FinalData;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.InitialData;

public class CalcRunner {
    private final InitialData initialData;

    public CalcRunner(InitialData initialData) {
        this.initialData = initialData;
    }

    public FinalData run(){
        FinalData finalData = new FinalData();
        MI3622Calculation MI3622Calculation = new MI3622Calculation(initialData);
        DataConverter.setCalcFinalDataFields(finalData, MI3622Calculation);
        DataRounder.round(finalData);
        return finalData;
    }
}
