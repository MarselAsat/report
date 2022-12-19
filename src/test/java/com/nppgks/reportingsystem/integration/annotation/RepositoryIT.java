package com.nppgks.reportingsystem.integration.annotation;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@DataJpaTest // provides an embedded database
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // removes that embedded database
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepositoryIT {
}
