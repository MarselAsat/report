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
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MI3313ReportGenerator extends ManualReportGenerator {

    @Setter
    private MI3313Type reportType;
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
        if (reportType.equals(MI3313Type.MULTIPLE_ESRMS)) {
            generateForMultipleEsrm();
        }
        Tag isFinishedTag = manualTagService.getTagByNameAndReportType("isFinished", ManualReportTypesEnum.mi3313OneEsrm.name());
        opcServiceRequests.sendTagValuesToOpc(Map.of(isFinishedTag.getAddress(), false));
        List<ManualTagForOpc> initialTags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypesEnum.mi3313OneEsrm.name());
        List<String> initialTagsForOpc = DataConverter.convertTagsToListOfAddresses(initialTags);
        Map<String, String> initialDataFromOpc = opcServiceRequests.getTagValuesFromOpc(initialTagsForOpc);

        Map<String, String> initialTagsMap = DataConverter.createPermanentNameToAddressMap(initialTags);
        //DataConverter.putInOrder2DArraysInOpcData(initialDataFromOpc, initialTagsMap, MI3313OneEsrmInitialData.class);
        MI3313OneEsrmInitialData MI3313InitialData = DataConverter.convertMapToInitialData(initialDataFromOpc, initialTagsMap, MI3313OneEsrmInitialData.class, false);
        MI3313OneEsrmCalculator mi3313OneEsrmCalculator = new MI3313OneEsrmCalculator(MI3313InitialData);
        MI3313OneEsrmFinalData mi3313FinalData = mi3313OneEsrmCalculator.calculate();

        List<ManualTagForOpc> finalTags = manualTagService.getTagsByInitialAndReportType(false, ManualReportTypesEnum.mi3313OneEsrm.name());

        Map<String, String> finalTagsMap = DataConverter.createPermanentNameToAddressMap(finalTags);

        Map<String, Object> finalDataForOpc = DataConverter.convertFinalDataToMap(mi3313FinalData, finalTagsMap);
        finalDataForOpc.put(isFinishedTag.getAddress(), true);
        opcServiceRequests.sendTagValuesToOpc(finalDataForOpc);
        return ReportGeneratorHelper.createListOfReportData(initialTags, finalTags, initialDataFromOpc, finalDataForOpc, report);
    }

    private void generateForMultipleEsrm() {
        Tag isFinishedTag = manualTagService.getTagByNameAndReportType("isFinished", ManualReportTypesEnum.mi3313ManyEsrm.name());
        opcServiceRequests.sendTagValuesToOpc(Map.of(isFinishedTag.getAddress(), false));
        List<ManualTagForOpc> initialTags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypesEnum.mi3313ManyEsrm.name());
        List<String> initialTagsForOpc = DataConverter.convertTagsToListOfAddresses(initialTags);

        addAddressesForMultipleEsrm(initialTagsForOpc);

        Map<String, String> initialDataFromOpc = opcServiceRequests.getTagValuesFromOpc(initialTagsForOpc);

        initialDataFromOpc = groupTagValuesByEsrm(initialDataFromOpc);

        Map<String, String> initialTagsMap = DataConverter.createPermanentNameToAddressMap(initialTags);
        MI3313ManyEsrmInitialData MI3313InitialData = DataConverter.convertMapToInitialData(initialDataFromOpc, initialTagsMap, MI3313ManyEsrmInitialData.class, false);
        MI3313ManyEsrmCalculator mi3313ManyEsrmCalculator = new MI3313ManyEsrmCalculator(MI3313InitialData);
        MI3313ManyEsrmFinalData mi3313FinalData = mi3313ManyEsrmCalculator.calculate();

        List<ManualTagForOpc> finalTags = manualTagService.getTagsByInitialAndReportType(false, ManualReportTypesEnum.mi3313ManyEsrm.name());

        Map<String, String> finalTagsMap = DataConverter.createPermanentNameToAddressMap(finalTags);

        Map<String, Object> finalDataForOpc = DataConverter.convertFinalDataToMap(mi3313FinalData, finalTagsMap);
        finalDataForOpc.put(isFinishedTag.getAddress(), true);
        opcServiceRequests.sendTagValuesToOpc(finalDataForOpc);
    }

    public static Map<String, String> groupTagValuesByEsrm(Map<String, String> initialDataFromOpc) {
        String commonPartInReg = "ji";
        String varietyPostReg = commonPartInReg + "\\d";
        String commonPostReg = commonPartInReg + "k";

        Set<String> commonAddresses = initialDataFromOpc.keySet()
                .stream()
                .filter(ad -> ad.matches(".+" + varietyPostReg))
                .map(ad -> ad.replaceAll(varietyPostReg, commonPostReg))
                .collect(Collectors.toSet());

        Map<String, String> commonTagAddressToValue = new HashMap<>();

        for (String commonAddress : commonAddresses) {
            int esrmCount = 1;
            StringBuilder newValue = new StringBuilder();
            String currentAddress = commonAddress.replaceAll(commonPostReg, commonPartInReg + esrmCount);
            while (initialDataFromOpc.containsKey(currentAddress)) {
                newValue.append(",").append(initialDataFromOpc.get(currentAddress));
                esrmCount++;
                currentAddress = commonAddress.replaceAll(commonPostReg, commonPartInReg + esrmCount);
            }
            commonTagAddressToValue.put(commonAddress, "[" + newValue.deleteCharAt(0) + "]");
        }

        Map<String, String> withoutEsrmTags = initialDataFromOpc.entrySet()
                .stream()
                .filter(entry -> !entry.getKey().matches(".+" + varietyPostReg))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        withoutEsrmTags.putAll(commonTagAddressToValue);
        return withoutEsrmTags;
    }

    public static void addAddressesForMultipleEsrm(List<String> initialTagsForOpc) {
        int esrmMaxCount = 10;
        List<String> commonAddresses = initialTagsForOpc.stream()
                .filter(ad -> ad.matches(".+jik"))
                .toList();
        for (String address : commonAddresses) {
            initialTagsForOpc.remove(address);
            for (int i = 1; i <= esrmMaxCount; i++) {
                initialTagsForOpc.add(address.replace("jik", "ji" + i));
            }
        }
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
