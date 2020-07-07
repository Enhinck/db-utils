package com.enhinck.db.excel;

import com.enhinck.db.freemark.BaseFacotry;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author huenbin
 * @date 2020-07-02 17:13
 */
@Data
public class JavaDefineEntity<T> {

    private String sheetName;
    /**
     * java类名
     */
    private String javaName;
    /**
     * 数据库表名
     */
    private String tableName;
    /**
     * 描述
     */
    private String describe;
    /**
     * 字段列表
     */
    private List<T> list = new ArrayList<>();
    /**
     * 基础包名
     */
    private String basePackageName;


    public void addField(T t) {
        list.add(t);
    }

    public List<String> getControllerImports() {
        List<String> list = new ArrayList<>();
        list.add(getServiceReference());
        list.add(getVOReference());
        return list;
    }


    public List<String> getServiceImports() {
        List<String> list = new ArrayList<>();
        list.add(getDOReference());
        list.add(getDTOReference());
        list.add(getVOReference());
        return list;
    }


    public List<String> getServiceImplImports() {
        List<String> list = new ArrayList<>();
        list.add(getDOReference());
        list.add(getDTOReference());
        list.add(getVOReference());
        list.add(getMapperReference());
        list.add(getServiceReference());
        return list;
    }

    public List<String> getMapperImports() {
        List<String> list = new ArrayList<>();
        list.add(getDOReference());
        return list;
    }


    public String getDOReference(){
        return getDOPackageName() + "." +  BaseFacotry.getDOName(this);
    }

    public String getDTOReference(){
        return getDTOPackageName() + "." +  BaseFacotry.getDTOName(this);
    }
    public String getVOReference(){
        return getVOPackageName() + "." +  BaseFacotry.getVOName(this);
    }
    public String getServiceReference(){
        return getServicePackageName() + "." +  BaseFacotry.getServiceName(this);
    }
    public String getServiceImplReference(){
        return getServiceImplPackageName() + "." +  BaseFacotry.getServiceImplName(this);
    }
    public String getControllerReference(){
        return getControllerPackageName() + "." +  BaseFacotry.getControllerName(this);
    }
    public String getMapperReference(){
        return getMapperPackageName() + "." +  BaseFacotry.getMapperName(this);
    }


    public String getDOPackageName() {
        return basePackageName + ".model.domain";
    }

    public String getDTOPackageName() {
        return basePackageName + ".model.dto";
    }

    public String getVOPackageName() {
        return basePackageName + ".model.vo";
    }

    public String getServicePackageName() {
        return basePackageName + ".service";
    }

    public String getServiceImplPackageName() {
        return basePackageName + ".service.impl";
    }

    public String getControllerPackageName() {
        return basePackageName + ".controller";
    }

    public String getMapperPackageName() {
        return basePackageName + ".mapper";
    }
}
