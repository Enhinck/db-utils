package com.enhinck.db.excel;


import com.alibaba.fastjson.JSONObject;
import com.enhinck.db.freemark.FreemarkUtil;
import com.enhinck.db.freemark.mybatisplus.*;
import com.monitorjbl.xlsx.StreamingReader;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JavaDefineExcelReadUtil {


    @Data
    public static class ColumnObject {
        private Field field;
        private ExcelColumn excelColumn;
    }

    static Map<Class<?>, IExcelCellValueAdapt> cache = new HashedMap();


    public static void main(String[] args) throws Exception {
        FreemarkUtil.init();
        File file = new File("/Users/huenbin/代码生成模板.xlsx");

        List<JavaDefineEntity<JavaFieldEntity>> javaDefineEntities = getDataFromExcel(new FileInputStream(file), JavaFieldEntity.class);


        javaDefineEntities.forEach(javaFieldEntityJavaDefineEntity -> {


            DOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
            DTOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
            VOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
            MapperFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
            ServiceFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
            ServiceImplFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
            ControllerFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
        });

        MySQLFactory.getInstance().write(javaDefineEntities);

        log.info(JSONObject.toJSONString(javaDefineEntities));

    }



    /**
     * @param is        流
     * @param classType 类型
     * @return
     */
    public static <T> List<JavaDefineEntity<T>> getDataFromExcel(InputStream is, Class<T> classType) {
        List<JavaDefineEntity<T>> objects = new ArrayList<>();
        try {
            // 缓存读取 低内存占用
            Workbook workbook = StreamingReader.builder()
                    .rowCacheSize(50)
                    .bufferSize(4096)
                    .open(is);
            int sheetCount = 0;
            Field[] fields = classType.getDeclaredFields();
            Map<Integer, ColumnObject> colums = new HashMap<>();
            for (Sheet sheet : workbook) {
                String sheetName = sheet.getSheetName();
                JavaDefineEntity<T> javaDefineEntity = new JavaDefineEntity<>();
                log.info("读取sheet:{}开始", sheetName);


                String[] strings = sheetName.split("\\(");

                String beanName = strings[0];

                String[] descTable = strings[1].split("-");

                String describe = descTable[0];

                String tableName = descTable[1].split("\\)")[0];

                javaDefineEntity.setSheetName(sheetName);
                javaDefineEntity.setJavaName(beanName);
                javaDefineEntity.setTableName(tableName);
                javaDefineEntity.setDescribe(describe);
                processCellIndexColumn(workbook, fields, colums);

                int rowCount = 1;
                for (Row r : sheet) {
                    int cellIndex = 0;
                    T object = classType.newInstance();
                    for (Cell cell : r) {
                        ColumnObject columnObject = colums.get(cellIndex);
                        if (columnObject != null) {
                            ExcelColumn excelColumn = columnObject.getExcelColumn();
                            IExcelCellValueAdapt iExcelCellValueAdapt = cache.get(excelColumn.valueAdapt());
                            if (iExcelCellValueAdapt == null) {
                                iExcelCellValueAdapt = excelColumn.valueAdapt().newInstance();
                                cache.put(excelColumn.valueAdapt(), iExcelCellValueAdapt);
                            }
                            Object value = iExcelCellValueAdapt.getCellValue(cell);
                            columnObject.getField().setAccessible(true);
                            columnObject.getField().set(object, value);
                            log.info("读取sheet:{}，第{}行，第{}列完毕", sheet.getSheetName(), rowCount + 1, cellIndex + 1);
                        }
                        cellIndex++;
                    }
                    javaDefineEntity.addField(object);
                    rowCount++;
                }
                objects.add(javaDefineEntity);
                sheetCount++;
            }
        } catch (Exception e) {
            log.info("读取excel异常:{}", e);
        }
        return objects;
    }


    /**
     * 获取excel标题列
     *
     * @param workbook
     * @param fields
     * @param colums
     */
    private static void processCellIndexColumn(Workbook workbook, Field[] fields, Map<Integer, ColumnObject> colums) {
        // 反射获取对应字段值
        out:
        for (Sheet sheet : workbook) {
            for (Row r : sheet) {
                int cellIndex = 0;
                for (Cell cell : r) {
                    String columnName = cell.getStringCellValue();
                    for (int i = 0; i < fields.length; i++) {
                        Field field = fields[i];
                        ExcelColumn excelColumn = fields[i].getAnnotation(ExcelColumn.class);
                        if (excelColumn != null) {
                            if (excelColumn.name().equalsIgnoreCase(columnName)) {
                                ColumnObject columnObject = new ColumnObject();
                                columnObject.setExcelColumn(excelColumn);
                                columnObject.setField(field);
                                colums.put(cellIndex, columnObject);
                            }
                        }
                    }
                    cellIndex++;
                }
                break out;
            }
        }
    }

}
