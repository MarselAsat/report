package com.nppgks.reports.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
public class ManualTagName {

    @Id
    private String permanentName;

    private String name;

    private String description;

    private Boolean initial;

    private String type;
}
