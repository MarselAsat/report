package com.nppgks.reports.service;

import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.entity.ReportTypesEnum;
import com.nppgks.reports.repository.ReportNameRepository;
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

    private final ReportNameRepository repository;

    @Autowired
    public ReportNameServiceImpl(ReportNameRepository reportNameRepository) {

        this.repository = reportNameRepository;
    }

    @Override
    public List<ReportName> getReportNameByDateAndReportId(String reportTypeId, String dtCreationStr) {
        if(reportTypeId==null){
            return findByDate(dtCreationStr);
        }
        else if(dtCreationStr==null||dtCreationStr.isBlank()){
            return findByReportTypeId(reportTypeId);
        }
        else{
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
            return repository.findByReportTypeIdAndCreationDtBetween(reportTypeId, dateTimeRange.getStartDateTime(), dateTimeRange.getEndDateTime());
        }
    }

    private List<ReportName> getShiftReportNamesByDate(String dtCreationStr) {
        String formattedDate = LocalDate.parse(dtCreationStr).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return repository.findByNameLikeAndReportTypeId("%" + formattedDate + "%", ReportTypesEnum.shift.name());
    }

    @Override
    public ReportName getById(Long reportNameId){
        return repository.findById(reportNameId)
                .orElseThrow();
    }

    @Override
    public List<ReportName> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ReportName> findByReportTypeId(String reportTypeId) {
        return repository.findByReportTypeId(reportTypeId);
    }

    @Override
    public boolean saveReportName(ReportName reportName) {
        return repository.save(reportName).getId()!=null;
    }

    @Override
    public List<ReportName> findByDate(String date) {
        DateTimeRange dtRangeForHourReport = DateTimeRangeBuilder.buildDateRangeForSearchingHourReport(date);
        DateTimeRange dtRangeForDailyReport = DateTimeRangeBuilder.buildDateRangeForSearchingDailyReport(date);
        DateTimeRange dtRangeForMonthReport = DateTimeRangeBuilder.buildDateRangeForSearchingMonthReport(date);
        DateTimeRange dtRangeForYearReport = DateTimeRangeBuilder.buildDateRangeForSearchingYearReport(date);

        List<ReportName> hourReportNames = repository.findByReportTypeIdAndCreationDtBetween(
                ReportTypesEnum.hour.name(),
                dtRangeForHourReport.getStartDateTime(),
                dtRangeForHourReport.getEndDateTime());
        List<ReportName> dailyReportNames = repository.findByReportTypeIdAndCreationDtBetween(
                ReportTypesEnum.daily.name(),
                dtRangeForDailyReport.getStartDateTime(),
                dtRangeForDailyReport.getEndDateTime());
        List<ReportName> monthReportNames = repository.findByReportTypeIdAndCreationDtBetween(
                ReportTypesEnum.month.name(),
                dtRangeForMonthReport.getStartDateTime(),
                dtRangeForMonthReport.getEndDateTime());
        List<ReportName> yearReportNames = repository.findByReportTypeIdAndCreationDtBetween(
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