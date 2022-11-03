package com.nppgks.reports.integration.service.calc_method_3622;

import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.ServiceIT;
import com.nppgks.reports.service.calc3622.CalcService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@ServiceIT
@ExtendWith(MockitoExtension.class)
@Sql({"classpath:sql/calculation/calc-method-3622.sql"})
class CalcServiceTest extends IntegrationBaseTest {

    private final CalcService calcService;

    @Autowired
    CalcServiceTest(CalcService calcService) {
        this.calcService = calcService;
    }

    @Test
    //@Disabled
    void doCalc3622() {
        calcService.doCalc3622();
    }
}