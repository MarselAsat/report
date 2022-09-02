package com.nppgks.reports.service;

import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.repository.ReportNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportNameServiceImpl implements ReportNameService{

    private final ReportNameRepository repository;

    @Autowired
    public ReportNameServiceImpl(ReportNameRepository reportNameRepository) {

        this.repository = reportNameRepository;
    }

    @Override
    public List<ReportName> getReportNameByDateAndReportId(Integer reportTypeId, String dtCreationStr) {
        if(reportTypeId==null){
            return findByDate(dtCreationStr);
        }
        else if(dtCreationStr==null||dtCreationStr.isBlank()){
            return findByReportTypeId(reportTypeId);
        }
        else{
            LocalDateTime dtCreationStart;
            LocalDateTime dtCreationEnd;
            switch (reportTypeId) {
                case 1 -> {
                    dtCreationStart = LocalDateTime.parse(dtCreationStr + "T02:00");
                    LocalDate endDay = LocalDate.parse(dtCreationStr).plusDays(1);
                    dtCreationEnd = LocalDateTime.parse(endDay + "T01:59");
                }
                case 2 -> {
                    dtCreationStart = LocalDateTime.parse(dtCreationStr + "T00:00").plusDays(1);
                    dtCreationEnd = LocalDateTime.parse(dtCreationStr + "T23:59").plusDays(1);
                }
                case 3 -> {
                    String formattedDate = LocalDate.parse(dtCreationStr).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    return repository.findByNameLikeAndReportTypeId("%"+formattedDate+"%", 3);
                }
                case 4 -> {
                    LocalDate dateForMonthReport = LocalDate.parse(dtCreationStr).plusMonths(1);
                    Month month = dateForMonthReport.getMonth();
                    int year = dateForMonthReport.getYear();
                    dtCreationStart = LocalDateTime.of(year, month, 1, 0, 0);
                    dtCreationEnd = LocalDateTime.of(year, month, month.length(false), 23, 59);
                }
                case 5 -> {
                    LocalDate dateForYearReport = LocalDate.parse(dtCreationStr).plusYears(1);
                    int y = dateForYearReport.getYear();
                    dtCreationStart = LocalDateTime.of(y, 1, 1, 0, 0);
                    dtCreationEnd = LocalDateTime.of(y, 12, 31, 23, 59);
                }
                default -> throw new RuntimeException("Invalid report type id");
            }
            return repository.findByReportTypeIdAndDtCreationBetween(reportTypeId, dtCreationStart, dtCreationEnd);
        }
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

    @Override
    public List<ReportName> findByDate(String date) {
        LocalDateTime dtCreationStart = LocalDateTime.parse(date+"T00:00");
        LocalDateTime dtCreationEnd = LocalDateTime.parse(date+"T23:59");
        return repository.findByDtCreationBetween(dtCreationStart, dtCreationEnd);
    }
}