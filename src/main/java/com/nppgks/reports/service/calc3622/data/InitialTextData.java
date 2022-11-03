package com.nppgks.reports.service.calc3622.data;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class InitialTextData {
    private String CPM_name;
    private String CPM_number;
    private String CPM_owner;
    private String calc_method;
    private String calc_place;
    private String[] PR_name;
    private String[] PR_number;
    private String check_leakproofness;
    private String check_inspection;
    private String check_software;
    private String check_testing;
    private String inspector_position;
    private String inspector_full_name;
    private String date;
}
