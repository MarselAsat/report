package com.nppgks.reports.repository;

import com.nppgks.reports.entity.TagName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagNameRepository extends JpaRepository<TagName, Long> {

    TagName findByName(String name);
}
