package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.recurring_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.recurring_reports.entity.TagName;
import com.nppgks.reportingsystem.dto.TagNameMapper;
import com.nppgks.reportingsystem.db.recurring_reports.repository.TagNameRepository;
import com.nppgks.reportingsystem.dto.TagNameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagNameService {

    private final TagNameRepository tagNameRepository;

    private final TagNameMapper tagNameMapper;

    public List<TagNameDto> getAllTagNamesDto() {
        return tagNameRepository.findByOrderByName().stream()
                .map(tagNameMapper::fromTagNameToTagNameReadDto)
                .toList();
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
        return tagNameRepository.findAllByReportType(reportType);
    }

    public boolean deleteTagName(Long id) {
        tagNameRepository.deleteById(id);
        return true;
    }

    public boolean partialUpdateTagName(Long id, Map<String, String> updates) {
        Optional<TagName> tagNameOpt = tagNameRepository.findById(id);

        if (tagNameOpt.isPresent()) {
            TagName tagName = tagNameOpt.get();
            TagNameDto tagNameDto = tagNameMapper.fromTagNameToTagNameReadDto(tagName);

            TagNameDto updatedTagNameDto = PartialUpdateService.updateObject(tagNameDto, updates);

            TagName updatedTagName = tagNameMapper.fromTagNameReadDtoToTagName(updatedTagNameDto);
            tagNameRepository.save(updatedTagName);
        }
        return true;
    }

    public Long saveTagName(TagNameDto tagNameDto){
        TagName tagName = tagNameMapper.fromTagNameReadDtoToTagName(tagNameDto);
        TagName saved = tagNameRepository.save(tagName);
        return saved.getId();
    }
}
