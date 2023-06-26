package com.nppgks.reportingsystem.reportgeneration.acts;

import com.nppgks.reportingsystem.db.calculations.entity.Report;
import com.nppgks.reportingsystem.db.calculations.entity.ReportData;
import com.nppgks.reportingsystem.db.calculations.repository.ReportDataRepository;
import com.nppgks.reportingsystem.db.calculations.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActOfAcceptanceDbService {
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
