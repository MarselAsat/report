package com.nppgks.reportingsystem.integration.repository.common;

import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.RepositoryIT;
import com.nppgks.reportingsystem.db.common.repository.SettingsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@RepositoryIT
@Sql({"classpath:sql/common/settings.sql"})
class SettingsRepositoryTest extends IntegrationBaseTest {

    private final SettingsRepository settingsRepository;

    @Autowired
    SettingsRepositoryTest(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Test
    void findByName(){
        settingsRepository.findByName(SettingsConstants.HOUR_REPORT_COLUMNS).ifPresent(
                (setting) -> Assertions.assertEquals(setting.getValue(), "sikn,il1,il2,il3,il4,bik")
        );
    }

    @Test
    void update(){
        settingsRepository.update("", "bik");
        System.out.println();
    }

}