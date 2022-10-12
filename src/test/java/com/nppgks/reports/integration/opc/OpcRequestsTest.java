package com.nppgks.reports.integration.opc;

import com.nppgks.reports.opc.OpcRequests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest(properties="spring.main.lazy-initialization=true")
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class OpcRequestsTest {
    private final OpcRequests opcRequests;

    @Autowired
    OpcRequestsTest(OpcRequests opcRequests) {
        this.opcRequests = opcRequests;
    }

    @Test
    void getTagDataFromOpc() {
        Map<String, String> tagDataFromOpc = opcRequests.getTagDataFromOpc(
                List.of("WinCC_OA.report.day.1", "WinCC_OA.report.day.100"));
        Assertions.assertThat(tagDataFromOpc).hasSize(1);
    }
}