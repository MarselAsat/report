package com.nppgks.reportingsystem.db.recurring_reports.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "row_in_report", schema = "recurring_reports")
public class RowInReport {
    @Id
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "\"order\"")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;
}
