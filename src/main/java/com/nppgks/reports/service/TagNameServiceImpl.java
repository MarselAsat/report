package com.nppgks.reports.service;

import com.nppgks.reports.dto.TagNameDto;
import com.nppgks.reports.dto.TagNameMapper;
import com.nppgks.reports.entity.TagName;
import com.nppgks.reports.repository.TagNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagNameServiceImpl implements TagNameService<TagNameDto, Long>{

    private TagNameRepository repository;

    private ReportTypeService reportTypeService;

    @Autowired
    public TagNameServiceImpl(TagNameRepository tagNameRepository, ReportTypeService reportTypeService){
        this.repository = tagNameRepository;
        this.reportTypeService = reportTypeService;
    }

    @Override
    public boolean saveTagName(TagNameDto tagNameDto) {
        TagName tagName = new TagNameMapper(reportTypeService).toTagName(tagNameDto);
        try{
            return repository.save(tagName).getId() != null;
        }
        catch(Exception e){
            return false;
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
        Map<Long, Boolean> responses = tagNames.stream()
                .map(tagName -> {
                    Boolean resp = saveTagName(tagName);
                    return Map.entry(tagName.getId(), resp);
                })
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        return responses;
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
