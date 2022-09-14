package com.nppgks.reports.integration.service;

import com.nppgks.reports.entity.SettingsConstants;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.ServiceIT;
import com.nppgks.reports.service.SettingsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceIT
@Sql({"classpath:sql/settings.sql"})
class SettingsServiceTest extends IntegrationBaseTest {

    private final SettingsService settingsService;

    @Autowired
    SettingsServiceTest(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Test
    void getListValuesBySettingName() {
        List<String> hourColumns = settingsService.getListValuesBySettingName(SettingsConstants.HOUR_REPORT_COLUMNS);
        assertThat(hourColumns).hasSize(6);

        List<String> dailyColumns = settingsService.getListValuesBySettingName(SettingsConstants.DAILY_REPORT_COLUMNS);
        assertThat(dailyColumns).hasSize(4);
    }
}