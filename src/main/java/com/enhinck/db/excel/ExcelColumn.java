package com.enhinck.db.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
    String name() default "";

    int row() default 0;

    int cell() default 0;

    ColumnType columnType() default ColumnType.CELL;

    Class<? extends IExcelCellValueAdapt> valueAdapt() default DefaultExcelCellValueAdapt.class;

    enum ColumnType {
        CELL, LIST
    }
}
