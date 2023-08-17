package com.nppgks.reportingsystem.reportgeneration.poverki;

import com.nppgks.reportingsystem.constants.ManualReportTypes;
import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.reportgeneration.poverki.mi3622.MI3622Runner;
import com.nppgks.reportingsystem.reportgeneration.poverki.mi3622.calculations.MI3622InitialData;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
