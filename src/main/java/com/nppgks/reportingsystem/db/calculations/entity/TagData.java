package com.nppgks.reportingsystem.db.calculations.entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity(name = "calc_tag_data")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tag_data", schema = "calculations")
public class TagData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calc.tag_data_id_seq")
    @SequenceGenerator(name="calc.tag_data_id_seq", sequenceName = "calculations.tag_data_id_seq")
    private Long id;

    @NotNull
    @Column(name = "data")
    private String data;

    @NotNull
    @Pattern(regexp = "array2D|array|singleValue")
    @Column(name = "data_type")
    private String dataType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tag_name_id")
    private TagName tagName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "report_name_id")
    private ReportName reportName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TagData tagData = (TagData) o;
        return id != null && Objects.equals(id, tagData.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}