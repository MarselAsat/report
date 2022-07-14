package com.nppgks.reports.scheduled_components;

import lombok.NoArgsConstructor;
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

    private String host;

    private String port;
    private final String path="/opc/read";

    private URI uri;

    public OpcRequests(@Value("${opc.host}") String host, @Value("${opc.port}") String port) {
        this.host = host;
        this.port = port;
        uri = UriComponentsBuilder.newInstance()
                .scheme("http").host(host).port(port).path(path).build().toUri();
    }

    public Map<String, String> getTagDataFromOpc(List<String> tagNames){
        RestTemplate restTemplate = new RestTemplate();

        List<String> requestJson = tagNames;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<String>> entity = new HttpEntity<>(requestJson, headers);
        Map<String, String> map = restTemplate.postForObject(uri, entity, HashMap.class);
        return map;
    }
}
