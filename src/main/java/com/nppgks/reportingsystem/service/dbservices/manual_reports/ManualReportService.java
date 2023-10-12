package com.nppgks.reportingsystem.service.dbservices.manual_reports;

import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.repository.ReportRepository;
import com.nppgks.reportingsystem.exception.MissingDbDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManualReportService {

    private final ReportRepository reportRepository;

    public Report findReportById(Long id) {
        return reportRepository.findById(id).orElseThrow(
                () -> new MissingDbDataException("В таблице calculations.report нет отчета с id = " + id));
    }

    public List<Report> findReportByDateAndType(LocalDate date, String reportType) {
        if (date == null) {
            return reportRepository.findByReportTypeId(reportType);
        } else {
            return reportRepository.findByDateRangeAndReportTypeId(
                    LocalDateTime.of(date, LocalTime.MIN),
                    LocalDateTime.of(date, LocalTime.MAX),
                    reportType);
        }

    }
}
