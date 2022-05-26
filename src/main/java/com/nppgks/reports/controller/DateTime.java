package com.nppgks.reports.controller;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component("DateTime")
public class DateTime {
    private String date;
    private String time;
}
