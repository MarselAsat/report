package com.nppgks.reports.service.db_services.calculation;

import com.nppgks.reports.db.calculations.entity.ReportName;
import com.nppgks.reports.db.calculations.repository.ReportNameRepository;
import com.nppgks.reports.exception.NoReportException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalcReportNameService {

    private final ReportNameRepository reportNameRepository;

    public ReportName findReportNameById(Long id){
        return reportNameRepository.findById(id).orElseThrow(
                () -> new NoReportException("В таблице calculations.report_name нет отчета с id = "+id));
    }
}
