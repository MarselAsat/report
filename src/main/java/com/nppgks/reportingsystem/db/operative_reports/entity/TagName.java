package com.nppgks.reportingsystem.db.operative_reports.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tag_name", schema = "operative_reports")
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
    @JoinColumn(name = "metering_node_id")
    private MeteringNode meteringNode;

    @ManyToOne
    @JoinColumn(name = "row_id")
    private ReportRow reportRow;


}
