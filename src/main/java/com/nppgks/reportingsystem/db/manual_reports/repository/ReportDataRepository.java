package com.nppgks.reportingsystem.db.manual_reports.repository;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reportDataManualRepository")
public interface ReportDataRepository extends JpaRepository<ReportData, Long> {

    @EntityGraph(attributePaths = {"tag"})
    List<ReportData> findByReportId(Long id);

    void deleteByReportId(Long id);

}
