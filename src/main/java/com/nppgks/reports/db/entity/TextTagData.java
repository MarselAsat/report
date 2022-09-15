package com.nppgks.reports.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "text_tag_data")
public class TextTagData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    private String data;

    @Column(name = "creation_dt")
    private LocalDateTime creationDt;

    @ManyToOne
    @JoinColumn(name = "manual_tag_name_id")
    private ManualTagName tagName;

    @ManyToOne
    @JoinColumn(name = "report_name_id")
    private ReportName reportName;
}
