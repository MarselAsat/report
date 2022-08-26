package com.nppgks.reports.service;

import com.nppgks.reports.dto.ManualTagNameDto;
import com.nppgks.reports.repository.ManualTagNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ManualTagNameService{

    private ManualTagNameRepository manualTagNameRepository;

    @Autowired
    public ManualTagNameService(ManualTagNameRepository repository){
        this.manualTagNameRepository = repository;
    }

    public Integer saveTagName(ManualTagNameDto tagNameDto) {
        try{
            return manualTagNameRepository.save(ManualTagNameDto.toManualTagName(tagNameDto)).getId();
        }
        catch(Exception e){
            return null;
        }
    }

    public List<ManualTagNameDto> getAllTagNames() {
        return manualTagNameRepository.findAll()
                .stream()
                .map(tagName -> ManualTagNameDto.fromManualTagName(tagName))
                .toList();
    }

    public Map<Integer, Boolean> updateTagNames(List<ManualTagNameDto> tagNames) {
        Map<Integer, Boolean> responses = tagNames.stream()
                .map(tagName -> {
                    Boolean resp = updateTagName(tagName)!=null;
                    return Map.entry(tagName.getId(), resp);
                })
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        return responses;
    }

    public Integer updateTagName(ManualTagNameDto tagNameDto) {
        try{
            manualTagNameRepository.updateManualTagName(
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
            manualTagNameRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
