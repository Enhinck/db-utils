package com.enhinck.db.bean;

import com.enhinck.db.util.SqlUtil;
import lombok.Data;

@Data
public class ClassField {
    private String name;
    private String comment;
    private String dbColumnType;
    private String fieldType;

    public String methodGetName(){
       return SqlUtil.getGetMethodName(name);
    }
}
