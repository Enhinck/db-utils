package com.enhinck.db.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExcelFile {
    private String fileName;
    private String docFileName;
    private String filePath = "";
    private List<ExcelSheet> sheets = new ArrayList<>();

    public ExcelFile() {
        fileName = "demo.xlsx";
    }

    public ExcelFile(String fileName) {
        this.fileName = fileName;
    }

    public void AddSheet(ExcelSheet excelSheet) {
        sheets.add(excelSheet);
    }
}
