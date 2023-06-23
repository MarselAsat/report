package com.nppgks.reportingsystem.controller.view;

import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.db.operative_reports.entity.MeteringNode;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportType;
import com.nppgks.reportingsystem.dto.ReportRowDto;
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
    private final AllTagsService allTagsService;

    @GetMapping("/calc-tags-editor")
    public String getAllCalcTags() {
        return "editors/calc-tags-editor";
    }

    @GetMapping("/operative-tables-editor/tags")
    public String tagEditorView(ModelMap modelMap) {
        modelMap.put("reportTypes",
                reportTypeService.getAllActiveReportTypes()
                        .stream()
                        .map(ReportType::getName)
                        .toList());
        modelMap.put("meteringNodes",
                meteringNodeService.getAllNodes()
                        .stream()
                        .map(MeteringNode::getName)
                        .toList());
        modelMap.put("reportRows",
                reportRowService.getAllReportRows()
                        .stream()
                        .map(ReportRowDto::combineNameAndType)
                        .toList());
        return "editors/tags-editor";
    }
    @GetMapping("/operative-tables-editor/report-rows")
    public String reportRowEditorView(ModelMap modelMap) {
        modelMap.put("reportTypes", reportTypeService.getAllActiveReportTypes()
                .stream()
                .map(ReportType::getName)
                .toList());
        return "editors/report-rows-editor";
    }

    @GetMapping("/operative-tables-editor/report-types")
    public String reportTypeEditorView() {
        return "editors/report-types-editor";
    }

    @GetMapping("/operative-tables-editor/metering-nodes")
    public String meteringNodeEditorView() {
        return "editors/metering-nodes-editor";
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

    @GetMapping("/opcServer")
    public String getOpcServerPage(ModelMap modelMap){
        modelMap.put("tags", allTagsService.getAllOperativeAndCalculationTags());
        return "opc-server";
    }
}
