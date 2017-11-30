package com.ryosms.mackerel.model;

import lombok.Data;

import java.util.List;

/**
 * メトリック名の一覧を取得するAPIの結果
 *
 * @see <a href="https://mackerel.io/ja/api-docs/entry/services#metric-names">https://mackerel.io/ja/api-docs/entry/services#metric-names</a>
 */
@Data
public class MetricNames {
    private List<String> names;
}
