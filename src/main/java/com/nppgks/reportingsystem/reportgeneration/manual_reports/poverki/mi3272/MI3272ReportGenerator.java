package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272;

import com.nppgks.reportingsystem.constants.ManualReportTypesEnum;
import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.exception.MissingReportStageException;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.ManualReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.SaveReportStrategy;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.ReportGeneratorHelper;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.TprCoeffInitData;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportTypeService;
import com.nppgks.reportingsystem.util.ArrayParser;
import com.nppgks.reportingsystem.util.DataRounder;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.MI3272Calculator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.MI3272FinalData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.MI3272TprInitData;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MI3272ReportGenerator extends ManualReportGenerator {
    private final OpcServiceRequests opcServiceRequests;
    private final ManualTagService manualTagService;
    private final ManualReportTypeService manualReportTypeService;

    public MI3272ReportGenerator(@Qualifier("saveOnceADayStrategy") SaveReportStrategy saveReportStrategy, OpcServiceRequests opcServiceRequests, ManualTagService manualTagService, ManualReportTypeService manualReportTypeService) {
        super(saveReportStrategy);
        this.opcServiceRequests = opcServiceRequests;
        this.manualTagService = manualTagService;
        this.manualReportTypeService = manualReportTypeService;
    }
    private Tag isFinishedTag;
    private List<ManualTagForOpc> allInitialTags;
    private MI3272Calculator calculator;
    private Map<String, String> tprCoeffInitDataFromOpc;
    private Map<String, String> initialTagsMap;
    public String generateTprCoeff(){
        isFinishedTag = manualTagService.getTagByNameAndReportType("isFinished", ManualReportTypesEnum.mi3272.name());
        opcServiceRequests.sendTagValuesToOpc(Map.of(isFinishedTag.getAddress(), false));

        allInitialTags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypesEnum.mi3272.name());
        List<String> tprCoeffInitTagsForOpc = DataConverter.convertTagsToListOfAddresses(allInitialTags.stream().filter(
                        tag -> ReportGeneratorHelper.tagNameMatchesAnyDataField(tag.permanentName(), TprCoeffInitData.class))
                .toList());
        tprCoeffInitDataFromOpc = opcServiceRequests.getTagValuesFromOpc(tprCoeffInitTagsForOpc);
        initialTagsMap = DataConverter.createPermanentNameToAddressMap(allInitialTags);
        TprCoeffInitData tprCoeffInitData = DataConverter.convertMapToInitialData(tprCoeffInitDataFromOpc, initialTagsMap, TprCoeffInitData.class, true);
        calculator = new MI3272Calculator();
        calculator.initTprCoeffData(tprCoeffInitData);
        double[] K_TPR_j = calculator.calculateK_j();
        String tagValueK_j = ArrayParser.fromObjectToJson(K_TPR_j);
        Tag tagK_j = manualTagService.getTagByNameAndReportType("K_TPR_j", ManualReportTypesEnum.mi3272.name());
        opcServiceRequests.sendTagValuesToOpc(Map.of(isFinishedTag.getAddress(), true, tagK_j.getAddress(), tagValueK_j));
        return tagValueK_j;
    }

    @Override
    protected List<ReportData> generateReportDataList() {
        if(tprCoeffInitDataFromOpc == null || tprCoeffInitDataFromOpc.isEmpty()){
            throw new MissingReportStageException("Прежде чем генерировать отчет МИ3272 с использованием ТПР, нужно расчитать коэффициент преобразования K_ТПР_j");
        }
        opcServiceRequests.sendTagValuesToOpc(Map.of(isFinishedTag.getAddress(), false));

        List<String> restInitTagsForOpc = DataConverter.convertTagsToListOfAddresses(allInitialTags.stream().filter(
                tag -> ReportGeneratorHelper.tagNameMatchesAnyDataField(tag.permanentName(), MI3272TprInitData.class))
                .toList());
        Map<String, String> restInitDataFromOpc = opcServiceRequests.getTagValuesFromOpc(restInitTagsForOpc);
        MI3272TprInitData MI3272TprInitData = DataConverter.convertMapToInitialData(restInitDataFromOpc, initialTagsMap, MI3272TprInitData.class, true);
        calculator.initRestData(MI3272TprInitData);
        MI3272FinalData mi3272FinalData = calculator.calculateWithTpr();
        DataRounder.roundPojo(mi3272FinalData);

        List<ManualTagForOpc> finalTags = manualTagService.getTagsByInitialAndReportType(false, ManualReportTypesEnum.mi3272.name());

        Map<String, String> finalTagsMap = DataConverter.createPermanentNameToAddressMap(finalTags);

        Map<String, Object> finalDataForOpc = DataConverter.convertFinalDataToMap(mi3272FinalData, finalTagsMap);
        finalDataForOpc.put(isFinishedTag.getAddress(), true);
        opcServiceRequests.sendTagValuesToOpc(finalDataForOpc);
        //restInitDataFromOpc.putAll(tprCoeffInitDataFromOpc);
        Map<String, String> allInitDataFromOc = mergeInitialMaps(tprCoeffInitDataFromOpc, restInitDataFromOpc);
        return ReportGeneratorHelper.createListOfReportData(allInitialTags, finalTags, allInitDataFromOc, finalDataForOpc, report);
    }

    public static Map<String, String> mergeInitialMaps(
            Map<String, String> initialCoeffDataFromOpc,
            Map<String, String> initialRestDataFromOpc) {

        Map<String, String> allInitData = new HashMap<>(initialCoeffDataFromOpc);

        for (Map.Entry<String, String> entry : initialRestDataFromOpc.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
            if(allInitData.containsKey(key)){
                allInitData.put(key, initialCoeffDataFromOpc.get(key)+"#"+value);
            }
            else{
                allInitData.put(key, value);
            }
        }
        return allInitData;
    }

    @Override
    protected Report createReport(LocalDateTime currentDt) {
        ReportType reportType = manualReportTypeService.findById(ManualReportTypesEnum.mi3272.name());
        return new Report(
                null,
                "Поверка МИ3272 от " + SingleDateTimeFormatter.formatToSinglePattern(currentDt),
                currentDt,
                reportType);
    }
}
