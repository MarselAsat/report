package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.scheduled_reports.entity.Report;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.scheduled_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.*;
import com.nppgks.reportingsystem.db.scheduled_reports.repository.ReportDataRepository;
import com.nppgks.reportingsystem.db.scheduled_reports.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReportDataServiceImpl implements ReportDataService {

    private final ReportDataRepository reportDataRepository;

    private final TagRepository tagRepository;

    @Autowired
    public ReportDataServiceImpl(ReportDataRepository reportDataRepository, TagRepository tagRepository) {
        this.reportDataRepository = reportDataRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<ReportData> saveReportValuesForReport(Map<String, String> tagValuesMap, Report report, LocalDateTime date) {
        List<ReportData> savedReportDataList = new ArrayList<>();
        for(Map.Entry<String, String> pair: tagValuesMap.entrySet()){
            Tag tag = tagRepository.findByAddress(pair.getKey());
            ReportData reportData = new ReportData(null, Double.parseDouble(pair.getValue()), date, tag, report);
            ReportData savedReportData = reportDataRepository.save(reportData);
            savedReportDataList.add(savedReportData);
        }
        return savedReportDataList;
    }

    @Override
    public List<ReportViewReportData> getReportViewReportData(Long reportId) {
        List<IReportViewReportData> reportDataViewInterface = reportDataRepository.getReportDataView(reportId);
        return ReportViewReportData.fromIReportViewReportData(reportDataViewInterface);
    }
}
