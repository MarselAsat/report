package com.nppgks.reports.service;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.repository.TagDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TagDataServiceImpl implements TagDataService {

    private final TagDataRepository tagDataRepository;

    @Autowired
    public TagDataServiceImpl(TagDataRepository tagDataRepository) {
        this.tagDataRepository = tagDataRepository;
    }

//    public Map<TagName, Double> getDataForReport(Long reportId, LocalDateTime start, LocalDateTime end){
//        List<TagData> result = getSourceDataForReport(reportId, start, end);
//
//        return result.stream().collect(Collectors.toMap(TagData::getTagName, TagData::getData));
//
//    }

    @Override
    public List<TagDataDto> getDataForReport(Long reportNameId) {
        List<TagData> resultList = tagDataRepository.findByReportName_Id(reportNameId);
        List<TagDataDto> tagDataDto = resultList.stream()
                .map(TagDataDto::fromTagData) // в fromTagData происходит изменение времени в зависимости от часового пояса
                .toList();
        return tagDataDto;
    }

    @Override
    public List<TagData> findAll() {
        return tagDataRepository.findAll();
    }

    @Override
    public TagData saveTagData(TagData tagData) {
        return tagDataRepository.save(tagData);
    }

//    private List<TagData> getSourceDataForReport(Long reportId, LocalDateTime start, LocalDateTime end){
//        List<TagData> resultData =  tagDataRepository.findByReportTypeAndDtCreationBetween(reportId, start, end);
//        for (TagData tagData: resultData) {
//            tagData.getTagName().getName();
//        }
//        return resultData;
//    }




}
