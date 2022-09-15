package com.nppgks.reports.service.db_services;

import com.nppgks.reports.dto.TagNameDto;
import com.nppgks.reports.dto.TagNameMapper;
import com.nppgks.reports.db.entity.TagName;
import com.nppgks.reports.db.repository.TagNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagNameServiceImpl implements TagNameService<TagNameDto, Long>{

    private final TagNameRepository repository;

    private final ReportTypeService reportTypeService;

    @Autowired
    public TagNameServiceImpl(TagNameRepository tagNameRepository, ReportTypeService reportTypeService){
        this.repository = tagNameRepository;
        this.reportTypeService = reportTypeService;
    }

    @Override
    public Long saveTagName(TagNameDto tagNameDto) {
        TagName tagName = new TagNameMapper(reportTypeService).toTagName(tagNameDto);
        try{
            return  repository.save(tagName).getId();
        }
        catch(Exception e){
            e.printStackTrace();
            return -1L;
        }
    }

    @Override
    public List<TagNameDto> getAllTagNames() {
        return repository.findAll().stream()
                .map(new TagNameMapper(reportTypeService)::toTagNameDto)
                .toList();
    }

    @Override
    public Map<Long, Boolean> saveTagNames(List<TagNameDto> tagNames) {
        return tagNames.stream()
                .map(tagName -> {
                    Boolean resp = saveTagName(tagName)!=-1L;
                    return Map.entry(tagName.getId(), resp);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public boolean deleteTagName(Long id) {
        try{
            repository.deleteById(id);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
