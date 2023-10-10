package com.nppgks.reportingsystem.reportgeneration.manual_reports.kmh;

import com.nppgks.reportingsystem.constants.ManualReportTypes;
import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.ManualReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.SaveReportStrategy;
import com.nppgks.reportingsystem.util.DataRounder;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.util.ArrayParser;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KmhViscReportGenerator extends ManualReportGenerator {

    private final OpcServiceRequests opcServiceRequests;
    private final ManualTagService manualTagService;
    public KmhViscReportGenerator(@Qualifier("saveManyTimesADayStrategy") SaveReportStrategy saveReportStrategy, OpcServiceRequests opcServiceRequests, ManualTagService manualTagService) {
        super(saveReportStrategy);
        this.opcServiceRequests = opcServiceRequests;
        this.manualTagService = manualTagService;
    }

    @Override
    protected List<ReportData> generateReportDataList() {
        List<ReportData> reportDataList = new ArrayList<>();
        List<ManualTagForOpc> tags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypes.KMH_VISCOMETER.name());
        List<String> tagAddresses = DataConverter.convertTagsToListOfAddresses(tags);
        Map<String, String> tagValuesFromOpc = opcServiceRequests.getTagValuesFromOpc(tagAddresses);
        Map<String, ManualTagForOpc> addressToTagMap = DataConverter.convertTagListToMapWithAddressKey(tags);
        Map<String, String> permanentNameToAddressMap = DataConverter.createPermanentNameToAddressMap(tags);

        for(Map.Entry<String, String> addressToValue: tagValuesFromOpc.entrySet()){
            reportDataList.add(new ReportData(
                    null,
                    addressToValue.getValue(),
                    ManualTagForOpc.toTag(addressToTagMap.get(addressToValue.getKey())),
                    report
            ));
        }

        double delta_v_PVz = Double.parseDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("delta_v_PVz")));
        double delta_v_il = Double.parseDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("delta_v_il")));
        double errorRate = delta_v_PVz+delta_v_il;

        String vPVzStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("v_PVz"));
        double[] v_PVzArr = ArrayParser.toArray(vPVzStr);

        String v_ilStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("v_il"));
        double[] v_ilArr = ArrayParser.toArray(v_ilStr);

        String conclusion = "годен";

        double[] v_PVz_minus_v_il = new double[v_ilArr.length];
        for (int i = 0; i < v_ilArr.length; i++) {
            v_PVz_minus_v_il[i] = Math.abs(v_PVzArr[i] - v_ilArr[i]);
            if(v_PVz_minus_v_il[i] > errorRate){
                conclusion = "не годен";
            }
        }

        Tag vPVzMinusVIlTag = manualTagService.getTagByNameAndReportType("v_PVz_minus_v_il", ManualReportTypes.KMH_VISCOMETER.name());
        Tag conclusionTag = manualTagService.getTagByNameAndReportType("conclusion", ManualReportTypes.KMH_VISCOMETER.name());

        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(DataRounder.roundDoubleArray(v_PVz_minus_v_il)),
                vPVzMinusVIlTag,
                report
        ));

        reportDataList.add(new ReportData(
                null,
                conclusion,
                conclusionTag,
                report
        ));

        return reportDataList;
    }

    @Override
    protected Report createReport(LocalDateTime currentDt) {
        return new Report(
                null,
                "КМХ рабочего преобразователя вязкости по вискозиметру от "+ SingleDateTimeFormatter.formatToSinglePattern(currentDt),
                currentDt,
                ManualReportTypes.KMH_VISCOMETER.name()
        );
    }
}
