package com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki;

import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.DataConverter;
import com.nppgks.reportingsystem.util.ArrayParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportGeneratorHelper {
    public static List<ReportData> createListOfReportData(
            List<ManualTagForOpc> initialTags,
            List<ManualTagForOpc> finalTags,
            Map<String, String> initialDataFromOpc,
            Map<String, Object> finalDataForOpc,
            Report report) {

        Map<String, ManualTagForOpc> addressToinitialTagMap = DataConverter.convertTagListToMapWithAddressKey(initialTags);
        Map<String, ManualTagForOpc> addressTofinalTagMap = DataConverter.convertTagListToMapWithAddressKey(finalTags);

        List<ReportData> reportDataList = new ArrayList<>();

        for (Map.Entry<String, String> entry : initialDataFromOpc.entrySet()) {
            String value = entry.getValue();
            ReportData reportData = new ReportData(
                    null,
                    value,
                    ManualTagForOpc.toTag(addressToinitialTagMap.get(entry.getKey())),
                    report
            );
            reportDataList.add(reportData);
        }

        for (Map.Entry<String, Object> entry : finalDataForOpc.entrySet()) {
            String value = ArrayParser.fromObjectToJson(entry.getValue());
            ReportData reportData = new ReportData(
                    null,
                    value,
                    ManualTagForOpc.toTag(addressTofinalTagMap.get(entry.getKey())),
                    report
            );
            reportDataList.add(reportData);
        }
        return reportDataList;
    }
}
