package com.enhinck.db.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.enhinck.db.excel.DateExcelCellValueAdapt;
import com.enhinck.db.excel.ExcelColumn;
import lombok.Data;

import java.util.Date;

@Data
public class DemoVO {
    @ExcelColumn(name = "姓名")
    private String name;
    @ExcelColumn(name = "排序")
    private Integer sort;
    @ExcelColumn(name = "分数")
    private Double score;
    /**
     * 默认的DefaultExcelCellValueAdapt也支持日期转换
     * 支持两种日期格式（文本格式的日期yyyy-MM-dd HH:mm:ss 和excel自己的日期类型）
     */
    @ExcelColumn(name = "日期", valueAdapt = DateExcelCellValueAdapt.class)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date time;
}
