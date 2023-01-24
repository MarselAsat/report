package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.service.dbservices.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsApiController {

    private final SettingsService settingsService;

    @PatchMapping
    public boolean updateSettings(@RequestBody Map<String, String> settings){
        return settingsService.updateSettingsList(settings);
    }
}
