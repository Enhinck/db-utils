package com.enhinck.db.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateExcelCellValueAdapt implements IExcelCellValueAdapt {
    @Override
    public Object getCellValue(Cell cell) {
        Date date = null;
        switch (cell.getCellTypeEnum()) {
            case STRING:
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    date = simpleDateFormat.parse(cell.getStringCellValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case NUMERIC:
                double numericCellValue = cell.getNumericCellValue();
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    date = new Date(
                            (HSSFDateUtil.getJavaDate(numericCellValue))
                                    .getTime());

                    return getDefaultCellValue(cell);
                }
                break;
            default:
                break;
        }
        return date;
    }
}
