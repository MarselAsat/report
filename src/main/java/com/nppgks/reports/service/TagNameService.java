package com.nppgks.reports.service;

import com.nppgks.reports.dto.TagNameDto;

import java.util.List;
import java.util.Map;

public interface TagNameService {
    public boolean saveTagName(TagNameDto tagname);
    public List<TagNameDto> getAllTagNames();

    Map<Long, Boolean> saveTagNames(List<TagNameDto> tagNames);
}
