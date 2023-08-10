package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.reportgeneration.calculations.DataRounder;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.MI3622FinalData;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.data.MI3622InitialData;

public class MI3622Runner {
    private final MI3622InitialData MI3622InitialData;

    public MI3622Runner(MI3622InitialData MI3622InitialData) {
        this.MI3622InitialData = MI3622InitialData;
    }

    public MI3622FinalData run() {
        MI3622Calculation MI3622Calculation = new MI3622Calculation(MI3622InitialData);
        MI3622FinalData MI3622FinalData = DataConverter.calculateFinalData(MI3622Calculation);
        DataRounder.round(MI3622FinalData);
        return MI3622FinalData;
    }
}
