package com.nppgks.reports.db.recurring_reports.repository;

import com.nppgks.reports.dto.IReportViewTagData;
import com.nppgks.reports.db.recurring_reports.entity.TagData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDataRepository extends JpaRepository<TagData, Long> {

    List<TagData> findByReportName_Id(Long reportNameId);

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
        select tn.name, tn.order as "order", data as value, tn.description as description, split_part(tn.name, '_', array_length(regexp_split_to_array(tn.name, '_'), 1)) as type
        from tag_data
                 INNER JOIN tag_name tn ON tn.id = tag_data.tag_name_id
        where tag_data.report_name_id = :reportNameId
    ) d
    group by d.description
    ORDER BY max(d.order);
    """,
    nativeQuery = true)
    List<IReportViewTagData> getTagDataView(Long reportNameId);
}
