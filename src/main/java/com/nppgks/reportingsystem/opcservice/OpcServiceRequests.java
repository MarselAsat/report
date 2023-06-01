package com.nppgks.reportingsystem.opcservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OpcServiceRequests {

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final URI uriCheckConnection;
    private final URI uriReconnect;
    private final URI uriRead;
    private final URI uriWrite;

    public OpcServiceRequests(@Value("${opc.service.host}") String host, @Value("${opc.service.port}") String port) {

        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String pathTestConnection = "/opc/test-connection";
        final String pathReconnect = "/opc/reconnect";
        final String pathRead = "/opc/read";
        final String pathWrite = "/opc/write";

        uriCheckConnection = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path(pathTestConnection).build().toUri();
        uriReconnect = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path(pathReconnect).build().toUri();
        uriRead = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path(pathRead).build().toUri();
        uriWrite = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path(pathWrite).build().toUri();
    }

    public Map<String, String> getTagDataFromOpc(List<String> tagNames) {
        HttpEntity<List<String>> entity = new HttpEntity<>(tagNames, headers);
        return restTemplate.postForObject(uriRead, entity, HashMap.class);
    }

    public ResponseEntity<String> getTagDataFromOpc(String tagName) {
        HttpEntity<List<String>> entity = new HttpEntity<>(List.of(tagName), headers);
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(uriRead, entity, Map.class);
        String noData = "No data";
        return new ResponseEntity<>( (String) responseEntity.getBody().getOrDefault(tagName, noData), HttpStatus.OK);
    }

    public boolean testOpcServerConnection() {
        return restTemplate.getForObject(uriCheckConnection, Boolean.class);
    }

    public void sendTagDataToOpc(Map<String, Object> data) {
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);
        restTemplate.postForObject(uriWrite, entity, Boolean.class);
    }

    public boolean reconnectToOpcServer() {
        return restTemplate.postForObject(uriReconnect, new HttpEntity<>(headers), Boolean.class);
    }
}
