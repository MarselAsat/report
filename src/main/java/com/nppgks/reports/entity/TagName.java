package com.nppgks.reports.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "tag_name")
@Data
public class TagName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(name = "\"order\"")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;
}
