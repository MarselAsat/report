package com.nppgks.reports.service.db_services;

import com.nppgks.reports.constants.ReportTypesEnum;
import com.nppgks.reports.constants.SettingsConstants;
import com.nppgks.reports.db.entity.Settings;
import com.nppgks.reports.db.repository.SettingsRepository;
import com.nppgks.reports.scheduled_components.RescheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SettingsService {

    private final SettingsRepository settingsRepository;
    private final RescheduleService rescheduleService;

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
            List<String> changedScheduledReports = new ArrayList<>();
            for(Map.Entry<String, String> settingEntry : settings.entrySet()){
                String settingName = settingEntry.getKey();
                String settingNewValue = settingEntry.getValue();
                Settings setting = settingsRepository.findByName(settingName)
                        .orElseThrow(() -> new RuntimeException("No setting with name "+settingName));
                String settingDbValue = setting.getValue();
                if(!settingNewValue.equals(settingDbValue)){
                    if(SettingsConstants.START_DAILY_REPORT.equals(settingName)){
                        changedScheduledReports.add(ReportTypesEnum.daily.name());
                    }
                    else if(SettingsConstants.START_SHIFT_REPORT.equals(settingName)){
                        changedScheduledReports.add(ReportTypesEnum.shift.name());
                    }
                    else if(SettingsConstants.START_MONTH_REPORT.equals(settingName)){
                        changedScheduledReports.add(ReportTypesEnum.month.name());
                    }
                    else if(SettingsConstants.START_YEAR_REPORT.equals(settingName)){
                        changedScheduledReports.add(ReportTypesEnum.year.name());
                    }
                    setting.setValue(settingNewValue);
                    settingsRepository.save(setting);
                }
            }
            rescheduleService.rescheduleReports(changedScheduledReports);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
