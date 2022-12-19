package com.nppgks.reportingsystem.dto;

import lombok.Value;

@Value
public class ReportViewTagData {
    String description;
    Double sikn;
    Double il1;
    Double il2;
    Double il3;
    Double il4;
    Double bik;

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
