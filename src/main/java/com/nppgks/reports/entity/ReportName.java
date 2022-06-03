package com.nppgks.reports.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "report_name")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_creation")
    private LocalDateTime dtCreation;

    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;
}
