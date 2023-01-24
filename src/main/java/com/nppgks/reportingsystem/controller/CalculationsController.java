package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.db.calculations.entity.ReportName;
import com.nppgks.reportingsystem.db.calculations.entity.TagData;
import com.nppgks.reportingsystem.opc.ArrayParser;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.CalcService;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcReportNameService;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcTagDataService;
import com.nppgks.reportingsystem.service.timeservices.SingleDateTimeFormatter;
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
public class CalculationsController {

    private final CalcReportNameService reportNameService;
    private final CalcTagDataService tagDataService;
    private final CalcService calcService;
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

        List<TagData> tagDataList = tagDataService.getTagDataList(reportNameId);

        tagDataList.forEach(td -> {
            Object value = ArrayParser.fromJsonToObject(td.getData());
                            modelMap.put(
                                    td.getTagName().getPermanentName(), value);
        });

        return "report_pages/calc3622-report-page";
    }
}
