package com.enhinck.db.excel;


import com.enhinck.db.entity.ExcelCell;
import com.enhinck.db.entity.ExcelFile;
import com.enhinck.db.entity.ExcelRow;
import com.enhinck.db.entity.ExcelSheet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

@Slf4j
public class CommonExcelWriteUtil {
    public static void main(String[] args) {
        ExcelFile excelFile = new ExcelFile();
        ExcelSheet excelSheet = new ExcelSheet();
        ExcelRow excelRow = new ExcelRow();
        for (int i = 0; i < 6; i++) {
            ExcelCell excelCell = new ExcelCell();
            excelCell.setColumnValue(i + "1");
            excelRow.AddCell(excelCell);
        }
        excelSheet.AddRow(excelRow);

        ExcelRow excelRow2 = new ExcelRow();
        for (int i = 0; i < 6; i++) {
            ExcelCell excelCell = new ExcelCell();
            excelCell.setColumnValue(i + "1");
            excelRow2.AddCell(excelCell);
        }
        excelSheet.AddRow(excelRow2);
        excelFile.AddSheet(excelSheet);
        write(excelFile);
    }

    public static void write(ExcelFile excelFile) {
        if (!excelFile.hasContent()){
            return;
        }

        try {
            String path = excelFile.getFilePath();
            // 生成路径
            if (StringUtils.isNotBlank(excelFile.getFilePath())) {
                File folder = new File(excelFile.getFilePath());
                if (!folder.exists()) {
                    folder.mkdirs();
                }
            }
            String fileAbsName = path + excelFile.getFileName();
            List<ExcelSheet> sheets = excelFile.getSheets();
            if (sheets.size() > 0) {
                Workbook workbook = new XSSFWorkbook();
                sheets.forEach(excelSheet -> {
                    Sheet sheet = workbook.createSheet(excelSheet.getSheetName());
                    setSheetStyle(sheet);
                    List<ExcelRow> excelRows = excelSheet.getExcelRows();
                    for (int i = 0; i < excelRows.size(); i++) {
                        Row row = sheet.createRow(i);
                        ExcelRow excelRow = excelRows.get(i);
                        List<ExcelCell> excelCells = excelRow.getCells();
                        for (int j = 0; j < excelCells.size(); j++) {
                            Cell cell = row.createCell(j);
                            ExcelCell excelCell = excelCells.get(j);
                            setCellStyle(workbook, cell, i);
                            switch (excelCell.getColumnType()) {
                                case STRING:
                                    cell.setCellValue((String) excelCell.getColumnValue());
                                    break;
                                case DATE:
                                    cell.setCellValue((Date) excelCell.getColumnValue());
                                    break;
                                case NUMBER:
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });
                FileOutputStream out = new FileOutputStream(fileAbsName);
                workbook.write(out);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setCellStyle(Workbook workbook, Cell cell, int rowIndex) {
        CellStyle style = workbook.createCellStyle();
        if (rowIndex == 0) {
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillBackgroundColor(IndexedColors.YELLOW.index);
            style.setFillForegroundColor(IndexedColors.GREEN.index);
        }
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        cell.setCellStyle(style);
    }

    public static void setSheetStyle(Sheet sheet) {
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 2000);
        sheet.setColumnWidth(3, 60*256);
        sheet.setColumnWidth(4, 60*256);
        sheet.setColumnWidth(5, 60*256);
        sheet.setColumnWidth(6, 60*256);
        sheet.setColumnWidth(7, 60*256);
        sheet.autoSizeColumn(1);
    }

}
