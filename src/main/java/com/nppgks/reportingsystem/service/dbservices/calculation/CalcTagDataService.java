package com.nppgks.reportingsystem.service.dbservices.calculation;

import com.nppgks.reportingsystem.db.calculations.entity.TagData;
import com.nppgks.reportingsystem.db.calculations.repository.TagDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalcTagDataService {

    private final TagDataRepository tagDataRepository;

    public List<TagData> getTagDataList(Long reportNameId){
        return tagDataRepository.findByReportNameId(reportNameId);
    }
}
