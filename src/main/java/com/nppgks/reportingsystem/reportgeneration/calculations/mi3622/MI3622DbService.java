package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.constants.ManualReportTypes;
import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.repository.ReportRepository;
import com.nppgks.reportingsystem.db.manual_reports.repository.ReportDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Класс, отвечающий за сохранение данных МИ3622 (исходные данные + результаты вычислений) в базу данных
 */
@Service
@RequiredArgsConstructor
public class MI3622DbService {
    private final ReportRepository reportRepository;
    private final ReportDataRepository reportDataRepository;

    @Transactional
    public String saveCalculations(List<ReportData> reportDataList, Report report) {
        if (reportDataList == null || report == null) {
            return "Нет данных для сохранения!";
        }
        LocalDate creationDate = report.getCreationDt().toLocalDate();
        List<Report> reports = reportRepository.findByDateRangeAndReportType(creationDate.atStartOfDay(), creationDate.atTime(LocalTime.MAX), ManualReportTypes.MI3622.name());
        String response = "В базе данных успешно создан отчет поверки и сохранены результаты!";
        if (!reports.isEmpty()) {
            deleteReport(reports.get(0).getId());
            response = "Результаты поверки успешно перезаписаны!";
        }
        reportRepository.save(report);
        reportDataRepository.saveAll(reportDataList);
        return response;
    }

    public void deleteReport(Long id) {
        reportDataRepository.deleteByReportId(id);
        reportRepository.deleteById(id);
    }
}
