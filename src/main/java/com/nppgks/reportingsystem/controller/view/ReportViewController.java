package com.nppgks.reportingsystem.controller.view;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportName;
import com.nppgks.reportingsystem.dto.ReportViewTagData;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.util.ArrayParser;
import com.nppgks.reportingsystem.service.dbservices.ReportNameService;
import com.nppgks.reportingsystem.service.dbservices.SettingsService;
import com.nppgks.reportingsystem.service.dbservices.TagDataService;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcReportNameService;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcTagDataService;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReportViewController {

    private final ReportNameService reportNameService;

    private final CalcReportNameService calcReportNameService;

    private final CalcTagDataService calcTagDataService;
    private final TagDataService tagDataService;

    private final SettingsService settingsService;

    @GetMapping(value = "/dailyReport/{reportNameId}")
    public String getDailyReport(ModelMap modelMap,
                            @PathVariable Long reportNameId){
        ReportName reportName = reportNameService.getById(reportNameId);
        List<ReportViewTagData> reportViewTagData = tagDataService.getReportViewTagData(reportNameId);
        List<String> dailyColumns = settingsService.getListValuesBySettingName(SettingsConstants.DAILY_REPORT_COLUMNS);
        fillModelMapForReportView(modelMap, reportName, reportViewTagData, dailyColumns);
        return "report_pages/daily-report-page";
    }

    @GetMapping(value = "/hourReport/{reportNameId}")
    public String getHourReport(ModelMap modelMap,
                            @PathVariable Long reportNameId){
        ReportName reportName = reportNameService.getById(reportNameId);
        List<ReportViewTagData> reportViewTagData = tagDataService.getReportViewTagData(reportNameId);

        // какие столбцы будут отображаться в таблице отчета. (например, 'ИЛ №1' будет, а 'ИЛ №2' не будет)
        List<String> hourColumns = settingsService.getListValuesBySettingName(SettingsConstants.HOUR_REPORT_COLUMNS);
        fillModelMapForReportView(modelMap, reportName, reportViewTagData, hourColumns);
        return "report_pages/hour-report-page";
    }

    @GetMapping(value = "/twohourReport/{reportNameId}")
    public String get2HourReport(ModelMap modelMap,
                                @PathVariable Long reportNameId){
        ReportName reportName = reportNameService.getById(reportNameId);
        List<ReportViewTagData> reportViewTagData = tagDataService.getReportViewTagData(reportNameId);
        List<String> twohourColumns = settingsService.getListValuesBySettingName(SettingsConstants.TWOHOUR_REPORT_COLUMNS);
        fillModelMapForReportView(modelMap, reportName, reportViewTagData, twohourColumns);
        return "report_pages/twohour-report-page";
    }

    @GetMapping(value = "/shiftReport/{reportNameId}")
    public String getShiftReport(ModelMap modelMap,
                                 @PathVariable Long reportNameId){
        ReportName reportName = reportNameService.getById(reportNameId);
        List<ReportViewTagData> reportViewTagData = tagDataService.getReportViewTagData(reportNameId);
        List<String> columnNames = settingsService.getListValuesBySettingName(SettingsConstants.SHIFT_REPORT_COLUMNS);
        fillModelMapForReportView(modelMap, reportName, reportViewTagData, columnNames);
        return "report_pages/shift-report-page";
    }

    @GetMapping(value = "/monthReport/{reportNameId}")
    public String getMonthReport(ModelMap modelMap,
                                 @PathVariable Long reportNameId){
        ReportName reportName = reportNameService.getById(reportNameId);
        List<ReportViewTagData> reportViewTagData = tagDataService.getReportViewTagData(reportNameId);
        List<String> columnNames = settingsService.getListValuesBySettingName(SettingsConstants.MONTH_REPORT_COLUMNS);
        fillModelMapForReportView(modelMap, reportName, reportViewTagData, columnNames);
        return "report_pages/month-report-page";
    }

    @GetMapping(value = "/yearReport/{reportNameId}")
    public String getYearReport(ModelMap modelMap,
                                 @PathVariable Long reportNameId){
        ReportName reportName = reportNameService.getById(reportNameId);
        List<ReportViewTagData> reportViewTagData = tagDataService.getReportViewTagData(reportNameId);
        List<String> columnNames = settingsService.getListValuesBySettingName(SettingsConstants.YEAR_REPORT_COLUMNS);
        fillModelMapForReportView(modelMap, reportName, reportViewTagData, columnNames);
        return "report_pages/year-report-page";
    }

    @GetMapping(value = "/poverkiReport/{reportNameId}")
    public String getPoverkiReport(ModelMap modelMap,
                                @PathVariable Long reportNameId){
        var reportName = calcReportNameService.findReportNameById(reportNameId);

        LocalDate creationDate = reportName.getCreationDt().toLocalDate();

        modelMap.put("date", SingleDateTimeFormatter.formatToSinglePattern(creationDate));

        var tagDataList = calcTagDataService.getTagDataList(reportNameId);

        tagDataList.forEach(td -> {
            Object value = ArrayParser.fromJsonToObject(td.getData());
            modelMap.put(
                    td.getTagName().getPermanentName(), value);
        });

        return "report_pages/calc3622-report-page";
    }

    private void fillModelMapForReportView(ModelMap modelMap, ReportName reportName, List<ReportViewTagData> reportViewTagData, List<String> columnNames) {
        String meteringStationName = settingsService.getStringValueBySettingName(SettingsConstants.METERING_STATION_NAME);
        LocalDateTime reportStartDt = reportName.getStartDt();
        LocalDateTime reportEndDt = reportName.getEndDt();
        modelMap.put("reportViewTagData", reportViewTagData);
        modelMap.put("reportNameDtCreation", SingleDateTimeFormatter
                .formatToSinglePattern(reportName.getCreationDt()));
        modelMap.put("startReportDate", SingleDateTimeFormatter
                .formatToSinglePattern(reportStartDt));
        modelMap.put("endReportDate", SingleDateTimeFormatter
                .formatToSinglePattern(reportEndDt));
        modelMap.put("columns", columnNames);
        modelMap.put("meteringStationName", meteringStationName);
    }
}
