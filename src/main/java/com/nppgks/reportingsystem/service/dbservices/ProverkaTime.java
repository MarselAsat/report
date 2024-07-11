package com.nppgks.reportingsystem.service.dbservices;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class ProverkaTime {
    private List<String> values;
    private String check;
    private String startTime;
    private String time;

}