package com.nppgks.reports.service;

import com.nppgks.reports.entity.Settings;
import com.nppgks.reports.repository.SettingsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class SettingsService {

    private final SettingsRepository settingsRepository;

    public List<String> getListValuesBySettingName(String name){
        Settings setting = settingsRepository.findByName(name);
        String value = setting.getValue();
        return Arrays.stream(value.split(",")).toList();
    }
}
