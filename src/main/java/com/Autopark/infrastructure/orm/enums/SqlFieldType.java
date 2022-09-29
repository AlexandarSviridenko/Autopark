package com.Autopark.infrastructure.orm.enums;

import lombok.Getter;

import java.util.Date;

@Getter
public enum SqlFieldType {
    INTEGER(Integer.class, "integer", "%s"),
    LONG(Long.class, "integer", "%s"),
    DOUBLE(Double.class, "decimal", "%s"),
    STRING(String.class, "varchar(255)", "'%s'"),
    DATE(Date.class, "date", "'%s'");
    private final Class<?> type;
    private final String sqlType;
    private final String insertPattern;

    SqlFieldType(Class<?> type, String sqlType, String insertPattern) {
        this.type = type;
        this.sqlType = sqlType;
        this.insertPattern = insertPattern;
    }

    public Class<?> getType() {
        return type;
    }

    public String getSqlType() {
        return sqlType;
    }

    public String getInsertPattern() {
        return insertPattern;
    }
}
