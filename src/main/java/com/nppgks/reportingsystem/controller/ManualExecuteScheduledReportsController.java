package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.reportgeneration.scheduled_reports.ReportsScheduler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManualExecuteScheduledReportsController {
    private final ReportsScheduler reportsScheduler;

    public ManualExecuteScheduledReportsController(ReportsScheduler reportsScheduler) {
        this.reportsScheduler = reportsScheduler;
    }

    @GetMapping("/manualGeneration/hourReport")
    public ResponseEntity<String> testHourReport() {
        if (reportsScheduler.generateReportDataForHourReport().isEmpty()) {
            return new ResponseEntity<>("Часовой отчет не активен", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Часовой отчет успешно сгенерирован", HttpStatus.OK);
        }
    }

    @GetMapping("/manualGeneration/2hourReport")
    public ResponseEntity<String> test2HourReport(){
        if (reportsScheduler.generateReportDataFor2HourReport().isEmpty()) {
            return new ResponseEntity<>("Двухчасовой отчет не активен", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Двухчасовой отчет успешно сгенерирован", HttpStatus.OK);
        }
    }

    @GetMapping("/manualGeneration/dailyReport")
    public ResponseEntity<String> testDailyReport(){
        if (reportsScheduler.generateReportDataForDailyReport().isEmpty()) {
            return new ResponseEntity<>("Суточный отчет не активен", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Суточный отчет успешно сгенерирован", HttpStatus.OK);
        }
    }
}
