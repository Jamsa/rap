package com.github.jamsa.rap.meta.model;

public class SqlAndParamValues {
    private String sql;
    private Object[] params;

    public SqlAndParamValues(String sql, Object[] params) {
        this.sql = sql;
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public Object[] getParams() {
        return params;
    }
}
