package com.enhinck.db.freemark.tkmapper;

import com.enhinck.db.excel.JavaDefineEntity;
import com.enhinck.db.excel.JavaFieldEntity;
import com.enhinck.db.freemark.BaseFacotry;
import com.enhinck.db.freemark.ClassGenerics;
import com.enhinck.db.freemark.FreemarkUtil;
import com.enhinck.db.freemark.JavaClassEntity;

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
        return "ScServiceImpl";
    }

    public String end() {
        return "ServiceImpl";
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


        return javaClassEntity;
    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }
}
