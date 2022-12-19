package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.dto.ReportTypeDto;
import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.recurring_reports.repository.ReportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public ReportType getReportTypeById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("no %s report type in database", id)));
    }

}
