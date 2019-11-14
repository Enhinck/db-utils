package com.enhinck.db.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
public class ClassObject {
    private String path;
    private String comment;
    private String packageName;
    private String dtoPackageName;
    private String mapperPackageName;
    private String servicePackageName;
    private String voPackageName;
    private String controllerPackageName;
    private String dtoName;
    private String serviceName;
    private String mapperName;
    private String className;

    private String voName;
    private String controllerName;
    private boolean useLombok = true;
    private String tableName;
    private Set<String> importClassList = new LinkedHashSet<>();
    private List<ClassField> classFieldList = new ArrayList<>();


    public String getServiceImplPackage() {
        return servicePackageName + ".impl";
    }

    public String getServiceImplName() {
        return serviceName + "Impl";
    }

    public static void main(String[] args) {
        ClassObject classObject = new ClassObject();


        classObject.setClassName("TestDO");
        classObject.setPackageName("com.greentown.ioc.model.domain");
        classObject.setComment("测试");
        classObject.setTableName("tb_test");

        ClassField classField = new ClassField();
        classField.setComment("姓名");
        classField.setFieldType("String");
        classField.setName("name");
        classObject.getClassFieldList().add(classField);

        ClassField classField2 = new ClassField();
        classField2.setComment("性别");
        classField2.setFieldType("Integer");
        classField2.setName("sex");
        classObject.getClassFieldList().add(classField2);

        classObject.setPath("E:\\huenbin\\ioc_merge\\sc-ioc-api\\src\\main\\java");
        BeanWriteUtil.writeJavaBean(classObject);

    }
}
