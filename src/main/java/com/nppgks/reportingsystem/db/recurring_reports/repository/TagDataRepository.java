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
       select d.description as description,
       max(case when type = 'sikn' then value end) sikn,
       max(case when type = 'il1' then value end) il1,
       max(case when type = 'il2' then value end) il2,
       max(case when type = 'il3' then value end) il3,
       max(case when type = 'il4' then value end) il4,
       max(case when type = 'bik' then value end) bik
       from
        (
        select tn.name, row.order as "order", data as value, row.name as description, split_part(tn.name, '_', array_length(regexp_split_to_array(tn.name, '_'), 1)) as type
        from recurring_reports.tag_data td
                 INNER JOIN recurring_reports.tag_name tn ON tn.id = td.tag_name_id
                 INNER JOIN recurring_reports.report_row row ON row.id = tn.row_id
        where td.report_name_id = :reportNameId
    ) d
    group by d.description
    ORDER BY max(d.order);
    """,
    nativeQuery = true)
    List<IReportViewTagData> getTagDataView(Long reportNameId);
}
