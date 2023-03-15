package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.operative_reports.entity.ReportName;
import com.nppgks.reportingsystem.db.operative_reports.entity.TagData;
import com.nppgks.reportingsystem.db.operative_reports.entity.TagName;
import com.nppgks.reportingsystem.dto.*;
import com.nppgks.reportingsystem.db.operative_reports.repository.TagDataRepository;
import com.nppgks.reportingsystem.db.operative_reports.repository.TagNameRepository;
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

    @Override
    public List<TagDataDto> getDataByReport(Long reportNameId) {
        List<TagData> resultList = tagDataRepository.findByReportNameId(reportNameId);
        return resultList.stream()
                .map(TagDataDto::fromTagData) // в fromTagData происходит изменение времени в зависимости от часового пояса
                .toList();
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

    @Override
    public List<ReportViewTagData> getReportViewTagData(Long reportNameId) {
        List<IReportViewTagData> tagDataViewInterface = tagDataRepository.getTagDataViewTest(reportNameId);
        return ReportViewTagData.fromIReportViewTagData(tagDataViewInterface);
    }
}
