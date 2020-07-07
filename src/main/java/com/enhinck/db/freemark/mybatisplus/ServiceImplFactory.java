package com.enhinck.db.freemark.mybatisplus;

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
public class ServiceImplFactory extends BaseFacotry {
    private static final ServiceImplFactory doFactory = new ServiceImplFactory();

    public static ServiceImplFactory getInstance() {
        return doFactory;
    }

    private ServiceImplFactory() {

    }

    @Override
    public String parentClass() {
        return "ScServiceImpl";
    }


    public JavaClassEntity create(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        JavaClassEntity javaClassEntity = createClass(javaDefineEntity);
        String className = getServiceImplName(javaDefineEntity);
        javaClassEntity.setClassName(className);

        List<String> annotations = new ArrayList<>();
        annotations.add("@Service");
        javaClassEntity.setAnnotations(annotations);

        List<ClassGenerics> classGenerics = new ArrayList<>();
        classGenerics.add(new ClassGenerics(getMapperName(javaDefineEntity)));
        addDTV(classGenerics, javaDefineEntity);
        javaClassEntity.setExtendsGenerics(classGenerics);

        List<String> implementsInterfaces = new ArrayList<>();
        implementsInterfaces.add(getServiceName(javaDefineEntity));
        javaClassEntity.setImplementsInterfaces(implementsInterfaces);
        Set<String> importList = new HashSet<>();
        importList.add("com.greentown.mybatisplus.service.IScService");
        importList.add("com.greentown.mybatisplus.service.impl.ScServiceImpl");
        importList.add("org.springframework.stereotype.Service");
        importList.addAll(javaDefineEntity.getServiceImplImports());


        javaClassEntity.setImportList(importList);
        javaClassEntity.setPackagePath(javaDefineEntity.getServiceImplPackageName());

        List<ClassMethod> classMethods = new ArrayList<>();
        javaClassEntity.setMethods(classMethods);
        if (!isIoc) {
            importList.add("com.kingboy.common.utils.page.PageResult");
            importList.add("com.greentown.phantom.utils.DataTransformer");
            importList.add("com.baomidou.mybatisplus.core.toolkit.Wrappers");
            importList.add("com.github.pagehelper.Page");
            importList.add("com.github.pagehelper.PageHelper");
            importList.add("com.greentown.mybatisplus.util.BaseSearchVO");
            importList.add("java.util.List");


            ClassMethod classMethod = new ClassMethod();
            classMethod.setMethodReturnType("PageResult<" + getVOName(javaDefineEntity) + ">");
            classMethod.setMethodName("basePageVO");
            classMethod.setClassMethodDescribe("分页");
            ClassField methodParam = new ClassField();
            methodParam.setFieldType("BaseSearchVO");
            methodParam.setFieldName("baseSearchVO");
            methodParam.setClassFieldDescribe("基础分页查询条件");
            List<ClassField> paramTypes = new ArrayList<>();
            paramTypes.add(methodParam);
            classMethod.setMethodParamTypes(paramTypes);
            List<String> codes = new ArrayList<>();
            codes.add("// 分页信息");
            codes.add("Page page = PageHelper.startPage(baseSearchVO.getP(), baseSearchVO.getC());");
            codes.add("// 查询分页");
            codes.add("List<" + getDOName(javaDefineEntity) + "> " + lowerCaseFirstChar(getDOName(javaDefineEntity)) + "S = getBaseMapper().selectList(Wrappers.emptyWrapper());");
            codes.add("List<" + getVOName(javaDefineEntity) + "> " + lowerCaseFirstChar(getVOName(javaDefineEntity)) + "S = DataTransformer.transform(" + lowerCaseFirstChar(getDOName(javaDefineEntity)) + "S, " + getVOName(javaDefineEntity) + ".class);");
            codes.add("return new PageResult<>(page.getPageNum(), page.getTotal(), " + lowerCaseFirstChar(getVOName(javaDefineEntity)) + "S);");
            classMethod.setCodes(codes);
            classMethods.add(classMethod);
        }

        return javaClassEntity;
    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }
}
