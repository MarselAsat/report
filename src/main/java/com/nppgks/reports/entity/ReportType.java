package com.nppgks.reports.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "report_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Integer timeZone;

    private int active;

}
