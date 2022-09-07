package com.nppgks.reports.entity;

import lombok.Value;

@Value
public class ReportViewTagData {
    String description;
    Integer sikn;
    Integer il1;
    Integer il2;
    Integer il3;
    Integer il4;
    Integer bik;

    public static ReportViewTagData fromIReportViewTagData(IReportViewTagData interfaceObject){
        return new ReportViewTagData(
                interfaceObject.getDescription(),
                interfaceObject.getSikn(),
                interfaceObject.getIl1(),
                interfaceObject.getIl2(),
                interfaceObject.getIl3(),
                interfaceObject.getIl4(),
                interfaceObject.getBik()
        );
    }
}
