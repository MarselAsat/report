package com.nppgks.reports.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "tag_name")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToOne
    private ReportType reportType;
}
