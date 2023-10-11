package com.nppgks.reportingsystem.db.manual_reports.repository;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reportTypeManualRepository")
public interface ReportTypeRepository extends JpaRepository<ReportType, String> {

    List<ReportType> findAllByActive(Boolean active);

}
