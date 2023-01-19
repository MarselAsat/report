package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportRow;
import com.nppgks.reportingsystem.db.recurring_reports.repository.ReportRowRepository;
import com.nppgks.reportingsystem.dto.ReportRowDto;
import com.nppgks.reportingsystem.dto.ReportRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportRowService {
    private final ReportRowRepository reportRowRepository;
    private final ReportRowMapper reportRowMapper;

    public List<ReportRowDto> getAllReportRows(){
        return reportRowRepository.findAll().stream()
                .map(reportRowMapper::fromReportRowToReportRowDto)
                .toList();
    }

    public Integer saveReportRow(ReportRowDto reportRowDto) {
        return reportRowRepository.save(
                reportRowMapper.fromReportRowDtoToReportRow(reportRowDto)).getId();
    }

    public void deleteReportRow(Integer id) {
        reportRowRepository.deleteById(id);
    }

    public void partialUpdateReportRow(Integer id, Map<String, String> updates) {
        Optional<ReportRow> reportRowOpt = reportRowRepository.findById(id);
        if(reportRowOpt.isPresent()){
            ReportRowDto reportRowDto = reportRowMapper.fromReportRowToReportRowDto(reportRowOpt.get());
            ReportRowDto updatedReportRowDto = PartialUpdateService.updateObject(reportRowDto, updates);
            ReportRow updatedReportRow = reportRowMapper.fromReportRowDtoToReportRow(updatedReportRowDto);
            reportRowRepository.save(updatedReportRow);
        }
        else {
            throw new NoSuchElementException();
        }
    }
}
