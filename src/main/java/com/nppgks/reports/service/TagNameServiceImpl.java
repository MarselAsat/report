package com.nppgks.reports.service;

import com.nppgks.reports.dto.TagNameDto;
import com.nppgks.reports.dto.TagNameMapper;
import com.nppgks.reports.entity.TagName;
import com.nppgks.reports.repository.TagNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagNameServiceImpl implements TagNameService{

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
}
