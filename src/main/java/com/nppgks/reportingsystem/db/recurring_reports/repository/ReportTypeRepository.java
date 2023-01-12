package com.nppgks.reportingsystem.db.recurring_reports.repository;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, String> {

    Optional<ReportType> findByName(String name);

}
