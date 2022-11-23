package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.recurring_reports.entity.TagName;
import com.nppgks.reportingsystem.dto.TagNameDto;
import com.nppgks.reportingsystem.dto.TagNameMapper;
import com.nppgks.reportingsystem.db.recurring_reports.repository.TagNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagNameService {

    private final TagNameRepository repository;

    private final ReportTypeService reportTypeService;

    @Autowired
    public TagNameService(TagNameRepository tagNameRepository, ReportTypeService reportTypeService){
        this.repository = tagNameRepository;
        this.reportTypeService = reportTypeService;
    }

    public Long saveTagName(TagNameDto tagNameDto) {
        TagName tagName = new TagNameMapper(reportTypeService).toTagName(tagNameDto);
        try{
            return repository.save(tagName).getId();
        }
        catch(Exception e){
            e.printStackTrace();
            return -1L;
        }
    }

    public List<TagNameDto> getAllTagNamesDto() {
        return repository.findBy();
    }

    public List<TagNameDto> getAllTagNamesDtoByReportType(String id){
        return repository.findAllByReportTypeId(id);
    }
    public List<TagName> getAllTagNames() {
        return repository.findAll();
    }

    public Map<Long, Boolean> saveTagNames(List<TagNameDto> tagNames) {
        return tagNames.stream()
                .map(tagName -> {
                    Boolean resp = saveTagName(tagName)!=-1L;
                    return Map.entry(tagName.getId(), resp);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public List<TagName> getAllTagNamesByReportType(ReportType reportType){
        return repository.findAllByReportType(reportType);
    }

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
