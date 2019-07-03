package com.enhinck.db.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExcelRow {
    private List<ExcelCell> cells = new ArrayList<>();

    public ExcelRow() {
    }

    public void AddCell(ExcelCell excelCell) {
        cells.add(excelCell);
    }
}
