package com.nppgks.reports.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "\"group\"")
    private String group;
    private String name;
    private String value;
}
