package com.nppgks.reports.service.db_services;

import java.util.List;
import java.util.Map;

public interface TagNameService<E, K> {
    K saveTagName(E object);
    List<E> getAllTagNames();

    Map<K, Boolean> saveTagNames(List<E> tagNames);
    boolean deleteTagName(K id);
}
