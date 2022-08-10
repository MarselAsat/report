package com.nppgks.reports.integration.repository;

import com.nppgks.reports.opc.OpcRequests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class SendDataToOpcTest {

    @Autowired
    OpcRequests opcRequests;


    @Test
    void send(){
        double val1 =2;
        double[] val2 = new double[]{1, 2};
        double[][] val3 = new double[][]{{45, 56, 78},{34, 45, 56}};
        Map<String, Object> map = Map.of("tag1", val1, "tag2", val2, "tag3", val3);
        opcRequests.sendTagDataToOpc(map);

    }
}
