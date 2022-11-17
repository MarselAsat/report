package com.nppgks.reports.service.db_services.calculation;

import com.nppgks.reports.db.calculations.entity.TagData;
import com.nppgks.reports.db.calculations.repository.TagDataRepository;
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
