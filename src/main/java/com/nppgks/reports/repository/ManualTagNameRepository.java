package com.nppgks.reports.repository;

import com.nppgks.reports.dto.TagNameForOpc;
import com.nppgks.reports.entity.ManualTagName;
import com.nppgks.reports.entity.TagName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManualTagNameRepository extends JpaRepository<ManualTagName, Integer> {

    List<TagNameForOpc> findAllByInitialAndType(Boolean initial, String type);

}
