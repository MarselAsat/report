package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313;

import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.ManualReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.SaveReportStrategy;

import java.time.LocalDateTime;
import java.util.List;

public class MI3313ReportGenerator extends ManualReportGenerator {
    public MI3313ReportGenerator(SaveReportStrategy saveReportStrategy) {
        super(saveReportStrategy);
    }

    @Override
    protected List<ReportData> generateReportDataList() {
        return null;
    }

    @Override
    protected Report createReport(LocalDateTime currentDt) {
        return null;
    }
}
