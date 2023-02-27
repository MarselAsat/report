package com.nppgks.reportingsystem.integration.service.calc_method_3622;

import com.nppgks.reportingsystem.dto.calc.CalcTagNameForOpc;
import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.integration.annotation.ServiceIT;
import com.nppgks.reportingsystem.reportgeneration.calculations.mi3622.MI3622InDbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

@ServiceIT
@Sql({"classpath:sql/calculation/calc-method-3622.sql"})
class MI3622InDbServiceTest extends IntegrationBaseTest {

    private final MI3622InDbService MI3622InDbService;

    @Autowired
    MI3622InDbServiceTest(MI3622InDbService MI3622InDbService) {
        this.MI3622InDbService = MI3622InDbService;
    }

    @Test
    void saveCalculations() {
        Map<String, Object> finalDataForOpc = Map.of(
                "WinCC_OA.rep_test.K_pm", "99.01",
                "WinCC_OA.rep_test.M_e_ij", "[[1.0,2.0,3.0],[4.0,5.0,6.0]]",
                "WinCC_OA.rep_test.K_j", "[1.0,2.0,3.0]"
        );
        Map<String, String> initialDataFromOpc = Map.of(
                "WinCC_OA.rep_test.measureCount", "2",
                "WinCC_OA.rep_test.pointsCount", "3",
                "WinCC_OA.rep_test.Q_ij", "[[1.0,2.0,3.0],[4.0,5.0,6.0]]",
                "CPM_name", "NameNameName",
                "WinCC_OA.rep_test.t_min", "12.56"
        );

        List<CalcTagNameForOpc> initialTagNames = List.of(
                new CalcTagNameForOpc(33, "WinCC_OA.rep_test.measureCount", "measureCount"),
                new CalcTagNameForOpc(34, "WinCC_OA.rep_test.pointsCount", "pointsCount"),
                new CalcTagNameForOpc(1, "WinCC_OA.rep_test.Q_ij", "Q_ij"),
                new CalcTagNameForOpc(19, "CPM_name", "CPM_name"),
                new CalcTagNameForOpc(77, "WinCC_OA.rep_test.t_min", "t_min")
        );

        List<CalcTagNameForOpc> finalTagNames = List.of(
                new CalcTagNameForOpc(35, "WinCC_OA.rep_test.K_pm", "K_pm"),
                new CalcTagNameForOpc(36, "WinCC_OA.rep_test.M_e_ij", "M_e_ij"),
                new CalcTagNameForOpc(41, "WinCC_OA.rep_test.K_j", "K_j")
        );

        MI3622InDbService.setFinalDataForOpc(finalDataForOpc);
        MI3622InDbService.setInitialDataFromOpc(initialDataFromOpc);
        MI3622InDbService.setInitialTagNames(initialTagNames);
        MI3622InDbService.setFinalTagNames(finalTagNames);
        MI3622InDbService.saveCalculations();
    }
}