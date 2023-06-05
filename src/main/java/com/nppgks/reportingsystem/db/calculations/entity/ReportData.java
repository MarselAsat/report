package com.nppgks.reportingsystem.db.calculations.entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity(name = "calc_report_data")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report_data", schema = "calculations")
public class ReportData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calc_report_data_id_seq")
    @SequenceGenerator(name = "calc_report_data_id_seq", sequenceName = "calc_report_data_id_seq", schema = "calculations")
    private Long id;

    @NotNull
    @Column(name = "data")
    private String data;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReportData reportData = (ReportData) o;
        return id != null && Objects.equals(id, reportData.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
