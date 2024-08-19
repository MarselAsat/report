package com.nppgks.reportingsystem.controller.view;

import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.MeteringNode;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.ReportType;
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

    @GetMapping("/manual-tags-editor")
    public String getManualTagsEditor() {
        return "editors/manual-tags-editor";
    }
    @GetMapping("/manual-report-types-editor")
    public String getManualReportTypesEditor() {
        return "editors/manual-report-types-editor";
    }

    @GetMapping("/scheduled-tables-editor/tags")
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
    @GetMapping("/scheduled-tables-editor/report-rows")
    public String reportRowEditorView(ModelMap modelMap) {
        modelMap.put("reportTypes", reportTypeService.getAllActiveReportTypes()
                .stream()
                .map(ReportType::getName)
                .toList());
        return "editors/report-rows-editor";
    }

    @GetMapping("/scheduled-tables-editor/report-types")
    public String reportTypeEditorView() {
        return "editors/report-types-editor";
    }

    @GetMapping("/scheduled-tables-editor/metering-nodes")
    public String meteringNodeEditorView() {
        return "editors/metering-nodes-editor";
    }

    @GetMapping("/settings")
    public String getSettingsPage(ModelMap modelMap) {

//        for (ReportTypesEnum reportType : ReportTypesEnum.values()) {
//            String reportTypeName = reportType.name();
//            Proverka proverka = settingsService.getValuesBySettingName(reportTypeName + SettingsConstants.REPORT_COLUMNS_POSTFIX);
//
//            List<String> columns = proverka.getValues();
//            String check = proverka.getCheck();
//            modelMap.put(reportTypeName + "Columns", columns);
//
//            if(reportType.equals(ReportTypesEnum.shift)){
//                //modelMap.put(reportTypeName+"StartTime",
//                //        settingsService.getMapValuesBySettingName(reportTypeName+SettingsConstants.START_TIME_REPORT_POSTFIX));
//                modelMap.put(reportTypeName+"StartTime",
//                        proverka.getStartTime());
//}
//            else  if(check.equals("start time report"))  {
//                modelMap.put(reportTypeName+"StartTime",
//                        proverka.getStartTime());
//            }
//        }
//
//
//        List<MeteringNode> allMeteringNodes = meteringNodeService.getAllNodes();
//        String meteringStationName = settingsService.getStringValueBySettingName(SettingsConstants.METERING_STATION_NAME);
//        modelMap.put("meteringNodes", allMeteringNodes);
//        modelMap.put("meteringStationName", meteringStationName);
//
//
//        return "settings";


//            for (ReportTypesEnum reportType : ReportTypesEnum.values()) {
//                String reportTypeName = reportType.name();
//                Proverka proverka = settingsService.getValuesBySettingName(reportTypeName + SettingsConstants.REPORT_COLUMNS_POSTFIX);
//                String startTime = settingsService.getStringValueBySettingName(reportTypeName + SettingsConstants.START_TIME_REPORT_POSTFIX);
//
//                List<String> columns = proverka.getValues();
//                String check = proverka.getCheck();
//                modelMap.put(reportTypeName + "Columns", columns);
//
//                if (check.equals("start time of report")) {
//                    modelMap.put(reportTypeName + "StartTime", startTime);
//                } else {
//                    modelMap.put(reportTypeName + "StartTime", proverka.getStartTime());
//                }
//            }
//
//            List<MeteringNode> allMeteringNodes = meteringNodeService.getAllNodes();
//            String meteringStationName = settingsService.getStringValueBySettingName(SettingsConstants.METERING_STATION_NAME);
//            modelMap.put("meteringNodes", allMeteringNodes);
//            modelMap.put("meteringStationName", meteringStationName);
//
//            return "settings";
//        }
//// из gpt с proverka1
        for (ReportTypesEnum reportType : ReportTypesEnum.values()) {
            String reportTypeName = reportType.name();
            Proverka proverka = settingsService.getValuesBySettingName(reportTypeName + SettingsConstants.REPORT_COLUMNS_POSTFIX);

            List<String> columns = proverka.getValues();
            String check = proverka.getCheck();
            modelMap.put(reportTypeName + "Columns", columns);

//            Integer minuteInterval = settingsService.getMinuteReportInterval();
//            modelMap.put("minuteReportName", minuteInterval + " минутный отчет");

            if (check.equals("start time report") && reportTypeName.indexOf("shift") >= 0) {
                modelMap.put(reportTypeName+"StartTime",
                        settingsService.getMapValuesBySettingName(reportTypeName+SettingsConstants.REPORT_COLUMNS_POSTFIX));
            }  else if (check.equals("start time report")) {
                String proverka1 = settingsService.getStringValueBySettingName(reportTypeName + SettingsConstants.REPORT_COLUMNS_POSTFIX);
                modelMap.put(reportTypeName + "StartTime", proverka1);
              }
             else  {
                // String proverka1 = settingsService.getStringValueBySettingName(reportTypeName + SettingsConstants.REPORT_COLUMNS_POSTFIX);
                  modelMap.put(reportTypeName + "StartTime", proverka.getStartTime());
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
        modelMap.put("tags", allTagsService.getAllScheduledAndManualTags());
        return "opc-server";
    }

    @GetMapping("/TipOtchetov")
    public String getTipOtchetovPage(ModelMap modelMap) {
        modelMap.put("tags", allTagsService.getAllScheduledAndManualTags());
        return "TipOtchetov";

    }


}
