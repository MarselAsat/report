package com.nppgks.reportingsystem.db.operative_reports.repository;

import com.nppgks.reportingsystem.dto.IReportViewReportData;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportDataRepository extends JpaRepository<ReportData, Long> {

    List<ReportData> findByReportId(Long reportId);

    @Query(value = """
             select row.order as order, row.name as description, rd.data as value, t.meteringNode.id as nodeName
                    from ReportData rd
                             INNER JOIN Tag t ON t.id = rd.tag.id
                             INNER JOIN ReportRow row ON row.id = t.reportRow.id
                    where rd.report.id = :reportId
            """)
    List<IReportViewReportData> getReportDataView(Long reportId);
}
