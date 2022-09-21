package com.nppgks.reports.service.db_services;

import com.nppgks.reports.db.entity.Settings;
import com.nppgks.reports.db.repository.SettingsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class SettingsService {

    private final SettingsRepository settingsRepository;

    public List<String> getListValuesBySettingName(String name){
        return settingsRepository.findByName(name)
                .map(settings -> {
                    String value = settings.getValue();
                    return Arrays.stream(value.split(",")).toList();
                })
                .orElseThrow(() -> new RuntimeException("No setting with name \""+name+"\""));

    }

    public String getStringValueBySettingName(String name){
        return settingsRepository.findByName(name)
                .map(Settings::getValue)
                .orElseThrow(() -> new RuntimeException("No setting with name \""+name+"\""));

    }

    public LinkedHashMap<String, String> getMapValuesBySettingName(String name){
        Settings setting = settingsRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("No setting with name \""+name+"\""));
        return Arrays.stream(setting.getValue().split(","))
                .map(keyVal -> {
                    String[] settingKeyValue = keyVal.split("-");
                    String key = settingKeyValue[0];
                    String value = settingKeyValue[1];
                    return Map.entry(key, value);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
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
