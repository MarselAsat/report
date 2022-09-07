package com.nppgks.reports.service;

import com.nppgks.reports.dto.TagDataDto;
import com.nppgks.reports.entity.*;
import com.nppgks.reports.repository.TagDataRepository;
import com.nppgks.reports.repository.TagNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
    public void saveTagDataMapByReportName(Map<String, String> tagDataMap, ReportName reportName, LocalDateTime date) {
        for(Map.Entry<String, String> pair: tagDataMap.entrySet()){
            TagName tagName = tagNameRepository.findByName(pair.getKey());
            TagData tagData = new TagData(null, Double.parseDouble(pair.getValue()), date, tagName, reportName);
            tagDataRepository.save(tagData);
        }
    }

    @Override
    public List<ReportViewTagData> getReportViewTagData(Long reportNameId) {
        List<IReportViewTagData> tagDataViewInterface = tagDataRepository.getTagDataView(reportNameId);
        return tagDataViewInterface.stream()
                .map(ReportViewTagData::fromIReportViewTagData)
                .toList();
    }
}
