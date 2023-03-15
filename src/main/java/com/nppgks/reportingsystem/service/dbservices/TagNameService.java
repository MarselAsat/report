package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.operative_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.operative_reports.entity.TagName;
import com.nppgks.reportingsystem.mapper.TagNameMapper;
import com.nppgks.reportingsystem.db.operative_reports.repository.TagNameRepository;
import com.nppgks.reportingsystem.dto.TagNameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public List<TagName> getAllTagNamesByReportType(ReportType reportType){
        return tagNameRepository.findAllByReportType(reportType);
    }

    public void deleteTagName(Long id) {
        tagNameRepository.deleteById(id);
    }

    public void partialUpdateTagName(Long id, Map<String, String> updates) {
        Optional<TagName> tagNameOpt = tagNameRepository.findById(id);

        if (tagNameOpt.isPresent()) {
            TagName tagName = tagNameOpt.get();
            TagNameDto tagNameDto = tagNameMapper.fromTagNameToTagNameReadDto(tagName);

            TagNameDto updatedTagNameDto = PartialUpdateService.updateObject(tagNameDto, updates);

            TagName updatedTagName = tagNameMapper.fromTagNameReadDtoToTagName(updatedTagNameDto);
            tagNameRepository.save(updatedTagName);
        }
        else{
            throw new NoSuchElementException("No tag name with such id: "+id+" in database");
        }
    }

    public Long saveTagName(TagNameDto tagNameDto){
        TagName tagName = tagNameMapper.fromTagNameReadDtoToTagName(tagNameDto);
        TagName saved = tagNameRepository.save(tagName);
        return saved.getId();
    }
}
