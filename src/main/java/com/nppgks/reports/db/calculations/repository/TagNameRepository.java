package com.nppgks.reports.db.calculations.repository;

import com.nppgks.reports.db.calculations.entity.TagName;
import com.nppgks.reports.dto.calc.CalcTagNameForOpc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TagNameCalcRepository")
public interface TagNameRepository extends JpaRepository<TagName, Integer> {

    @Modifying
    @Query("update calc_tag_name tn " +
            "set tn.name = :name, " +
            "tn.description = :description where tn.id = :id")
    int updateTagName(@Param("id") Integer id, @Param("name") String name, @Param("description") String description);

    List<CalcTagNameForOpc> findAllByInitialAndType(Boolean initial, String type);
}
