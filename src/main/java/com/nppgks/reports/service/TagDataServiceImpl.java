package com.nppgks.reports.service;

import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;
import com.nppgks.reports.repository.TagDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagDataServiceImpl implements TagDataService {

    private final TagDataRepository tagDataRepository;

    @Autowired
    public TagDataServiceImpl(TagDataRepository tagDataRepository) {
        this.tagDataRepository = tagDataRepository;
    }

    public Map<TagName, Double> getDataForReport(Long reportId, LocalDateTime start, LocalDateTime end){
        List<TagData> result = getSourceDataForReport(reportId, start, end);

        return result.stream().collect(Collectors.toMap(TagData::getTagName, TagData::getData));

    }

    private List<TagData> getSourceDataForReport(Long reportId, LocalDateTime start, LocalDateTime end){
        List<TagData> resultData =  tagDataRepository.findByReportTypeAndDtCreationBetween(reportId, start, end);
        for (TagData tagData: resultData) {
            tagData.getTagName().getName();
        }
        return resultData;
    }
}
