package com.nppgks.reports.db.calculations.repository;

import com.nppgks.reports.db.calculations.entity.TagData;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tagDataCalcRepository")
public interface TagDataRepository extends JpaRepository<TagData, Long> {

    @EntityGraph(attributePaths = {"tagName"})
    List<TagData> findByReportNameId(Long id);

}
