package com.nppgks.reportingsystem.service.dbservices.manual_reports;

import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.manual.ManualTagDto;
import com.nppgks.reportingsystem.db.manual_reports.repository.TagRepository;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
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
public class ManualTagService {

    private final TagRepository tagRepository;

    @Autowired
    public ManualTagService(TagRepository repository){
        this.tagRepository = repository;
    }

    public List<ManualTagForOpc> getTagsByInitialAndReportType(Boolean initial, String type){
        return tagRepository.findAllByInitialAndReportType(initial, type);
    }

    public List<ManualTagDto> getAllTags() {
        return tagRepository.findByOrderByPermanentName()
                .stream()
                .map(ManualTagDto::fromTag)
                .toList();
    }

    public Tag getTagByNameAndReportType(String name, String reportType){
        return tagRepository.findByPermanentNameAndReportType(name, reportType)
                .orElseThrow(() -> new RuntimeException("В таблице manual_reports.tag нет тега с именем %s и типом %s"
                        .formatted(name, reportType)));
    }
    public Integer saveTag(ManualTagDto tagNameDto) {
        try{
            return tagRepository.save(ManualTagDto.toTag(tagNameDto)).getId();
        }
        catch(Exception e){
            return null;
        }
    }

    public Map<Integer, Boolean> updateTags(List<ManualTagDto> tagNames) {
        return tagNames.stream()
                .map(tagName -> {
                    Boolean resp = updateTagName(tagName)!=null;
                    // TODO: 31.10.2022 batch queries
                    return Map.entry(tagName.getId(), resp);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Integer updateTagName(ManualTagDto tagNameDto) {
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
