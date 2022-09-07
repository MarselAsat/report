package com.nppgks.reports.service;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.entity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TagDataService {

    List<TagDataDto> getDataForReport(Long reportNameId);

    List<TagData> findAll();

    TagData saveTagData(TagData tagData);

    void saveTagDataMapByReportName(Map<String, String> tagDataMap, ReportName reportName, LocalDateTime date);

    List<ReportViewTagData> getReportViewTagData(Long reportNameId);
}
