package com.enhinck.db.entity;

import lombok.Getter;

/**
 * 字段类型
 *
 * @author
 */
@Getter
public enum ColumnTypeEnum {
    // 文本
    STRING("String", "文本"),
    // 文本
    NUMBER("NUMBER", "数值"),
    // 日期
    DATE("Date", "日期");
    private String value;
    private String desc;

    ColumnTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
