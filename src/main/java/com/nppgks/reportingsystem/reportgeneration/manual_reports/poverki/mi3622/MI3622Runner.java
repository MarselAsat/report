package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.DataRounder;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622.calculations.MI3622Calculation;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622.calculations.MI3622FinalData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622.calculations.MI3622InitialData;

public class MI3622Runner {
    private final com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622.calculations.MI3622InitialData MI3622InitialData;

    public MI3622Runner(MI3622InitialData MI3622InitialData) {
        this.MI3622InitialData = MI3622InitialData;
    }

    public MI3622FinalData run() {
        MI3622Calculation MI3622Calculation = new MI3622Calculation(MI3622InitialData);
        MI3622FinalData MI3622FinalData = DataConverter.calculateMI3622FinalData(MI3622Calculation);
        DataRounder.round(MI3622FinalData);
        return MI3622FinalData;
    }
}
