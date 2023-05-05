package com.nppgks.reportingsystem.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.db.calculations.entity.ReportName;
import com.nppgks.reportingsystem.db.calculations.entity.TagData;
import com.nppgks.reportingsystem.db.calculations.repository.ReportNameRepository;
import com.nppgks.reportingsystem.db.calculations.repository.TagDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public void saveCalculations(List<TagData> tagDataList, ReportName reportName) {
        reportNameRepository.save(reportName);
        tagDataRepository.saveAll(tagDataList);
    }
}
