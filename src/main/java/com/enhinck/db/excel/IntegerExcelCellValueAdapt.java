package com.enhinck.db.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;

@Slf4j
public class IntegerExcelCellValueAdapt implements IExcelCellValueAdapt {
    @Override
    public Object getCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return Integer.valueOf(cell.getStringCellValue());
            case NUMERIC:
                return Integer.valueOf(cell.getStringCellValue());
            default:
                break;
        }
        return cell.getNumericCellValue();
    }
}
