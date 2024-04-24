package com.nppgks.reportingsystem.util.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;

public class DateTimeRangeBuilder {

    /**
     * Этот метод используется для создания временного диапазона,
     * в рамках которого будет производиться поиск часового отчета.
     * Если в веб интерфейсе выбран часовой отчет и определенный день,
     * то показываются часовые отчеты за этот день.
     */
    public static DateTimeRange buildDateRangeForSearchingHourReport(String dtCreationStr) {
        LocalDateTime dtCreationStart = LocalDateTime.parse(dtCreationStr + "T01:00");
        LocalDate endDay = LocalDate.parse(dtCreationStr).plusDays(1);
        LocalDateTime dtCreationEnd = LocalDateTime.parse(endDay + "T00:59");
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }

    /**
     * Этот метод используется для создания временного диапазона,
     * в рамках которого будет производиться поиск двухчасового отчета.
     * Если в веб интерфейсе выбран двухчасовой отчет и определенный день,
     * то показываются двухчасовые отчеты за этот день.
     */
    public static DateTimeRange buildDateRangeForSearching2HourReport(String dateCreationStr) {
        LocalDateTime dtCreationStart = LocalDateTime.parse(dateCreationStr + "T02:00");
        LocalDate endDay = LocalDate.parse(dateCreationStr).plusDays(1);
        LocalDateTime dtCreationEnd = LocalDateTime.parse(endDay + "T00:59");
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }

    /**
     * Этот метод используется для создания временного диапазона,
     * в рамках которого будет производиться поиск суточного отчета.
     * Если в веб интерфейсе выбран суточный отчет и определенный день,
     * то показываются суточные отчеты за этот день.
     */
    public static DateTimeRange buildDateRangeForSearchingDailyReport(String dtCreationStr) {
        LocalDateTime dtCreationStart = LocalDateTime.parse(dtCreationStr + "T00:00").plusDays(1);
        LocalDateTime dtCreationEnd = LocalDateTime.parse(dtCreationStr + "T23:59").plusDays(1);
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }

    /**
     * Этот метод используется для создания временного диапазона,
     * в рамках которого будет производиться поиск месячного отчета.
     * Если в веб интерфейсе выбран месячный отчет и определенный день,
     * то показываются месячные отчеты за месяц, к которому принадлежит выбранный день.
     */
    public static DateTimeRange buildDateRangeForSearchingMonthReport(String dtCreationStr) {
        LocalDate dateForMonthReport = LocalDate.parse(dtCreationStr).plusMonths(1);
        Month month = dateForMonthReport.getMonth();
        int year = dateForMonthReport.getYear();
        LocalDateTime dtCreationStart = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime dtCreationEnd = LocalDateTime.of(year, month, month.length(false), 23, 59);
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }

    /**
     * Этот метод используется для создания временного диапазона,
     * в рамках которого будет производиться поиск годового отчета.
     * Если в веб интерфейсе выбран годовой отчет и определенный день,
     * то показываются годовые отчеты за год, к которому принадлежит выбранный день.
     */
    public static DateTimeRange buildDateRangeForSearchingYearReport(String dtCreationStr) {
        LocalDate dateForYearReport = LocalDate.parse(dtCreationStr).plusYears(1);
        int y = dateForYearReport.getYear();
        LocalDateTime dtCreationStart = LocalDateTime.of(y, 1, 1, 0, 0);
        LocalDateTime dtCreationEnd = LocalDateTime.of(y, 12, 31, 23, 59);
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }

    /**
     * Этот метод используется для создания временного диапазона,
     * определяющий начало и конец периода, за который формируется часовой отчет.
     * Например, если часовой отчет создается в 10:15,
     * то начало периода будет в 09:00, а конец в 10:00.
     */
    public static DateTimeRange buildStartEndDateForHourReport(LocalDateTime dateTime) {
        LocalDateTime startDt = dateTime.minusHours(1).withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime endDt = dateTime.withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        return new DateTimeRange(startDt, endDt);
    }


