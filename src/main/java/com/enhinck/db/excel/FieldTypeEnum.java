package com.enhinck.db.excel;

import lombok.Data;
import lombok.Getter;

/**
 * 描述
 *
 * @author huenbin
 * @date 2020-07-06 14:08
 */
@Getter
public enum FieldTypeEnum {
    LONG("Long"),
    STRING("String"),
    INTEGER("Integer"),
    DATE("Date");
    private String typeValue;
    FieldTypeEnum(String typeValue) {
        this.typeValue = typeValue;
    }
    public static FieldTypeEnum toEnum(String typeValue) {
        for (FieldTypeEnum fieldTypeEnum : FieldTypeEnum.values()) {
            if (fieldTypeEnum.typeValue.equals(typeValue)) {
                return fieldTypeEnum;
            }

        }
        return STRING;
    }
}
