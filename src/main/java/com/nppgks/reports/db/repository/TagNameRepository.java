package com.nppgks.reports.db.repository;

import com.nppgks.reports.db.entity.TagName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagNameRepository extends JpaRepository<TagName, Long> {

    TagName findByName(String name);
}
