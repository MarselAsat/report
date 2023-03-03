package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportName;
import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.db.recurring_reports.repository.ReportNameRepository;
import com.nppgks.reportingsystem.util.time.DateTimeRange;
import com.nppgks.reportingsystem.util.time.DateTimeRangeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportNameServiceImpl implements ReportNameService{

    private final ReportNameRepository reportNameRepository;

    @Autowired
    public ReportNameServiceImpl(ReportNameRepository reportNameRepository) {

        this.reportNameRepository = reportNameRepository;
    }

    @Override
    public List<ReportName> getReportNameByDateAndReportId(String reportTypeId, LocalDate dtCreation) {
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
                    return getShiftReportNamesByDate(dtCreationStr);
                }
                case month -> dateTimeRange = DateTimeRangeBuilder.buildDateRangeForSearchingMonthReport(dtCreationStr);
                case year -> dateTimeRange = DateTimeRangeBuilder.buildDateRangeForSearchingYearReport(dtCreationStr);
                default -> throw new RuntimeException("Invalid report type id");
            }
            return reportNameRepository.findByReportTypeAndDateRange(reportTypeId, dateTimeRange.getStartDateTime(), dateTimeRange.getEndDateTime());
        }
    }

    private List<ReportName> getShiftReportNamesByDate(String dtCreationStr) {
        String formattedDate = LocalDate.parse(dtCreationStr).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return reportNameRepository.findByNameLikeAndReportType("%" + formattedDate + "%", ReportTypesEnum.shift.name());
    }

    @Override
    public ReportName getById(Long reportNameId){
        return reportNameRepository.findById(reportNameId)
                .orElseThrow();
    }

    @Override
    public List<ReportName> findByReportTypeId(String reportTypeId) {
        return reportNameRepository.findByReportType(reportTypeId);
    }

    @Override
    public boolean saveReportName(ReportName reportName) {
        return reportNameRepository.save(reportName).getId()!=null;
    }
}