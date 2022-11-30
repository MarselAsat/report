package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.dto.TagDataDto;
import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportName;
import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.dto.ReportViewTagData;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.opc.ArrayParser;
import com.nppgks.reportingsystem.service.dbservices.ReportNameService;
import com.nppgks.reportingsystem.service.dbservices.ReportTypeService;
import com.nppgks.reportingsystem.service.dbservices.SettingsService;
import com.nppgks.reportingsystem.service.dbservices.TagDataService;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcReportNameService;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcTagDataService;
import com.nppgks.reportingsystem.service.timeservices.SingleDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReportViewController {

    private final ReportNameService reportNameService;

    private final CalcReportNameService calcReportNameService;

    private final CalcTagDataService calcTagDataService;
    private final TagDataService tagDataService;
    private final ReportTypeService reportTypeService;
    private final SettingsService settingsService;

    @GetMapping("/startPage")
    public String getStartPage(ModelMap model){
        setCommonParams(model);
        return "start-page";
    }

    @GetMapping(value = "/startPage/filter")
    public String getReportNameByDateAndReportType(ModelMap modelMap,
                                      @RequestParam(required = false) LocalDate date,
                                      @RequestParam(required = false) String reportTypeId){
        if(date == null && reportTypeId == null){
            return "redirect:/startPage";
        }
        if(reportTypeId.equals("poverki")){
            var calcReportNames = calcReportNameService.findReportNameByDate(date);
            modelMap.put("reportNames", calcReportNames);
        }
        else{
            List<ReportName> reportNames = reportNameService.getReportNameByDateAndReportId(reportTypeId, date);
            modelMap.put("reportNames", reportNames);
        }

        setCommonParams(modelMap);
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

//        String sixOrSevenTable = settingsService.getStringValueBySettingName(SettingsConstants.MI3622_6OR7_TABLE);
//        modelMap.put("sixOrSevenTable", sixOrSevenTable);

        return "report_pages/calc3622-report-page";
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

        String sixOrSevenTable = settingsService.getStringValueBySettingName(SettingsConstants.MI3622_6OR7_TABLE);
        modelMap.put("MI3622_6or7Table", sixOrSevenTable);

        return "settings";
    }

    @PostMapping("/settings/update")
    @ResponseBody
    public boolean updateSettings(@RequestBody Map<String, String> settings){
        return settingsService.updateSettingsList(settings);
    }

    void setCommonParams(ModelMap model){
        model.put("reportTypes", reportTypeService.getAllReportTypes());
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
