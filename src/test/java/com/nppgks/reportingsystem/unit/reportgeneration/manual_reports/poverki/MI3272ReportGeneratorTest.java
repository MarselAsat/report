package com.nppgks.reportingsystem.unit.reportgeneration.manual_reports.poverki;

import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.MI3272ReportGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MI3272ReportGeneratorTest {

    @Test
    void mergeInitialMaps(){
        Map<String, String> map1 = new HashMap<>(
                Map.of("key1", "[value1]",
                "key2", "[value2]"));
        Map<String, String> map2 = new HashMap<>(
                Map.of("key1", "[value2]",
                        "key3", "value3",
                        "key2", "[value2]"));
        Map<String, String> actual = MI3272ReportGenerator.mergeInitialMaps(map1, map2);
        assertThat(actual).hasSize(3)
                .containsEntry("key1", "[value1]#[value2]")
                .containsEntry("key2", "[value2]#[value2]")
                .containsEntry("key3", "value3");
    }
}
