package com.enhinck.db.bean;

import com.enhinck.db.excel.ExcelColumn;
import lombok.Data;

@Data
public class DictionaryVO {
    @ExcelColumn(name = "字段名")
    private String columnName;
    @ExcelColumn(name = "描述")
    private String desc;
    @ExcelColumn(name = "java类型")
    private String javaType;
    @ExcelColumn(name = "数据库类型")
    private String dbType;
}
