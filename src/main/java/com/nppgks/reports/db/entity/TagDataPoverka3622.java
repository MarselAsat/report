package com.nppgks.reports.db.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tag_data_poverka3622")
public class TagDataPoverka3622 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_data_poverka3622")
    @SequenceGenerator(name="tag_data_poverka3622", sequenceName = "tag_data_poverka3622_seq")
    private Long id;

    @NotNull
    @Column(name = "data")
    private String data;

    @NotNull
    @Pattern(regexp = "double2DArray|doubleArray|doubleValue")
    @Column(name = "data_type")
    private String dataType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "manual_tag_name_id")
    private ManualTagName manualTagName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "report_name_poverka_id")
    private ReportNamePoverka reportNamePoverka;
}
