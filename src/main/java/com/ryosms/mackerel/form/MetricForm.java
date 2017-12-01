package com.ryosms.mackerel.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class MetricForm {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$")
    private String metricName;

    @NotNull
    private Double metricValue;

}
