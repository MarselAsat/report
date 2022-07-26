package com.nppgks.reports.service;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;
import com.nppgks.reports.repository.TagDataRepository;
import com.nppgks.reports.repository.TagNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TagDataServiceImpl implements TagDataService {

    private final TagDataRepository tagDataRepository;

    private final TagNameRepository tagNameRepository;

    @Autowired
    public TagDataServiceImpl(TagDataRepository tagDataRepository, TagNameRepository tagNameRepository) {
        this.tagDataRepository = tagDataRepository;
        this.tagNameRepository = tagNameRepository;
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

    @Override
    public List<TagData> saveTagDataMapByReportName(Map<String, String> tagDataMap, ReportName reportName, LocalDateTime date) {
        List<TagData> savedTagDataList = new ArrayList<>();
        for(Map.Entry<String, String> pair: tagDataMap.entrySet()){
            TagName tagName = tagNameRepository.findByName(pair.getKey());
            TagData tagData = new TagData(null, Double.parseDouble(pair.getValue()), date, tagName, reportName);
            TagData savedTagData = tagDataRepository.save(tagData);
            savedTagDataList.add(savedTagData);
        }
        return savedTagDataList;
    }

//    private List<TagData> getSourceDataForReport(Long reportId, LocalDateTime start, LocalDateTime end){
//        List<TagData> resultData =  tagDataRepository.findByReportTypeAndDtCreationBetween(reportId, start, end);
//        for (TagData tagData: resultData) {
//            tagData.getTagName().getName();
//        }
//        return resultData;
//    }




}
