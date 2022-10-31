package com.nppgks.reports.db.poverka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "poverka.tag_name")
@Table(name = "tag_name", schema = "poverka")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagName {

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
