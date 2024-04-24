package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.db.common.entity.Settings;
import com.nppgks.reportingsystem.db.common.repository.SettingsRepository;
import com.nppgks.reportingsystem.reportgeneration.scheduled_reports.RescheduleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@Transactional
public class SettingsService {
    private final SettingsRepository settingsRepository;
    private final RescheduleService rescheduleService;


    public SettingsService(SettingsRepository settingsRepository, RescheduleService rescheduleService) {
        this.settingsRepository = settingsRepository;
        this.rescheduleService = rescheduleService;


    }
    public Integer getMinuteReportInterval() {
        Optional<Settings> settingsOptional = settingsRepository.findByName(SettingsConstants.MINUTE_REPORT_COLUMNS);
        if (settingsOptional.isPresent()) {
            Settings settings = settingsOptional.get();
            return Integer.parseInt(settings.getValue());
        } else {
            throw new RuntimeException("Настройка интервала минутного отчета не найдена в базе данных");
        }
    }

    // Метод для получения значения времени из базы данных
    public Integer getMinutesFromSettings () {
        // Ищем настройку по имени "time" в репозитории
        Optional<Settings> settingsOptional = settingsRepository.findByName("time");
        if (settingsOptional.isPresent()) {
            Settings settings = settingsOptional.get();
            // Возвращаем значение времени из базы данных
            return settings.getTime();
        } else {
            // Если настройка не найдена, выбрасываем исключение или возвращаем значение по умолчанию
            throw new RuntimeException("Настройка времени не найдена в базе данных");
        }
    }



    //    // Метод для получения значений настройки по имени
    public Proverka getValuesBySettingName(String name) {
        // Ищем настройку по имени в репозитории
        return settingsRepository.findByName(name)
                .map(settings -> {
                    // Получаем значение настройки
                    String value = settings.getValue();
                    // Создаем новый объект Test
                    Proverka test = new Proverka();
                    // Разбиваем значение на список строк по запятой и устанавливаем его в объект Test
                    test.setValues(Arrays.asList(value.split(",")));
                    // Устанавливаем значение check из настройки в объект Test
                    test.setCheck(settings.getCheck());
                    test.setStartTime(settings.getStartTime());
                    return test;
                })
                // Если настройка не найдена, выбрасываем исключение
                .orElseThrow(() -> new RuntimeException("В таблице настроек нет настройки с названием \"" + name + "\""));
    }

    // Метод для получения строкового значения настройки по имени
    public String getStringValueBySettingName(String name){
        return settingsRepository.findByName(name)
                .map(Settings::getValue)
                .orElseThrow(() -> new RuntimeException("В таблице настроек нет настройки с названием \""+name+"\""));

    }

    public LinkedHashMap<String, String> getMapValuesBySettingName(String name){
        Settings setting = settingsRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("В таблице настроек нет настройки с названием \""+name+"\""));
        return Arrays.stream(setting.getStartTime().split(","))
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
                        .orElseThrow(() -> new RuntimeException("В таблице настроек нет настройки с названием "+settingName));
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
