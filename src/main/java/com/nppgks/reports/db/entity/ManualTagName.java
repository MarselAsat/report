package com.nppgks.reports.db.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "manual_tag_name")
@Data
public class ManualTagName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "permanent_name")
    private String permanentName;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "initial")
    private Boolean initial;

    @Column(name = "type")
    private String type;
}
