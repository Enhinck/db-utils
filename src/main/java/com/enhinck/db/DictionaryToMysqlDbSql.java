package com.enhinck.db;

import com.alibaba.fastjson.JSONObject;
import com.enhinck.db.bean.DemoVO;
import com.enhinck.db.bean.DictionaryVO;
import com.enhinck.db.excel.CommonExcelReadUtil;
import com.enhinck.db.util.Database;
import com.enhinck.db.util.SqlUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

/**
 * 数据字典生成mysql数据库脚本工具
 *
 * @author huenb
 */
@Slf4j
public class DictionaryToMysqlDbSql extends MysqlDbCompare {


    public static String getDbType(String javaType) {
        switch (javaType) {
            case "Long":
                return "bigint(20)";
            case "String":
                return "varchar(50)";
            case "Integer":
                return "int(11)";
            case "Date":
                return "datetime";
        }
        return "";
    }

    public static final String ID = "id";

    /**
     * 应用启动入口
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {


        String tableName = "tb_hik_camera";
        try {
            InputStream is = new FileInputStream(new File("D:\\数据字典原型.xlsx"));
            List<DictionaryVO> dictionaryVOS = CommonExcelReadUtil.getDataFromExcel(is, DictionaryVO.class);
            StringBuilder sql = new StringBuilder();
            sql.append("create table `").append(tableName).append("` (\n");
            dictionaryVOS.forEach(demoVO -> {
                ;

                String columnName = SqlUtil.camelCaseToUnderScoreCase(demoVO.getColumnName());
                String type = getDbType(demoVO.getJavaType());
                String comment = demoVO.getDesc();
                if (ID.equals(columnName)) {
                    sql.append("`").append(columnName).append("` ").append(type).append(" NOT NULL AUTO_INCREMENT COMMENT '").append(comment).append("',\n");
                } else {
                    sql.append("`").append(columnName).append("` ").append(type).append(" DEFAULT NULL COMMENT '").append(comment).append("',\n");
                }
            });

            sql.append("PRIMARY KEY (`id`)");

            sql.append("\n);");


            System.out.println(sql.toString());
        } catch (Exception e) {

        }


    }


}
