package com.nppgks.reportingsystem.reportgeneration.manual_reports.acts_passport;

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
import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.Gost51858;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportTypeService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OilQualityPassportReportGenerator extends ManualReportGenerator {

    private final OpcServiceRequests opcServiceRequests;
    private final ManualReportTypeService manualReportTypeService;
    private final ManualTagService manualTagService;

    public OilQualityPassportReportGenerator(@Qualifier("saveOnceADayStrategy") SaveReportStrategy saveReportStrategy, OpcServiceRequests opcServiceRequests, ManualReportTypeService manualReportTypeService, ManualTagService manualTagService) {
        super(saveReportStrategy);
        this.opcServiceRequests = opcServiceRequests;
        this.manualReportTypeService = manualReportTypeService;
        this.manualTagService = manualTagService;
    }

    @Override
    protected List<ReportData> generateReportDataList() {
        List<ReportData> reportDataList = new ArrayList<>();
        List<ManualTagForOpc> tags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypesEnum.oilQualityPassport.name());
        List<String> tagAddresses = DataConverter.convertTagsToListOfAddresses(tags);
        Map<String, String> tagValuesFromOpc = opcServiceRequests.getTagValuesFromOpc(tagAddresses);
        Map<String, ManualTagForOpc> addressToTagMap = DataConverter.convertTagListToMapWithAddressKey(tags);
        Map<String, String> permanentNameToAddressMap = DataConverter.createPermanentNameToAddressMap(tags);

        for (Map.Entry<String, String> addressToValue : tagValuesFromOpc.entrySet()) {
            reportDataList.add(new ReportData(
                    null,
                    addressToValue.getValue(),
                    ManualTagForOpc.toTag(addressToTagMap.get(addressToValue.getKey())),
                    report
            ));
        }
        boolean export = Boolean.parseBoolean(tagValuesFromOpc.get(permanentNameToAddressMap.get("export")));
        double sulfur = toDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("sulfurFractionResult")));
        double density20 = toDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("density20Result")));
        double density15 = toDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("density15Result")));
        double out200 = toDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("fractionOut200Result")));

        double out300 = toDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("fractionOut300Result")));
        double paraffin = toDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("paraffinFractionResult")));
        double water = toDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("waterFractionResult")));
        double salt = toDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("chlorideSaltFractionResult")));
        double admix = toDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("admixtureFractionResult")));
        double hydroSulfide = toDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("hydroSulfideFractionResult")));
        double meth = toDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("methFractionResult")));

        String oilSymbol = Gost51858.defineOilSymbol(export, sulfur, density20, density15, out200, out300, water, salt, hydroSulfide, meth);

        Tag gostTag = manualTagService.getTagByNameAndReportType("GOST", ManualReportTypesEnum.oilQualityPassport.name());

        reportDataList.add(new ReportData(
                null,
                oilSymbol,
                gostTag,
                report
        ));

        return reportDataList;
    }

    private double toDouble(String result) {
        return Double.parseDouble(result.replace("менее ", ""));
    }

    @Override
    protected Report createReport(LocalDateTime currentDt) {
        ReportType reportType = manualReportTypeService.findById(ManualReportTypesEnum.oilQualityPassport.name());
        return new Report(
                null,
                "Паспорт качества нефти от " + SingleDateTimeFormatter.formatToSinglePattern(currentDt),
                currentDt,
                reportType
        );
    }
}
