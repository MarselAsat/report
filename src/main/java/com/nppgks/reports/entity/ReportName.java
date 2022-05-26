package com.nppgks.reports.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "report_name")
@Data
public class ReportName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_name")
    private String name;

    @Column(name = "date_creation")
    private LocalDateTime dtCreation;

    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;
}
