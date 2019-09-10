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
    public static void main(String[] args){
        final Database oldDB = getDatabse(NEW);
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
        tables2ExcelSheets(oldDbConnection, excelFile, oldTableNameSets);
        CommonExcelWriteUtil.write(excelFile);
        CommonWordWriteUtil.write(excelFile);
    }


    /**
     * 数据字典
     *
     * @param oldDbConnection
     * @return
     */
    public static void db2Dictionary(final Connection oldDbConnection, List<String> tableNames) {
        ExcelFile excelFile = new ExcelFile();
        excelFile.setDocFileName("G:\\DOC\\增量表.docx");
        excelFile.setFileName("G:\\DOC\\增量表.xlsx");
        for (String tableName : tableNames) {
            List<InformationSchemaTables> oldTableNameSets = MysqlDbUtil.getTables(oldDbConnection, tableName);
            tables2ExcelSheets(oldDbConnection, excelFile, oldTableNameSets);
        }
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

    private static void addCompareTitle(ExcelSheet excelSheet) {
        ExcelRow excelRow = new ExcelRow();
        excelRow.AddCell(new ExcelCell("原字段名"));
        excelRow.AddCell(new ExcelCell("原字段类型"));
        excelRow.AddCell(new ExcelCell("原是否必填"));
        excelRow.AddCell(new ExcelCell("原描述"));
        excelRow.AddCell(new ExcelCell("新字段名"));
        excelRow.AddCell(new ExcelCell("新字段类型"));
        excelRow.AddCell(new ExcelCell("新是否必填"));
        excelRow.AddCell(new ExcelCell("新描述"));
        excelSheet.AddRow(excelRow);
    }


    public static void toOneVersionSummaryDoc(final Connection newDbConnection, OneVersionModifySummary oneVersionModifySummary) {
        ExcelFile excelFile = new ExcelFile();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = simpleDateFormat.format(new Date());
        excelFile.setDocFileName("G:\\DOC\\"+fileName+"summary.docx");
        excelFile.setFileName("G:\\DOC\\"+fileName+"summary.xlsx");
        List<InformationSchemaTables> oldTableNameSets = oneVersionModifySummary.getAddTable();
        // 新增表
        tables2ExcelSheets(newDbConnection, excelFile, oldTableNameSets);
        // 新增字段
        tablesAdds2ExcelSheets(oneVersionModifySummary, excelFile);
        // 修改字段
        tablesModifys2ExcelSheets(oneVersionModifySummary, excelFile);

        //   CommonExcelWriteUtil.write(excelFile);
        CommonWordWriteUtil.write(excelFile);
    }

    public static void tablesModifys2ExcelSheets(OneVersionModifySummary oneVersionModifySummary, ExcelFile excelFile) {
        Map<String, List<MapDifference.ValueDifference<InformationSchemaColumns>>> modifys = oneVersionModifySummary.getModifys();
        modifys.forEach((key, value) -> {
            if (value.size() > 0) {
                ExcelSheet excelSheet = new ExcelSheet(key + "(modify)");
                if (oneVersionModifySummary.getMap().get(key) != null) {
                    excelSheet.setComment(oneVersionModifySummary.getMap().get(key).getTableComment());
                }
                int count = 0;
                for (MapDifference.ValueDifference<InformationSchemaColumns> difference : value) {
                    if (count == 0) {
                        addCompareTitle(excelSheet);
                    }
                    ExcelRow excelRow = new ExcelRow();
                    excelRow.AddCell(new ExcelCell(difference.leftValue().getColumnName()));
                    excelRow.AddCell(new ExcelCell(difference.leftValue().getColumnType()));
                    excelRow.AddCell(new ExcelCell(difference.leftValue().excelCanNo()));
                    excelRow.AddCell(new ExcelCell(difference.leftValue().getColumnComment()));
                    excelRow.AddCell(new ExcelCell(difference.rightValue().getColumnName()));
                    excelRow.AddCell(new ExcelCell(difference.rightValue().getColumnType()));
                    excelRow.AddCell(new ExcelCell(difference.rightValue().excelCanNo()));
                    excelRow.AddCell(new ExcelCell(difference.rightValue().getColumnComment()));
                    excelSheet.AddRow(excelRow);
                    count++;
                }
                excelFile.AddSheet(excelSheet);
            }
        });
    }


    private static void tablesAdds2ExcelSheets(OneVersionModifySummary oneVersionModifySummary, ExcelFile excelFile) {
        Map<String, List<InformationSchemaColumns>> adds = oneVersionModifySummary.getAdds();
        adds.forEach((key, value) -> {
            if (value.size() > 0) {
                ExcelSheet excelSheet = new ExcelSheet(key + "(add)");
                if (oneVersionModifySummary.getMap().get(key) != null) {
                    excelSheet.setComment(oneVersionModifySummary.getMap().get(key).getTableComment());
                }
                int count = 0;
                for (InformationSchemaColumns informationSchemaColumns : value) {
                    if (count == 0) {
                        addTitle(excelSheet);
                    }
                    ExcelRow excelRow = new ExcelRow();
                    excelRow.AddCell(new ExcelCell(informationSchemaColumns.getColumnName()));
                    excelRow.AddCell(new ExcelCell(informationSchemaColumns.getColumnType()));
                    excelRow.AddCell(new ExcelCell(informationSchemaColumns.excelCanNo()));
                    excelRow.AddCell(new ExcelCell(informationSchemaColumns.getColumnComment()));
                    excelSheet.AddRow(excelRow);
                    count++;
                }
                excelFile.AddSheet(excelSheet);
            }
        });


    }


    private static void tables2ExcelSheets(Connection newDbConnection, ExcelFile excelFile, List<InformationSchemaTables> oldTableNameSets) {
        oldTableNameSets.forEach(table -> {
            ExcelSheet excelSheet = new ExcelSheet(table.getTableName());
            excelSheet.setComment(table.getTableComment());
            Map<String, InformationSchemaColumns> oldMap = MysqlDbUtil.getColumnsByTableName(table.getTableName(), newDbConnection);
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
    }


}
