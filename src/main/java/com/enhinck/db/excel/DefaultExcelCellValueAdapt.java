package com.enhinck.db.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;

@Slf4j
public class DefaultExcelCellValueAdapt implements IExcelCellValueAdapt {
    @Override
    public Object getCellValue(Cell cell) {
        return getDefaultCellValue(cell);
    }
}
