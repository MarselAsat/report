package com.nppgks.reports.service;

import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.repository.ReportNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReportNameServiceImpl implements ReportNameService{

    private ReportNameRepository repository;

    @Autowired
    public ReportNameServiceImpl(ReportNameRepository reportNameRepository) {

        this.repository = reportNameRepository;
    }

    @Override
    public List<ReportName> getReportNameByDateAndReportId(Integer reportTypeId, String dtCreationStr) {
        LocalDateTime dtCreationStart;
        LocalDateTime dtCreationEnd;
        switch (reportTypeId.intValue()) {
            case 4:
                Month month = LocalDateTime.parse(dtCreationStr+"T00:00").getMonth();
                int year = LocalDateTime.parse(dtCreationStr+"T00:00").getYear();
                dtCreationStart = LocalDateTime.of(year, month, 1, 0, 0);
                dtCreationEnd = LocalDateTime.of(year, month, month.length(false), 23, 59);
                break;
            case 5:
                int y = LocalDateTime.parse(dtCreationStr+"T00:00").getYear();
                dtCreationStart = LocalDateTime.of(y, 1, 1, 0, 0);
                dtCreationEnd = LocalDateTime.of(y, 12, 31, 23, 59);
                break;
            default:
                dtCreationStart = LocalDateTime.parse(dtCreationStr+"T00:00");
                dtCreationEnd = LocalDateTime.parse(dtCreationStr+"T23:59");
                break;
        }
        return repository.findByReportTypeIdAndDtCreationBetween(reportTypeId, dtCreationStart, dtCreationEnd);
    }

    @Override
    public List<ReportName> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ReportName> findByReportTypeId(Integer reportTypeId) {
        return repository.findByReportTypeId(reportTypeId);
    }

    @Override
    public boolean saveReportName(ReportName reportName) {
        return repository.save(reportName).getId()!=null;
    }
}