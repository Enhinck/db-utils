package com.enhinck.db.util;

import com.enhinck.db.annotation.ETable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SqlUtil {

    @Data
    public static class Sqls {
        private Class<?> classType;
        private Map<String, Object> params = new HashedMap();
        private List<String> orderAsc = new ArrayList<>();

        public Sqls() {
        }

        public Sqls orderByAsc(String field) {
            orderAsc.add(field);
            return this;
        }

        public Sqls(Class<?> classType) {
            this.classType = classType;
        }

        public Sqls andEqualTo(String field, String value) {
            params.put(field, value);
            return this;
        }

        public String build() {
            StringBuilder builder = new StringBuilder();
            if (params.size() > 0) {
                builder.append(" where ");
                params.forEach((key, value) -> {
                    builder.append(" ").append(camelCaseToUnderScoreCase(key)).append(" = ").append("? ").append("AND");
                });
                builder.delete(builder.length() - 3, builder.length());
            }

            if (orderAsc.size() > 0) {
                builder.append(" order by ");
                orderAsc.forEach(column -> {
                    builder.append(" ").append(camelCaseToUnderScoreCase(column)).append(",");
                });
                builder.delete(builder.length() - 1, builder.length());
                builder.append(" asc");
            }

            return builder.toString();
        }

        public void setParams(PreparedStatement pstmt) {
            AtomicInteger count = new AtomicInteger(1);
            params.forEach((key, value) -> {
                try {
                    pstmt.setObject(count.get(), value);
                } catch (SQLException e) {
                    log.error("{}", e);
                }
                count.getAndIncrement();
            });
        }
    }


    public static Sqls getWhere(Class<?> classType) {
        return new Sqls(classType);
    }

    public static String getSelectSql(Class<?> classType, String where) {
        Field[] fields = classType.getDeclaredFields();
        ETable eTable = classType.getAnnotation(ETable.class);
        String tableName = null;
        if (eTable == null) {
            tableName = camelCaseToUnderScoreCase(classType.getSimpleName());
        } else {
            tableName = eTable.value();
        }
        final StringBuilder bsql = new StringBuilder();
        bsql.append("select ");
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (!isStatic) {
                String fieldName = camelCaseToUnderScoreCase(field.getName());
                bsql.append(fieldName);
                bsql.append(" ,");
            }
        }
        bsql.delete(bsql.length() - 1, bsql.length());
        bsql.append(" from " + tableName);
        bsql.append(" ").append(where);
        return bsql.toString();
    }

    public static String getSelectAllSql(String tableName) {
        final StringBuilder bsql = new StringBuilder();
        bsql.append("select * from " + tableName);
        return bsql.toString();
    }


    static String getMethodName(String fieldName) {
        String first = fieldName.substring(0, 1).toUpperCase();
        String end = fieldName.substring(1, fieldName.length());
        return first + end;
    }


    public static <T> T getDbData(Class<T> classType, ResultSet rs) {
        T object = null;
        String thisName = "";
        try {
            object = classType.newInstance();
            Field[] fields = classType.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                boolean isStatic = Modifier.isStatic(field.getModifiers());
                if (!isStatic) {
                    String fieldName = field.getName();
                    thisName = fieldName;
                    Class<?> filedType = fields[i].getType();
                    String methodSetName = "set" + getMethodName(fieldName);
                    Method method = classType.getMethod(methodSetName,
                            filedType);
                    Object value = rs.getObject(camelCaseToUnderScoreCase(fieldName));
                    if (value != null) {
                        method.invoke(object, value);
                    }
                }
            }
        } catch (Exception e) {
            log.error("column：{}，Exception：{}", e, thisName);
        }

        return object;
    }

    private static Pattern camelCasePattern = Pattern.compile("[A-Z]");
    private static Pattern underScoreCasePattern = Pattern.compile("_(\\w)");

    /**
     * 下划线转驼峰
     */
    public static String underScoreCaseToCamelCase(String str) {
        str = str.toLowerCase();
        Matcher matcher = underScoreCasePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String toJavaName(String str) {
        String name = underScoreCaseToCamelCase(str);
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * 驼峰转下划线
     */
    public static String camelCaseToUnderScoreCase(String str) {
        Matcher matcher = camelCasePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     */
    public static String firstToLowerCase(String str) {
        StringBuffer sb = new StringBuffer();
        sb.append(str.substring(0, 1).toLowerCase()).append(str.substring(1, str.length()));
        return sb.toString();
    }

    public static String getGetMethodName(String fieldName) {
        StringBuilder builder = new StringBuilder();
        String first = fieldName.substring(0, 1).toUpperCase();
        String end = fieldName.substring(1, fieldName.length());
        builder.append("get").append(first).append(end);
        return builder.toString();
    }
}
