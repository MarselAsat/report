package com.nppgks.reportingsystem.integration.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.ServiceIT;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.MI3622Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@ServiceIT
@ExtendWith(MockitoExtension.class)
@Sql({"classpath:sql/calculation/calc-MI3622.sql"})
class MI3622ServiceTest extends IntegrationBaseTest {

    private final MI3622Service MI3622Service;

    @Autowired
    MI3622ServiceTest(MI3622Service MI3622Service) {
        this.MI3622Service = MI3622Service;
    }

    @Test
    //@Disabled
    void doCalc3622() {
        MI3622Service.doCalc3622();
    }
}