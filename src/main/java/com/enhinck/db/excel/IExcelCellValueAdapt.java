package com.enhinck.db.excel;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

public interface IExcelCellValueAdapt {
    public static final String STRING_FORMATE = "@";

    default Object getDefaultCellValue(Cell cell) {
        Object result = null;
        try {
            // 如果是自定义文本类型 直接返回文本内容
            String dataFormatString = cell.getCellStyle().getDataFormatString();
            if (STRING_FORMATE.equals(dataFormatString)) {
                return cell.getStringCellValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (cell.getCellTypeEnum()) {
            case STRING:
                result = cell.getStringCellValue();
                break;
            case BOOLEAN:
                result = new Boolean(
                        cell.getBooleanCellValue());
                break;
            case FORMULA:
                result = cell.getCellFormula();
                break;
            case NUMERIC:
                double numericCellValue = cell.getNumericCellValue();
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    result = new java.sql.Date(
                            (HSSFDateUtil.getJavaDate(numericCellValue))
                                    .getTime());
                } else {
                    int temp = (int) numericCellValue;
                    if (temp == numericCellValue)
                        result = temp;
                    else
                        result = numericCellValue;
                }
                break;
            case _NONE:
                break;
            case ERROR:
                result = new Byte(cell.getErrorCellValue());
                break;
            case BLANK:
                break;
            default:
                break;
        }
        return result;
    }

    Object getCellValue(Cell cell);
}
