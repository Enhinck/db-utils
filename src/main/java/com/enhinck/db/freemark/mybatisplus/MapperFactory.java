package com.enhinck.db.freemark.mybatisplus;

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
public class MapperFactory extends BaseFacotry {
    private static final MapperFactory doFactory = new MapperFactory();

    public static MapperFactory getInstance() {
        return doFactory;
    }

    private MapperFactory() {

    }

    @Override
    public String parentClass() {
        return "BaseMapper";
    }


    public JavaClassEntity create(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        JavaClassEntity javaClassEntity = createInteface(javaDefineEntity);
        String className = getMapperName(javaDefineEntity);
        javaClassEntity.setClassName(className);
        List<ClassGenerics> classGenerics = new ArrayList<>();
        ClassGenerics classGeneric = new ClassGenerics();
        classGeneric.setClassName(getDOName(javaDefineEntity));
        classGenerics.add(classGeneric);
        javaClassEntity.setExtendsGenerics(classGenerics);
        Set<String> importList = new HashSet<>();
        importList.add("com.baomidou.mybatisplus.core.mapper.BaseMapper");

        importList.addAll(javaDefineEntity.getMapperImports());

        javaClassEntity.setImportList(importList);
        javaClassEntity.setPackagePath(javaDefineEntity.getMapperPackageName());

        return javaClassEntity;
    }


    public void write(JavaDefineEntity<JavaFieldEntity> javaDefineEntity) {
        FreemarkUtil.write(create(javaDefineEntity));
    }
}
