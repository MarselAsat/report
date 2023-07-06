package com.nppgks.reportingsystem.integration.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.ServiceIT;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.MI3622Generator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@ServiceIT
@ExtendWith(MockitoExtension.class)
@Sql({"classpath:sql/manual_reports/MI3622.sql"})
class MI3622GeneratorTest extends IntegrationBaseTest {

    private final MI3622Generator MI3622Generator;

    @Autowired
    MI3622GeneratorTest(MI3622Generator MI3622Generator) {
        this.MI3622Generator = MI3622Generator;
    }

    @Test
    //@Disabled
    void calcualteMI3622() {
        MI3622Generator.generateMI3622Report();
    }
}