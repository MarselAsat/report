package com.nppgks.reports.db.calculations.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "calc_tag_name")
@Table(name = "tag_name", schema = "calculations")
@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TagName tagName = (TagName) o;
        return id != null && Objects.equals(id, tagName.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
