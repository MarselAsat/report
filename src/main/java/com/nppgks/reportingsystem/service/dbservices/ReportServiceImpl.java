package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.scheduled_reports.entity.Report;
import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.db.scheduled_reports.repository.ReportRepository;
import com.nppgks.reportingsystem.util.time.DateTimeRange;
import com.nppgks.reportingsystem.util.time.DateTimeRangeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {

        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> getReportsByDateAndReportId(String reportTypeId, LocalDate dtCreation) {
        if(dtCreation==null){
            return findByReportTypeId(reportTypeId);
        }
        else{
            String dtCreationStr = dtCreation.toString();
            DateTimeRange dateTimeRange;
            switch (ReportTypesEnum.valueOf(reportTypeId)) {
                case hour -> dateTimeRange = DateTimeRangeBuilder.buildDateRangeForSearchingHourReport(dtCreationStr);
                case twohour -> dateTimeRange = DateTimeRangeBuilder.buildDateRangeForSearching2HourReport(dtCreationStr);
                case daily -> dateTimeRange = DateTimeRangeBuilder.buildDateRangeForSearchingDailyReport(dtCreationStr);
                case shift -> {
                    return getShiftReportsByDate(dtCreationStr);
                }
                case month -> dateTimeRange = DateTimeRangeBuilder.buildDateRangeForSearchingMonthReport(dtCreationStr);
                case year -> dateTimeRange = DateTimeRangeBuilder.buildDateRangeForSearchingYearReport(dtCreationStr);
                default -> throw new RuntimeException("Invalid report type id");
            }
            return reportRepository.findByReportTypeAndDateRange(reportTypeId, dateTimeRange.getStartDateTime(), dateTimeRange.getEndDateTime());
        }
    }

    private List<Report> getShiftReportsByDate(String dtCreationStr) {
        String formattedDate = LocalDate.parse(dtCreationStr).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return reportRepository.findByNameLikeAndReportType("%" + formattedDate + "%", ReportTypesEnum.shift.name());
    }

    @Override
    public Report getById(Long reportId){
        return reportRepository.findById(reportId)
                .orElseThrow();
    }

    @Override
    public List<Report> findByReportTypeId(String reportTypeId) {
        return reportRepository.findByReportType(reportTypeId);
    }

    @Override
    public boolean saveReport(Report report) {
        return reportRepository.save(report).getId()!=null;
    }
}