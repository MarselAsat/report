package com.nppgks.reportingsystem.mapper;

import com.nppgks.reportingsystem.db.operative_reports.entity.MeteringNode;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportRow;
import com.nppgks.reportingsystem.db.operative_reports.entity.Tag;
import com.nppgks.reportingsystem.db.operative_reports.repository.MeteringNodeRepository;
import com.nppgks.reportingsystem.db.operative_reports.repository.ReportTypeRepository;
import com.nppgks.reportingsystem.db.operative_reports.repository.ReportRowRepository;
import com.nppgks.reportingsystem.dto.ReportRowDto;
import com.nppgks.reportingsystem.dto.TagDto;
import com.nppgks.reportingsystem.exception.TableDataMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TagMapper {

    private final ReportTypeRepository reportTypeRepository;

    private final ReportRowMapper reportRowMapper;

    private final ReportRowRepository rowRepository;

    private final MeteringNodeRepository meteringNodeRepository;

    public TagDto fromTagToTagReadDto(Tag tag){
        return new TagDto(
                tag.getId(),
                tag.getAddress(),
                tag.getDescription(),
                tag.getReportType().getName(),
                tag.getMeteringNode().getName(),
                reportRowMapper.fromReportRowToReportRowDto(tag.getReportRow()).combineNameAndType());
    }
    public Tag fromTagReadDtoToTag(TagDto tagDto){
        ReportType reportType = reportTypeRepository.findByName(tagDto.getReportTypeName()).orElseThrow();
        MeteringNode meteringNode = meteringNodeRepository.findByName(tagDto.getMeteringNodeName()).orElseThrow();
        String reportTypeName = reportType.getName();
        String reportTypeAndRowName = tagDto.getReportTypeConcatRowName();
        if(!tagDto.getReportTypeConcatRowName().matches(".*"+ReportRowDto.getPartOfReportTypeName(reportTypeName)+".*")){
            throw new TableDataMismatchException("Столбец 'Тип отчета' (%s) не соответствует типу отчета в столбце 'Строка в отчете' (%s)"
                    .formatted(reportTypeName, reportTypeAndRowName));
        }
        String rowName = tagDto.getReportTypeConcatRowName().substring(6);
        ReportRow row = rowRepository.findByNameAndReportType(rowName, reportType).orElseThrow();
        return new Tag(
                tagDto.getId(),
                tagDto.getAddress(),
                tagDto.getDescription(),
                reportType,
                meteringNode,
                row);
    }
}
