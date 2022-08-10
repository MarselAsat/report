package com.nppgks.reports.service;

import com.nppgks.reports.dto.ManualTagNameDto;
import com.nppgks.reports.repository.ManualTagNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ManualTagNameService implements TagNameService<ManualTagNameDto, Integer>{

    private ManualTagNameRepository manualTagNameRepository;

    @Autowired
    public ManualTagNameService(ManualTagNameRepository repository){
        this.manualTagNameRepository = repository;
    }

    @Override
    public Integer saveTagName(ManualTagNameDto tagNameDto) {
        try{
            return manualTagNameRepository.save(ManualTagNameDto.toManualTagName(tagNameDto)).getId();
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public List<ManualTagNameDto> getAllTagNames() {
        return manualTagNameRepository.findAll()
                .stream()
                .map(tagName -> ManualTagNameDto.fromManualTagName(tagName))
                .toList();
    }

    @Override
    public Map<Integer, Boolean> saveTagNames(List<ManualTagNameDto> tagNames) {
        Map<Integer, Boolean> responses = tagNames.stream()
                .map(tagName -> {
                    Boolean resp = saveTagName(tagName)!=null;
                    return Map.entry(tagName.getId(), resp);
                })
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        return responses;
    }

    @Override
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
