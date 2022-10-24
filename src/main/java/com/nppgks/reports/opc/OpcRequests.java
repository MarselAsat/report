package com.nppgks.reports.opc;

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
public class OpcRequests {

    private URI uriRead;
    private URI uriWrite;

    public OpcRequests(@Value("${opc.host}") String host, @Value("${opc.port}") String port) {
        final String pathRead ="/opc/read";
        final String pathWrite ="/opc/write";

        uriRead = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path(pathRead).build().toUri();
        uriWrite = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path(pathWrite).build().toUri();
    }

    public Map<String, String> getTagDataFromOpc(List<String> tagNames){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<String>> entity = new HttpEntity<>(tagNames, headers);
        return restTemplate.postForObject(uriRead, entity, HashMap.class);
    }

    public void sendTagDataToOpc(Map<String, Object> data){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);
        restTemplate.postForObject(uriWrite, entity, HashMap.class);
    }
}
