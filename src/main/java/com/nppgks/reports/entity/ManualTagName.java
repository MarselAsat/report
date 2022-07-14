package com.nppgks.reports.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ManualTagName {

    @Id
    private String permanentName;

    private String name;

    private String description;
}
