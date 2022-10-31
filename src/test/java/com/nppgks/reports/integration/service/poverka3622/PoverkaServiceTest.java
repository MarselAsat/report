package com.nppgks.reports.integration.service.poverka3622;

import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.ServiceIT;
import com.nppgks.reports.service.poverka3622.PoverkaService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@ServiceIT
@ExtendWith(MockitoExtension.class)
@Sql({"classpath:sql/poverka/poverka3622.sql"})
class PoverkaServiceTest extends IntegrationBaseTest {

    private final PoverkaService poverkaService;

    @Autowired
    PoverkaServiceTest(PoverkaService poverkaService) {
        this.poverkaService = poverkaService;
    }

    @Test
    //@Disabled
    void doPoverka3622() {
        poverkaService.doPoverka3622();
    }
}