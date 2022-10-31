package com.nppgks.reports.db.poverka.repository;

import com.nppgks.reports.db.poverka.entity.TagName;
import com.nppgks.reports.dto.TagNameForOpc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TagNamePoverkaRepository")
public interface TagNameRepository extends JpaRepository<TagName, Integer> {

    @Modifying
    @Query("update poverka_tag_name tn " +
            "set tn.name = :name, " +
            "tn.description = :description where tn.id = :id")
    int updateManualTagName(@Param("id") Integer id, @Param("name") String name, @Param("description") String description);

    List<TagNameForOpc> findAllByInitialAndType(Boolean initial, String type);
}
