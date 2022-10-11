package com.nppgks.reports.opc;

import com.nppgks.reports.integration.IntegrationBaseTest;
import com.nppgks.reports.integration.annotation.ServiceIT;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

@ServiceIT
class OpcRequestsTest extends IntegrationBaseTest {
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
        System.out.println();
    }
}