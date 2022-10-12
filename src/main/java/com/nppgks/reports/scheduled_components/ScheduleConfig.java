package com.nppgks.reports.scheduled_components;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
@Profile("scheduling")
public class ScheduleConfig {
}
