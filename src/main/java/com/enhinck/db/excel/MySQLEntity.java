package com.enhinck.db.excel;

import lombok.Data;

import java.util.List;

/**
 * 描述
 *
 * @author huenbin
 * @date 2020-07-06 19:49
 */
@Data
public class MySQLEntity {
   private List<JavaDefineEntity<JavaFieldEntity>> tables;
}
