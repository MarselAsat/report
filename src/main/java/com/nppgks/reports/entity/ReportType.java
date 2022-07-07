package com.nppgks.reports.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "report_type")
@Data
public class ReportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Integer timeZone;

    private int active;

}
