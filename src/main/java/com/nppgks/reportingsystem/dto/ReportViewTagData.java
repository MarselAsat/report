package com.nppgks.reportingsystem.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ReportViewTagData(
        int order,
        String description,
        Map<String, Double> meteringNodeValues) implements Comparable<ReportViewTagData> {

    @Override
    public int compareTo(ReportViewTagData o) {
        return Integer.compare(order(), o.order());
    }

    public static List<ReportViewTagData> fromIReportViewTagData(List<IReportViewTagData> iTagDataList) {
        Map<String, Integer> uniqueDescriptionsToOrder = iTagDataList.stream()
                .collect(Collectors.toMap(IReportViewTagData::getDescription, IReportViewTagData::getOrder, (p, q) -> p));

        List<ReportViewTagData> tagDataList = new ArrayList<>();
        for(Map.Entry<String, Integer> uniqueDescToOrder: uniqueDescriptionsToOrder.entrySet()){
            Map<String, Double> nodeToValue = iTagDataList.stream()
                    .filter(td -> td.getDescription().equals(uniqueDescToOrder.getKey()))
                    .collect(Collectors.toMap(IReportViewTagData::getNodeName, IReportViewTagData::getValue));
            ReportViewTagData tagData = new ReportViewTagData(
                    uniqueDescToOrder.getValue(),
                    uniqueDescToOrder.getKey(),
                    nodeToValue
            );
            tagDataList.add(tagData);
        }
        Collections.sort(tagDataList);
        return tagDataList;
    }
}
