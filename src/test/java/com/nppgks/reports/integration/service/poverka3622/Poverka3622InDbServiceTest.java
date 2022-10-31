package com.nppgks.reports.integration.service.poverka3622;

import com.nppgks.reports.dto.TagNameForOpc;
import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.ServiceIT;
import com.nppgks.reports.service.poverka3622.Poverka3622InDbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

@ServiceIT
@Sql({"classpath:sql/poverka/poverka3622.sql"})
class Poverka3622InDbServiceTest extends IntegrationBaseTest {

    private final Poverka3622InDbService poverka3622InDbService;

    @Autowired
    Poverka3622InDbServiceTest(Poverka3622InDbService poverka3622InDbService) {
        this.poverka3622InDbService = poverka3622InDbService;
    }

    @Test
    void savePoverka() {
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

        List<TagNameForOpc> initialTagNames = List.of(
                new TagNameForOpc(33, "WinCC_OA.rep_test.measureCount", "measureCount"),
                new TagNameForOpc(34, "WinCC_OA.rep_test.pointsCount", "pointsCount"),
                new TagNameForOpc(1, "WinCC_OA.rep_test.Q_ij", "Q_ij"),
                new TagNameForOpc(19, "CPM_name", "CPM_name"),
                new TagNameForOpc(77, "WinCC_OA.rep_test.t_min", "t_min")
        );

        List<TagNameForOpc> finalTagNames = List.of(
                new TagNameForOpc(35, "WinCC_OA.rep_test.K_pm", "K_pm"),
                new TagNameForOpc(36, "WinCC_OA.rep_test.M_e_ij", "M_e_ij"),
                new TagNameForOpc(41, "WinCC_OA.rep_test.K_j", "K_j")
        );

        poverka3622InDbService.setFinalDataForOpc(finalDataForOpc);
        poverka3622InDbService.setInitialDataFromOpc(initialDataFromOpc);
        poverka3622InDbService.setInitialTagNames(initialTagNames);
        poverka3622InDbService.setFinalTagNames(finalTagNames);
        poverka3622InDbService.savePoverka();
    }
}