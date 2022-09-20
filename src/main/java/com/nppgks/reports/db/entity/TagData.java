package com.nppgks.reports.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tag_data")
public class TagData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    private double data;

    @Column(name = "creation_dt")
    private LocalDateTime creationDt;

    @ManyToOne
    @JoinColumn(name = "tag_name_id")
    private TagName tagName;

    @ManyToOne
    @JoinColumn(name = "report_name_id")
    private ReportName reportName;
}