package com.nppgks.reports.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class TextTagData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;

    @Column(name = "date_creation")
    private LocalDateTime dtCreation;

    @ManyToOne
    @JoinColumn(name = "manual_tag_name_id")
    private TagName tagName;

    @ManyToOne
    @JoinColumn(name = "report_name_id")
    private ReportName reportName;
}
