package com.nppgks.reportingsystem.mapper;

import com.nppgks.reportingsystem.db.operative_reports.entity.MeteringNode;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportRow;
import com.nppgks.reportingsystem.db.operative_reports.entity.TagName;
import com.nppgks.reportingsystem.db.operative_reports.repository.MeteringNodeRepository;
import com.nppgks.reportingsystem.db.operative_reports.repository.ReportTypeRepository;
import com.nppgks.reportingsystem.db.operative_reports.repository.ReportRowRepository;
import com.nppgks.reportingsystem.dto.TagNameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TagNameMapper {

    private final ReportTypeRepository reportTypeRepository;

    private final ReportRowMapper reportRowMapper;

    private final ReportRowRepository rowRepository;

    private final MeteringNodeRepository meteringNodeRepository;

    public TagNameDto fromTagNameToTagNameReadDto(TagName tagName){
        return new TagNameDto(
                tagName.getId(),
                tagName.getName(),
                tagName.getDescription(),
                tagName.getReportType().getName(),
                tagName.getMeteringNode().getName(),
                reportRowMapper.fromReportRowToReportRowDto(tagName.getReportRow()).combineNameAndType());
    }
    public TagName fromTagNameReadDtoToTagName(TagNameDto tagNameDto){
        ReportType reportType = reportTypeRepository.findByName(tagNameDto.getReportTypeName()).orElseThrow();
        MeteringNode meteringNode = meteringNodeRepository.findByName(tagNameDto.getMeteringNodeName()).orElseThrow();
        String rowName = tagNameDto.getRowNameAndReportType().substring(6);
        ReportRow row = rowRepository.findByNameAndReportType(rowName, reportType).orElseThrow();
        return new TagName (
                tagNameDto.getId(),
                tagNameDto.getName(),
                tagNameDto.getDescription(),
                reportType,
                meteringNode,
                row);
    }
}
