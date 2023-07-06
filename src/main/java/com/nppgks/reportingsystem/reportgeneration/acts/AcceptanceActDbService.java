package com.nppgks.reportingsystem.reportgeneration.acts;

import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.repository.ReportDataRepository;
import com.nppgks.reportingsystem.db.manual_reports.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcceptanceActDbService {
    private final ReportRepository reportRepository;
    private final ReportDataRepository reportDataRepository;

    @Transactional
    public String saveReportData(List<ReportData> reportDataList, Report report) {
        if (reportDataList == null || report == null) {
            return "Нет данных для сохранения!";
        }
        String response = "В базе данных успешно создан отчет акта приема-сдачи нефти и сохранены результаты!";
        reportRepository.save(report);
        reportDataRepository.saveAll(reportDataList);
        return response;
    }
}
