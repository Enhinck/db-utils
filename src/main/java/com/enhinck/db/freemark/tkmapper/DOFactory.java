package com.enhinck.db.freemark.tkmapper;

import com.enhinck.db.excel.JavaDefineEntity;
import com.enhinck.db.excel.JavaFieldEntity;
import com.enhinck.db.freemark.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * VO工厂
 *
 * @author huenbin
 * @date 2020-07-02 17:00
 */
public class DOFactory extends BaseFacotry {
    private static final DOFactory doFactory = new DOFactory();

    public static DOFactory getInstance() {
        return doFactory;
    }

    private DOFactory() {

    }

    @Override
    public String parentClass() {
        return "MyBatisPlusBaseDO";
    }


    public JavaClassEntity create(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        JavaClassEntity javaClassEntity = createClass(javaDefineEntity);
        String className = getDOName(javaDefineEntity);
        javaClassEntity.setClassName(className);

        List<ClassGenerics> classGenerics = new ArrayList<>();
        ClassGenerics classGeneric = new ClassGenerics();
        classGeneric.setClassName(className);
        classGenerics.add(classGeneric);
        javaClassEntity.setExtendsGenerics(classGenerics);

        List<String> annotations = commonPOJOAnnotations();
        javaClassEntity.setAnnotations(annotations);

        Set<String> importList = commonPOJOImports();
        importList.add("com.baomidou.mybatisplus.annotation.*");
        importList.add("com.greentown.mybatisplus.domain.MyBatisPlusBaseDO");
        javaClassEntity.setImportList(importList);

        javaClassEntity.setPackagePath(javaDefineEntity.getDOPackageName());
        List<ClassField> fields = new ArrayList<>();

        List<JavaFieldEntity> javaFieldEntities = javaDefineEntity.getList();
        for (JavaFieldEntity javaFieldEntity : javaFieldEntities) {
            ClassField classField = new ClassField();
            classField.setFieldType(javaFieldEntity.javaType());
            classField.setFieldName(javaFieldEntity.javaFieldName());
            classField.setClassFieldDescribe(javaFieldEntity.getDescribe());
            if (javaFieldEntity.isId()) {
                List<String> fieldAnnontations = new ArrayList<>();
                fieldAnnontations.add("@TableId(type = IdType.AUTO)");
                classField.setAnnotations(fieldAnnontations);
            }

            if (javaFieldEntity.isDelFlag()) {
                List<String> fieldAnnontations = new ArrayList<>();
                fieldAnnontations.add("@TableLogic(value = \"0\", delval = \"1\")");
                classField.setAnnotations(fieldAnnontations);
            }

            fields.add(classField);
        }
        javaClassEntity.setFields(fields);


        return javaClassEntity;
    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }

}
