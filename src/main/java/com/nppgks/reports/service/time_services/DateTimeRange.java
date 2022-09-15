package com.nppgks.reports.service.time_services;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DateTimeRange {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
