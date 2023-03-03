package com.nppgks.reportingsystem.db.recurring_reports.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "metering_node", schema = "recurring_reports")
public class MeteringNode {

    @Id
    private String id;

    private String name;
}
