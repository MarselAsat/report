package com.nppgks.reports.db.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "\"group\"")
    private String group;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;
}
