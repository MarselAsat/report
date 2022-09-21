package com.nppgks.reports.integration.scheduled_components;

import com.nppgks.reports.service.db_services.SettingsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.PostConstruct;

import static org.mockito.Mockito.doReturn;

@TestConfiguration
public class ScheduledTestConfig {

    @MockBean
    private SettingsService settingsService;

    @PostConstruct
    public void initMock(){
        doReturn("10:00").when(settingsService)
                .getStringValueBySettingName("start daily report");
    }
}
