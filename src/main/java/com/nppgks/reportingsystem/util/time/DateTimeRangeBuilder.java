package com.nppgks.reportingsystem.util.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;

public class DateTimeRangeBuilder {

    public static DateTimeRange buildDateRangeForSearchingHourReport(String dtCreationStr){
        LocalDateTime dtCreationStart = LocalDateTime.parse(dtCreationStr + "T02:00");
        LocalDate endDay = LocalDate.parse(dtCreationStr).plusDays(1);
        LocalDateTime dtCreationEnd = LocalDateTime.parse(endDay + "T01:59");
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }

    public static DateTimeRange buildDateRangeForSearchingDailyReport(String dtCreationStr){
        LocalDateTime dtCreationStart = LocalDateTime.parse(dtCreationStr + "T00:00").plusDays(1);
        LocalDateTime dtCreationEnd = LocalDateTime.parse(dtCreationStr + "T23:59").plusDays(1);
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }

    public static DateTimeRange buildDateRangeForSearchingMonthReport(String dtCreationStr){
        LocalDate dateForMonthReport = LocalDate.parse(dtCreationStr).plusMonths(1);
        Month month = dateForMonthReport.getMonth();
        int year = dateForMonthReport.getYear();
        LocalDateTime dtCreationStart = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime dtCreationEnd = LocalDateTime.of(year, month, month.length(false), 23, 59);
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }

    public static DateTimeRange buildDateRangeForSearchingYearReport(String dtCreationStr){
        LocalDate dateForYearReport = LocalDate.parse(dtCreationStr).plusYears(1);
        int y = dateForYearReport.getYear();
        LocalDateTime dtCreationStart = LocalDateTime.of(y, 1, 1, 0, 0);
        LocalDateTime dtCreationEnd = LocalDateTime.of(y, 12, 31, 23, 59);
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }

    public static DateTimeRange buildStartEndDateForHourReport(LocalDateTime dateTime){
        LocalDateTime startDt = dateTime.minusHours(1).withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime endDt = dateTime.withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        return new DateTimeRange(startDt, endDt);
    }

    public static DateTimeRange buildStartEndDateForDailyReport(LocalDateTime dateTime){
        LocalDateTime startDt = dateTime.minusDays(1).withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime endDt = dateTime.withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        return new DateTimeRange(startDt, endDt);
    }

    public static DateTimeRange buildStartEndDateForMonthReport(LocalDateTime dateTime){
        LocalDateTime startDt = dateTime.minusMonths(1).withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime endDt = dateTime.withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        return new DateTimeRange(startDt, endDt);
    }

    public static DateTimeRange buildStartEndDateForYearReport(LocalDateTime dateTime){
        LocalDateTime startDt = dateTime.minusYears(1).withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime endDt = dateTime.withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        return new DateTimeRange(startDt, endDt);
    }

    public static DateTimeRange buildStartEndDateForShiftReport(LinkedHashMap<String, String> shiftNumToStartTime, String shiftNum, LocalDateTime reportDtCreation) {
        String startTimeStr = shiftNumToStartTime.get(shiftNum);
        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime reportTimeCreation = reportDtCreation.toLocalTime();
        LocalDate startDate;
        LocalDate endDate = reportDtCreation.toLocalDate();
        if(startTime.isAfter(reportTimeCreation)){
            startDate = endDate.minusDays(1);
        }
        else{
            startDate = reportDtCreation.toLocalDate();
        }

        int shiftNumInt = Integer.parseInt(shiftNum);

        String nextShiftNum = shiftNumInt == shiftNumToStartTime.size() ? 1 + "" : ++shiftNumInt + "";

        LocalTime endTime = LocalTime.parse(shiftNumToStartTime.get(nextShiftNum));

        return new DateTimeRange(
                LocalDateTime.of(startDate, startTime),
                LocalDateTime.of(endDate, endTime)
        );
    }
}
