package com.enhinck.db.freemark.mybatisplus;

import com.enhinck.db.excel.JavaDefineEntity;
import com.enhinck.db.excel.JavaFieldEntity;
import com.enhinck.db.freemark.BaseFacotry;
import com.enhinck.db.freemark.ClassField;
import com.enhinck.db.freemark.FreemarkUtil;
import com.enhinck.db.freemark.JavaClassEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * VO工厂
 *
 * @author huenbin
 * @date 2020-07-02 17:00
 */
public class QueryVOFactory extends BaseFacotry {
    private static final QueryVOFactory doFactory = new QueryVOFactory();

    public static QueryVOFactory getInstance() {
        return doFactory;
    }

    private QueryVOFactory() {

    }




    @Override
    public String parentClass() {
        if (isIoc) {
            return "BaseVO";
        } else {
            return null;
        }

    }


    public JavaClassEntity create(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        JavaClassEntity javaClassEntity = createClass(javaDefineEntity);
        String className = getQueryVOName(javaDefineEntity);
        javaClassEntity.setClassName(className);

        List<String> annotations = commonVODTOAnnotations(javaDefineEntity);
        javaClassEntity.setAnnotations(annotations);

        Set<String> importList = commonDTOVOImports();
        if (isIoc) {
            importList.add("com.greentown.common.model.vo.BaseVO");
        }


        javaClassEntity.setImportList(importList);

        javaClassEntity.setPackagePath(javaDefineEntity.getVOPackageName());

        processField(javaDefineEntity, javaClassEntity);


        ClassField classField = new ClassField();
        List<String> fieldAnnontations = new ArrayList<>();
        fieldAnnontations.add("@ApiModelProperty(value = \"页码\")");
        classField.setAnnotations(fieldAnnontations);
        classField.setFieldType("Integer");
        classField.setFieldName("pageNum");
        javaClassEntity.getFields().add(classField);
        ClassField classField2 = new ClassField();
        List<String> fieldAnnontations2 = new ArrayList<>();
        fieldAnnontations2.add("@ApiModelProperty(value = \"分页大小\")");
        classField2.setAnnotations(fieldAnnontations2);
        classField2.setFieldType("Integer");
        classField2.setFieldName("numPerPage");
        javaClassEntity.getFields().add(classField2);

        return javaClassEntity;
    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }
}
