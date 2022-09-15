package com.nppgks.reports.controller;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.entity.ReportTypesEnum;
import com.nppgks.reports.entity.ReportViewTagData;
import com.nppgks.reports.entity.SettingsConstants;
import com.nppgks.reports.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
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
        return "blog";
    }

    @GetMapping(value = "/startPage/filter")
    public String getReportNameByDateAndReportType(ModelMap modelMap,
                                      @RequestParam(required = false) String date,
                                      @RequestParam(required = false) Integer reportTypeId){
        if(Objects.equals(date, "")&&reportTypeId==null){
            return "redirect:/startPage";
        }
        List<ReportName> reportNames = reportNameService.getReportNameByDateAndReportId(reportTypeId, date);
        modelMap.put("reportNames", reportNames);
        setCommonParams(modelMap, false);
        return "blog";
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
        DateTimeRange dateTimeRange = DateTimeRangeBuilder
                .buildStartEndDateForDailyReport(reportName.getCreationDt());

        List<String> dailyColumns = settingsService.getListValuesBySettingName(SettingsConstants.DAILY_REPORT_COLUMNS);
        fillModelMapForReportView(modelMap, reportName, reportViewTagData, dateTimeRange, dailyColumns);
        return "daily-report-page";
    }

    private void fillModelMapForReportView(ModelMap modelMap, ReportName reportName, List<ReportViewTagData> reportViewTagData, DateTimeRange dateTimeRange, List<String> columnNames) {
        String meteringStationName = settingsService.getStringValueBySettingName(SettingsConstants.METERING_STATION_NAME);
        modelMap.put("reportViewTagData", reportViewTagData);
        modelMap.put("reportNameDtCreation", SingleDateTimeFormatter
                .formatToSinglePattern(reportName.getCreationDt()));
        modelMap.put("startReportDate", SingleDateTimeFormatter
                .formatToSinglePattern(dateTimeRange.getStartDateTime()));
        modelMap.put("endReportDate", SingleDateTimeFormatter
                .formatToSinglePattern(dateTimeRange.getEndDateTime()));
        modelMap.put("columns", columnNames);
        modelMap.put("meteringStationName", meteringStationName);
    }

    @GetMapping(value = "/hourReport/{reportNameId}")
    public String getHourReport(ModelMap modelMap,
                            @PathVariable Long reportNameId){
        ReportName reportName = reportNameService.getById(reportNameId);
        List<ReportViewTagData> reportViewTagData = tagDataService.getReportViewTagData(reportNameId);
        DateTimeRange dateTimeRange = DateTimeRangeBuilder
                .buildStartEndDateForHourReport(reportName.getCreationDt());

        List<String> dailyColumns = settingsService.getListValuesBySettingName(SettingsConstants.HOUR_REPORT_COLUMNS);
        fillModelMapForReportView(modelMap, reportName, reportViewTagData, dateTimeRange, dailyColumns);
        return "hour-report-page";
    }

    @GetMapping(value = "/shiftReport/{reportNameId}")
    public String getShiftReport(ModelMap modelMap,
                                 @PathVariable Long reportNameId){
        ReportName reportName = reportNameService.getById(reportNameId);
        List<ReportViewTagData> reportViewTagData = tagDataService.getReportViewTagData(reportNameId);
        LinkedHashMap<String, String> shiftNumAndTime = settingsService.getMapValuesBySettingName(SettingsConstants.START_SHIFT_REPORT);
        DateTimeRange dateTimeRange = DateTimeRangeBuilder
                .buildStartEndDateForShiftReport(shiftNumAndTime, reportName.getName(), reportName.getCreationDt());

        List<String> columnNames = settingsService.getListValuesBySettingName(SettingsConstants.SHIFT_REPORT_COLUMNS);
        fillModelMapForReportView(modelMap, reportName, reportViewTagData, dateTimeRange, columnNames);
        return "shift-report-page";
    }

    @GetMapping("/settings")
    public String getSettingsPage(ModelMap modelMap) {

        for (ReportTypesEnum reportType : ReportTypesEnum.values()) {
            String reportTypeName = reportType.name();
            List<String> columns = settingsService.getListValuesBySettingName(reportTypeName + " " + SettingsConstants.REPORT_COLUMNS);
            modelMap.put(reportTypeName + "Columns", columns);
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
}
