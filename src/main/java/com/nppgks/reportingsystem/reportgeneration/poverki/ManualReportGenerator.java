package com.nppgks.reportingsystem.reportgeneration.poverki;

import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public abstract class ManualReportGenerator {
    private final SaveReportStrategy saveReportStrategy;
    protected boolean isSaved = false;
    protected List<ReportData> reportDataList = new ArrayList<>();
    protected Report report;

    public List<ReportData> generateReport() {
        LocalDateTime currentDt = LocalDateTime.now();
        report = createReport(currentDt);
        reportDataList = generateReportDataList();
        isSaved = false;
        return reportDataList;
    }

    protected abstract List<ReportData> generateReportDataList();

    protected abstract Report createReport(LocalDateTime currentDt);

    public String saveReportInDb() {
        if (isSaved) {
            return "Эти результаты уже сохранены в базу данных";
        }
        String response = saveReportStrategy.saveReportDataInDb(reportDataList, report);
        isSaved = true;
        return response;
    }

}
