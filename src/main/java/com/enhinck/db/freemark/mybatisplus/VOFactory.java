package com.enhinck.db.freemark.mybatisplus;

import com.enhinck.db.excel.JavaDefineEntity;
import com.enhinck.db.excel.JavaFieldEntity;
import com.enhinck.db.freemark.BaseFacotry;
import com.enhinck.db.freemark.FreemarkUtil;
import com.enhinck.db.freemark.JavaClassEntity;

import java.util.List;
import java.util.Set;

/**
 * VO工厂
 *
 * @author huenbin
 * @date 2020-07-02 17:00
 */
public class VOFactory extends BaseFacotry {
    private static final VOFactory doFactory = new VOFactory();

    public static VOFactory getInstance() {
        return doFactory;
    }

    private VOFactory() {

    }


    @Override
    public String parentClass() {
        if (isIoc) {
            return "BaseVO";
        }
        return null;
    }


    public JavaClassEntity create(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        JavaClassEntity javaClassEntity = createClass(javaDefineEntity);
        String className = getVOName(javaDefineEntity);
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


        return javaClassEntity;
    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }
}
