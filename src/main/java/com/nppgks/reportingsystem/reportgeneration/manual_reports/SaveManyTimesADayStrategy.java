package com.nppgks.reportingsystem.reportgeneration.manual_reports;

import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.repository.ReportDataRepository;
import com.nppgks.reportingsystem.db.manual_reports.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SaveManyTimesADayStrategy implements SaveReportStrategy{
    private final ReportRepository reportRepository;
    private final ReportDataRepository reportDataRepository;
    @Override
    @Transactional
    public String saveReportDataInDb(List<ReportData> reportDataList, Report report) {
        if (reportDataList == null || report == null) {
            return "Нет данных для сохранения!";
        }
        String response = "В базе данных успешно создан отчет и сохранены результаты!";
        reportRepository.save(report);
        reportDataRepository.saveAll(reportDataList);
        return response;
    }
}
