package com.nppgks.reportingsystem.service.timeservices;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DateTimeRange {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
