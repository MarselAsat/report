package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.dto.ReportTypeDto;
import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.recurring_reports.repository.ReportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ReportTypeServiceImpl implements ReportTypeService {

    private final ReportTypeRepository reportTypeRepository;

    @Autowired
    public ReportTypeServiceImpl(ReportTypeRepository reportTypeRepository) {
        this.reportTypeRepository = reportTypeRepository;
    }

    @Override
    public List<ReportTypeDto> getAllReportTypes(){
        return reportTypeRepository.findAll().stream()
                .map(ReportTypeDto::fromReportType)
                .toList();
    }

    @Override
    public ReportType getReportTypeById(String id) {
        return reportTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("no %s report type in database", id)));
    }

    @Override
    public void partialUpdateReportType(String id, Map<String, String> updates) {
        Optional<ReportType> reportTypeOpt = reportTypeRepository.findById(id);
        if(reportTypeOpt.isPresent()){
            ReportTypeDto reportTypeDto = ReportTypeDto.fromReportType(reportTypeOpt.get());
            ReportTypeDto updatedReportTypeDto = PartialUpdateService.updateObject(reportTypeDto, updates);
            ReportType updatedReportRow = ReportTypeDto.toReportType(updatedReportTypeDto);
            reportTypeRepository.save(updatedReportRow);
        }
        else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<ReportType> getAllActiveReportTypes() {
        return reportTypeRepository.findAllByActive(true);
    }

}
