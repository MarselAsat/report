package com.nppgks.reportingsystem.db.recurring_reports.repository;

import com.nppgks.reportingsystem.dto.IReportViewTagData;
import com.nppgks.reportingsystem.db.recurring_reports.entity.TagData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDataRepository extends JpaRepository<TagData, Long> {

    List<TagData> findByReportNameId(Long reportNameId);

    @Query(value = """
 select row.order as order, row.name as description, td.data as value, tn.meteringNode.id as nodeName
        from TagData td
                 INNER JOIN TagName tn ON tn.id = td.tagName.id
                 INNER JOIN ReportRow row ON row.id = tn.reportRow.id
        where td.reportName.id = :reportNameId
""")
    List<IReportViewTagData> getTagDataViewTest(Long reportNameId);
}
