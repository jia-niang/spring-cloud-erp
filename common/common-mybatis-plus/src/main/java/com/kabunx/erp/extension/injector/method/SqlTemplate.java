package com.kabunx.erp.extension.injector.method;

public enum SqlTemplate {
    SOLE("sole", "确定且唯一", "<script>%s SELECT %s FROM %s %s %s\n</script>");

    private final String method;
    private final String description;
    private final String sql;

    SqlTemplate(String method, String description, String sql) {
        this.method = method;
        this.description = description;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDescription() {
        return description;
    }

    public String getSql() {
        return sql;
    }
}
