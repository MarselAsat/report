package com.nppgks.reportingsystem.unit.reportgeneration.poverki;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3313.MI3313ReportGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MI3313ReportGeneratorTest {

    @Test
    void assembleEsrmTagsInOne(){
        Map<String, String> opcValues = Map.of("Q_ji1", "[2, 3, 4]",
                "N_ji1", "[1, 2, 3]",
                "Q_ji2", "[6, 7, 8]",
                "N_ji2", "[5, 6, 7]",
                "T_ji", "[1, 2, 3]");
        Map<String, String> actual = MI3313ReportGenerator.groupTagValuesByEsrm(opcValues);
        Map<String, String> expected = Map.of("Q_jik", "[[2, 3, 4],[6, 7, 8]]",
                "N_jik", "[[1, 2, 3],[5, 6, 7]]",
                "T_ji", "[1, 2, 3]");
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void addAddressesForMultipleEsrm(){
        List<String> addresses = new ArrayList<>(List.of("Q_jik", "N_jik", "T_ji"));
        MI3313ReportGenerator.addAddressesForMultipleEsrm(addresses);
        Assertions.assertThat(addresses)
                .hasSize(21)
                .contains("Q_ji4", "Q_ji5", "Q_ji10", "T_ji", "N_ji1", "N_ji10")
                        .doesNotContain("Q_ji11", "N_ji0");
    }
}
