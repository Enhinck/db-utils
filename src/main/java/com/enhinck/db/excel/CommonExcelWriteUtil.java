package com.enhinck.db.excel;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.enhinck.db.entity.ExcelCell;
import com.enhinck.db.entity.ExcelFile;
import com.enhinck.db.entity.ExcelRow;
import com.enhinck.db.entity.ExcelSheet;
import com.enhinck.db.util.BeanUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

@Slf4j
public class CommonExcelWriteUtil {
    public static void main(String[] args) throws Exception {

        String jsonStr = FileUtils.readFileToString(new File("d:\\camera.txt"), "UTF-8");


        ExcelFile excelFile = new ExcelFile();
        ExcelSheet excelSheet = new ExcelSheet();
        excelFile.AddSheet(excelSheet);

        ExcelRow titleRow = new ExcelRow();
        ExcelCell excelCell = new ExcelCell();
        excelCell.setColumnValue("摄像头id");
        titleRow.AddCell(excelCell);
        ExcelCell excelCell1 = new ExcelCell();
        excelCell1.setColumnValue("摄像头名称");
        titleRow.AddCell(excelCell1);
        excelSheet.AddRow(titleRow);

        JSONArray array = JSONArray.parseArray(jsonStr);

        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            ExcelRow camera = new ExcelRow();
            ExcelCell uniqueId = new ExcelCell();
            uniqueId.setColumnValue(object.getString("remark"));
            camera.AddCell(uniqueId);
            ExcelCell name = new ExcelCell();
            name.setColumnValue(object.getString("name"));
            camera.AddCell(name);
            excelSheet.AddRow(camera);
        }
        write(excelFile);

    /*    ExcelReportDemo excelReportDemo = new ExcelReportDemo();
        excelReportDemo.setPalce("场所：如心小镇广场");
        excelReportDemo.setDate("日期：2019-11-27 星期三");
        excelReportDemo.setWeather("天气:15/25℃ 晴");
        excelReportDemo.setTodayPerson("2001");
        excelReportDemo.setLastYearPerson("1823");
        excelReportDemo.setMaleToatlCount(300);
        excelReportDemo.setFemaleToatalCount(300);
        excelReportDemo.setUnknowToatlCount(300);

        excelReportDemo.setImmaturityCount(0.15);
        excelReportDemo.setYouthCount(0.25);
        excelReportDemo.setMiddleCount(0.30);
        excelReportDemo.setOldCount(0.30);

        List<ExcelReportDemo.Record> records = new ArrayList<>();
        ExcelReportDemo.Record record = new ExcelReportDemo.Record();
        record.setTimePeriod("00 ：00 - 01：00");
        record.setTotalCount(15);
        record.setMaleCount(10);
        record.setFemaleCount(5);
        records.add(record);
        record = new ExcelReportDemo.Record();
        record.setTimePeriod("01 ：00 - 02：00");
        record.setTotalCount(18);
        record.setMaleCount(15);
        record.setFemaleCount(3);
        records.add(record);
        record = new ExcelReportDemo.Record();
        record.setTimePeriod("合计");
        record.setTotalCount(33);
        record.setMaleCount(25);
        record.setFemaleCount(8);
        records.add(record);

        excelReportDemo.setRecords(records);
        write(excelReportDemo);*/
    }

    public static void write(ExcelFile excelFile) {
        if (!excelFile.hasContent()) {
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
                log.info("生成表格文件:{}", fileAbsName);
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
        sheet.setColumnWidth(3, 60 * 256);
        sheet.setColumnWidth(4, 60 * 256);
        sheet.setColumnWidth(5, 60 * 256);
        sheet.setColumnWidth(6, 60 * 256);
        sheet.setColumnWidth(7, 60 * 256);
        sheet.autoSizeColumn(1);
    }

    public static void write(ExcelReportDemo excelReportDemo) {
        try {
            FileInputStream is = new FileInputStream(new File(excelReportDemo.getTemple()));
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            process(excelReportDemo, sheet, null);
            FileOutputStream out = new FileOutputStream("D:/demo2.xlsx");
            workbook.write(out);
            out.close();
            is.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void process(Object object, Sheet sheet, Integer startRow) {
        Map<ExcelColumn, Object> map = BeanUtils.getExcelData(object);
        map.forEach((key, value) -> {
            switch (key.columnType()) {
                case CELL:
                    int row = key.row();
                    if (startRow != null) {
                        row = startRow;
                    }
                    Cell cell = sheet.getRow(row).getCell(key.cell());
                    if (value instanceof Double) {
                        cell.setCellValue((double) value);
                    } else if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else {
                        cell.setCellValue(value + "");
                    }
                    break;
                case LIST:
                    List<?> list = (List<?>) value;
                    for (int i = 0; i < list.size(); i++) {
                        process(list.get(i), sheet, key.row() + i);
                    }
                    break;
                default:
                    break;
            }
        });
    }


}
