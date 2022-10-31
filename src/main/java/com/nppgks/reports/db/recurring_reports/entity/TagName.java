package com.nppgks.reports.db.recurring_reports.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tag_name", schema = "recurring_reports")
public class TagName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "\"order\"")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;
}
