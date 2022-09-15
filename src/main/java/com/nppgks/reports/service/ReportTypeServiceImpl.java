package com.nppgks.reports.service;

import com.nppgks.reports.dto.ReportTypeDto;
import com.nppgks.reports.entity.ReportType;
import com.nppgks.reports.repository.ReportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReportTypeServiceImpl implements ReportTypeService {

    private final ReportTypeRepository repository;

    @Autowired
    public ReportTypeServiceImpl(ReportTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ReportTypeDto> getAllReportTypes(){
        return repository.findAll().stream()
                .map(ReportTypeDto::fromReportType)
                .toList();
    }

    @Override
    public Optional<ReportType> getReportTypeById(String id) {
        return repository.findById(id);
    }

}
