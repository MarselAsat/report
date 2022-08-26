package com.nppgks.reports.repository;

import com.nppgks.reports.entity.ManualTagName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManualTagNameRepository extends JpaRepository<ManualTagName, Integer> {

    @Modifying
    @Query("update ManualTagName u " +
            "set u.name = :name, " +
            "u.description = :description where u.id = :id")
    int updateManualTagName(@Param("id") Integer id, @Param("name") String name, @Param("description") String description);
}
