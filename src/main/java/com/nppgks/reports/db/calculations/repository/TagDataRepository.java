package com.nppgks.reports.db.calculations.repository;

import com.nppgks.reports.db.calculations.entity.TagData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("tagDataCalcRepository")
public interface TagDataRepository extends JpaRepository<TagData, Long> {

}
