package com.enhinck.db.freemark.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
public class ServiceFactory extends BaseFacotry {
    private static final ServiceFactory doFactory = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return doFactory;
    }

    private ServiceFactory() {

    }


    @Override
    public String parentClass() {
        return "IScService";
    }

    public JavaClassEntity create(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        JavaClassEntity javaClassEntity = createInteface(javaDefineEntity);
        String className = getServiceName(javaDefineEntity);
        javaClassEntity.setClassName(className);

        List<ClassGenerics> classGenerics = new ArrayList<>();
        addDTV(classGenerics, javaDefineEntity);

        javaClassEntity.setExtendsGenerics(classGenerics);
        Set<String> importList = new HashSet<>();
        importList.add("com.greentown.mybatisplus.service.IScService");


        importList.addAll(javaDefineEntity.getServiceImports());

        javaClassEntity.setImportList(importList);
        javaClassEntity.setPackagePath(javaDefineEntity.getServicePackageName());

        List<ClassMethod> classMethods = new ArrayList<>();
        javaClassEntity.setMethods(classMethods);
        if (!isIoc) {

            importList.add("com.kingboy.common.utils.page.PageResult");
            importList.add("com.greentown.mybatisplus.util.BaseSearchVO");
            ClassMethod classMethod = new ClassMethod();
            classMethod.setMethodReturnType("PageResult<" + getVOName(javaDefineEntity) + ">");
            classMethod.setMethodName("basePageVO");
            classMethod.setClassMethodDescribe("分页");
            ClassField methodParam = new ClassField();
            methodParam.setFieldType("BaseSearchVO");
            methodParam.setFieldName("page");
            methodParam.setClassFieldDescribe("基础分页查询条件");
            List<ClassField> paramTypes = new ArrayList<>();
            paramTypes.add(methodParam);
            classMethod.setMethodParamTypes(paramTypes);

            classMethods.add(classMethod);
        }


        return javaClassEntity;
    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }
}
