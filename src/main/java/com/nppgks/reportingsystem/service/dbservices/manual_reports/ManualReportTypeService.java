package com.nppgks.reportingsystem.service.dbservices.manual_reports;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.manual_reports.repository.ReportTypeRepository;
import com.nppgks.reportingsystem.dto.ReportTypeDto;
import com.nppgks.reportingsystem.dto.manual.ManualReportTypeDto;
import com.nppgks.reportingsystem.exception.MissingDbDataException;
import com.nppgks.reportingsystem.service.dbservices.PartialUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManualReportTypeService {
    private final ReportTypeRepository reportTypeRepository;

    public ReportType findById(String id){
        return reportTypeRepository.findById(id).orElseThrow(() -> new MissingDbDataException("В таблице manual_reports.report_type нет типа отчета с id = " + id));
    }

    public List<ReportType> getAllActiveReportTypes(){
        return reportTypeRepository.findAllByActive(true);
    }

    public List<ReportType> getAllReportTypes() {
        return reportTypeRepository.findAll();
    }

    public void partialUpdateReportType(String id, Map<String, String> updates) {
        Optional<ReportType> reportTypeOpt = reportTypeRepository.findById(id);
        if(reportTypeOpt.isPresent()){
            ManualReportTypeDto reportTypeDto = ManualReportTypeDto.fromReportType(reportTypeOpt.get());
            ManualReportTypeDto updatedReportTypeDto = PartialUpdateService.updateObject(reportTypeDto, updates);
            ReportType updatedReportRow = ManualReportTypeDto.toReportType(updatedReportTypeDto);
            reportTypeRepository.save(updatedReportRow);
        }
        else {
            throw new NoSuchElementException();
        }
    }
}
