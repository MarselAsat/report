package com.nppgks.reports.controller;

import com.nppgks.reports.constants.SettingsConstants;
import com.nppgks.reports.db.calculations.entity.ReportName;
import com.nppgks.reports.db.calculations.entity.TagData;
import com.nppgks.reports.opc.ArrayParser;
import com.nppgks.reports.service.calc3622.CalcService;
import com.nppgks.reports.service.db_services.SettingsService;
import com.nppgks.reports.service.db_services.calculation.CalcReportNameService;
import com.nppgks.reports.service.db_services.calculation.CalcTagDataService;
import com.nppgks.reports.service.time_services.SingleDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class CalcController {

    private final CalcReportNameService reportNameService;
    private final CalcTagDataService tagDataService;
    private final CalcService calcService;

    private final SettingsService settingsService;
    private final String CALC_3622 = "3622";

    @ResponseBody
    @GetMapping("/calc"+ CALC_3622)
    public void doCalc3622(){
        calcService.doCalc3622();
    }

    @ResponseBody
    @GetMapping("/calc"+ CALC_3622 +"/save")
    public void saveDataCalc3622(){
        calcService.saveInDb();
    }

    @GetMapping("/calc"+ CALC_3622 +"/display/{reportNameId}")
    public String displayCalc3622(@PathVariable Long reportNameId, ModelMap modelMap){

        ReportName reportName = reportNameService.findReportNameById(reportNameId);

        LocalDate creationDate = reportName.getCreationDt().toLocalDate();

        modelMap.put("date", SingleDateTimeFormatter.formatToSinglePattern(creationDate));
        modelMap.put("conclusion", "не годен");

        List<TagData> tagDataList = tagDataService.getTagDataList(reportNameId);

        tagDataList.forEach(td -> {
            Object value = ArrayParser.fromJsonToObject(td.getData());
                            modelMap.put(
                                    td.getTagName().getPermanentName(), value);
        });

        String sixOrSevenTable = settingsService.getStringValueBySettingName(SettingsConstants.MI3622_6OR7_TABLE);
        modelMap.put("sixOrSevenTable", sixOrSevenTable);

        return "report_pages/calc3622-report-page";
    }
}
