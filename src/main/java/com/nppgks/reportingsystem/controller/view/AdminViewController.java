package com.nppgks.reportingsystem.controller.view;

import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.db.recurring_reports.entity.MeteringNode;
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
    private final MeteringNodeService meteringNodeService;


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
        modelMap.put("meteringNodes",
                meteringNodeService.getAllNodes().stream()
                .map(MeteringNode::getName)
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
            else if(!reportType.equals(ReportTypesEnum.hour) && !reportType.equals(ReportTypesEnum.twohour)){
                modelMap.put(reportTypeName+"StartTime",
                        settingsService.getStringValueBySettingName(reportTypeName+SettingsConstants.START_TIME_REPORT_POSTFIX));
            }
        }
        List<MeteringNode> allMeteringNodes = meteringNodeService.getAllNodes();
        String meteringStationName = settingsService.getStringValueBySettingName(SettingsConstants.METERING_STATION_NAME);
        modelMap.put("meteringNodes", allMeteringNodes);
        modelMap.put("meteringStationName", meteringStationName);


        return "settings";
    }
}
