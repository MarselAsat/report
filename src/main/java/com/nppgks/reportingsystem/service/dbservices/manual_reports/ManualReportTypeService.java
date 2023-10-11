package com.nppgks.reportingsystem.service.dbservices.manual_reports;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.manual_reports.repository.ReportTypeRepository;
import com.nppgks.reportingsystem.exception.MissingDbDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManualReportTypeService {
    private final ReportTypeRepository reportTypeRepository;

    public ReportType findById(String id){
        return reportTypeRepository.findById(id).orElseThrow(() -> new MissingDbDataException("В таблице manual_reports.report_type нет типа отчета с id = " + id));
    }
}
