package com.enhinck.db;

import com.enhinck.db.entity.*;
import com.enhinck.db.excel.CommonExcelWriteUtil;
import com.enhinck.db.util.*;
import com.enhinck.db.word.CommonWordWriteUtil;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * mysql数据库比对生产升级脚本工具
 *
 * @author huenb
 */
@Slf4j
public class MysqlDbCompare {
    static Map<String, String> map;

    static {
        map = PropertiesUtil.readProperties("db.properties");
    }

    public static final String OLD = "old";
    public static final String NEW = "new";

    public static Database getDatabse(String prefix) {
        String url = map.get(prefix + ".db.url");
        String username = map.get(prefix + ".db.username");
        String password = map.get(prefix + ".db.password");
        Database database = Database.builder().driver(Database.MYSQL_DRIVER).url(url).username(username).password(password).build();
        return database;
    }

    public static List<String> getSyncTables() {
        String tablesStr = map.get("db.datasync.tables");
        String[] tables = StringUtils.split(tablesStr, "[,，;；]");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < tables.length; i++) {
            list.add(tables[i]);
        }
        return list;
    }


    /**
     * 应用启动入口
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final Database oldDB = getDatabse(OLD);
        Connection oldDBConnection = oldDB.getConnection();
        final Database newDb = getDatabse(NEW);
        Connection newDbConnection = newDb.getConnection();
        log.info("数据库已连接成功,被升级库：{},目标库{}", oldDB.getUrl(), newDb.getUrl());
        StringBuilder stringBuilder = new StringBuilder();
        log.info("开始生成增量表建表语句...");
        OneVersionModifySummary oneVersionModifySummary = new OneVersionModifySummary();

        // 增量表语句
        String tableCreates = compareTableNames(oldDBConnection, newDbConnection, oneVersionModifySummary);
        log.info("开始生成表字段新增和修改语句...");
        // 表字段新增和修改
        String columnsAddModify = compareTableColumns(oldDBConnection, newDbConnection, oneVersionModifySummary);

        List<String> list = getSyncTables();

        // 关键配置数据同步
        String dataAddModify = compareTableDatas(list, oldDBConnection, newDbConnection);

        // 合成脚本内容
        stringBuilder.append(tableCreates).append(columnsAddModify);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = simpleDateFormat.format(new Date()) + "数据库更新脚本.sql";
        FileUtils.write(new File(fileName), stringBuilder.toString(), "UTF-8");


        MysqlDbToDictionary.toOneVersionSummaryDoc(newDbConnection, oneVersionModifySummary);

        log.info("升级脚本已生成:{}", fileName);
    }

    private static String compareTableDatas(List<String> list, Connection oldDBConnection, Connection newDbConnection) {

        // 增量

        // 修改

        // 删除


        return null;
    }


    /**
     * @param oldDbConnection 需要升级的库
     * @param newDBConnection 已升级的库
     */
    public static String compareTableNames(final Connection oldDbConnection, final Connection newDBConnection, OneVersionModifySummary oneVersionModifySummary) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-- 新增的表开始").append(NEW_LINE);

        List<InformationSchemaTables> oldTables = MysqlDbUtil.getTables(oldDbConnection);
        Set<String> oldTableNameSets = new HashSet<>();
        oldTables.forEach(table -> {
            oldTableNameSets.add(table.getTableName());
        });

        List<InformationSchemaTables> newTables = MysqlDbUtil.getTables(newDBConnection);
        Set<String> newTableNameSets = new HashSet<>();
        newTables.forEach(table -> {
            newTableNameSets.add(table.getTableName());
            oneVersionModifySummary.getMap().put(table.getTableName(), table);
        });

        newTableNameSets.removeAll(oldTableNameSets);
        // 增量表
        Set<String> addTableNameSets = newTableNameSets;
        addTableNameSets.forEach(tableName -> {
            String tableDDL = MysqlDbUtil.getTableDDL(tableName, newDBConnection);
            stringBuilder.append(tableDDL).append(END_SQL).append(NEW_LINE);
            //  tables.add(tableName);
            oneVersionModifySummary.getAddTable().add(oneVersionModifySummary.getMap().get(tableName));
        });
        stringBuilder.append("-- 新增的表开始结束").append(NEW_LINE);
        // 增量表数据字典

        return stringBuilder.toString();
    }

    public static final String NEW_LINE = "\n";
    public static final String END_SQL = ";";
    public static final String ALTER_TABLE = "ALTER TABLE ";
    public static final String ADD_COLUMN = "ADD COLUMN  ";
    public static final String MODIFY_COLUMN = "MODIFY COLUMN ";
    public static final String COMMENT = "COMMENT ";
    public static final String EMPTY_SPACE = " ";
    public static final String AFTER = "AFTER ";

    /**
     * 比对表字段
     *
     * @param oldDbConnection
     * @param newDBConnection
     * @return
     */
    public static String compareTableColumns(final Connection oldDbConnection, final Connection newDBConnection, OneVersionModifySummary oneVersionModifySummary) {
        final StringBuilder stringBuilder = new StringBuilder();
        Set<String> oldTableNameSets = MysqlDbUtil.getTablesSet(oldDbConnection);
        //sql格式  ADD COLUMN column column_type is_null default_value extra comment

        oldTableNameSets.forEach(tableName -> {
            stringBuilder.append("-- ").append(tableName).append("表更新开始-----------").append(NEW_LINE);
            Map<String, InformationSchemaColumns> oldMap = MysqlDbUtil.getColumnsByTableName(tableName, oldDbConnection);
            Map<String, InformationSchemaColumns> newMap = MysqlDbUtil.getColumnsByTableName(tableName, newDBConnection);
            MapDifference<String, InformationSchemaColumns> difference = Maps.difference(oldMap, newMap);

            // 新增字段脚本
            AddColumns(stringBuilder, tableName, newMap, difference, oneVersionModifySummary);

            // 修改字段脚本
            ModifyColumns(stringBuilder, tableName, newMap, difference, oneVersionModifySummary);

            // 删除字段
            deleteColumns(stringBuilder, tableName, newMap, difference);

            stringBuilder.append("-- ").append(tableName).append("表更新结束-----------").append(NEW_LINE);
        });

        return stringBuilder.toString();
    }


    private static void AddColumns(StringBuilder stringBuilder, String tableName, Map<String, InformationSchemaColumns> newMap, MapDifference<String, InformationSchemaColumns> difference, OneVersionModifySummary oneVersionModifySummary) {

        List<InformationSchemaColumns> adds = new ArrayList<>();
        if (difference.entriesOnlyOnRight().size() != 0) {
            stringBuilder.append("-- 新增的字段开始").append(NEW_LINE);
            // 新建字段
            stringBuilder.append(ALTER_TABLE).append(tableName);
            difference.entriesOnlyOnRight().forEach((key, value) -> {
                InformationSchemaColumns addColumns = value;
                BigInteger bigInteger = addColumns.getOrdinalPosition();
                int ordinalPosition = bigInteger.intValue() - 1;
                String columnName = getPostionColumnName(newMap, ordinalPosition);
                stringBuilder.append(NEW_LINE)
                        .append(ADD_COLUMN).append(addColumns.getColumnName()).append(EMPTY_SPACE)
                        .append(addColumns.getColumnType()).append(EMPTY_SPACE)
                        .append(addColumns.isNullable()).append(EMPTY_SPACE)
                        .append(addColumns.getColumnDefault()).append(EMPTY_SPACE)
                        .append(addColumns.getExtra()).append(EMPTY_SPACE)
                        .append(COMMENT).append("'").append(addColumns.getColumnComment()).append("'");
                // 更正字段排序
                if (columnName != null) {
                    stringBuilder.append(EMPTY_SPACE).append(AFTER).append(columnName);
                }
                stringBuilder.append(",");

                adds.add(addColumns);
            });
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            stringBuilder.append(END_SQL).append(NEW_LINE);
            stringBuilder.append("-- 新增的字段结束").append(NEW_LINE);
        }
        oneVersionModifySummary.getAdds().put(tableName, adds);
    }

    private static void ModifyColumns(StringBuilder stringBuilder, String tableName, Map<String, InformationSchemaColumns> newMap, MapDifference<String, InformationSchemaColumns> difference, OneVersionModifySummary oneVersionModifySummary) {
        List<MapDifference.ValueDifference<InformationSchemaColumns>> differences = new ArrayList<>();
        if (difference.entriesDiffering().size() != 0) {
            // 修改字段
            stringBuilder.append("-- 修改字段开始").append(NEW_LINE);
            stringBuilder.append(ALTER_TABLE).append(tableName);
            difference.entriesDiffering().forEach((key, diff) -> {
                differences.add(diff);
                InformationSchemaColumns updateColumns = diff.rightValue();
                BigInteger bigInteger = updateColumns.getOrdinalPosition();
                int ordinalPosition = bigInteger.intValue() - 1;
                String columnName = getPostionColumnName(newMap, ordinalPosition);
                stringBuilder.append(NEW_LINE)
                        .append(MODIFY_COLUMN).append(updateColumns.getColumnName()).append(EMPTY_SPACE)
                        .append(updateColumns.getColumnType()).append(EMPTY_SPACE)
                        .append(updateColumns.isNullable()).append(EMPTY_SPACE)
                        .append(updateColumns.getColumnDefault()).append(EMPTY_SPACE)
                        .append(updateColumns.getExtra()).append(EMPTY_SPACE)
                        .append(COMMENT).append("'").append(updateColumns.getColumnComment()).append("'");
                // 更正字段排序
                if (columnName != null) {
                    stringBuilder.append(EMPTY_SPACE).append(AFTER).append(columnName);
                }
                stringBuilder.append(",");
            });
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            stringBuilder.append(END_SQL).append(NEW_LINE);
            stringBuilder.append("-- 修改字段结束").append(NEW_LINE);
        }

        oneVersionModifySummary.getModifys().put(tableName, differences);
    }

    private static void deleteColumns(StringBuilder stringBuilder, String tableName, Map<String, InformationSchemaColumns> newMap, MapDifference<String, InformationSchemaColumns> difference) {
        difference.entriesOnlyOnLeft().forEach((key, value) -> {
            InformationSchemaColumns deleteColumn = value;
            //本期不做
        });
    }


    /**
     * 获取指定位置字段的名称
     *
     * @param oldMap
     * @param ordinalPosition
     * @return
     */
    private static String getPostionColumnName(Map<String, InformationSchemaColumns> oldMap, int ordinalPosition) {
        for (Map.Entry<String, InformationSchemaColumns> entry : oldMap.entrySet()) {
            if (entry.getValue().getOrdinalPosition().intValue() == ordinalPosition) {
                return entry.getValue().getColumnName();
            }
        }
        return null;
    }


}
