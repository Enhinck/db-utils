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
public class TkDOFactory extends BaseFacotry {
    private static final TkDOFactory doFactory = new TkDOFactory();

    public static TkDOFactory getInstance() {
        return doFactory;
    }

    private TkDOFactory() {

    }

    @Override
    public String parentClass() {
        return "BaseDO";
    }


    public JavaClassEntity create(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        JavaClassEntity javaClassEntity = createClass(javaDefineEntity);
        String className = getDOName(javaDefineEntity);
        javaClassEntity.setClassName(className);

        List<String> annotations = commonPOJOAnnotations();
        annotations.add("@Table(name = \"" + javaDefineEntity.getTableName() + "\")");
        javaClassEntity.setAnnotations(annotations);

        Set<String> importList = commonPOJOImports();
        importList.add("javax.persistence.Table");
        importList.add("com.greentown.common.model.domain.BaseDO");
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

                classField.setAnnotations(fieldAnnontations);
            } else {

                if (javaFieldEntity.isDelFlag()) {
                    List<String> fieldAnnontations = new ArrayList<>();
                    importList.add("com.greentown.tkmapper.annontation.TkDeleteFlag");
                    fieldAnnontations.add("@TkDeleteFlag(value = 0, delval = 1)");
                    classField.setAnnotations(fieldAnnontations);
                }

                fields.add(classField);
            }
        }
        javaClassEntity.setFields(fields);


        return javaClassEntity;
    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }

}
