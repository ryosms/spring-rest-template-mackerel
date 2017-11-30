package com.ryosms.mackerel.service;

import com.ryosms.mackerel.MackerelSettings;
import com.ryosms.mackerel.model.ServiceList;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

@Service
public class MackerelService {

    private final MackerelSettings mackerelSettings;
    private final RestOperations restOperations;

    public MackerelService(MackerelSettings mackerelSettings, RestTemplateBuilder restTemplateBuilder) {
        this.mackerelSettings = mackerelSettings;
        this.restOperations = restTemplateBuilder
                .rootUri(this.mackerelSettings.getApiRoot())
                .build();
    }

    /**
     * Mackerelに登録されているServiceの一覧を返す
     *
     * @throws RestClientException APIアクセスで200が返ってこなかった場合にthrowする
     */
    public ServiceList loadServiceList() {
        HttpEntity<?> entity = new HttpEntity<>(mackerelApiHeaders());
        ResponseEntity<ServiceList> response = restOperations.exchange("/services", HttpMethod.GET, entity, ServiceList.class);
        return response.getBody();
    }

    private HttpHeaders mackerelApiHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Api-Key", this.mackerelSettings.getApiKey());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

}
