package com.nppgks.reportingsystem.integration.opcservice;

import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.util.ArrayParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

//@Disabled("OPC service is not available now")
@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class OpcServiceRequestsTest {
    private final OpcServiceRequests opcServiceRequests;

    @Autowired
    OpcServiceRequestsTest(OpcServiceRequests opcServiceRequests) {
        this.opcServiceRequests = opcServiceRequests;
    }

    @Test
    void checkConnection() {
        boolean isReachable = opcServiceRequests.testOpcServerConnection();
        assertThat(isReachable).isTrue();
    }

    @Test
    void getTagDataFromOpc() {
        Map<String, String> tagDataFromOpc = opcServiceRequests.getTagDataFromOpc(
                List.of("daily_mass_gross_bik", "nonexistent_tag"));
        assertThat(tagDataFromOpc).hasSize(1);
    }

    @Test
    void getSingleTagDataFromOpc() {
        ResponseEntity<String> tagDataFromOpc = opcServiceRequests.getTagDataFromOpc(
                "opc.tag.name");
        assertThat(tagDataFromOpc).isNotNull();
    }

    @Test
    void sendTagDataToOpcDa() {
        double val1 = 2;
        double[] val2 = new double[]{1, 2};
        double[][] val3 = new double[][]{{45, 56, 78}, {34, 45, 56}};
        Map<String, Object> map = Map.of("WinCC_OA.rep_test.MF_ij", val3, "WinCC_OA.rep_test.K_pm", val1);
        opcServiceRequests.sendTagDataToOpc(map);
    }

    @Test
    void sendTagDataToOpcUa() {
        int val1 = 2;
        double[] val2 = new double[]{1, 2};
        double[][] val3 = new double[][]{{45, 56, 78}, {34, 45, 56}};
        Map<String, Object> map = Map.of("ns=0;i=50240", val1, "ns=0;i=50251", val2, "ns=0;i=50242", val3);
        opcServiceRequests.sendTagDataToOpc(map);
        ResponseEntity<String> val1FromOpc = opcServiceRequests.getTagDataFromOpc("ns=0;i=50240");
        ResponseEntity<String> val2FromOpc = opcServiceRequests.getTagDataFromOpc("ns=0;i=50251");
        ResponseEntity<String> val3FromOpc = opcServiceRequests.getTagDataFromOpc("ns=0;i=50242");
        System.out.println();
        assertThat(Integer.parseInt(val1FromOpc.getBody())).isEqualTo(val1);
        assertThat(ArrayParser.toArray(val2FromOpc.getBody())).isEqualTo(val2);
        assertThat(ArrayParser.to2DArray(val3FromOpc.getBody())).isEqualTo(val3);
    }
}