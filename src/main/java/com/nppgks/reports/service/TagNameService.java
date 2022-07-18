package com.nppgks.reports.service;

import com.nppgks.reports.dto.TagNameDto;

import java.util.List;
import java.util.Map;

public interface TagNameService<E, K> {
    public K saveTagName(E object);
    public List<E> getAllTagNames();

    Map<K, Boolean> saveTagNames(List<E> tagNames);
    boolean deleteTagName(K id);
}