    /**
     * Этот метод используется для создания временного диапазона,
     * определяющий начало и конец периода, за который формируется двухчасовой отчет.
     * Например, если двуччасовой отчет создается в 10:15,
     * то начало периода будет в 08:00, а конец в 10:00.
     */
    public static DateTimeRange buildStartEndDateFor2HourReport(LocalDateTime dateTime) {
        LocalDateTime startDt = dateTime.minusHours(2).withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime endDt = dateTime.withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        return new DateTimeRange(startDt, endDt);
    }

    /**
     * Этот метод используется для создания временного диапазона,
     * определяющий начало и конец периода, за который формируется суточный отчет.
     * Например, если суточный отчет создается в 10:15 21 февраля,
     * то начало периода будет в 10:00 20 февраля, а конец в 10:00 21 февраля.
     */
    public static DateTimeRange buildStartEndDateForDailyReport(LocalDateTime dateTime) {
        LocalDateTime startDt = dateTime.minusDays(1).withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime endDt = dateTime.withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        return new DateTimeRange(startDt, endDt);
    }

    /**
     * Этот метод используется для создания временного диапазона,
     * определяющий начало и конец периода, за который формируется месячный отчет.
     * Например, если месячный отчет создается в 10:15 1 февраля,
     * то начало периода будет в 10:00 1 января, а конец в 10:00 1 февраля.
     */
    public static DateTimeRange buildStartEndDateForMonthReport(LocalDateTime dateTime) {
        LocalDateTime startDt = dateTime.minusMonths(1).withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime endDt = dateTime.withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        return new DateTimeRange(startDt, endDt);
    }

    /**
     * Этот метод используется для создания временного диапазона,
     * определяющий начало и конец периода, за который формируется годовой отчет.
     * Например, если годовой отчет создается в 10:15 1 января 2023г,
     * то начало периода будет в 10:00 1 января 2022г, а конец в 10:00 1 января 2023г.
     */
    public static DateTimeRange buildStartEndDateForYearReport(LocalDateTime dateTime) {
        LocalDateTime startDt = dateTime.minusYears(1).withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime endDt = dateTime.withMinute(0)
                .truncatedTo(ChronoUnit.MINUTES);
        return new DateTimeRange(startDt, endDt);
    }

    /**
     * Этот метод используется для создания временного диапазона,
     * определяющий начало и конец периода, за который формируется сменный отчет.
     * Например, если сменный отчет за 1 смену (с 10:00 до 18:00) создается в 18:15 21 февраля,
     * то начало периода будет в 10:00 21 февраля, а конец в 18:00 21 февраля.
     */
    public static DateTimeRange buildStartEndDateForShiftReport(LinkedHashMap<String, String> shiftNumToStartTime, String shiftNum, LocalDateTime reportDtCreation) {
        String startTimeStr = shiftNumToStartTime.get(shiftNum);
        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime reportTimeCreation = reportDtCreation.toLocalTime();
        LocalDate startDate;
        LocalDate endDate = reportDtCreation.toLocalDate();
        if (startTime.isAfter(reportTimeCreation) || startTime.equals(reportTimeCreation)) {
            startDate = endDate.minusDays(1);
        } else {
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
   // метод для минутного отчета

    public static DateTimeRange buildStartEndDateForMinuteReport(LocalDateTime dateTime) {
        LocalDateTime startDt = dateTime.minusMinutes(1).withSecond(0)
                .truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime endDt = dateTime.withSecond(0)
                .truncatedTo(ChronoUnit.SECONDS);
        return new DateTimeRange(startDt, endDt);
    }

    public static DateTimeRange buildDateRangeForMinuteReport(String dtCreationStr) {
        LocalDateTime dtCreationStart = LocalDateTime.parse(dtCreationStr + "T01:00");
        LocalDate endDay = LocalDate.parse(dtCreationStr).plusDays(1);
        LocalDateTime dtCreationEnd = LocalDateTime.parse(endDay + "T00:59");
        return new DateTimeRange(dtCreationStart, dtCreationEnd);
    }


}
