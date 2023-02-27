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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public List<ReportName> findByDate(String date) {
        DateTimeRange dtRangeForHourReport = DateTimeRangeBuilder.buildDateRangeForSearchingHourReport(date);
        DateTimeRange dtRangeForDailyReport = DateTimeRangeBuilder.buildDateRangeForSearchingDailyReport(date);
        DateTimeRange dtRangeForMonthReport = DateTimeRangeBuilder.buildDateRangeForSearchingMonthReport(date);
        DateTimeRange dtRangeForYearReport = DateTimeRangeBuilder.buildDateRangeForSearchingYearReport(date);

        List<ReportName> hourReportNames = reportNameRepository.findByReportTypeAndDateRange(
                ReportTypesEnum.hour.name(),
                dtRangeForHourReport.getStartDateTime(),
                dtRangeForHourReport.getEndDateTime());
        List<ReportName> dailyReportNames = reportNameRepository.findByReportTypeAndDateRange(
                ReportTypesEnum.daily.name(),
                dtRangeForDailyReport.getStartDateTime(),
                dtRangeForDailyReport.getEndDateTime());
        List<ReportName> monthReportNames = reportNameRepository.findByReportTypeAndDateRange(
                ReportTypesEnum.month.name(),
                dtRangeForMonthReport.getStartDateTime(),
                dtRangeForMonthReport.getEndDateTime());
        List<ReportName> yearReportNames = reportNameRepository.findByReportTypeAndDateRange(
                ReportTypesEnum.year.name(),
                dtRangeForYearReport.getStartDateTime(),
                dtRangeForYearReport.getEndDateTime());

        List<ReportName> shiftReportNames = getShiftReportNamesByDate(date);

        return Stream.of(hourReportNames,
                        dailyReportNames,
                        shiftReportNames,
                        monthReportNames,
                        yearReportNames)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}