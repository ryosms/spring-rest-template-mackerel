package com.ryosms.mackerel.service;

import com.ryosms.mackerel.MackerelSettings;
import com.ryosms.mackerel.form.MetricForm;
import com.ryosms.mackerel.model.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
     * Organization名を取得する
     */
    public Organization loadOrganizationName() {
        HttpEntity<?> entity = new HttpEntity<>(mackerelApiHeaders());
        ResponseEntity<Organization> response = restOperations.exchange("/org", HttpMethod.GET, entity, Organization.class);
        return response.getBody();
    }

    /**
     * Organizationに登録されているServiceの一覧を返す
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

    /**
     * ServiceにMetricを投稿する
     *
     * @throws RestClientException APIアクセスで200以外が返ってきた場合にthrowする
     */
    public boolean sendMetric(String serviceName, MetricForm... metrics) {
        List<ServiceMetric> serviceMetrics = Arrays.stream(metrics)
                .map(metric -> new ServiceMetric(metric.getMetricName(), new Date(), metric.getMetricValue()))
                .collect(Collectors.toList());

        String url = String.format("/services/%s/tsdb", serviceName);
        HttpEntity<List<ServiceMetric>> request = new HttpEntity<>(serviceMetrics, mackerelApiHeaders());
        PostResult response = restOperations.postForObject(url, request, PostResult.class);
        return response.getSuccess();
    }

    private HttpHeaders mackerelApiHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Api-Key", this.mackerelSettings.getApiKey());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

}
