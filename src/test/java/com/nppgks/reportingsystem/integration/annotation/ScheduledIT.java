package com.nppgks.reportingsystem.integration.annotation;

import com.nppgks.reportingsystem.integration.scheduled_components.ScheduledTestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ActiveProfiles("scheduling")
@SpringBootTest(classes = ScheduledTestConfig.class)
@Transactional
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScheduledIT {
}
