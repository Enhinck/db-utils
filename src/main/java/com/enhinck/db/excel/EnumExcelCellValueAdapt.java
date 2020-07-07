package com.enhinck.db.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class EnumExcelCellValueAdapt implements IExcelCellValueAdapt {
    @Override
    public Object getCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return FieldTypeEnum.toEnum(cell.getStringCellValue());
            default:
                break;
        }
        return null;
    }
}
