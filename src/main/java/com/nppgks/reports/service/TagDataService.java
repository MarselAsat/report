package com.nppgks.reports.service;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TagDataService {

    public List<TagDataDto> getDataForReport(Long reportNameId);

    public List<TagData> findAll();
}
