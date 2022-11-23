package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportName;
import com.nppgks.reportingsystem.db.recurring_reports.entity.TagData;
import com.nppgks.reportingsystem.dto.ReportViewTagData;
import com.nppgks.reportingsystem.dto.TagDataDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TagDataService {

    List<TagDataDto> getDataForReport(Long reportNameId);

    List<TagData> findAll();

    TagData saveTagData(TagData tagData);

    List<TagData> saveTagDataMapByReportName(Map<String, String> tagDataMap, ReportName reportName, LocalDateTime date);

    List<ReportViewTagData> getReportViewTagData(Long reportNameId);
}
