package com.nppgks.reportingsystem.service.dbservices;

import liquibase.pro.packaged.S;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Proverka {
    private List<String> values;
    private String check;
    private String startTime;
    private Integer time;

}