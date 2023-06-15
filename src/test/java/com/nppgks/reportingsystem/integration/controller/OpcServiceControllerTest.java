package com.nppgks.reportingsystem.integration.controller;

import com.nppgks.reportingsystem.integration.IntegrationBaseTest;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResourceAccessException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
//@WebMvcTest(OpcServiceController.class)
@SpringBootTest
@WithMockUser(username = "testName", password = "test", authorities = "ROLE_ADMIN")
public class OpcServiceControllerTest extends IntegrationBaseTest{

    private final MockMvc mockMvc;

    @MockBean
    private final OpcServiceRequests opcServiceRequests;

    @Autowired
    public OpcServiceControllerTest(MockMvc mockMvc, OpcServiceRequests opcServiceRequests) {
        this.mockMvc = mockMvc;
        this.opcServiceRequests = opcServiceRequests;
    }

    @Test
    void testConnectionShouldReturnTrueWhenOpcServerOn() throws Exception {
        Mockito.when(opcServiceRequests.testOpcServerConnection()).thenReturn(true);
        mockMvc.perform(get("/opcService/testOpcServerConnection"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("true"));
    }

    @Test
    void testConnectionShouldReturnErrorMessageWhenOpcServiceOff() throws Exception {
        Mockito.when(opcServiceRequests.testOpcServerConnection()).thenThrow(ResourceAccessException.class);
        mockMvc.perform(get("/opcService/testOpcServerConnection"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().string("OPC сервис недоступен"));
    }

    @Test
    void reconnectShouldReturnTrueWhenOpcServerOn() throws Exception {
        Mockito.when(opcServiceRequests.reconnectToOpcServer()).thenReturn(true);
        mockMvc.perform(get("/opcService/reconnect"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("true"));
    }

    @Test
    void reconnectShouldReturnErrorMessageWhenOpcServiceOff() throws Exception {
        Mockito.when(opcServiceRequests.reconnectToOpcServer()).thenThrow(ResourceAccessException.class);
        mockMvc.perform(get("/opcService/reconnect"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().string("OPC сервис недоступен"));
    }
}
