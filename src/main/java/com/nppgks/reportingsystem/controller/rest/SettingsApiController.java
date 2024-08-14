package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.service.dbservices.SettingsService;
import com.nppgks.reportingsystem.reportgeneration.scheduled_reports.ReportsScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsApiController {

    private final SettingsService settingsService;
    private final ReportsScheduler reportsScheduler;

    @PatchMapping
    public boolean updateSettings(@RequestBody Map<String, String> settings){
        return settingsService.updateSettingsList(settings);
    }

    @PostMapping("/minute")
    public void resetMinuteReport() {
        reportsScheduler.resetMinuteReport();
    }

//    @PostMapping("/minute")
//    public void resetMinuteReport(@RequestBody Map<String, Integer> body) {
//        int minutes = body.get("minutes");
//        reportsScheduler.resetMinuteReport(minutes);
//    }
}