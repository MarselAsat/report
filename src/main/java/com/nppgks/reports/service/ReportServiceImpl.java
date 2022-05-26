package com.nppgks.reports.service;

import com.nppgks.reports.dto.ReportTypeDto;
import com.nppgks.reports.repository.ReportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportTypeRepository repository;

    @Autowired
    public ReportServiceImpl(ReportTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ReportTypeDto> getAllReportTypes(){
        return repository.findAll().stream()
                .map(ReportTypeDto::fromReportType)
                .toList();
    }

}
