package com.nppgks.reportingsystem.integration.reportgeneration.calculations.mi3622;

import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.ServiceIT;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622.MI3622ReportGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@ServiceIT
@ExtendWith(MockitoExtension.class)
@Sql({"classpath:sql/manual_reports/MI3622.sql"})
class MI3622ReportGeneratorTest extends IntegrationBaseTest {

    private final MI3622ReportGenerator MI3622ReportGenerator;

    @Autowired
    MI3622ReportGeneratorTest(MI3622ReportGenerator MI3622ReportGenerator) {
        this.MI3622ReportGenerator = MI3622ReportGenerator;
    }

    @Test
    //@Disabled
    void calcualteMI3622() {
        MI3622ReportGenerator.generateReport();
    }
}