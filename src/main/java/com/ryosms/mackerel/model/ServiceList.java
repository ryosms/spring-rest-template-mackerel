package com.ryosms.mackerel.model;

import lombok.Data;

import java.util.List;


/**
 * Mackerelに登録されているServiceの一覧を取得するAPIの結果
 *
 * @see <a href="https://mackerel.io/ja/api-docs/entry/services#list">https://mackerel.io/ja/api-docs/entry/services#list</a>
 */
@Data
public class ServiceList {

    private List<Service> services;

    @Data
    public static class Service {
        private String name;
        private String memo;
        private List<String> roles;
    }
}
