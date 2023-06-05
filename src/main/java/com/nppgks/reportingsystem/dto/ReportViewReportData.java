package com.nppgks.reportingsystem.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ReportViewReportData(
        int order,
        String description,
        Map<String, Double> meteringNodeValues) implements Comparable<ReportViewReportData> {

    @Override
    public int compareTo(ReportViewReportData o) {
        return Integer.compare(order(), o.order());
    }

    public static List<ReportViewReportData> fromIReportViewReportData(List<IReportViewReportData> iReportDataList) {
        Map<String, Integer> uniqueDescriptionsToOrder = iReportDataList.stream()
                .collect(Collectors.toMap(IReportViewReportData::getDescription, IReportViewReportData::getOrder, (p, q) -> p));

        List<ReportViewReportData> reportDataList = new ArrayList<>();
        for(Map.Entry<String, Integer> uniqueDescToOrder: uniqueDescriptionsToOrder.entrySet()){
            Map<String, Double> nodeToValue = iReportDataList.stream()
                    .filter(rd -> rd.getDescription().equals(uniqueDescToOrder.getKey()))
                    .collect(Collectors.toMap(IReportViewReportData::getNodeName, IReportViewReportData::getValue));
            ReportViewReportData reportData = new ReportViewReportData(
                    uniqueDescToOrder.getValue(),
                    uniqueDescToOrder.getKey(),
                    nodeToValue
            );
            reportDataList.add(reportData);
        }
        Collections.sort(reportDataList);
        return reportDataList;
    }
}
