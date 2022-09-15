package com.nppgks.reports.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "report_type")
@Data
public class ReportType {

    @Id
    private String id;

    private String name;

    private String description;

    private Integer timeZone;

    private Boolean active;

}
