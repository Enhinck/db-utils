package com.enhinck.db.entity;

import lombok.Data;

@Data
public class ExcelCell {
    private String backgroudColor;
    private String columnName;
    private ColumnTypeEnum columnType = ColumnTypeEnum.STRING;
    private Object columnValue;

    public ExcelCell() {

    }

    public ExcelCell(String value) {
        columnValue = value;
    }
}
