package com.enhinck.db.entity;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ExcelFile {
    private String fileName;
    private String docFileName;
    private String filePath = "";
    private List<ExcelSheet> sheets = new ArrayList<>();

    public ExcelFile() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        fileName = date + ".xlsx";
    }

    public boolean hasContent() {
        return sheets.size() > 0 && sheets.get(0) != null && sheets.get(0).getExcelRows().size() != 0;
    }

    public ExcelFile(String fileName) {
        this.fileName = fileName;
    }

    public void AddSheet(ExcelSheet excelSheet) {
        sheets.add(excelSheet);
    }
}
