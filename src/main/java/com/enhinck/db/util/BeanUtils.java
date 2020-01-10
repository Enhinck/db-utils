package com.enhinck.db.util;

import com.enhinck.db.excel.ExcelColumn;
import com.enhinck.db.excel.ExcelReportDemo;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author huenb
 */
@Slf4j
public class BeanUtils {

    public static String getExceptionAllMessage(Exception ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        ex.printStackTrace(pout);
        String ret = new String(out.toByteArray());
        try {
            pout.close();
            out.close();
        } catch (Exception e) {
        }
        return ret;
    }

    public static Map<String, Object> getParams(Object object) {
        Map<String, Object> params = new LinkedHashMap<>();
        Class<?> tempClass = object.getClass();
        List<Field> fields = new ArrayList<>();
        // 当父类为null的时候说明到达了最上层的父类(Object类).
        while (tempClass != null) {
            fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            // 得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                Object value = field.get(object);
                if (value != null) {
                    params.put(key, value);
                }
            } catch (IllegalArgumentException e) {
                log.warn("{}", e);
            } catch (IllegalAccessException e) {
                log.warn("{}", e);
            }
        }
        params = sortMapByKey(params);

        return params;
    }

    public static Map<ExcelColumn, Object> getExcelData(Object object) {
        Map<ExcelColumn, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (!isStatic) {
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                if (excelColumn != null) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(object);
                        map.put(excelColumn, value);
                    } catch (IllegalAccessException e) {
                        log.warn("getExcelData error {}", e);
                    }
                }
            }
        }
        return map;
    }

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }

    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return map;
        }
        Map<String, Object> sortMap = new TreeMap<String, Object>(new MapKeyComparator());
        sortMap.putAll(map);
        Map<String, Object> linked = new LinkedHashMap<>();
        linked.putAll(sortMap);
        return linked;
    }


    public static String[] getList(String names) {
        return names.split(",");
    }


    public static Object invokGet(Object object, String field) {
        if (object == null) {
            return null;
        }
        try {
            Class<?> classType = object.getClass();
            String methodSetName = getGetMethodName(field);
            Method method = classType.getMethod(methodSetName);
            Object obj = method.invoke(object);
            return obj;
        } catch (Exception e) {
            log.warn("{}", e);
        }
        return null;
    }

    public static Object invokMethod(Object object, String methodName, Object... params) {
        if (object == null) {
            return null;
        }
        try {
            Class[] paramTypes = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                paramTypes[i] = params[i].getClass();
            }
            Class<?> classType = object.getClass();
            if (params.length == 0) {
                Method method = classType.getMethod(methodName);
                Object obj = method.invoke(object);
                return obj;
            } else {
                Method method = classType.getMethod(methodName, paramTypes);
                Object obj = method.invoke(object, params);
                return obj;
            }
        } catch (Exception e) {
            log.warn("调用{}的{}方法异常:{}", object, methodName, e);
        }
        return null;
    }


    private static String getGetMethodName(String fieldName) {
        StringBuilder builder = new StringBuilder();
        String first = fieldName.substring(0, 1).toUpperCase();
        String end = fieldName.substring(1, fieldName.length());
        builder.append("get").append(first).append(end);
        return builder.toString();
    }


}
