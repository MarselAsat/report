package com.nppgks.reportingsystem.integration.reportgeneration;

import com.nppgks.reportingsystem.reportgeneration.recurring.ReportsScheduler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class ServiceTestConfig {
    /*
   ScheduledReports is excluded from application context
   because @Sql is called after initializing the application context,
   but ScheduledReport needs some data from db in @PostConstruct.
   It leads to the exception
   // TODO: 20.09.2022 come up with something
    */
    @MockBean
    ReportsScheduler reportsScheduler;
}
