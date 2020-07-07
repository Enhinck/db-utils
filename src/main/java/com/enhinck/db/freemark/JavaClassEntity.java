package com.enhinck.db.freemark;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类对等实体对象
 *
 * @author huenbin
 * @date 2020-07-01 11:05
 */
@Data
public class JavaClassEntity {
    /**
     * 包路径
     */
    private String packagePath;




    /**
     * 引入的对象申明
     */
    public Set<String> importList;
    /**
     * 类描述
     */
    private String classDescribe;
    /**
     * 类注解
     */
    private List<String> annotations;
    /**
     * 类的类型
     */
    private String classType;
    /**
     * 类名
     */
    private String className;
    /**
     * 父类
     */
    private String extendsClass;
    /**
     * 创建时间
     */
    private String createDateTime;
    /**
     * 父类
     */
    private List<String> implementsInterfaces;
    /**
     * 类泛型列表
     */
    private List<ClassGenerics> extendsGenerics;

    /**
     * 字段列表
     */
    private List<ClassField> fields;
    /**
     * 方法列表
     */
    private List<ClassMethod> methods;

    public String getCreateDateTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    public Set<String> getImportList() {
        if (importList == null) {
            importList = new HashSet<>();
        }
        if (extendsGenerics != null) {
            extendsGenerics.forEach(generics -> {
                if (generics.getReference() != null) {
                    importList.add(generics.getReference());
                }
            });


        }
        if (fields != null) {
            fields.forEach(field -> {
                if (field.getImportList() != null) {
                    importList.addAll(field.getImportList());
                }
            });
        }
        if (methods != null) {
            methods.forEach(method -> {
                if (method.getImportList() != null) {
                    importList.addAll(method.getImportList());
                }
            });
        }
        return importList;
    }
}
