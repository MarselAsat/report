package com.nppgks.reportingsystem.controller.view;

import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.dto.ReportRowDto;
import com.nppgks.reportingsystem.dto.ReportTypeDto;
import com.nppgks.reportingsystem.service.dbservices.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminViewController {

    private final ReportTypeService reportTypeService;
    private final ReportRowService reportRowService;
    private final SettingsService settingsService;


    @GetMapping("/calc-tag-name-editor")
    public String getAllcalcTagNames(){
        return "calc-tag-names-editor";
    }

    @GetMapping("/tag-name-editor")
    public String tagNameEditorView(ModelMap modelMap){
        modelMap.put("reportTypes",
                reportTypeService.getAllReportTypes().stream()
                .map(ReportTypeDto::getName)
                .toList());
        modelMap.put("reportRows",
                reportRowService.getAllReportRows().stream()
                .map(ReportRowDto::combineNameAndType)
                .toList());
        return "tag-names-editor";
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
}
