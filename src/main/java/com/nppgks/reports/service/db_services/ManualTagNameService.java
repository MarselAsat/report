package com.nppgks.reports.service.db_services;

import com.nppgks.reports.dto.ManualTagNameDto;
import com.nppgks.reports.db.poverka.repository.TagNameRepository;
import com.nppgks.reports.dto.TagNameForOpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ManualTagNameService{

    private final TagNameRepository tagNameRepository;

    @Autowired
    public ManualTagNameService(TagNameRepository repository){
        this.tagNameRepository = repository;
    }

    public List<TagNameForOpc> getTagNamesByInitialAndType(Boolean initial, String type){
        return tagNameRepository.findAllByInitialAndType(initial, type);
    }

    public Integer saveTagName(ManualTagNameDto tagNameDto) {
        try{
            return tagNameRepository.save(ManualTagNameDto.toManualTagName(tagNameDto)).getId();
        }
        catch(Exception e){
            return null;
        }
    }

    public List<ManualTagNameDto> getAllTagNames() {
        return tagNameRepository.findAll()
                .stream()
                .map(ManualTagNameDto::fromManualTagName)
                .toList();
    }

    public Map<Integer, Boolean> updateTagNames(List<ManualTagNameDto> tagNames) {
        return tagNames.stream()
                .map(tagName -> {
                    Boolean resp = updateTagName(tagName)!=null;
                    return Map.entry(tagName.getId(), resp);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Integer updateTagName(ManualTagNameDto tagNameDto) {
        try{
            tagNameRepository.updateManualTagName(
                    tagNameDto.getId(),
                    tagNameDto.getName(),
                    tagNameDto.getDescription());
            return tagNameDto.getId();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteTagName(Integer id) {
        try{
            tagNameRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
