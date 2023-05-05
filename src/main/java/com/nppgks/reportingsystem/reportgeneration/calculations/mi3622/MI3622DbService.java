package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.db.calculations.entity.ReportName;
import com.nppgks.reportingsystem.db.calculations.entity.TagData;
import com.nppgks.reportingsystem.db.calculations.repository.ReportNameRepository;
import com.nppgks.reportingsystem.db.calculations.repository.TagDataRepository;
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
    private final ReportNameRepository reportNameRepository;
    private final TagDataRepository tagDataRepository;

    @Transactional
    public String saveCalculations(List<TagData> tagDataList, ReportName reportName) {
        if (tagDataList == null || reportName == null) {
            return "Нет данных для сохранения!";
        }
        LocalDate creationDate = reportName.getCreationDt().toLocalDate();
        List<ReportName> reportNames = reportNameRepository.findByDateRange(creationDate.atStartOfDay(), creationDate.atTime(LocalTime.MAX));
        String response = "В базе данных успешно создан отчет поверки и сохранены результаты!";
        if (!reportNames.isEmpty()) {
            deleteReport(reportNames.get(0).getId());
            response = "В базе данных результаты поверки успешно перезаписаны!";
        }
        reportNameRepository.save(reportName);
        tagDataRepository.saveAll(tagDataList);
        return response;
    }

    public void deleteReport(Long id) {
        tagDataRepository.deleteByReportNameId(id);
        reportNameRepository.deleteById(id);
    }
}
