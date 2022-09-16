package com.nppgks.reports.service.db_services;

import com.nppgks.reports.db.entity.ReportName;
import com.nppgks.reports.db.entity.TagData;
import com.nppgks.reports.dto.ReportViewTagData;
import com.nppgks.reports.dto.TagDataDto;

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
