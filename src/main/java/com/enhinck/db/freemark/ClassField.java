package com.enhinck.db.freemark;

import lombok.Data;

import java.util.List;

/**
 * 类字段
 *
 * @author huenbin
 * @date 2020-07-01 11:07
 */
@Data
public class ClassField {
    private String reference;
    /**
     * 字段描述
     */
    private String classFieldDescribe;
    /**
     * 字段前缀
     */
    private String classModify;
    /**
     * 字段类型
     */
    private String fieldType;
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 引用列表
     */
    private List<String> importList;
    /**
     * 字段注解
     */
    private List<String> annotations;
}
