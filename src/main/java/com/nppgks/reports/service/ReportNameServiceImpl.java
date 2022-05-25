package com.nppgks.reports.service;

import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.repository.ReportNameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class ReportNameServiceImpl implements ReportNameService{

    private ReportNameRepository repository;

    @Autowired
    public ReportNameServiceImpl(ReportNameRepository reportNameRepository) {

        this.repository = reportNameRepository;
    }

    @Override
    public List<ReportName> getReportNameByDateAndReportId(Long reportTypeId, LocalDateTime dtCreation) {
        return repository.findByReportTypeIdAndDtCreation(reportTypeId, dtCreation);
    }
}
