package com.enhinck.db;

import com.enhinck.db.entity.*;
import com.enhinck.db.excel.CommonExcelWriteUtil;
import com.enhinck.db.util.Database;
import com.enhinck.db.util.MysqlDbUtil;
import com.enhinck.db.word.CommonWordWriteUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

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
