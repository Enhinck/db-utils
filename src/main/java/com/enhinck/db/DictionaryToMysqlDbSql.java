package com.enhinck.db;

import com.enhinck.db.util.Database;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;

/**
 * 数据字典生成mysql数据库脚本工具
 *
 * @author huenb
 */
@Slf4j
public class DictionaryToMysqlDbSql extends MysqlDbCompare {

    /**
     * 应用启动入口
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args){
        final Database oldDB = getDatabse(OLD);
        Connection oldDBConnection = oldDB.getConnection();
        log.info("数据库已连接成功：{}", oldDB.getUrl());
    }



}
