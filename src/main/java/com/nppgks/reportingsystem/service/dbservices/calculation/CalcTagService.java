package com.nppgks.reportingsystem.service.dbservices.calculation;

import com.nppgks.reportingsystem.db.calculations.entity.Tag;
import com.nppgks.reportingsystem.dto.calc.CalcTagDto;
import com.nppgks.reportingsystem.db.calculations.repository.TagRepository;
import com.nppgks.reportingsystem.dto.calc.CalcTagForOpc;
import com.nppgks.reportingsystem.service.dbservices.PartialUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CalcTagService {

    private final TagRepository tagRepository;

    @Autowired
    public CalcTagService(TagRepository repository){
        this.tagRepository = repository;
    }

    public List<CalcTagForOpc> getTagsByInitialAndCalcMethod(Boolean initial, String type){
        return tagRepository.findAllByInitialAndCalcMethod(initial, type);
    }

    public Integer saveTag(CalcTagDto tagNameDto) {
        try{
            return tagRepository.save(CalcTagDto.toTag(tagNameDto)).getId();
        }
        catch(Exception e){
            return null;
        }
    }

    public List<CalcTagDto> getAllTags() {
        return tagRepository.findByOrderByPermanentName()
                .stream()
                .map(CalcTagDto::fromTag)
                .toList();
    }

    public Map<Integer, Boolean> updateTags(List<CalcTagDto> tagNames) {
        return tagNames.stream()
                .map(tagName -> {
                    Boolean resp = updateTagName(tagName)!=null;
                    // TODO: 31.10.2022 batch queries
                    return Map.entry(tagName.getId(), resp);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Integer updateTagName(CalcTagDto tagNameDto) {
        try{
            tagRepository.updateTag(
                    tagNameDto.getId(),
                    tagNameDto.getAddress(),
                    tagNameDto.getDescription());
            return tagNameDto.getId();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteTag(Integer id) {
        try{
            tagRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean partialUpdateTag(Integer id, Map<String, String> updates) {
        Optional<Tag> tagNameOpt = tagRepository.findById(id);

        if (tagNameOpt.isPresent()) {
            Tag tag = tagNameOpt.get();
            Tag updatedTag = PartialUpdateService.updateObject(tag, updates);
            tagRepository.save(updatedTag);
        }
        return true;
    }
}
