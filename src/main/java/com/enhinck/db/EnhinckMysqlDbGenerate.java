package com.enhinck.db;

import com.enhinck.db.bean.BeanWriteUtil;
import com.enhinck.db.bean.ClassField;
import com.enhinck.db.bean.ClassObject;
import com.enhinck.db.entity.*;
import com.enhinck.db.excel.CommonExcelWriteUtil;
import com.enhinck.db.util.Database;
import com.enhinck.db.util.FileUtil;
import com.enhinck.db.util.MysqlDbUtil;
import com.enhinck.db.util.SqlUtil;
import com.enhinck.db.word.CommonWordWriteUtil;
import com.google.common.collect.MapDifference;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * mysql数据库生成DO DTO Service ServiceImpl代码
 *
 * @author huenb
 */
@Slf4j
public class EnhinckMysqlDbGenerate extends MysqlDbCompare {

    /**
     * 应用启动入口
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {
        final Database oldDB = getDatabse(NEW);
        Connection oldDBConnection = oldDB.getConnection();
        log.info("数据库已连接成功：{}", oldDB.getUrl());

        //subscribe
        ClassObject classObject = new ClassObject();
        classObject.setPath("/Users/huenbin/work/ioc/sc-ioc-api/src/main/java");
        // DO包路径
        classObject.setPackageName("com.greentown.ioc.model.domain");
        // DTO包路径
        classObject.setDtoPackageName("com.greentown.ioc.model.dto");
        // mapper包路径
        classObject.setMapperPackageName("com.greentown.ioc.mapper");
        // service包路径
        classObject.setServicePackageName("com.greentown.ioc.service");
        classObject.setVoPackageName("com.greentown.ioc.model.vo");
        classObject.setControllerPackageName("com.greentown.ioc.controller");
        classObject.getClassFieldList().clear();
        if (db2JavaBean(oldDBConnection, "tb_parking_useage_trend", classObject)) {
            BeanWriteUtil.writeJavaBean(classObject);
            log.info("生成JavaBean完成");
        }
    }

    /**
     * 数据字典
     *
     * @param oldDbConnection
     * @return
     */
    public static boolean db2JavaBean(final Connection oldDbConnection, String tableName, final ClassObject classObject) {
        boolean flag = false;
        log.info("开始生成JavaBean...");
        String className = tableName.replaceAll("tb_", "_");
        String tempName = SqlUtil.underScoreCaseToCamelCase(className);

        className = tempName + "DO";
        String mapperName = tempName + "Mapper";
        String serviceName = tempName + "Service";
        String dtoName = tempName + "DTO";
        String voName = tempName + "VO";
        String controllerName = tempName + "Controller";
        classObject.setDtoName(dtoName);
        classObject.setClassName(className);
        classObject.setMapperName(mapperName);
        classObject.setServiceName(serviceName);
        classObject.setVoName(voName);
        classObject.setControllerName(controllerName);
        List<InformationSchemaTables> informationSchemaTables = MysqlDbUtil.getTables(oldDbConnection);
        for (InformationSchemaTables informationSchemaTable : informationSchemaTables) {
            if (informationSchemaTable.getTableName().equals(tableName)) {
                classObject.setTableName(tableName);
                classObject.setComment(informationSchemaTable.getTableComment());
                tables2ClassObject(oldDbConnection, classObject);
                flag = true;
            }
        }

        return flag;
    }

    private static void tables2ClassObject(Connection newDbConnection, ClassObject classObject) {
        Map<String, InformationSchemaColumns> oldMap = MysqlDbUtil.getColumnsByTableName(classObject.getTableName(), newDbConnection);
        for (Map.Entry<String, InformationSchemaColumns> entry : oldMap.entrySet()) {
            InformationSchemaColumns informationSchemaColumns = entry.getValue();
            String columnName = informationSchemaColumns.getColumnName();
            String columnComment = informationSchemaColumns.getColumnComment();
            String columnType = informationSchemaColumns.getColumnType();
            String fieldType = "String";
            if (columnType.contains("bigint")) {
                fieldType = "Long";
            } else if (columnType.contains("int")) {
                fieldType = "Integer";
            } else if (columnType.contains("time")) {
                fieldType = "Date";
                classObject.getImportClassList().add("java.util.Date");
            }
            String fieldName = SqlUtil.underScoreCaseToCamelCase(columnName);
            ClassField classField = new ClassField();
            classField.setComment(columnComment);
            classField.setFieldType(fieldType);
            classField.setName(fieldName);
            classObject.getClassFieldList().add(classField);
        }
    }


}
