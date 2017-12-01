package com.ryosms.mackerel.service;

import com.ryosms.mackerel.MackerelSettings;
import com.ryosms.mackerel.model.MetricNames;
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
     * @throws RestClientException APIアクセスで200以外が返ってきた場合にthrowする
     */
    public ServiceList loadServiceList() {
        HttpEntity<?> entity = new HttpEntity<>(mackerelApiHeaders());
        ResponseEntity<ServiceList> response = restOperations.exchange("/services", HttpMethod.GET, entity, ServiceList.class);
        return response.getBody();
    }

    /**
     * Serviceに登録されているMetricの一覧を返す
     *
     * @param serviceName 取得対象のService名
     * @throws RestClientException APIアクセスで200以外が返ってきた場合にthrowする
     */
    public MetricNames loadMetricList(String serviceName) {
        HttpEntity<?> entity = new HttpEntity<>(mackerelApiHeaders());
        String url = String.format("/services/%s/metric-names", serviceName);
        ResponseEntity<MetricNames> response = restOperations.exchange(url, HttpMethod.GET, entity, MetricNames.class);
        return response.getBody();
    }

    private HttpHeaders mackerelApiHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Api-Key", this.mackerelSettings.getApiKey());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

}
