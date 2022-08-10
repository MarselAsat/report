package com.nppgks.reports.repository;

import com.nppgks.reports.entity.ManualTagName;
import com.nppgks.reports.entity.TagName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManualTagNameRepository extends JpaRepository<ManualTagName, Integer> {

}
