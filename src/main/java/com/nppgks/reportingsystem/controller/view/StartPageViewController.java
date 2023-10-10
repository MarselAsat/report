package com.nppgks.reportingsystem.controller.view;

import com.nppgks.reportingsystem.certification.CrcGenerator;
import com.nppgks.reportingsystem.constants.ManualReportTypes;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.Report;
import com.nppgks.reportingsystem.service.dbservices.ReportService;
import com.nppgks.reportingsystem.service.dbservices.ReportTypeService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class StartPageViewController {

    private final BuildProperties buildProperties;
    private final ReportTypeService reportTypeService;
    private final ReportService reportService;

    private final ManualReportService manualReportService;


    @GetMapping
    public String getStartPage(ModelMap model){
        setCommonParams(model);
        return "start-page";
    }

    @GetMapping(value = "/filter")
    public String getReportByDateAndReportType(ModelMap modelMap,
                                               @RequestParam(required = false) LocalDate date,
                                               @RequestParam(required = false) String reportTypeId){
        if(date == null && reportTypeId == null) {
            return "redirect:/";
        }
        else if(reportTypeId != null && reportTypeId.equals("mi3622")) {
            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypes.MI3622.name());
            modelMap.put("reports", reports);
        }
        else if(reportTypeId != null && reportTypeId.equals("acceptanceAct")) {
            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypes.ACCEPTANCE_ACT.name());
            modelMap.put("reports", reports);
        }
        else if(reportTypeId != null && reportTypeId.equals("mi3272")) {
            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypes.MI3272.name());
            modelMap.put("reports", reports);
        }
        else if(reportTypeId != null && reportTypeId.equals("kmhViscometer")) {
            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypes.KMH_VISCOMETER.name());
            modelMap.put("reports", reports);
        }
        else if(reportTypeId != null && reportTypeId.equals("kmhMoisturemeter")) {
            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypes.KMH_MOISTURE_METER.name());
            modelMap.put("reports", reports);
        }
        else if(reportTypeId != null && reportTypeId.equals("kmhMassmByMassm")) {
            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypes.KMH_MASSM_BY_MASSM.name());
            modelMap.put("reports", reports);
        }
        else if(reportTypeId != null && reportTypeId.equals("kmhMassmByPu")) {
            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypes.KMH_MASSM_BY_PU.name());
            modelMap.put("reports", reports);
        }
        else if(reportTypeId != null && reportTypeId.equals("kmhDensityMeter")) {
            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypes.KMH_DENSITY_METER.name());
            modelMap.put("reports", reports);
        }
        // TODO: 10.10.2023 Нужно создать отдельную таблицу с ручными типами отчетов,
        //  тогда необходимость в этих if-else отпадет.
        else if(reportTypeId != null){
            List<Report> reports = reportService.getReportsByDateAndReportId(reportTypeId, date);
            modelMap.put("reports", reports);
        }


        setCommonParams(modelMap);
        return "start-page";
    }

    @ResponseBody
    @GetMapping("/info")
    public Map<String, String> getAppInfo(){
        String version = buildProperties.get("version");
        return Map.of("version", version,
                "crc", String.valueOf(CrcGenerator.getCertificationAlgorithmsCrc(version)));
    }

    void setCommonParams(ModelMap model){
        model.put("reportTypes", reportTypeService.getAllActiveReportTypes());
    }
}
