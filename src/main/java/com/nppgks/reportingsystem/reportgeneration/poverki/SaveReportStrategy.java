package com.nppgks.reportingsystem.reportgeneration.poverki;

import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import java.util.List;

public interface SaveReportStrategy {
    String saveReportDataInDb(List<ReportData> reportDataList, Report report);
}
