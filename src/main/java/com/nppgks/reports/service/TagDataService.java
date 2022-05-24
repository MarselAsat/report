package com.nppgks.reports.service;

import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TagDataService {

    public Map<TagName, Double> getDataForReport(Long reportId, LocalDateTime start, LocalDateTime end);

}
