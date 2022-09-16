package com.nppgks.reports.db.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "report_type")
public class ReportType {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "time_zone")
    private Integer timeZone;

    @Column(name = "active")
    private Boolean active;

}
