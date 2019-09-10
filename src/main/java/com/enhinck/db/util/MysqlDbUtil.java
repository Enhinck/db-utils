package com.enhinck.db.util;

import com.enhinck.db.entity.InformationSchemaColumns;
import com.enhinck.db.entity.InformationSchemaTables;
import org.apache.commons.collections.map.HashedMap;

import java.sql.*;
import java.util.*;

public class MysqlDbUtil {

    /**
     * 获取建表语句
     *
     * @param tablename
     * @param con
     * @return
     */
    public static String getTableDDL(String tablename, Connection con) {
        String sql = "show create table " + tablename;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String create = rs.getString(2);
                return create;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.releaseConnection(null, pstmt, rs);
        }
        return "";
    }

    public static Set<String> getTablesSet(Connection con) {
        Set<String> sets = new HashSet<>();
        String sql = "show tables";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String tableName = rs.getString(1);
                sets.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.releaseConnection(null, pstmt, rs);
        }
        return sets;
    }


    /**
     * 当前库的名称
     *
     * @param con
     * @return
     */
    public static String currentDatabase(Connection con) {
        String sql = "select database()";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String database = rs.getString(1);
                return database;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.releaseConnection(null, pstmt, rs);
        }
        return "";
    }

    public static Map<String, InformationSchemaColumns> getColumnsByTableName(String tablename, Connection con) {
        String tableSchema = currentDatabase(con);
        Map<String, InformationSchemaColumns> map = new LinkedHashMap<>();
        SqlUtil.Sqls sqls = SqlUtil.getWhere(InformationSchemaColumns.class).andEqualTo("tableName", tablename).andEqualTo("tableSchema", tableSchema).orderByAsc("ordinalPosition");
        String sql = SqlUtil.getSelectSql(InformationSchemaColumns.class, sqls.build());
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql);
            sqls.setParams(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                InformationSchemaColumns informationSchemaColumns = SqlUtil.getDbData(InformationSchemaColumns.class, rs);
                map.put(informationSchemaColumns.getColumnName(), informationSchemaColumns);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.releaseConnection(null, pstmt, rs);
        }
        return map;
    }

    public static List<InformationSchemaTables> getTables(Connection con) {
        String tableSchema = currentDatabase(con);
        List<InformationSchemaTables> tables = new ArrayList<>();
        SqlUtil.Sqls sqls = SqlUtil.getWhere(InformationSchemaTables.class).andEqualTo("tableSchema", tableSchema).andEqualTo("tableType", "BASE TABLE").orderByAsc("create_time");
        String sql = SqlUtil.getSelectSql(InformationSchemaTables.class, sqls.build());
        getTables(con, tables, sqls, sql);
        return tables;
    }


    public static List<InformationSchemaTables> getTables(Connection con, String tableName) {
        String tableSchema = currentDatabase(con);
        List<InformationSchemaTables> tables = new ArrayList<>();
        SqlUtil.Sqls sqls = SqlUtil.getWhere(InformationSchemaTables.class).andEqualTo("tableSchema", tableSchema).andEqualTo("tableType", "BASE TABLE").andEqualTo("tableName", tableName).orderByAsc("create_time");
        String sql = SqlUtil.getSelectSql(InformationSchemaTables.class, sqls.build());

        getTables(con, tables, sqls, sql);
        return tables;
    }

    private static void getTables(Connection con, List<InformationSchemaTables> tables, SqlUtil.Sqls sqls, String sql) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql);
            sqls.setParams(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                InformationSchemaTables informationSchemaTables = SqlUtil.getDbData(InformationSchemaTables.class, rs);
                tables.add(informationSchemaTables);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.releaseConnection(null, pstmt, rs);
        }
    }


    public static  Map<Long, Map<String, Object>>  getTableDatas(Connection con, String tableName) {
        String sql = SqlUtil.getSelectAllSql(tableName);
        Map<Long, Map<String, Object>> datas = new LinkedHashMap<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Long key = rs.getLong("id");
                Map<String, Object> row = new LinkedHashMap();
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                int columCount = resultSetMetaData.getColumnCount();
                for (int i = 1; i <= columCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                   // System.out.println(columnName + "=" + value);
                }
                datas.put(key, row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.releaseConnection(null, pstmt, rs);
        }
        return datas;
    }


}
