package com.nppgks.reports.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "report_name")
@Data
public class ReportName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "report_name")
    private String name;
}
