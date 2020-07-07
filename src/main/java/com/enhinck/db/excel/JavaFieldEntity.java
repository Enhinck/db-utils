package com.enhinck.db.excel;

import com.enhinck.db.util.SqlUtil;
import lombok.Data;

/**
 * 描述
 *
 * @author huenbin
 * @date 2020-07-06 14:07
 */
@Data
public class JavaFieldEntity {
    /**
     * 字段名
     */
    @ExcelColumn(name = "字段名")
    private String name;
    /**
     * 字段类型
     */
    @ExcelColumn(name = "类型", valueAdapt = EnumExcelCellValueAdapt.class)
    private FieldTypeEnum fieldType;
    /**
     * 长度
     */
    @ExcelColumn(name = "长度", valueAdapt = IntegerExcelCellValueAdapt.class)
    private Integer length;
    /**
     * 描述
     */
    @ExcelColumn(name = "描述")
    private String describe;


    public String importClass() {
        switch (fieldType) {
            case DATE:
                return "java.util.Date";
            default:
                break;
        }
        return null;
    }

    public String javaFieldName() {
        return SqlUtil.underScoreCaseToCamelCase(name);
    }

    public String dbColumnName() {
        return SqlUtil.camelCaseToUnderScoreCase(name);
    }

    public String javaType() {
        return fieldType.getTypeValue();
    }

    public String dbType() {
        switch (fieldType) {
            case STRING:
                if (length > 255) {
                    return "text";
                }
                return "varchar(" + length + ")";
            case LONG:
                return "bigint(20)";
            case INTEGER:
                return "int(11)";
            case DATE:
                return "datetime";
            default:
                return "varchar(50)";
        }
    }

    public boolean isId() {
        return "id".equalsIgnoreCase(name);
    }
    public boolean isDelFlag() {
        return "delFlag".equalsIgnoreCase(javaFieldName());
    }
}
