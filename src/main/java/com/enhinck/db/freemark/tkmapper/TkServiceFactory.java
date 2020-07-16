package com.enhinck.db.freemark.tkmapper;

import com.enhinck.db.excel.JavaDefineEntity;
import com.enhinck.db.excel.JavaFieldEntity;
import com.enhinck.db.freemark.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * VO工厂
 *
 * @author huenbin
 * @date 2020-07-02 17:00
 */
public class TkServiceFactory extends BaseFacotry {
    private static final TkServiceFactory doFactory = new TkServiceFactory();

    public static TkServiceFactory getInstance() {
        return doFactory;
    }

    private TkServiceFactory() {

    }

    @Override
    public String parentClass() {
        return "ITkService";
    }

    public JavaClassEntity create(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        JavaClassEntity javaClassEntity = createInteface(javaDefineEntity);
        String className = getServiceName(javaDefineEntity);
        javaClassEntity.setClassName(className);

        List<ClassGenerics> classGenerics = new ArrayList<>();
        addDTV(classGenerics,javaDefineEntity);

        javaClassEntity.setExtendsGenerics(classGenerics);
        Set<String> importList = new HashSet<>();
        importList.add("com.greentown.tkmapper.service.ITkService");

        importList.add("com.greentown.common.model.page.PageBean");
        importList.addAll(javaDefineEntity.getServiceImports());
        importList.add(javaDefineEntity.getQueryDTOReference());
        javaClassEntity.setImportList(importList);
        javaClassEntity.setPackagePath(javaDefineEntity.getServicePackageName());

        List<ClassMethod> classMethods = new ArrayList<>();
        javaClassEntity.setMethods(classMethods);
        {
            ClassMethod classMethod = new ClassMethod();
            classMethod.setClassMethodDescribe("组合分页查询");
            classMethod.setMethodReturnType("PageBean<" + javaDefineEntity.getJavaName() + "DTO>");
            classMethod.setMethodName("selectPage");
            classMethods.add(classMethod);
            ClassField methodParam = new ClassField();
            methodParam.setFieldType(javaDefineEntity.getJavaName() + "QueryDTO");
            methodParam.setFieldName("queryDTO");
            methodParam.setClassFieldDescribe("查询对象");
            List<ClassField> paramTypes = new ArrayList<>();
            paramTypes.add(methodParam);
            classMethod.setMethodParamTypes(paramTypes);
        }


        return javaClassEntity;
    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }
}
