package com.enhinck.db;

import com.enhinck.db.entity.*;
import com.enhinck.db.excel.CommonExcelWriteUtil;
import com.enhinck.db.util.Database;
import com.enhinck.db.util.MysqlDbUtil;
import com.enhinck.db.util.PropertiesUtil;
import com.enhinck.db.word.CommonWordWriteUtil;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * mysql数据库生成数据字典工具
 *
 * @author huenb
 */
@Slf4j
public class MysqlDbToDictionary extends MysqlDbCompare {

    /**
     * 应用启动入口
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final Database oldDB = getDatabse(OLD);
        Connection oldDBConnection = oldDB.getConnection();
        log.info("数据库已连接成功：{}", oldDB.getUrl());
        db2Dictionary(oldDBConnection);
    }


    /**
     * 数据字典
     *
     * @param oldDbConnection
     * @return
     */
    public static void db2Dictionary(final Connection oldDbConnection) {
        List<InformationSchemaTables> oldTableNameSets = MysqlDbUtil.getTables(oldDbConnection);
        ExcelFile excelFile = new ExcelFile();
        excelFile.setDocFileName("数据字典.docx");
        oldTableNameSets.forEach(table -> {
            ExcelSheet excelSheet = new ExcelSheet(table.getTableName());
            excelSheet.setComment(table.getTableComment());
            Map<String, InformationSchemaColumns> oldMap = MysqlDbUtil.getColumnsByTableName(table.getTableName(), oldDbConnection);
            int count = 0;
            for (Map.Entry<String, InformationSchemaColumns> entry : oldMap.entrySet()) {
                if (count == 0) {
                    addTitle(excelSheet);
                }
                ExcelRow excelRow = new ExcelRow();
                InformationSchemaColumns informationSchemaColumns = entry.getValue();
                excelRow.AddCell(new ExcelCell(informationSchemaColumns.getColumnName()));
                excelRow.AddCell(new ExcelCell(informationSchemaColumns.getColumnType()));
                excelRow.AddCell(new ExcelCell(informationSchemaColumns.excelCanNo()));
                excelRow.AddCell(new ExcelCell(informationSchemaColumns.getColumnComment()));
                excelSheet.AddRow(excelRow);
                count++;
            }
            excelFile.AddSheet(excelSheet);
        });
        CommonExcelWriteUtil.write(excelFile);
        CommonWordWriteUtil.write(excelFile);
    }

    private static void addTitle(ExcelSheet excelSheet) {
        ExcelRow excelRow = new ExcelRow();
        excelRow.AddCell(new ExcelCell("字段名"));
        excelRow.AddCell(new ExcelCell("字段类型"));
        excelRow.AddCell(new ExcelCell("是否必填"));
        excelRow.AddCell(new ExcelCell("描述"));
        excelSheet.AddRow(excelRow);
    }


}
