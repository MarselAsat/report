package com.nppgks.reportingsystem.integration.opc;

import com.nppgks.reportingsystem.opc.OpcRequests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

//@Disabled("OPC server is not available now")
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
                List.of("daily_mass_gross_bik", "nonexistent_tag"));
        Assertions.assertThat(tagDataFromOpc).hasSize(1);
    }

    @Test
    void sendTagDataToOpc(){
        double val1 = 2;
        double[] val2 = new double[]{1, 2};
        double[][] val3 = new double[][]{{45, 56, 78},{34, 45, 56}};
        Map<String, Object> map = Map.of("WinCC_OA.rep_test.MF_ij", val3, "WinCC_OA.rep_test.K_pm", val1);
        opcRequests.sendTagDataToOpc(map);
    }
}