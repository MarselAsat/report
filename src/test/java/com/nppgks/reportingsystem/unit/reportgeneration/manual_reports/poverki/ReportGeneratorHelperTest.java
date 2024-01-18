package com.nppgks.reportingsystem.unit.reportgeneration.manual_reports.poverki;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.ReportGeneratorHelper;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.TprCoeffInitData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReportGeneratorHelperTest {

    @Test
    void tagNameMatchesAnyDataField(){
        boolean actual = ReportGeneratorHelper.tagNameMatchesAnyDataField("rho_TPR_ij", TprCoeffInitData.class);
        Assertions.assertThat(actual).isTrue();
        boolean actual2 = ReportGeneratorHelper.tagNameMatchesAnyDataField("bembem", TprCoeffInitData.class);
        Assertions.assertThat(actual2).isFalse();
    }
}
