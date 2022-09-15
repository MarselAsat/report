package com.nppgks.reports.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
