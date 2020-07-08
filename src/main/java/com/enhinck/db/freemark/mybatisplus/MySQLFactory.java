package com.enhinck.db.freemark.mybatisplus;

import com.enhinck.db.excel.JavaDefineEntity;
import com.enhinck.db.excel.JavaFieldEntity;
import com.enhinck.db.excel.MySQLEntity;
import com.enhinck.db.freemark.BaseFacotry;
import com.enhinck.db.freemark.FreemarkUtil;

import java.util.List;

/**
 * VO工厂
 *
 * @author huenbin
 * @date 2020-07-02 17:00
 */
public class MySQLFactory extends BaseFacotry {
    private static final MySQLFactory doFactory = new MySQLFactory();

    public static MySQLFactory getInstance(){
        return doFactory;
    }
    private MySQLFactory() {

    }


    public void write(List<JavaDefineEntity<JavaFieldEntity>> javaDefineEntity) {
        MySQLEntity mySQLEntity = new MySQLEntity();
        mySQLEntity.setTables(javaDefineEntity);
        FreemarkUtil.writeSql(mySQLEntity);
    }

}
