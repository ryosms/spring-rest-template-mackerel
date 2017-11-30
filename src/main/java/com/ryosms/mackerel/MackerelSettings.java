package com.ryosms.mackerel;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mackerel")
public class MackerelSettings {
    private String apiRoot;
    private String apiKey;
}
