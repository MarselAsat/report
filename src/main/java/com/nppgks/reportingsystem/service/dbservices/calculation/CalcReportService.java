package com.nppgks.reportingsystem.service.dbservices.calculation;

import com.nppgks.reportingsystem.db.calculations.entity.Report;
import com.nppgks.reportingsystem.db.calculations.repository.ReportRepository;
import com.nppgks.reportingsystem.exception.MissingDbDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalcReportService {

    private final ReportRepository reportRepository;

    public Report findReportById(Long id) {
        return reportRepository.findById(id).orElseThrow(
                () -> new MissingDbDataException("В таблице calculations.report нет отчета с id = " + id));
    }

    public List<Report> findReportByDateAndType(LocalDate date, String reportType) {
        if (date == null) {
            return reportRepository.findByReportType(reportType);
        } else {
            return reportRepository.findByDateRangeAndReportType(
                    LocalDateTime.of(date, LocalTime.MIN),
                    LocalDateTime.of(date, LocalTime.MAX),
                    reportType);
        }

    }
}
