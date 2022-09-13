package com.nppgks.reports.service;

import com.nppgks.reports.entity.Settings;
import com.nppgks.reports.repository.SettingsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional
public class SettingsService {

    private final SettingsRepository settingsRepository;

    public List<String> getListValuesBySettingName(String name){
        Settings setting = settingsRepository.findByName(name);
        String value = setting.getValue();
        return Arrays.stream(value.split(",")).toList();
    }

    public String getStringValueBySettingName(String name){
        Settings setting = settingsRepository.findByName(name);
        return setting.getValue();
    }

    public boolean updateSettingsList(Map<String, String> settings){
        try{
            for(Map.Entry<String, String> setting: settings.entrySet()){
                settingsRepository.update(setting.getKey(), setting.getValue());
            }
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
