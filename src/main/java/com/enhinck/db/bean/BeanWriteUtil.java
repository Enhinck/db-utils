package com.enhinck.db.bean;

import com.enhinck.db.util.SqlUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanWriteUtil {

    public static void writeJavaBean(ClassObject classObject) {
        String path = classObject.getPath();


        path = path.replaceAll("\\\\", "/");

        // DO
        {
            StringBuilder doPath = new StringBuilder();
            doPath.append(path);
            StringBuilder doBeanContent = new StringBuilder();
            processPackage(classObject.getPackageName(), classObject.getClassName(), doPath, doBeanContent);
            processDOContent(classObject, doBeanContent);
            File javaBeanFile = new File(doPath.toString());
            try {
                FileUtils.write(javaBeanFile, doBeanContent.toString(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // DTO
        {
            StringBuilder dtoPath = new StringBuilder();
            dtoPath.append(path);
            StringBuilder dtoBeanContent = new StringBuilder();
            processPackage(classObject.getDtoPackageName(), classObject.getDtoName(), dtoPath, dtoBeanContent);
            processDTOContent(classObject, dtoBeanContent);
            File javaBeanFileDTO = new File(dtoPath.toString());
            try {
                FileUtils.write(javaBeanFileDTO, dtoBeanContent.toString(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Mapper
        {
            StringBuilder mapperPath = new StringBuilder();
            mapperPath.append(path);
            StringBuilder mapperBeanContent = new StringBuilder();
            processPackage(classObject.getMapperPackageName(), classObject.getMapperName(), mapperPath, mapperBeanContent);
            processMapperContent(classObject, mapperBeanContent);
            File javaBeanFileMapper = new File(mapperPath.toString());
            try {
                FileUtils.write(javaBeanFileMapper, mapperBeanContent.toString(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // Service
        {
            StringBuilder servicePath = new StringBuilder();
            servicePath.append(path);
            StringBuilder serviceBeanContent = new StringBuilder();
            processPackage(classObject.getServicePackageName(), classObject.getServiceName(), servicePath, serviceBeanContent);
            processServiceContent(classObject, serviceBeanContent);
            File javaBeanFileService = new File(servicePath.toString());
            try {
                FileUtils.write(javaBeanFileService, serviceBeanContent.toString(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        // ServiceImpl
        {
            StringBuilder serviceImplPath = new StringBuilder();
            serviceImplPath.append(path);
            StringBuilder serviceImplBeanContent = new StringBuilder();
            processPackage(classObject.getServiceImplPackage(), classObject.getServiceImplName(), serviceImplPath, serviceImplBeanContent);
            processServiceImplContent(classObject, serviceImplBeanContent);
            File javaBeanFileServiceImpl = new File(serviceImplPath.toString());
            try {
                FileUtils.write(javaBeanFileServiceImpl, serviceImplBeanContent.toString(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void processDOContent(ClassObject classObject, StringBuilder doBeanContent) {
        doBeanContent.append("\n");
        if (classObject.isUseLombok()) {
            doBeanContent.append("import lombok.Data;\n\n");
        }
        classObject.getImportClassList().forEach(importClass ->
                doBeanContent.append("import ").append(importClass).append(";\n")
        );
        doBeanContent.append("import javax.persistence.GeneratedValue;\n");
        doBeanContent.append("import javax.persistence.Id;\n");
        if (StringUtils.isNotBlank(classObject.getTableName())) {
            doBeanContent.append("import javax.persistence.Table;\n");
        }
        doBeanContent.append("import java.io.Serializable;\n");
        doBeanContent.append("\n");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        doBeanContent.append("/**\n" +
                " * ").append(classObject.getComment()).append("\n" +
                " *\n" +
                " * @author huenb\n" +
                " * @date ").append(dateFormat.format(now)).append("\n" +
                " */\n");
        if (classObject.isUseLombok()) {
            doBeanContent.append("@Data\n");
        }
        if (StringUtils.isNotBlank(classObject.getTableName())) {
            doBeanContent.append("@Table(name = \"").append(classObject.getTableName()).append("\")\n");
        }
        doBeanContent.append("public class ").append(classObject.getClassName()).append(" implements Serializable {\n");
        doBeanContent.append("\n");
        classObject.getClassFieldList().forEach(classField -> {
            // 字段注释
            doBeanContent.append("    /**\n" +
                    "     * ").append(classField.getComment()).append("\n" +
                    "     */\n");
            // 字段
            if (classField.getName().equals("id")){
                doBeanContent.append("    @Id\n" +
                        "    @GeneratedValue(generator = \"JDBC\")\n");
            }
            doBeanContent.append("    private ").append(classField.getFieldType()).append(" ").append(classField.getName()).append(";\n");
            // 空行
            doBeanContent.append("\n");
        });
        doBeanContent.append("}");
    }


    private static void processDTOContent(ClassObject classObject, StringBuilder dtoBeanContent) {
        dtoBeanContent.append("\n");
        if (classObject.isUseLombok()) {
            dtoBeanContent.append("import lombok.Data;\n");
        }
        dtoBeanContent.append("import org.springframework.beans.BeanUtils;\n\n");

        classObject.getImportClassList().forEach(importClass ->
                dtoBeanContent.append("import ").append(importClass).append(";\n")
        );
        dtoBeanContent.append("import java.io.Serializable;\n");
        dtoBeanContent.append("\n");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dtoBeanContent.append("/**\n" +
                " * ").append(classObject.getComment()).append("\n" +
                " *\n" +
                " * @author huenb\n" +
                " * @date ").append(dateFormat.format(now)).append("\n" +
                " */\n");
        if (classObject.isUseLombok()) {
            dtoBeanContent.append("@Data\n");
        }
        dtoBeanContent.append("public class ").append(classObject.getDtoName()).append(" implements Serializable {\n");
        dtoBeanContent.append("\n");
        classObject.getClassFieldList().forEach(classField -> {
            // 字段注释
            dtoBeanContent.append("    /**\n" +
                    "     * ").append(classField.getComment()).append("\n" +
                    "     */\n");
            // 字段
            dtoBeanContent.append("    private ").append(classField.getFieldType()).append(" ").append(classField.getName()).append(";\n");
            // 空行
            dtoBeanContent.append("\n");
        });

        dtoBeanContent.append("    public <T> T copyPropertiesTo(T target, String... ignoreProperties) {\n" +
                "        BeanUtils.copyProperties(this, target, ignoreProperties);\n" +
                "        return target;\n" +
                "    }\n");


        dtoBeanContent.append("}");
    }

    private static void processMapperContent(ClassObject classObject, StringBuilder mapperBeanContent) {
        mapperBeanContent.append("\n");
        mapperBeanContent.append("import com.greentown.common.mapper.IBaseMapper;\n");
        mapperBeanContent.append("import ").append(classObject.getPackageName()).append(".").append(classObject.getClassName()).append(";\n");
        mapperBeanContent.append("import org.springframework.stereotype.Repository;\n");
        mapperBeanContent.append("\n");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapperBeanContent.append("/**\n" +
                " * ").append(classObject.getComment()).append("\n" +
                " *\n" +
                " * @author huenb\n" +
                " * @date ").append(dateFormat.format(now)).append("\n" +
                " */\n");
        mapperBeanContent.append("@Repository\n");
        mapperBeanContent.append("public interface ").append(classObject.getMapperName()).append(" extends IBaseMapper<").append(classObject.getClassName()).append("> {\n");
        mapperBeanContent.append("\n");
        mapperBeanContent.append("}");
    }

    private static void processServiceContent(ClassObject classObject, StringBuilder serviceBeanContent) {
        serviceBeanContent.append("\n");
        serviceBeanContent.append("import com.greentown.common.model.page.PageBean;\n");
        serviceBeanContent.append("import ").append(classObject.getDtoPackageName()).append(".").append(classObject.getDtoName()).append(";\n");
        serviceBeanContent.append("\n");
        serviceBeanContent.append("import java.util.List;\n");
        serviceBeanContent.append("\n");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        serviceBeanContent.append("/**\n" +
                " * ").append(classObject.getComment()).append("\n" +
                " *\n" +
                " * @author huenb\n" +
                " * @date ").append(dateFormat.format(now)).append("\n" +
                " */\n");

        String param = SqlUtil.firstToLowerCase(classObject.getDtoName());

        serviceBeanContent.append("public interface ").append(classObject.getServiceName()).append(" {\n");
        serviceBeanContent.append("\n");

        serviceBeanContent.append("  /**\n" +
                "     * 新增\n" +
                "     *\n" +
                "     * @param ").append(param).append("\n" +
                "     */\n" +
                "    void save").append(classObject.getDtoName()).append("(").append(classObject.getDtoName()).append(" ").append(param).append(");\n\n");

        serviceBeanContent.append("  /**\n" +
                "     * 修改\n" +
                "     *\n" +
                "     * @param ").append(param).append("\n" +
                "     */\n" +
                "    void update").append(classObject.getDtoName()).append("(").append(classObject.getDtoName()).append(" ").append(param).append(");\n\n");

        serviceBeanContent.append("  /**\n" +
                "     * 删除\n" +
                "     *\n" +
                "     * @param id" +
                "     */\n" +
                "    void delete").append(classObject.getDtoName()).append("(Long id);\n\n");

        serviceBeanContent.append("  /**\n" +
                "     * 查询\n" +
                "     *\n" +
                "     * @param ").append(param).append("\n" +
                "     */\n" +
                "    List<").append(classObject.getDtoName()).append("> select").append(classObject.getDtoName()).append("(").append(classObject.getDtoName()).append(" ").append(param).append(");\n\n");

        serviceBeanContent.append("  /**\n" +
                "     * 分页查询\n" +
                "     * @param ").append(param).append("\n" +
                "     * @param pageNum\n" +
                "     * @param numPerPage\n" +
                "     * @return\n" +
                "     */\n" +
                "    PageBean<").append(classObject.getDtoName()).append("> selectPage").append(classObject.getDtoName()).append("(").append(classObject.getDtoName()).append(" ").append(param).append(", int pageNum, int numPerPage);\n\n");


        serviceBeanContent.append("}");
    }


    private static void processServiceImplContent(ClassObject classObject, StringBuilder serviceBeanContent) {
        serviceBeanContent.append("\n");


        serviceBeanContent.append("import com.github.pagehelper.Page;\n");
        serviceBeanContent.append("import com.github.pagehelper.PageHelper;\n");
        serviceBeanContent.append("import com.greentown.common.model.page.PageBean;\n");
        serviceBeanContent.append("import com.greentown.common.util.DataTransformer;\n");
        serviceBeanContent.append("import ").append(classObject.getMapperPackageName()).append(".").append(classObject.getMapperName()).append(";\n");
        serviceBeanContent.append("import ").append(classObject.getPackageName()).append(".").append(classObject.getClassName()).append(";\n");
        serviceBeanContent.append("import ").append(classObject.getDtoPackageName()).append(".").append(classObject.getDtoName()).append(";\n");
        serviceBeanContent.append("import ").append(classObject.getServicePackageName()).append(".").append(classObject.getServiceName()).append(";\n");
        serviceBeanContent.append("import org.apache.commons.lang3.StringUtils;\n");
        serviceBeanContent.append("import org.springframework.stereotype.Service;\n");
        serviceBeanContent.append("import tk.mybatis.mapper.entity.Example;\n");
        serviceBeanContent.append("import tk.mybatis.mapper.util.Sqls;\n\n");
        serviceBeanContent.append("import javax.annotation.Resource;\n");
        serviceBeanContent.append("import java.util.List;\n\n");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        serviceBeanContent.append("/**\n" +
                " * ").append(classObject.getComment()).append("\n" +
                " *\n" +
                " * @author huenb\n" +
                " * @date ").append(dateFormat.format(now)).append("\n" +
                " */\n");
        String serviceName = SqlUtil.firstToLowerCase(classObject.getServiceName());

        String param = SqlUtil.firstToLowerCase(classObject.getDtoName());
        String mapper = SqlUtil.firstToLowerCase(classObject.getMapperName());

        serviceBeanContent.append("@Service(\"").append(serviceName).append("\")\n");
        serviceBeanContent.append("public class ").append(classObject.getServiceImplName()).append(" implements ").append(classObject.getServiceName()).append(" {\n");
        serviceBeanContent.append("\n");
        serviceBeanContent.append("    @Resource\n" +
                "    private ").append(classObject.getMapperName()).append(" ").append(mapper).append(";\n\n");

        serviceBeanContent.append("  /**\n" +
                "     * 新增\n" +
                "     *\n" +
                "     * @param ").append(param).append("\n" +
                "     */\n" +
                "    @Override\n" +
                "    public void save").append(classObject.getDtoName()).append("(").append(classObject.getDtoName()).append(" ").append(param).append("){\n");




        serviceBeanContent.append("        ").append(mapper).append(".insertSelective(").append(param).append(".copyPropertiesTo(new ").append(classObject.getClassName()).append("()));\n");
        serviceBeanContent.append("    }\n");

        serviceBeanContent.append("  /**\n" +
                "     * 修改\n" +
                "     *\n" +
                "     * @param ").append(param).append("\n" +
                "     */\n" +
                "    @Override\n" +
                "    public void update").append(classObject.getDtoName()).append("(").append(classObject.getDtoName()).append(" ").append(param).append("){\n");
        serviceBeanContent.append("        ").append(mapper).append(".updateByPrimaryKeySelective(").append(param).append(".copyPropertiesTo(new ").append(classObject.getClassName()).append("()));\n");


        serviceBeanContent.append("    }\n");

        serviceBeanContent.append("  /**\n" +
                "     * 删除\n" +
                "     *\n" +
                "     * @param id" +
                "     */\n" +
                "    @Override\n" +
                "    public void delete").append(classObject.getDtoName()).append("(Long id){\n");

        serviceBeanContent.append("        ").append(mapper).append(".deleteByPrimaryKey(id);\n");


        serviceBeanContent.append("    }\n");
        serviceBeanContent.append("  /**\n" +
                "     * 查询\n" +
                "     *\n" +
                "     * @param ").append(param).append("\n" +
                "     */\n" +
                "    @Override\n" +
                "    public List<").append(classObject.getDtoName()).append("> select").append(classObject.getDtoName()).append("(").append(classObject.getDtoName()).append(" ").append(param).append("){\n");

        serviceBeanContent.append("        Sqls sqls = Sqls.custom();\n");

        dynamicSql(classObject, serviceBeanContent, param);
        String dos = SqlUtil.firstToLowerCase(classObject.getClassName()) + "S";
        serviceBeanContent.append("        Example example = Example.builder(").append(classObject.getClassName()).append(".class).where(sqls).build();\n");
        serviceBeanContent.append("        List<").append(classObject.getClassName()).append("> ").append(dos).append(" = ").append(mapper).append(".selectByExample(example);\n");
        serviceBeanContent.append("        return DataTransformer.transform(").append(dos).append(", ").append(classObject.getDtoName()).append(".class);\n");

        serviceBeanContent.append("    }\n");
        serviceBeanContent.append("  /**\n" +
                "     * 分页查询\n" +
                "     * @param ").append(param).append("\n" +
                "     * @param pageNum\n" +
                "     * @param numPerPage\n" +
                "     * @return\n" +
                "     */\n" +
                "    @Override\n" +
                "    public PageBean<").append(classObject.getDtoName()).append("> selectPage").append(classObject.getDtoName()).append("(").append(classObject.getDtoName()).append(" ").append(param).append(", int pageNum, int numPerPage){\n");

        serviceBeanContent.append("        Sqls sqls = Sqls.custom();\n");
        dynamicSql(classObject, serviceBeanContent, param);
        serviceBeanContent.append("        Example example = Example.builder(").append(classObject.getClassName()).append(".class).where(sqls).build();\n");
        serviceBeanContent.append("        Page page = PageHelper.startPage(pageNum, numPerPage);\n");
        serviceBeanContent.append("        List<").append(classObject.getClassName()).append("> ").append(dos).append(" = ").append(mapper).append(".selectByExample(example);\n");
        String dtos = SqlUtil.firstToLowerCase(classObject.getDtoName()) + "S";
        serviceBeanContent.append("        List<").append(classObject.getDtoName()).append("> ").append(dtos).append(" = DataTransformer.transform(").append(dos).append(", ").append(classObject.getDtoName()).append(".class);\n");
        serviceBeanContent.append("        return new PageBean<>(pageNum, numPerPage, page.getTotal(), ").append(dtos).append(");\n");

        serviceBeanContent.append("    }\n");
        serviceBeanContent.append("}");
    }

    private static void dynamicSql(ClassObject classObject, StringBuilder serviceBeanContent, String param) {
        classObject.getClassFieldList().forEach(classField -> {

            String getMethod = classField.methodGetName();
            if (classField.getFieldType().equals("String")) {
                serviceBeanContent.append("        if (StringUtils.isNotBlank(").append(param).append(".").append(getMethod).append("())) {\n")
                        .append("            sqls.andEqualTo(\"").append(classField.getName()).append("\", ").append(param).append(".").append(getMethod).append("());\n")
                        .append("        }\n");
            } else {
                serviceBeanContent.append("        if (").append(param).append(".").append(getMethod).append("() != null) {\n")
                        .append("            sqls.andEqualTo(\"").append(classField.getName()).append("\", ").append(param).append(".").append(getMethod).append("());\n")
                        .append("        }\n");
            }
        });
    }


    private static void processDOPackage(ClassObject classObject, StringBuilder doPath, StringBuilder beanContent) {
        if (StringUtils.isNotBlank(classObject.getPackageName())) {
            String[] folders = classObject.getPackageName().split("\\.");
            for (int i = 0; i < folders.length; i++) {
                doPath.append("/").append(folders[i]);
            }
            File file = new File(doPath.toString());
            if (!file.isDirectory()) {
                file.mkdirs();
            }
            doPath.append("/").append(classObject.getClassName()).append(".java");

            beanContent.append("package ").append(classObject.getPackageName()).append(";");
            beanContent.append("\n");
        }
    }


    private static void processPackage(String packageName, String className, StringBuilder classpath, StringBuilder javacontent) {
        if (StringUtils.isNotBlank(packageName)) {
            String[] folders = packageName.split("\\.");
            for (int i = 0; i < folders.length; i++) {
                classpath.append("/").append(folders[i]);
            }
            File file = new File(classpath.toString());
            if (!file.isDirectory()) {
                file.mkdirs();
            }
            classpath.append("/").append(className).append(".java");

            javacontent.append("package ").append(packageName).append(";");
            javacontent.append("\n");
        }
    }

}
