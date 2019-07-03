package com.enhinck.db.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ExcelSheet {
    private String sheetName;
    private String comment;
    private List<ExcelRow> excelRows = new ArrayList<>();

    public ExcelSheet() {
        sheetName = "defaultSheetName";
    }

    public ExcelSheet(String sheetName) {
        this.sheetName = sheetName;
    }

    public void AddRow(ExcelRow excelRow) {
        excelRows.add(excelRow);
    }
}
