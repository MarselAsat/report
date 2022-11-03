package com.nppgks.reports.service.calc3622;

import com.nppgks.reports.service.calc3622.data.DataConverter;
import com.nppgks.reports.service.calc3622.data.FinalData;
import com.nppgks.reports.service.calc3622.data.InitialData;

public class CalcRunner {
    private final InitialData initialData;

    public CalcRunner(InitialData initialData) {
        this.initialData = initialData;
    }

    public FinalData run(){
        FinalData finalData = new FinalData();
        Calc3622 calc3622 = new Calc3622(initialData);
        DataConverter.setCalcFinalDataFields(finalData, calc3622);
        return finalData;
    }
}
