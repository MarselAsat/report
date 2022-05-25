package com.nppgks.reports.service;

import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.repository.ReportNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReportNameServiceImpl implements ReportNameService{

    private ReportNameRepository repository;

    @Autowired
    public ReportNameServiceImpl(ReportNameRepository reportNameRepository) {

        this.repository = reportNameRepository;
    }

    @Override
    public List<ReportName> getReportNameByDateAndReportId(Long reportTypeId, LocalDateTime dtCreationStart) {
        LocalDateTime dtCreationEnd = dtCreationStart.plus(10, ChronoUnit.MINUTES);
        return repository.findByReportTypeIdAndDtCreationBetween(reportTypeId, dtCreationStart, dtCreationEnd);
    }

    @Override
    public List<ReportName> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ReportName> findByReportTypeId(Long reportTypeId) {
        return repository.findByReportTypeId(reportTypeId);
    }
}