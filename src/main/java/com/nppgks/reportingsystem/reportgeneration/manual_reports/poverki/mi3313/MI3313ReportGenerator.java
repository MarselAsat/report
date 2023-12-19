package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313;

import com.nppgks.reportingsystem.constants.ManualReportTypesEnum;
import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.ManualReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.SaveReportStrategy;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.ReportGeneratorHelper;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportTypeService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MI3313ReportGenerator extends ManualReportGenerator {

    private final OpcServiceRequests opcServiceRequests;
    private final ManualTagService manualTagService;
    private final ManualReportTypeService manualReportTypeService;

    public MI3313ReportGenerator(@Qualifier("saveOnceADayStrategy") SaveReportStrategy saveReportStrategy, OpcServiceRequests opcServiceRequests, ManualTagService manualTagService, ManualReportTypeService manualReportTypeService) {
        super(saveReportStrategy);
        this.opcServiceRequests = opcServiceRequests;
        this.manualTagService = manualTagService;
        this.manualReportTypeService = manualReportTypeService;
    }

    @Override
    protected List<ReportData> generateReportDataList() {
        Tag isFinishedTag = manualTagService.getTagByNameAndReportType("isFinished", ManualReportTypesEnum.mi3313OneEsrm.name());
        opcServiceRequests.sendTagValuesToOpc(Map.of(isFinishedTag.getAddress(), false));

        List<ManualTagForOpc> initialTags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypesEnum.mi3313OneEsrm.name());
        List<String> initialTagsForOpc = DataConverter.convertTagsToListOfAddresses(initialTags);
        Map<String, String> initialDataFromOpc = opcServiceRequests.getTagValuesFromOpc(initialTagsForOpc);

        Map<String, String> initialTagsMap = DataConverter.createPermanentNameToAddressMap(initialTags);
        //DataConverter.putInOrder2DArraysInOpcData(initialDataFromOpc, initialTagsMap, MI3313OneEsrmInitialData.class);

        MI3313OneEsrmInitialData MI3313InitialData = DataConverter.convertMapToInitialData(initialDataFromOpc, initialTagsMap, MI3313OneEsrmInitialData.class);
        MI3313Calculator mi3313Calculator = new MI3313Calculator(MI3313InitialData);
        MI3313OneEsrmFinalData mi3313FinalData = mi3313Calculator.calculate();

        List<ManualTagForOpc> finalTags = manualTagService.getTagsByInitialAndReportType(false, ManualReportTypesEnum.mi3313OneEsrm.name());

        Map<String, String> finalTagsMap = DataConverter.createPermanentNameToAddressMap(finalTags);

        Map<String, Object> finalDataForOpc = DataConverter.convertFinalDataToMap(mi3313FinalData, finalTagsMap);
        finalDataForOpc.put(isFinishedTag.getAddress(), true);
        opcServiceRequests.sendTagValuesToOpc(finalDataForOpc);
        return ReportGeneratorHelper.createListOfReportData(initialTags, finalTags, initialDataFromOpc, finalDataForOpc, report);

    }

    @Override
    protected Report createReport(LocalDateTime currentDt) {
        ReportType reportType = manualReportTypeService.findById(ManualReportTypesEnum.mi3313OneEsrm.name());
        return new Report(
                null,
                "Поверка МИ3313 c помощью одного ЭСРМ от " + SingleDateTimeFormatter.formatToSinglePattern(currentDt),
                currentDt,
                reportType);
    }
}
