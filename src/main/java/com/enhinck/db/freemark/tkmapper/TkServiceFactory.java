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
public class TkServiceFactory extends BaseFacotry {
    private static final TkServiceFactory doFactory = new TkServiceFactory();

    public static TkServiceFactory getInstance() {
        return doFactory;
    }

    private TkServiceFactory() {

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
        addDTV(classGenerics,javaDefineEntity);

        javaClassEntity.setExtendsGenerics(classGenerics);
        Set<String> importList = new HashSet<>();
        importList.add("com.greentown.mybatisplus.service.IScService");

        importList.addAll(javaDefineEntity.getServiceImports());

        javaClassEntity.setImportList(importList);
        javaClassEntity.setPackagePath(javaDefineEntity.getServicePackageName());


        return javaClassEntity;
    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }
}
