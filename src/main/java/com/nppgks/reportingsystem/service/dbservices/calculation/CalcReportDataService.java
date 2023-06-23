package com.nppgks.reportingsystem.service.dbservices.calculation;

import com.nppgks.reportingsystem.db.calculations.entity.ReportData;
import com.nppgks.reportingsystem.db.calculations.repository.ReportDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalcReportDataService {

    private final ReportDataRepository reportDataRepository;

    public List<ReportData> getReportDataList(Long reportId){
        return reportDataRepository.findByReportId(reportId);
    }
}
