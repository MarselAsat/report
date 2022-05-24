package com.nppgks.reports.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tag_data")
@Data
public class TagData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double data;

    @Column(name = "date_creation")
    private LocalDateTime dtCreation;

    @ManyToOne
    @JoinColumn(name = "name_id")
    private TagName tagName;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private ReportType reportType;
}
