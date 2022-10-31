package com.nppgks.reports.service.db_services.poverka;

import com.nppgks.reports.dto.poverka.PoverkaTagNameDto;
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
public class PoverkaTagNameService {

    private final TagNameRepository tagNameRepository;

    @Autowired
    public PoverkaTagNameService(TagNameRepository repository){
        this.tagNameRepository = repository;
    }

    public List<TagNameForOpc> getTagNamesByInitialAndType(Boolean initial, String type){
        return tagNameRepository.findAllByInitialAndType(initial, type);
    }

    public Integer saveTagName(PoverkaTagNameDto tagNameDto) {
        try{
            return tagNameRepository.save(PoverkaTagNameDto.toTagName(tagNameDto)).getId();
        }
        catch(Exception e){
            return null;
        }
    }

    public List<PoverkaTagNameDto> getAllTagNames() {
        return tagNameRepository.findAll()
                .stream()
                .map(PoverkaTagNameDto::fromTagName)
                .toList();
    }

    public Map<Integer, Boolean> updateTagNames(List<PoverkaTagNameDto> tagNames) {
        return tagNames.stream()
                .map(tagName -> {
                    Boolean resp = updateTagName(tagName)!=null;
                    // TODO: 31.10.2022 batch queries
                    return Map.entry(tagName.getId(), resp);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Integer updateTagName(PoverkaTagNameDto tagNameDto) {
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
