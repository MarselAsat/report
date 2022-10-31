package com.nppgks.reports.db.poverka.repository;

import com.nppgks.reports.db.poverka.entity.TagData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("tagDataPoverkaRepository")
public interface TagDataRepository extends JpaRepository<TagData, Long> {

}
