package com.nppgks.reportingsystem.service.dbservices.calculation;

import com.nppgks.reportingsystem.db.calculations.entity.TagName;
import com.nppgks.reportingsystem.dto.calc.CalcTagNameDto;
import com.nppgks.reportingsystem.db.calculations.repository.TagNameRepository;
import com.nppgks.reportingsystem.dto.calc.CalcTagNameForOpc;
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
public class CalcTagNameService {

    private final TagNameRepository tagNameRepository;

    @Autowired
    public CalcTagNameService(TagNameRepository repository){
        this.tagNameRepository = repository;
    }

    public List<CalcTagNameForOpc> getTagNamesByInitialAndType(Boolean initial, String type){
        return tagNameRepository.findAllByInitialAndCalcMethod(initial, type);
    }

    public Integer saveTagName(CalcTagNameDto tagNameDto) {
        try{
            return tagNameRepository.save(CalcTagNameDto.toTagName(tagNameDto)).getId();
        }
        catch(Exception e){
            return null;
        }
    }

    public List<CalcTagNameDto> getAllTagNames() {
        return tagNameRepository.findByOrderByPermanentName()
                .stream()
                .map(CalcTagNameDto::fromTagName)
                .toList();
    }

    public Map<Integer, Boolean> updateTagNames(List<CalcTagNameDto> tagNames) {
        return tagNames.stream()
                .map(tagName -> {
                    Boolean resp = updateTagName(tagName)!=null;
                    // TODO: 31.10.2022 batch queries
                    return Map.entry(tagName.getId(), resp);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Integer updateTagName(CalcTagNameDto tagNameDto) {
        try{
            tagNameRepository.updateTagName(
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

    public boolean partialUpdateTagName(Integer id, Map<String, String> updates) {
        Optional<TagName> tagNameOpt = tagNameRepository.findById(id);

        if (tagNameOpt.isPresent()) {
            TagName tagName = tagNameOpt.get();
            CalcTagNameDto tagNameDto = CalcTagNameDto.fromTagName(tagName);

            CalcTagNameDto updatedTagNameDto = PartialUpdateService.updateObject(tagNameDto, updates);

            TagName updatedTagName = CalcTagNameDto.toTagName(updatedTagNameDto);
            tagNameRepository.save(updatedTagName);
        }
        return true;
    }
}
