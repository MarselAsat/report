package com.nppgks.reportingsystem.controller.view;

import com.nppgks.reportingsystem.certification.CrcGenerator;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.Report;
import com.nppgks.reportingsystem.service.dbservices.ReportService;
import com.nppgks.reportingsystem.service.dbservices.ReportTypeService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class StartPageViewController {

    private final BuildProperties buildProperties;
    private final ReportTypeService reportTypeService;
    private final ReportService reportService;
    private final ManualReportTypeService manualReportTypeService;

    private final ManualReportService manualReportService;


    @GetMapping
    public String getStartPage(ModelMap model) {
        setCommonParams(model);
        return "start-page";
    }

    @GetMapping(value = "/filter")
    public String getReportByDateAndReportType(ModelMap modelMap,
                                               @RequestParam(required = false) LocalDate date,
                                               @RequestParam(required = false) String reportTypeId,
                                               @RequestParam(required = false) String group) {
        if (date == null && reportTypeId == null) {
            return "redirect:/";
        } else if (reportTypeId != null && !group.equals("scheduled")) {
            var reports = manualReportService.findReportByDateAndType(date, reportTypeId);
            modelMap.put("reports", reports);}
//        } else if (reportTypeId != null && reportTypeId.equals("ACCEPTANCE_ACT")) {
//            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypesEnum.ACCEPTANCE_ACT.name());
//            modelMap.put("reports", reports);
//        } else if (reportTypeId != null && reportTypeId.equals("MI3272")) {
//            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypesEnum.MI3272.name());
//            modelMap.put("reports", reports);
//        } else if (reportTypeId != null && reportTypeId.equals("KMH_VISCOMETER")) {
//            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypesEnum.KMH_VISCOMETER.name());
//            modelMap.put("reports", reports);
//        } else if (reportTypeId != null && reportTypeId.equals("KMH_MOISTUREMETER")) {
//            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypesEnum.KMH_MOISTURE_METER.name());
//            modelMap.put("reports", reports);
//        } else if (reportTypeId != null && reportTypeId.equals("kmhMassmByMassm")) {
//            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypesEnum.KMH_MASSM_BY_MASSM.name());
//            modelMap.put("reports", reports);
//        } else if (reportTypeId != null && reportTypeId.equals("kmhMassmByPu")) {
//            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypesEnum.KMH_MASSM_BY_PU.name());
//            modelMap.put("reports", reports);
//        } else if (reportTypeId != null && reportTypeId.equals("kmhDensityMeter")) {
//            var reports = manualReportService.findReportByDateAndType(date, ManualReportTypesEnum.KMH_DENSITY_METER.name());
//            modelMap.put("reports", reports);
//        }
        // TODO: 10.10.2023 Нужно создать отдельную таблицу с ручными типами отчетов,
        //  тогда необходимость в этих if-else отпадет.
        else if (reportTypeId != null) {
            List<Report> reports = reportService.getReportsByDateAndReportId(reportTypeId, date);
            modelMap.put("reports", reports);
        }


        setCommonParams(modelMap);
        return "start-page";
    }

    @ResponseBody
    @GetMapping("/info")
    public Map<String, String> getAppInfo() {
        String version = buildProperties.get("version");
        return Map.of("version", version,
                "crc", String.valueOf(CrcGenerator.getCertificationAlgorithmsCrc(version)));
    }

    void setCommonParams(ModelMap model) {
        List<ReportType> allActiveReportTypes = manualReportTypeService.getAllActiveReportTypes();
        // Нужно разбить ручные типы отчетов на группы: кмх, поверки, акты.
        // Акты содержат в id "act", кмх "kmh", а поверки "mi"
        List<ReportType> acts = new ArrayList<>();
        List<ReportType> kmh = new ArrayList<>();
        List<ReportType> poverki = new ArrayList<>();
        for (ReportType reportType : allActiveReportTypes) {
            if(reportType.getId().toLowerCase().contains("kmh")){
                kmh.add(reportType);
            }
            else if(reportType.getId().toLowerCase().contains("act")){
                acts.add(reportType);
            }
            else if(reportType.getId().toLowerCase().contains("mi")){
                poverki.add(reportType);
            }
        }
        model.put("acts", acts);
        model.put("kmh", kmh);
        model.put("poverki", poverki);
        model.put("reportTypes", reportTypeService.getAllActiveReportTypes());
    }
}
