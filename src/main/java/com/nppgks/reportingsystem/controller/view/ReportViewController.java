package com.nppgks.reportingsystem.controller.view;

import com.nppgks.reportingsystem.controller.ModelMapFiller;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.MeteringNode;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.Report;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.dto.ReportViewReportData;
import com.nppgks.reportingsystem.service.dbservices.MeteringNodeService;
import com.nppgks.reportingsystem.util.ArrayParser;
import com.nppgks.reportingsystem.service.dbservices.ReportService;
import com.nppgks.reportingsystem.service.dbservices.SettingsService;
import com.nppgks.reportingsystem.service.dbservices.ReportDataService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportDataService;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReportViewController {

    private final ReportService reportService;

    private final ManualReportService manualReportService;

    private final ManualReportDataService manualReportDataService;
    private final ReportDataService reportDataService;

    private final SettingsService settingsService;

    private final MeteringNodeService meteringNodeService;

    @GetMapping(value = "/dailyReport/{reportId}")
    public String getDailyReport(ModelMap modelMap,
                            @PathVariable Long reportId) {
        fillModelMapForReportView(modelMap, reportId, SettingsConstants.DAILY_REPORT_COLUMNS);
        return "report_pages/scheduled/daily-report-page";
    }

    @GetMapping(value = "/hourReport/{reportId}")
    public String getHourReport(ModelMap modelMap,
                            @PathVariable Long reportId){
        fillModelMapForReportView(modelMap, reportId, SettingsConstants.HOUR_REPORT_COLUMNS);
        return "report_pages/scheduled/hour-report-page";
    }

    @GetMapping(value = "/twohourReport/{reportId}")
    public String get2HourReport(ModelMap modelMap,
                                @PathVariable Long reportId){
        fillModelMapForReportView(modelMap, reportId, SettingsConstants.TWOHOUR_REPORT_COLUMNS);
        return "report_pages/scheduled/twohour-report-page";
    }

    @GetMapping(value = "/shiftReport/{reportId}")
    public String getShiftReport(ModelMap modelMap,
                                 @PathVariable Long reportId){
        fillModelMapForReportView(modelMap, reportId, SettingsConstants.SHIFT_REPORT_COLUMNS);
        return "report_pages/scheduled/shift-report-page";
    }

    @GetMapping(value = "/monthReport/{reportId}")
    public String getMonthReport(ModelMap modelMap,
                                 @PathVariable Long reportId){
        fillModelMapForReportView(modelMap, reportId, SettingsConstants.MONTH_REPORT_COLUMNS);
        return "report_pages/scheduled/month-report-page";
    }

    @GetMapping(value = "/yearReport/{reportId}")
    public String getYearReport(ModelMap modelMap,
                                 @PathVariable Long reportId){
        fillModelMapForReportView(modelMap, reportId, SettingsConstants.YEAR_REPORT_COLUMNS);
        return "report_pages/scheduled/year-report-page";
    }

    @GetMapping(value = "/mi3622Report/{reportId}")
    public String getMi3622Report(ModelMap modelMap,
                                  @PathVariable Long reportId){
        var report = manualReportService.findReportById(reportId);

        LocalDate creationDate = report.getCreationDt().toLocalDate();

        modelMap.put("date", SingleDateTimeFormatter.formatToSinglePattern(creationDate));

        var reportDataList = manualReportDataService.getReportDataList(reportId);

        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });

        return "report_pages/poverki/MI3622-report-page";
    }

    @GetMapping(value = "/mi3272Report/{reportId}")
    public String getMi3272Report(ModelMap modelMap,
                                  @PathVariable Long reportId){
        var report = manualReportService.findReportById(reportId);

        LocalDate creationDate = report.getCreationDt().toLocalDate();

        Map<Integer, String> monthsInRussian = new HashMap<>();
        monthsInRussian.put(1, "января");
        monthsInRussian.put(2, "февраля");
        monthsInRussian.put(3, "марта");
        monthsInRussian.put(4, "апреля");
        monthsInRussian.put(5, "мая");
        monthsInRussian.put(6, "июня");
        monthsInRussian.put(7, "июля");
        monthsInRussian.put(8, "августа");
        monthsInRussian.put(9, "сентября");
        monthsInRussian.put(10, "октября");
        monthsInRussian.put(11, "ноября");
        monthsInRussian.put(12, "декабря");

        modelMap.put("dateDay", creationDate.getDayOfMonth());
        modelMap.put("dateYear", creationDate.getYear());
        modelMap.put("dateMonth", monthsInRussian.get(creationDate.getMonthValue()));

        var reportDataList = manualReportDataService.getReportDataList(reportId);

        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });

        return "report_pages/poverki/MI3272-report-page";
    }

    @GetMapping(value = "/acceptanceActReport/{reportId}")
    public String getAcceptanceActReport(ModelMap modelMap,
                                  @PathVariable Long reportId){
        var reportDataList = manualReportDataService.getReportDataList(reportId);
        ModelMapFiller.fillForAcceptanceAct(modelMap, reportDataList);
        return "report_pages/acts/acceptance-oil-act";
    }

    @GetMapping(value = "/kmhViscometerReport/{reportId}")
    public String getKmhViscometerReport(ModelMap modelMap,
                                         @PathVariable Long reportId){
        var reportDataList = manualReportDataService.getReportDataList(reportId);
        var report = manualReportService.findReportById(reportId);
        modelMap.put("kmh_date", SingleDateTimeFormatter.formatToSinglePattern(report.getCreationDt().toLocalDate()));

        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
        return "report_pages/kmh/kmh-viscometer-report-page";
    }

    @GetMapping(value = "/kmhMoistureMeterReport/{reportId}")
    public String getKmhMoisturemeterReport(ModelMap modelMap,
                                         @PathVariable Long reportId){
        var reportDataList = manualReportDataService.getReportDataList(reportId);
        var report = manualReportService.findReportById(reportId);
        modelMap.put("kmh_date", SingleDateTimeFormatter.formatToSinglePattern(report.getCreationDt().toLocalDate()));

        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
        return "report_pages/kmh/kmh-moisturemeter-report-page";
    }

    @GetMapping(value = "/kmhMassmByMassmReport/{reportId}")
    public String getKmhMassmByMassmReport(ModelMap modelMap,
                                            @PathVariable Long reportId){
        var reportDataList = manualReportDataService.getReportDataList(reportId);
        var report = manualReportService.findReportById(reportId);
        modelMap.put("kmh_date", SingleDateTimeFormatter.formatToSinglePattern(report.getCreationDt()));

        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
        return "report_pages/kmh/kmh-massm-by-massm-report-page";
    }

    @GetMapping(value = "/kmhMassmByPuReport/{reportId}")
    public String getKmhMassmByPuReport(ModelMap modelMap,
                                         @PathVariable Long reportId){
        var reportDataList = manualReportDataService.getReportDataList(reportId);
        var report = manualReportService.findReportById(reportId);
        modelMap.put("kmh_date", SingleDateTimeFormatter.formatToSinglePattern(report.getCreationDt()));

        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
        return "report_pages/kmh/kmh-massm-by-pu-report-page";
    }

    @GetMapping(value = "/kmhDensityMeterReport/{reportId}")
    public String getKmhDensityMeterReport(ModelMap modelMap,
                                        @PathVariable Long reportId){
        var reportDataList = manualReportDataService.getReportDataList(reportId);
        var report = manualReportService.findReportById(reportId);
        modelMap.put("kmh_date", SingleDateTimeFormatter.formatToSinglePattern(report.getCreationDt()));

        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
        return "report_pages/kmh/kmh-density-meter-report-page";
    }
    @GetMapping(value = "/oilQualityPassportReport/{reportId}")
    public String getoilQualityPassportReport(ModelMap modelMap,
                                           @PathVariable Long reportId){
        var reportDataList = manualReportDataService.getReportDataList(reportId);
        var report = manualReportService.findReportById(reportId);
        modelMap.put("date", SingleDateTimeFormatter.formatToSinglePattern(report.getCreationDt()));

        reportDataList.forEach(rd -> {
            Object value = ArrayParser.fromJsonToObject(rd.getData());
            modelMap.put(
                    rd.getTag().getPermanentName(), value);
        });
        return "report_pages/acts/oil-quality-passport";
    }


    private void fillModelMapForReportView(ModelMap modelMap, Long reportId, String columnsFromSetting) {
        Report report = reportService.getById(reportId);
        List<ReportViewReportData> reportViewReportData = reportDataService.getReportViewReportData(reportId);
        List<String> meteringNodesDisplayIds = settingsService.getListValuesBySettingName(columnsFromSetting);
        List<MeteringNode> meteringNodesDisplay = meteringNodeService.getAllNodes().stream()
                .filter(mn -> meteringNodesDisplayIds.contains(mn.getId()))
                .toList();

        String meteringStationName = settingsService.getStringValueBySettingName(SettingsConstants.METERING_STATION_NAME);
        LocalDateTime reportStartDt = report.getStartDt();
        LocalDateTime reportEndDt = report.getEndDt();
        modelMap.put("reportViewReportData", reportViewReportData);
        modelMap.put("reportDtCreation", SingleDateTimeFormatter
                .formatToSinglePattern(report.getCreationDt()));
        modelMap.put("startReportDate", SingleDateTimeFormatter
                .formatToSinglePattern(reportStartDt));
        modelMap.put("endReportDate", SingleDateTimeFormatter
                .formatToSinglePattern(reportEndDt));
        modelMap.put("columns", meteringNodesDisplay);
        modelMap.put("meteringStationName", meteringStationName);
    }
}
