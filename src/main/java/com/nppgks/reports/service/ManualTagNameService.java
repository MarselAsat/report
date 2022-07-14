package com.nppgks.reports.service;

import com.nppgks.reports.dto.ManualTagNameDto;
import com.nppgks.reports.dto.TagNameDto;

import java.util.List;
import java.util.Map;

public interface ManualTagNameService{
    public boolean saveTagName(ManualTagNameDto tagname);
    public List<ManualTagNameDto> getAllManualTagNames();

    Map<String, Boolean> saveTagNames(List<ManualTagNameDto> tagNames);
    boolean deleteTagName(String permanentName);
}
