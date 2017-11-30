package com.ryosms.mackerel.model;

import lombok.Data;

import java.util.Date;

/**
 * Mackerelに投稿するサービスメトリック用モデルクラス
 *
 * @see <a href="https://mackerel.io/ja/api-docs/entry/service-metrics#post">https://mackerel.io/ja/api-docs/entry/service-metrics#post</a>
 */
@Data
public class ServiceMetric {
    private String name;
    private long time;
    private double value;

    private void setTime(Date date) {
        this.time = date.getTime();
    }
}
