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
public class DTOFactory extends BaseFacotry {
    private static final DTOFactory doFactory = new DTOFactory();

    public static DTOFactory getInstance() {
        return doFactory;
    }

    private DTOFactory() {

    }




    @Override
    public String parentClass() {
        if (isIoc) {
            return "BaseDTO";
        } else {
            return null;
        }

    }


    public JavaClassEntity create(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        JavaClassEntity javaClassEntity = createClass(javaDefineEntity);
        String className = getDTOName(javaDefineEntity);
        javaClassEntity.setClassName(className);

        List<String> annotations = commonVODTOAnnotations(javaDefineEntity);
        javaClassEntity.setAnnotations(annotations);

        Set<String> importList = commonDTOVOImports();
        if (isIoc) {
            importList.add("com.greentown.common.model.dto.BaseDTO");
        }

        javaClassEntity.setImportList(importList);

        javaClassEntity.setPackagePath(javaDefineEntity.getDTOPackageName());

        processField(javaDefineEntity, javaClassEntity);


        return javaClassEntity;
    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }
}
