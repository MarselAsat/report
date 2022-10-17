package com.nppgks.reports.db.repository;

import com.nppgks.reports.db.entity.ManualTagName;
import com.nppgks.reports.dto.TagNameForOpc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManualTagNameRepository extends JpaRepository<ManualTagName, Integer> {

    @Modifying
    @Query("update ManualTagName u " +
            "set u.name = :name, " +
            "u.description = :description where u.id = :id")
    int updateManualTagName(@Param("id") Integer id, @Param("name") String name, @Param("description") String description);

    List<TagNameForOpc> findAllByInitialAndType(Boolean initial, String type);
}
