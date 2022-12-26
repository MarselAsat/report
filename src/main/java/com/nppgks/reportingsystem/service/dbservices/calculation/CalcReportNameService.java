package com.nppgks.reportingsystem.service.dbservices.calculation;

import com.nppgks.reportingsystem.db.calculations.entity.ReportName;
import com.nppgks.reportingsystem.db.calculations.repository.ReportNameRepository;
import com.nppgks.reportingsystem.exception.NoReportException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalcReportNameService {

    private final ReportNameRepository reportNameRepository;

    public ReportName findReportNameById(Long id){
        return reportNameRepository.findById(id).orElseThrow(
                () -> new NoReportException("В таблице calculations.report_name нет отчета с id = "+id));
    }

    public List<ReportName> findReportNameByDate(LocalDate date){
        if(date==null){
            return reportNameRepository.findBy();
        }
        else{
            return reportNameRepository.findByDateRange(
                    LocalDateTime.of(date, LocalTime.MIN),
                    LocalDateTime.of(date, LocalTime.MAX));
        }

    }
}
