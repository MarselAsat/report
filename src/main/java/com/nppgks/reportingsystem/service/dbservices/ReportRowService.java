package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.recurring_reports.repository.ReportRowRepository;
import com.nppgks.reportingsystem.dto.ReportRowDto;
import com.nppgks.reportingsystem.dto.ReportRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
