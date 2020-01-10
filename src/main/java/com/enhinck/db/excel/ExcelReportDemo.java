package com.enhinck.db.excel;

import lombok.Data;

import java.util.List;

@Data
public class ExcelReportDemo {
    private String todayPerson;
    private String lastMonthPerson;
    private String compareWithLastMonth;
    private String lastYearPerson;
    private String compareWithLastYear;
    private String temple = "D:/客流量日报表模板.xlsx";
    @ExcelColumn(row = 3, cell = 0)
    private String palce;
    @ExcelColumn(row = 3, cell = 1)
    private String date;
    @ExcelColumn(row = 3, cell = 2)
    private String weather;
    @ExcelColumn(row = 11, cell = 1)
    private double maleToatlCount;
    @ExcelColumn(row = 12, cell = 1)
    private double femaleToatalCount;
    @ExcelColumn(row = 13, cell = 1)
    private double unknowToatlCount;

    @ExcelColumn(row = 11, cell = 3)
    private double immaturityCount;
    @ExcelColumn(row = 12, cell = 3)
    private double youthCount;
    @ExcelColumn(row = 13, cell = 3)
    private double middleCount;
    @ExcelColumn(row = 14, cell = 3)
    private double oldCount;

    @ExcelColumn(row = 21, columnType = ExcelColumn.ColumnType.LIST)
    private List<Record> records;

    @Data
    public static class Record {
        @ExcelColumn(cell = 0)
        private String timePeriod;
        @ExcelColumn(cell = 1)
        private double totalCount;
        @ExcelColumn(cell = 2)
        private double maleCount;
        @ExcelColumn(cell = 3)
        private double femaleCount;
    }

}
