package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622;

import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.constants.ManualReportTypesEnum;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.ManualReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.SaveReportStrategy;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.ReportGeneratorHelper;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622.calculations.MI3622FinalData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3622.calculations.MI3622InitialData;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportTypeService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MI3622ReportGenerator extends ManualReportGenerator {

    private final OpcServiceRequests opcServiceRequests;
    private final ManualTagService manualTagService;
    private final ManualReportTypeService manualReportTypeService;

    public MI3622ReportGenerator(@Qualifier("saveOnceADayStrategy") SaveReportStrategy saveReportStrategy, OpcServiceRequests opcServiceRequests, ManualTagService manualTagService, ManualReportTypeService manualReportTypeService) {
        super(saveReportStrategy);
        this.opcServiceRequests = opcServiceRequests;
        this.manualTagService = manualTagService;
        this.manualReportTypeService = manualReportTypeService;
    }

    @Override
    protected List<ReportData> generateReportDataList() {
        Tag isFinishedTag = manualTagService.getTagByNameAndReportType("isFinished", ManualReportTypesEnum.mi3622.name());
        opcServiceRequests.sendTagValuesToOpc(Map.of(isFinishedTag.getAddress(), false));

        List<ManualTagForOpc> initialTags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypesEnum.mi3622.name());
        List<String> initialTagsForOpc = DataConverter.convertTagsToListOfAddresses(initialTags);

        Map<String, String> initialDataFromOpc = opcServiceRequests.getTagValuesFromOpc(initialTagsForOpc);

        Map<String, String> initialTagsMap = DataConverter.createPermanentNameToAddressMap(initialTags);
        DataConverter.putInOrder2DArraysInOpcData(initialDataFromOpc, initialTagsMap, MI3622InitialData.class);

        MI3622InitialData MI3622InitialData = DataConverter.convertMapToInitialData(initialDataFromOpc, initialTagsMap, MI3622InitialData.class);
        MI3622Runner MI3622Runner = new MI3622Runner(MI3622InitialData);
        MI3622FinalData MI3622FinalData = MI3622Runner.run();

        List<ManualTagForOpc> finalTags = manualTagService.getTagsByInitialAndReportType(false, ManualReportTypesEnum.mi3622.name());

        Map<String, String> finalTagsMap = DataConverter.createPermanentNameToAddressMap(finalTags);

        Map<String, Object> finalDataForOpc = DataConverter.convertFinalDataToMap(MI3622FinalData, finalTagsMap);
        finalDataForOpc.put(isFinishedTag.getAddress(), true);
        opcServiceRequests.sendTagValuesToOpc(finalDataForOpc);

        return ReportGeneratorHelper.createListOfReportData(initialTags, finalTags, initialDataFromOpc, finalDataForOpc, report);
    }

    @Override
    protected Report createReport(LocalDateTime currentDt) {
        ReportType reportType = manualReportTypeService.findById(ManualReportTypesEnum.mi3622.name());
        return new Report(
                null,
                "Поверка МИ3622 от " + SingleDateTimeFormatter.formatToSinglePattern(currentDt),
                currentDt,
                reportType);
    }
}
