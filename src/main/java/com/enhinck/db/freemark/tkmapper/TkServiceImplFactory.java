package com.enhinck.db.freemark.tkmapper;

import com.enhinck.db.excel.FieldTypeEnum;
import com.enhinck.db.excel.JavaDefineEntity;
import com.enhinck.db.excel.JavaFieldEntity;
import com.enhinck.db.freemark.*;
import com.enhinck.db.util.SqlUtil;

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
public class TkServiceImplFactory extends BaseFacotry {
    private static final TkServiceImplFactory doFactory = new TkServiceImplFactory();

    public static TkServiceImplFactory getInstance() {
        return doFactory;
    }

    private TkServiceImplFactory() {

    }

    @Override
    public String parentClass() {
        return "TkServiceImpl";
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
        importList.add("com.greentown.tkmapper.service.ITkService");
        importList.add("com.greentown.tkmapper.service.impl.TkServiceImpl");
        importList.add("org.springframework.stereotype.Service");
        importList.add("org.apache.commons.lang.StringUtils");

        importList.add("com.greentown.common.model.page.PageBean");
        importList.add("com.greentown.common.util.DataTransformer");

        importList.addAll(javaDefineEntity.getServiceImplImports());
        importList.add(javaDefineEntity.getQueryDTOReference());
        importList.add("tk.mybatis.mapper.entity.Example");
        importList.add("tk.mybatis.mapper.util.Sqls");
        importList.add("com.github.pagehelper.Page");
        importList.add("com.github.pagehelper.PageHelper");
        importList.add("java.util.List");


        List<ClassMethod> classMethods = new ArrayList<>();
        javaClassEntity.setMethods(classMethods);
        {
            ClassMethod classMethod = new ClassMethod();
            classMethod.setClassMethodDescribe("组合分页查询");
            classMethod.setMethodReturnType("PageBean<" + javaDefineEntity.getJavaName() + "DTO>");
            classMethod.setMethodName("selectPage");
            List<String> methodAnnotations = new ArrayList<>();
            methodAnnotations.add("@Override");
            classMethod.setAnnotations(methodAnnotations);
            ClassField methodParam = new ClassField();
            methodParam.setFieldType(javaDefineEntity.getJavaName() + "QueryDTO");
            methodParam.setFieldName("queryDTO");
            methodParam.setClassFieldDescribe("查询对象");
            List<ClassField> paramTypes = new ArrayList<>();
            paramTypes.add(methodParam);
            classMethod.setMethodParamTypes(paramTypes);
            List<String> codes = new ArrayList<>();
            codes.add("Sqls sqls = Sqls.custom();");
            dynamicSql(javaDefineEntity, codes);
            codes.add("Example example = Example.builder(" + javaDefineEntity.getJavaName() + "DO.class).where(sqls).build();");
            codes.add("Page page = PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getNumPerPage());");
            codes.add("List<"+javaDefineEntity.getJavaName()+"DO> listDOS = getBaseMapper().selectByExample(example);");
            codes.add("List<"+javaDefineEntity.getJavaName()+"DTO> listDTOS = DataTransformer.transform(listDOS, "+javaDefineEntity.getJavaName()+"DTO.class);");
            codes.add("return new PageBean<>(queryDTO.getPageNum(), queryDTO.getNumPerPage(), page.getTotal(),listDTOS);");

            classMethod.setCodes(codes);
            classMethods.add(classMethod);
        }


        javaClassEntity.setImportList(importList);
        javaClassEntity.setPackagePath(javaDefineEntity.getServiceImplPackageName());


        return javaClassEntity;
    }

    private void dynamicSql(JavaDefineEntity<JavaFieldEntity> javaDefineEntity, List<String> codes) {
        javaDefineEntity.getList().forEach(classField -> {
            String getMethod = SqlUtil.getGetMethodName(classField.javaFieldName());
            if (classField.getFieldType().equals(FieldTypeEnum.STRING)) {
                codes.add("if (StringUtils.isNotBlank(queryDTO." + getMethod + "())) {");
                codes.add("    sqls.andEqualTo(\"" + classField.javaFieldName() + "\", queryDTO." + getMethod + "());");
                codes.add("}");
            } else {
                codes.add("if (queryDTO." + getMethod + "() != null) {");
                codes.add("    sqls.andEqualTo(\"" + classField.javaFieldName() + "\", queryDTO." + getMethod + "());");
                codes.add("}");
            }
        });

    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }
}
