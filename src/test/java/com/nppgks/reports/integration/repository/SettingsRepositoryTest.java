package com.nppgks.reports.integration.repository;

import com.nppgks.reports.db.entity.Settings;
import com.nppgks.reports.constants.SettingsConstants;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.RepositoryIT;
import com.nppgks.reports.db.repository.SettingsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@RepositoryIT
@Sql({"classpath:sql/settings.sql"})
class SettingsRepositoryTest extends IntegrationBaseTest {

    private final SettingsRepository settingsRepository;

    @Autowired
    SettingsRepositoryTest(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Test
    void findByName(){
        Settings settings = settingsRepository.findByName(SettingsConstants.HOUR_REPORT_COLUMNS);
        Assertions.assertEquals(settings.getValue(), "sikn,il1,il2,il3,il4,bik");
    }

    @Test
    void update(){
        settingsRepository.update("", "bik");
        System.out.println();
    }

}