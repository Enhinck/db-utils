package com.enhinck.db.freemark;

import com.enhinck.db.excel.MySQLEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 描述
 *
 * @author huenbin
 * @date 2020-07-01 11:00
 */
@Slf4j
public class FreemarkUtil {
    private static String templePath = "/Users/huenbin/work/otherProject/db-utils/src/main/resources/javaTemplate/";
    private static String CLASS_PATH = "/Users/huenbin/work/otherProject/db-utils/src/main/java/com/enhinck/db/freemark/hello/";

    private static String osName = null;

    private static boolean isLinux = false;

    static Configuration configuration = new Configuration();
    static Template javaTemplate;

    // step4 加载模版文件
    static Template mysqlTemplate;


    public static void setClassPath(String classPath) {
        if (classPath.endsWith("/") || classPath.endsWith("\\")) {
            CLASS_PATH = classPath;
        } else {
            if (isLinux) {
                CLASS_PATH = classPath + "/";
            } else {
                CLASS_PATH = classPath + "\\";
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FreemarkUtil.init();
        ClassPathResource resource = new ClassPathResource("javaTemplate/service.ftl");
        InputStream javaIn = resource.getInputStream();

        System.out.println(111);
        System.out.println(IOUtils.toString(javaIn, "UTF-8"));
    }

    public static void init() {
        // step2 获取模版路径
        try {


            File file = new File("");

            osName = System.getProperty("os.name");
            String workSpace = file.getAbsolutePath();
            log.info("工作目录：{}，工作系统：{}", file.getAbsolutePath(), osName);

            if (osName.contains("window")) {
                templePath = workSpace + "\\temple\\";
            } else {
                templePath = workSpace + "/temple/";
                isLinux = true;
            }


            log.info("TemplePath:{}", templePath);

            File fileTemple = new File(templePath);
            if (!fileTemple.exists()) {
                fileTemple.mkdirs();
                while (!fileTemple.exists()) {
                    waitM(100);
                }


                File javaFile = new File(templePath + "service.ftl");
                if (!javaFile.exists()) {
                    try {
                        javaFile.createNewFile();

                        ClassPathResource resource = new ClassPathResource("javaTemplate/service.ftl");
                        InputStream javaIn = resource.getInputStream();

                        FileOutputStream javaOutputStream = new FileOutputStream(javaFile);
                        IOUtils.copy(javaIn, javaOutputStream);
                        IOUtils.closeQuietly(javaIn);
                        IOUtils.closeQuietly(javaOutputStream);
                    } catch (Exception e) {

                    }

                }

                File mysqlFile = new File(templePath + "MySQL.ftl");
                if (!mysqlFile.exists()) {
                    try {
                        mysqlFile.createNewFile();
                        ClassPathResource resource = new ClassPathResource("javaTemplate/MySQL.ftl");
                        InputStream mysqlIn = resource.getInputStream();
                        FileOutputStream mysqlOutputStream = new FileOutputStream(mysqlFile);
                        IOUtils.copy(mysqlIn, mysqlOutputStream);
                        IOUtils.closeQuietly(mysqlIn);
                        IOUtils.closeQuietly(mysqlOutputStream);
                    } catch (Exception e) {

                    }
                }
            }

            configuration.setDirectoryForTemplateLoading(new File(templePath));
            // step4 加载模版文件
            javaTemplate = configuration.getTemplate("service.ftl");

            mysqlTemplate = configuration.getTemplate("MySQL.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }


        waitM(100);

    }

    public static void waitM(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void write(JavaClassEntity javaClassEntity) {
        Writer out = null;
        try {
            String packagePath = javaClassEntity.getPackagePath();
            String relativePath = null;
            if (isLinux) {
                relativePath = packagePath.replaceAll("\\.", "/") + "/";
            } else {
                relativePath = packagePath.replaceAll("\\.", "\\") + "\\";
            }
            File folder = new File(CLASS_PATH + relativePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // step5 生成数据
            File javaFile = new File(CLASS_PATH + relativePath + javaClassEntity.getClassName() + ".java");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javaFile)));
            // step6 输出文件
            javaTemplate.process(javaClassEntity, out);
            log.info("『{}』类生成成功", javaClassEntity.getClassName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void writeSql(MySQLEntity javaClassEntity) {
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(templePath));

            // step5 生成数据
            File docFile = new File(CLASS_PATH + "create" + ".sql");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            mysqlTemplate.process(javaClassEntity, out);
            log.info("sql脚本生成成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    public static void main2(String[] args) {
        // step1 创建freeMarker配置实例

        JavaClassEntity javaClassEntity = new JavaClassEntity();
        javaClassEntity.setClassType("class");

        javaClassEntity.setExtendsClass("TTT");


        List<String> interfaces = new ArrayList<>();
        interfaces.add("IService");

        javaClassEntity.setImplementsInterfaces(interfaces);
        List<ClassGenerics> classGenerics = new ArrayList<>();
        ClassGenerics classGenerics1 = new ClassGenerics();
        classGenerics1.setClassName("String");
        classGenerics.add(classGenerics1);
        ClassGenerics classGenerics2 = new ClassGenerics();
        classGenerics2.setClassName("String");
        classGenerics.add(classGenerics2);
        javaClassEntity.setExtendsGenerics(classGenerics);
        javaClassEntity.setClassName("Demo");
        javaClassEntity.setClassDescribe("案例");
        List<String> annotations = new ArrayList<>();
        annotations.add("@Data");
        javaClassEntity.setAnnotations(annotations);

        Set<String> importList = new HashSet<>();

        importList.add("lombok.Data");

        javaClassEntity.setImportList(importList);


        javaClassEntity.setPackagePath("com.enhinck.db.freemark.hello");
        List<ClassField> fields = new ArrayList<>();

        {
            ClassField classField = new ClassField();
            classField.setFieldType("String");
            classField.setFieldName("name");
            classField.setClassFieldDescribe("姓名");
            List<String> fieldAnnontations = new ArrayList<>();
            fieldAnnontations.add("@TableId(type = IdType.AUTO)");
            List<String> fieldimportList = new ArrayList<>();
            fieldimportList.add("com.baomidou.mybatisplus.annotation.*");
            classField.setImportList(fieldimportList);
            classField.setAnnotations(fieldAnnontations);
            fields.add(classField);
        }

        {
            ClassField classField = new ClassField();
            classField.setFieldType("Integer");
            classField.setFieldName("sex");
            classField.setClassFieldDescribe("性别");
            fields.add(classField);
        }

        javaClassEntity.setFields(fields);


        List<ClassMethod> classMethods = new ArrayList<>();
        ClassMethod classMethod = new ClassMethod();


        classMethod.setMethodReturnType("String");
        classMethod.setClassMethodDescribe("测试方法");
        classMethod.setMethodName("test");
        List<ClassField> paramTypes = new ArrayList<>();

        ClassField classField = new ClassField();
        classField.setFieldType("String");
        classField.setFieldName("string");
        classField.setClassFieldDescribe("字符串");
        paramTypes.add(classField);
        classMethod.setMethodParamTypes(paramTypes);


        List<String> codes = new ArrayList<>();

        codes.add("System.out.print(string);");
        codes.add("return string;");
        classMethod.setCodes(codes);


        classMethods.add(classMethod);


        javaClassEntity.setMethods(classMethods);


        write(javaClassEntity);
    }
}
