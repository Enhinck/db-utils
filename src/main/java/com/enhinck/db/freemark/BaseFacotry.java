package com.enhinck.db.freemark;

import com.enhinck.db.excel.JavaDefineEntity;
import com.enhinck.db.excel.JavaFieldEntity;
import com.enhinck.db.freemark.ClassField;
import com.enhinck.db.freemark.ClassGenerics;
import com.enhinck.db.freemark.JavaClassEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 描述
 *
 * @author huenbin
 * @date 2020-07-07 15:36
 */
public class BaseFacotry {
    public static String lowerCaseFirstChar(String name) {

        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    public static boolean isIoc = true;

    public String parentClass() {
        return null;
    }

    protected List<String> commonPOJOAnnotations() {
        List<String> annotations = new ArrayList<>();
        annotations.add("@Data");
        if (isIoc) {
            annotations.add("@EqualsAndHashCode(callSuper = true)");
        }

        return annotations;
    }

    protected List<String> commonVODTOAnnotations(JavaDefineEntity javaDefineEntity) {
        List<String> annotations = commonPOJOAnnotations();
        annotations.add("@ApiModel(\"" + javaDefineEntity.getDescribe() + "\")");
        return annotations;
    }

    protected Set<String> commonPOJOImports() {
        Set<String> imports = new HashSet<>();
        imports.add("lombok.*");
        return imports;
    }

    protected Set<String> commonDTOVOImports() {
        Set<String> imports = commonPOJOImports();
        imports.add("io.swagger.annotations.*");
        return imports;
    }


    protected void processField(JavaDefineEntity<JavaFieldEntity> javaDefineEntity, JavaClassEntity javaClassEntity) {
        List<ClassField> fields = new ArrayList<>();
        List<JavaFieldEntity> javaFieldEntities = javaDefineEntity.getList();
        for (JavaFieldEntity javaFieldEntity : javaFieldEntities) {
            ClassField classField = new ClassField();
            List<String> fieldAnnontations = new ArrayList<>();
            fieldAnnontations.add("@ApiModelProperty(value = \"" + javaFieldEntity.getDescribe() + "\")");
            classField.setAnnotations(fieldAnnontations);
            classField.setFieldType(javaFieldEntity.javaType());
            classField.setFieldName(javaFieldEntity.javaFieldName());
            fields.add(classField);
        }
        javaClassEntity.setFields(fields);
    }


    protected JavaClassEntity createClass(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        JavaClassEntity javaClassEntity = new JavaClassEntity();
        javaClassEntity.setClassType("class");
        if (parentClass() != null) {
            javaClassEntity.setExtendsClass(parentClass());
        }
        javaClassEntity.setClassDescribe(javaDefineEntity.getDescribe());
        return javaClassEntity;
    }

    protected JavaClassEntity createInteface(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        JavaClassEntity javaClassEntity = new JavaClassEntity();
        javaClassEntity.setClassType("interface");
        if (parentClass() != null) {
            javaClassEntity.setExtendsClass(parentClass());
        }
        javaClassEntity.setClassDescribe(javaDefineEntity.getDescribe());
        return javaClassEntity;
    }


    public static String getServiceName(JavaDefineEntity javaDefineEntity) {
        return javaDefineEntity.getJavaName() + "Service";
    }

    public static String getServiceImplName(JavaDefineEntity javaDefineEntity) {
        return javaDefineEntity.getJavaName() + "ServiceImpl";
    }

    public static String getDOName(JavaDefineEntity javaDefineEntity) {
        return javaDefineEntity.getJavaName() + "DO";
    }

    public static String getDTOName(JavaDefineEntity javaDefineEntity) {
        return javaDefineEntity.getJavaName() + "DTO";
    }

    public static String getVOName(JavaDefineEntity javaDefineEntity) {
        return javaDefineEntity.getJavaName() + "VO";
    }

    public static String getControllerName(JavaDefineEntity javaDefineEntity) {
        return javaDefineEntity.getJavaName() + "Controller";
    }

    public static String getMapperName(JavaDefineEntity javaDefineEntity) {
        return javaDefineEntity.getJavaName() + "Mapper";
    }

    protected void addDTV(List<ClassGenerics> classGenerics, JavaDefineEntity javaDefineEntity) {
        classGenerics.add(new ClassGenerics(getDOName(javaDefineEntity)));
        classGenerics.add(new ClassGenerics(getDTOName(javaDefineEntity)));
        classGenerics.add(new ClassGenerics(getVOName(javaDefineEntity)));
    }
}
