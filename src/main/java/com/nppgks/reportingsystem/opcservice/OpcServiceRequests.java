package com.nppgks.reportingsystem.opcservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OpcServiceRequests {

    private URI uriCheckConnection;
    private URI uriRead;
    private URI uriReadSingle;
    private URI uriWrite;

    public OpcServiceRequests(@Value("${opc.service.host}") String host, @Value("${opc.service.port}") String port) {

        final String pathCheckConnection = "/opc/checkConnection";
        final String pathRead = "/opc/read";
        final String pathReadSingle = "/opc/readOne";
        final String pathWrite = "/opc/write";

        uriCheckConnection = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path(pathCheckConnection).build().toUri();
        uriRead = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path(pathRead).build().toUri();
        uriReadSingle = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path(pathReadSingle).build().toUri();
        uriWrite = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path(pathWrite).build().toUri();
    }

    public Map<String, String> getTagDataFromOpc(List<String> tagNames) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<String>> entity = new HttpEntity<>(tagNames, headers);
        return restTemplate.postForObject(uriRead, entity, HashMap.class);
    }

    public String getTagDataFromOpc(String tagName) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(tagName, headers);
        return restTemplate.postForObject(uriReadSingle, entity, String.class);
    }

    public boolean checkOpcServerConnection(){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.getForObject(uriCheckConnection, Boolean.class);
    }

    public void sendTagDataToOpc(Map<String, Object> data) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);
        restTemplate.postForObject(uriWrite, entity, HashMap.class);
    }
}
