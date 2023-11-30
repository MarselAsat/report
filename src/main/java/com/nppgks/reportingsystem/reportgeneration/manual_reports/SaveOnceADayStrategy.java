package com.nppgks.reportingsystem.reportgeneration.manual_reports;

import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.repository.ReportDataRepository;
import com.nppgks.reportingsystem.db.manual_reports.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveOnceADayStrategy implements SaveReportStrategy{
    private final ReportRepository reportRepository;
    private final ReportDataRepository reportDataRepository;
    @Override
    @Transactional
    public String saveReportDataInDb(List<ReportData> reportDataList, Report report) {
        if (reportDataList == null || report == null) {
            return "Нет данных для сохранения!";
        }
        LocalDate creationDate = report.getCreationDt().toLocalDate();
        List<Report> reports = reportRepository.findByDateRangeAndReportTypeId(creationDate.atStartOfDay(), creationDate.atTime(LocalTime.MAX), report.getReportType().getId());
        String response = "В базе данных успешно создан отчет и сохранены результаты!";
        if (!reports.isEmpty()) {
            deleteReport(reports.get(0).getId());
            response = "Результаты поверки успешно перезаписаны!";
        }
        reportRepository.save(report);
        reportDataRepository.saveAll(reportDataList);
        return response;
    }
    private void deleteReport(Long id) {
        reportDataRepository.deleteByReportId(id);
        reportRepository.deleteById(id);
    }
}
