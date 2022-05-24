package com.nppgks.reports.service;

import com.nppgks.reports.entity.ReportType;
import com.nppgks.reports.repository.ReportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ReportServiceImpl implements ReportService {

    private final ReportTypeRepository repository;

    @Autowired
    public ReportServiceImpl(ReportTypeRepository repository) {
        this.repository = repository;
    }

}
