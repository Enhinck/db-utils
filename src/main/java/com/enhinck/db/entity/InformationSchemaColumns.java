package com.enhinck.db.entity;


import com.enhinck.db.annotation.ETable;
import lombok.Data;

import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * 字段信息表
 */
@Data
@ETable("information_schema.columns")
public class InformationSchemaColumns {
    private String columnName;
    private String columnType;
    private String isNullable;
    private String columnComment;
    private String columnDefault;
    private String extra;
    private BigInteger ordinalPosition;

    public static final Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
    public static final String YES = "YES";
    public static final String NO = "NO";
    public static final String NULL = "NULL";
    public static final String NOT_NULL = "NOT NULL";
    public static final String DEFAULT_NULL = "DEFAULT NULL";
    public static final String DEFAULT = "DEFAULT ";
    public static final String CURRENT = "CURRENT";


    public static boolean isInteger(String str) {
        return pattern.matcher(str).matches();
    }

    public String isNullable() {
        if (YES.equals(isNullable)) {
            return NULL;
        } else if (NO.equals(isNullable)) {
            return NOT_NULL;
        }
        return "";
    }

    public String excelCanNo() {
        if (YES.equals(isNullable)) {
            return "否";
        } else if (NO.equals(isNullable)) {
            return "是";
        }
        return "";
    }


    public String getColumnDefault() {
        if (columnDefault == null) {
            if (NO.equals(isNullable)) {
                return "";
            }
            return DEFAULT_NULL;
        }
        if (isInteger(columnDefault) || columnDefault.contains(CURRENT)) {
            return DEFAULT + columnDefault;
        }
        return DEFAULT + "'" + columnDefault + "'";
    }

}
