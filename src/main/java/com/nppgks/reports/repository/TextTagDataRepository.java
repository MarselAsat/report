package com.nppgks.reports.repository;

import com.nppgks.reports.entity.TextTagData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextTagDataRepository extends JpaRepository<TextTagData, Long> {
}
