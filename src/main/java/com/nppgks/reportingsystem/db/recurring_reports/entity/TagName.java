package com.nppgks.reportingsystem.db.recurring_reports.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tag_name", schema = "recurring_reports")
@AllArgsConstructor
@NoArgsConstructor
public class TagName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;

    @ManyToOne
    @JoinColumn(name = "row_id")
    private ReportRow reportRow;
}
