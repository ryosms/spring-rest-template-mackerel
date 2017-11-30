package com.ryosms.mackerel.model;

import lombok.Data;

/**
 * MackerelにPOSTした際の結果（汎用）
 *
 * @see <a href="https://mackerel.io/ja/api-docs/entry/service-metrics#post">https://mackerel.io/ja/api-docs/entry/service-metrics#post</a>
 */
@Data
public class PostResult {
    private Boolean success;
}
