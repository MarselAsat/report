package com.nppgks.reports.controller;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.db.recurring_reports.entity.ReportName;
import com.nppgks.reports.constants.ReportTypesEnum;
import com.nppgks.reports.dto.ReportViewTagData;
import com.nppgks.reports.constants.SettingsConstants;
import com.nppgks.reports.service.db_services.ReportNameService;
import com.nppgks.reports.service.db_services.ReportTypeService;
import com.nppgks.reports.service.db_services.SettingsService;
import com.nppgks.reports.service.db_services.TagDataService;
import com.nppgks.reports.service.time_services.SingleDateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class ReportViewController {

    private final ReportNameService reportNameService;
    private final TagDataService tagDataService;
    private final ReportTypeService reportTypeService;
    private final SettingsService settingsService;

    @Autowired
    public ReportViewController(ReportNameService reportNameService,
                                TagDataService tagDataService,
                                ReportTypeService reportTypeService, SettingsService settingsService) {
        this.reportNameService = reportNameService;
        this.reportTypeService = reportTypeService;
        this.tagDataService = tagDataService;
        this.settingsService = settingsService;
    }

    @GetMapping("/startPage")
    public String getStartPage(ModelMap model){
        setCommonParams(model, true);
        return "start-page";
    }

    @GetMapping(value = "/startPage/filter")
    public String getReportNameByDateAndReportType(ModelMap modelMap,
                                      @RequestParam(required = false) String date,
                                      @RequestParam(required = false) String reportTypeId){
        if(Objects.equals(date, "")&&reportTypeId==null){
            return "redirect:/startPage";
        }
        List<ReportName> reportNames = reportNameService.getReportNameByDateAndReportId(reportTypeId, date);
        modelMap.put("reportNames", reportNames);
        setCommonParams(modelMap, false);
        return "start-page";
    }

    @GetMapping("/tagData/{reportNameId}")
    @ResponseBody
    public List<TagDataDto> getTagData(@PathVariable Long reportNameId){
        return tagDataService.getDataForReport(reportNameId);
    }

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
        List<String> dailyColumns = settingsService.getListValuesBySettingName(SettingsConstants.HOUR_REPORT_COLUMNS);
        fillModelMapForReportView(modelMap, reportName, reportViewTagData, dailyColumns);
        return "report_pages/hour-report-page";
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

    @GetMapping("/settings")
    public String getSettingsPage(ModelMap modelMap) {

        for (ReportTypesEnum reportType : ReportTypesEnum.values()) {
            String reportTypeName = reportType.name();
            List<String> columns = settingsService.getListValuesBySettingName(reportTypeName + SettingsConstants.REPORT_COLUMNS_POSTFIX);
            modelMap.put(reportTypeName + "Columns", columns);

            if(reportType.equals(ReportTypesEnum.shift)){
                modelMap.put(reportTypeName+"StartTime",
                        settingsService.getMapValuesBySettingName(reportTypeName+SettingsConstants.START_TIME_REPORT_POSTFIX));
            }
            else if(!reportType.equals(ReportTypesEnum.hour)){
                modelMap.put(reportTypeName+"StartTime",
                        settingsService.getStringValueBySettingName(reportTypeName+SettingsConstants.START_TIME_REPORT_POSTFIX));
            }
        }
        String meteringStationName = settingsService.getStringValueBySettingName(SettingsConstants.METERING_STATION_NAME);
        modelMap.put("meteringStationName", meteringStationName);

        return "settings";
    }

    @PostMapping("/settings/update")
    @ResponseBody
    public boolean updateSettings(@RequestBody Map<String, String> settings){
        return settingsService.updateSettingsList(settings);
    }

    void setCommonParams(ModelMap model, boolean defaultView){
        if(defaultView){
            model.put("reportTypes", reportTypeService.getAllReportTypes());
            model.put("reportNames", reportNameService.findAll());
        }
        else{
            model.put("reportTypes", reportTypeService.getAllReportTypes());
        }
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
