package com.nppgks.reportingsystem.service.dbservices.manual_reports;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.repository.ReportDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManualReportDataService {

    private final ReportDataRepository reportDataRepository;

    public List<ReportData> getReportDataList(Long reportId){
        return reportDataRepository.findByReportId(reportId);
    }
}
