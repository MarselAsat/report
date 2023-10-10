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
public class KmhMoistMetReportGenerator extends ManualReportGenerator {

    private final OpcServiceRequests opcServiceRequests;
    private final ManualTagService manualTagService;
    public KmhMoistMetReportGenerator(@Qualifier("saveOnceADayStrategy") SaveReportStrategy saveReportStrategy, OpcServiceRequests opcServiceRequests, ManualTagService manualTagService) {
        super(saveReportStrategy);
        this.opcServiceRequests = opcServiceRequests;
        this.manualTagService = manualTagService;
    }

    @Override
    protected List<ReportData> generateReportDataList() {
        List<ReportData> reportDataList = new ArrayList<>();
        List<ManualTagForOpc> tags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypes.KMH_MOISTURE_METER.name());
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

        double working_mm_delta_W_kv = Double.parseDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("working_mm_delta_W_kv")));
        double working_mm_delta_W_0 = Double.parseDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("working_mm_delta_W_0")));
        double workErrorRate = working_mm_delta_W_kv+working_mm_delta_W_0;

        double reserve_mm_delta_W_kv = Double.parseDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("reserve_mm_delta_W_kv")));
        double reserve_mm_delta_W_0 = Double.parseDouble(tagValuesFromOpc.get(permanentNameToAddressMap.get("reserve_mm_delta_W_0")));
        double reserveErrorRate = reserve_mm_delta_W_kv+reserve_mm_delta_W_0;

        String work_mm_W_kvStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("work_mm_W_kv"));
        double[] work_mm_W_kvArr = ArrayParser.toArray(work_mm_W_kvStr);

        String reserve_mm_W_kvStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("reserve_mm_W_kv"));
        double[] reserve_mm_W_kvArr = ArrayParser.toArray(reserve_mm_W_kvStr);

        String W_0Str = tagValuesFromOpc.get(permanentNameToAddressMap.get("W_0"));
        double[] W_0Arr = ArrayParser.toArray(W_0Str);

        double[] work_mm_W_id = new double[W_0Arr.length];
        double[] reserve_mm_W_id = new double[W_0Arr.length];
        String working_mm_conclusion = "годен";
        String reserve_mm_conclusion = "годен";

        for (int i = 0; i < W_0Arr.length; i++) {
            work_mm_W_id[i] = Math.abs(work_mm_W_kvArr[i] - W_0Arr[i]);
            reserve_mm_W_id[i] = Math.abs(reserve_mm_W_kvArr[i] - W_0Arr[i]);
            if(work_mm_W_id[i] > workErrorRate){
                working_mm_conclusion = "не годен";
            }
            if(reserve_mm_W_id[i] > reserveErrorRate){
                reserve_mm_conclusion = "не годен";
            }
        }

        Tag work_mm_W_idTag = manualTagService.getTagByNameAndReportType("work_mm_W_id", ManualReportTypes.KMH_MOISTURE_METER.name());
        Tag reserve_mm_W_idTag = manualTagService.getTagByNameAndReportType("reserve_mm_W_id", ManualReportTypes.KMH_MOISTURE_METER.name());
        Tag workingConclusionTag = manualTagService.getTagByNameAndReportType("working_mm_conclusion", ManualReportTypes.KMH_MOISTURE_METER.name());
        Tag reserveConclusionTag = manualTagService.getTagByNameAndReportType("reserve_mm_conclusion", ManualReportTypes.KMH_MOISTURE_METER.name());

        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(DataRounder.roundDoubleArray(reserve_mm_W_id)),
                reserve_mm_W_idTag,
                report
        ));

        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(DataRounder.roundDoubleArray(work_mm_W_id)),
                work_mm_W_idTag,
                report
        ));

        reportDataList.add(new ReportData(
                null,
                working_mm_conclusion,
                workingConclusionTag,
                report
        ));

        reportDataList.add(new ReportData(
                null,
                reserve_mm_conclusion,
                reserveConclusionTag,
                report
        ));

        return reportDataList;
    }

    @Override
    protected Report createReport(LocalDateTime currentDt) {
        return new Report(
                null,
                "КМХ рабочего и резервного влагомеров по лабораторным измерениям от "+ SingleDateTimeFormatter.formatToSinglePattern(currentDt),
                currentDt,
                ManualReportTypes.KMH_MOISTURE_METER.name()
        );
    }
}
