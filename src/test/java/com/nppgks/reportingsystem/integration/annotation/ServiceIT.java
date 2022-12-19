package com.nppgks.reportingsystem.integration.annotation;

import com.nppgks.reportingsystem.integration.service.ServiceTestConfig;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SpringBootTest(classes = ServiceTestConfig.class)
@Transactional
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceIT {
}
