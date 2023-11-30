package com.nppgks.reportingsystem.db.scheduled_reports.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report_row", schema = "scheduled_reports")
public class ReportRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "\"order\"")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReportRow reportRow = (ReportRow) o;
        return id != null && Objects.equals(id, reportRow.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
