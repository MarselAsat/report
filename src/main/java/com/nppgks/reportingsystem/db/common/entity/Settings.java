package com.nppgks.reportingsystem.db.common.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "settings", schema = "public")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "\"group\"", length = 32)
    private String group;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;
}
