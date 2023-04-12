package com.nppgks.reportingsystem.util.time;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DateTimeRange {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
