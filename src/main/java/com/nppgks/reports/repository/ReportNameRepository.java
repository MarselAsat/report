package com.nppgks.reports.repository;

import com.nppgks.reports.entity.ReportName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportNameRepository extends JpaRepository<ReportName, Long> {

    List<ReportName> findByReportTypeIdAndDtCreationBetween(Integer reportTypeId, LocalDateTime dtCreationStart, LocalDateTime dtCreationEnd);

    List<ReportName> findByReportTypeId(Integer reportTypeId);

    List<ReportName> findByDtCreationBetween(LocalDateTime dateStart, LocalDateTime dateEnd);

}