package com.enhinck.db.excel;

import lombok.Data;

import java.util.UUID;

@Data
public class DictionaryTemplate {
    public static final String COLUMN_NAME = "字段名";
    public static final String COLUMN_TYPE = "字段类型";
    public static final String COLUMN_IS_NEED = "是否必填";
    public static final String COLUMN_COMMIT = "描述";
    @ExcelColumn(name = COLUMN_NAME)
    public String columnName;
    @ExcelColumn(name = COLUMN_TYPE)
    public String columnType;
    @ExcelColumn(name = COLUMN_IS_NEED)
    public String columnNeed;
    @ExcelColumn(name = COLUMN_COMMIT)
    public String columnCommit;

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
    }
}
