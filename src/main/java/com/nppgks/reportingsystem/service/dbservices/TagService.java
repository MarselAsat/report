package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.scheduled_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.Tag;
import com.nppgks.reportingsystem.mapper.TagMapper;
import com.nppgks.reportingsystem.db.scheduled_reports.repository.TagRepository;
import com.nppgks.reportingsystem.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    public List<TagDto> getAllTagsDto() {
        return tagRepository.findByOrderByAddress().stream()
                .map(tagMapper::fromTagToTagReadDto)
                .toList();
    }

    public List<Tag> getAllTagsByReportType(ReportType reportType) {
        return tagRepository.findAllByReportType(reportType);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    public void partialUpdateTag(Long id, Map<String, String> updates) {
        Optional<Tag> tagOpt = tagRepository.findById(id);

        if (tagOpt.isPresent()) {
            Tag tag = tagOpt.get();
            TagDto tagDto = tagMapper.fromTagToTagReadDto(tag);

            TagDto updatedTagDto = PartialUpdateService.updateObject(tagDto, updates);

            Tag updatedTag = tagMapper.fromTagReadDtoToTag(updatedTagDto);
            tagRepository.save(updatedTag);
        } else {
            throw new NoSuchElementException("В БД нет тега с таким id: " + id);
        }
    }

    public Long saveTag(TagDto tagDto) {
        Tag tag = tagMapper.fromTagReadDtoToTag(tagDto);
        Tag saved = tagRepository.save(tag);
        return saved.getId();
    }
}
