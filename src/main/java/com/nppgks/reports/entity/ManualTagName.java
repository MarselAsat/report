package com.nppgks.reports.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ManualTagName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String permanentName;

    private String name;

    private String description;

    private boolean initial;

    private String type;
}
