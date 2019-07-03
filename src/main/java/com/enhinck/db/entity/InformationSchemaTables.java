package com.enhinck.db.entity;


import com.enhinck.db.annotation.ETable;
import lombok.Data;

import java.util.Date;

/**
 * 字段信息表
 */
@Data
@ETable("information_schema.tables")
public class InformationSchemaTables {
    private String tableSchema;
    private String tableName;
    private String tableComment;
    private String tableType;
    private String tableCollation;
    private Date createTime;
}
