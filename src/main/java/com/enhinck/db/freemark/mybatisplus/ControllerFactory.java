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
public class ControllerFactory extends BaseFacotry {
    private static final ControllerFactory doFactory = new ControllerFactory();

    public static ControllerFactory getInstance() {
        return doFactory;
    }


    private ControllerFactory() {

    }


    public JavaClassEntity create(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        JavaClassEntity javaClassEntity = createClass(javaDefineEntity);
        String className = getControllerName(javaDefineEntity);
        javaClassEntity.setClassName(className);

        List<String> annotations = new ArrayList<>();
        annotations.add("@Slf4j");
        annotations.add("@Api(description = \"" + javaDefineEntity.getDescribe() + "管理\", tags = {\"" + className + "\"})");
        annotations.add("@RestController");
        annotations.add("@AllArgsConstructor");
        javaClassEntity.setAnnotations(annotations);

        Set<String> importList = new HashSet<>();

        importList.add("java.util.List");
        importList.add("lombok.*");
        importList.add("io.swagger.annotations.*");
        importList.add("lombok.extern.slf4j.Slf4j");
        importList.add("org.springframework.web.bind.annotation.*");

        String responseWrapper = null;

        String pageWrapper = null;
        if (isIoc){
            importList.add("com.greentown.common.response.WebResponseEntity");
            importList.add("com.baomidou.mybatisplus.extension.plugins.pagination.Page");
            responseWrapper = "WebResponseEntity";
            pageWrapper = "Page";
        }else {
            importList.add("com.greentown.phantom.utils.DataTransformer");
            importList.add("com.greentown.phantom.common.response.WebResponse");
            importList.add("com.kingboy.common.utils.page.PageResult");
            importList.add("com.greentown.mybatisplus.util.BaseSearchVO");
            responseWrapper = "WebResponse";
            pageWrapper = "PageResult";
        }




        importList.addAll(javaDefineEntity.getControllerImports());

        javaClassEntity.setImportList(importList);
        importList.addAll(javaDefineEntity.getControllerImports());
        javaClassEntity.setPackagePath(javaDefineEntity.getControllerPackageName());

        List<ClassField> fields = new ArrayList<>();
        ClassField classField = new ClassField();
        classField.setFieldType(getServiceName(javaDefineEntity));
        String serviceName = lowerCaseFirstChar(getServiceName(javaDefineEntity));
        classField.setFieldName(serviceName);
        classField.setClassModify("final");
        fields.add(classField);
        javaClassEntity.setFields(fields);


        List<ClassMethod> classMethods = new ArrayList<>();
        javaClassEntity.setMethods(classMethods);
        {
            ClassMethod classMethod = new ClassMethod();
            classMethod.setClassMethodDescribe("列表");
            classMethod.setMethodReturnType(responseWrapper+"<List<" + javaDefineEntity.getJavaName() + "VO>>");
            classMethod.setMethodName("list");
            List<String> methodAnnotations = new ArrayList<>();
            methodAnnotations.add("@ApiOperation(\"列表\")");
            methodAnnotations.add("@GetMapping(\"/" + javaDefineEntity.getJavaName().toLowerCase() + "s\")");
            classMethod.setAnnotations(methodAnnotations);
            List<String> codes = new ArrayList<>();
            codes.add("return "+responseWrapper+".ok(" + serviceName + ".listVO());");
            classMethod.setCodes(codes);
            classMethods.add(classMethod);
        }

        {
            ClassMethod classMethod = new ClassMethod();
            classMethod.setMethodReturnType(responseWrapper+"<"+pageWrapper+"<" + javaDefineEntity.getJavaName() + "VO>>");
            classMethod.setMethodName("page");
            classMethod.setClassMethodDescribe("分页");
            List<String> methodAnnotations = new ArrayList<>();
            methodAnnotations.add("@ApiOperation(\"分页\")");
            methodAnnotations.add("@GetMapping(\"/" + javaDefineEntity.getJavaName().toLowerCase() + "/page\")");
            classMethod.setAnnotations(methodAnnotations);
            ClassField methodParam = new ClassField();

            if (isIoc){
                methodParam.setFieldType("Page<" + javaDefineEntity.getJavaName() + "VO>");
                methodParam.setFieldName("page");
            }else {
                methodParam.setFieldType("BaseSearchVO");
                methodParam.setFieldName("page");
            }


            methodParam.setClassFieldDescribe("分页查询条件");
            List<ClassField> paramTypes = new ArrayList<>();
            paramTypes.add(methodParam);
            classMethod.setMethodParamTypes(paramTypes);
            List<String> codes = new ArrayList<>();

            if (isIoc){
                codes.add("return "+responseWrapper+".ok(" + serviceName + ".pageVO(page));");
            }else {
                codes.add("return "+responseWrapper+".ok(" + serviceName + ".basePageVO(page));");
            }

            classMethod.setCodes(codes);
            classMethods.add(classMethod);
        }

        {
            ClassMethod classMethod = new ClassMethod();
            classMethod.setMethodReturnType(responseWrapper+"<Boolean>");
            classMethod.setMethodName("add");
            classMethod.setClassMethodDescribe("新增");
            List<String> methodAnnotations = new ArrayList<>();
            methodAnnotations.add("@ApiOperation(\"新增\")");
            methodAnnotations.add("@PostMapping(\"/" + javaDefineEntity.getJavaName().toLowerCase() + "\")");
            classMethod.setAnnotations(methodAnnotations);
            ClassField methodParam = new ClassField();
            methodParam.setFieldType("@RequestBody " + javaDefineEntity.getJavaName() + "VO");
            methodParam.setFieldName("entityVO");
            methodParam.setClassFieldDescribe("新增对象");
            List<ClassField> paramTypes = new ArrayList<>();
            paramTypes.add(methodParam);
            classMethod.setMethodParamTypes(paramTypes);
            List<String> codes = new ArrayList<>();
            codes.add("return "+responseWrapper+".ok(" + serviceName + ".saveVO(entityVO));");
            classMethod.setCodes(codes);
            classMethods.add(classMethod);
        }

        {
            ClassMethod classMethod = new ClassMethod();
            classMethod.setMethodReturnType(responseWrapper+"<Boolean>");
            classMethod.setMethodName("update");
            classMethod.setClassMethodDescribe("新增");
            List<String> methodAnnotations = new ArrayList<>();
            methodAnnotations.add("@ApiOperation(\"新增\")");
            methodAnnotations.add("@PutMapping(\"/" + javaDefineEntity.getJavaName().toLowerCase() + "\")");
            classMethod.setAnnotations(methodAnnotations);
            ClassField methodParam = new ClassField();
            methodParam.setFieldType("@RequestBody " + javaDefineEntity.getJavaName() + "VO");
            methodParam.setFieldName("entityVO");
            List<ClassField> paramTypes = new ArrayList<>();
            paramTypes.add(methodParam);
            classMethod.setMethodParamTypes(paramTypes);
            List<String> codes = new ArrayList<>();
            codes.add("return "+responseWrapper+".ok(" + serviceName + ".updateVOById(entityVO));");
            classMethod.setCodes(codes);
            classMethods.add(classMethod);
        }

        {
            ClassMethod classMethod = new ClassMethod();
            classMethod.setMethodReturnType(responseWrapper+"<" + javaDefineEntity.getJavaName() + "VO>");
            classMethod.setMethodName("get");
            classMethod.setClassMethodDescribe("查询");
            List<String> methodAnnotations = new ArrayList<>();
            methodAnnotations.add("@ApiOperation(\"查询\")");
            methodAnnotations.add("@GetMapping(\"/" + javaDefineEntity.getJavaName().toLowerCase() + "/{id}\")");
            classMethod.setAnnotations(methodAnnotations);
            ClassField methodParam = new ClassField();
            methodParam.setFieldType("@PathVariable(\"id\") Long");
            methodParam.setFieldName("id");
            List<ClassField> paramTypes = new ArrayList<>();
            paramTypes.add(methodParam);
            classMethod.setMethodParamTypes(paramTypes);
            List<String> codes = new ArrayList<>();
            codes.add("return "+responseWrapper+".ok(" + serviceName + ".getVOById(id));");
            classMethod.setCodes(codes);
            classMethods.add(classMethod);
        }

        {
            ClassMethod classMethod = new ClassMethod();
            classMethod.setMethodReturnType(responseWrapper+"<Boolean>");
            classMethod.setMethodName("delete");
            classMethod.setClassMethodDescribe("删除");
            List<String> methodAnnotations = new ArrayList<>();
            methodAnnotations.add("@ApiOperation(\"删除\")");
            methodAnnotations.add("@DeleteMapping(\"/" + javaDefineEntity.getJavaName().toLowerCase() + "/{id}\")");
            classMethod.setAnnotations(methodAnnotations);
            ClassField methodParam = new ClassField();
            methodParam.setFieldType("@PathVariable(\"id\") Long");
            methodParam.setFieldName("id");
            List<ClassField> paramTypes = new ArrayList<>();
            paramTypes.add(methodParam);
            classMethod.setMethodParamTypes(paramTypes);
            List<String> codes = new ArrayList<>();
            codes.add("return "+responseWrapper+".ok(" + serviceName + ".removeById(id));");
            classMethod.setCodes(codes);
            classMethods.add(classMethod);
        }


        return javaClassEntity;
    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }


}
