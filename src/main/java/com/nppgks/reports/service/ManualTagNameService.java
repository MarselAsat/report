package com.nppgks.reports.service;

import com.nppgks.reports.dto.ManualTagNameDto;
import com.nppgks.reports.repository.ManualTagNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ManualTagNameService implements TagNameService<ManualTagNameDto, String>{

    private ManualTagNameRepository manualTagNameRepository;

    @Autowired
    public ManualTagNameService(ManualTagNameRepository repository){
        this.manualTagNameRepository = repository;
    }

    @Override
    public String saveTagName(ManualTagNameDto tagNameDto) {
        try{
            return manualTagNameRepository.save(ManualTagNameDto.toManualTagName(tagNameDto)).getPermanentName();
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public List<ManualTagNameDto> getAllTagNamesDto() {
        return manualTagNameRepository.findAll()
                .stream()
                .map(tagName -> ManualTagNameDto.fromManualTagName(tagName))
                .toList();
    }

    @Override
    public Map<String, Boolean> saveTagNames(List<ManualTagNameDto> tagNames) {
        Map<String, Boolean> responses = tagNames.stream()
                .map(tagName -> {
                    Boolean resp = saveTagName(tagName)!=null;
                    return Map.entry(tagName.getPermanentName(), resp);
                })
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        return responses;
    }

    @Override
    public boolean deleteTagName(String permanentName) {
        try{
            manualTagNameRepository.deleteById(permanentName);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
