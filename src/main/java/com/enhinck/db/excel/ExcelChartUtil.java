package com.enhinck.db.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAreaChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAreaSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAxDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTCatAx;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLegend;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTMarker;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTMarkerStyle;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTNumRef;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPie3DChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPieChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTScaling;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTSerTx;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTStrRef;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx;
import org.openxmlformats.schemas.drawingml.x2006.chart.STAxPos;
import org.openxmlformats.schemas.drawingml.x2006.chart.STBarDir;
import org.openxmlformats.schemas.drawingml.x2006.chart.STBarGrouping;
import org.openxmlformats.schemas.drawingml.x2006.chart.STBarGrouping.Enum;
import org.openxmlformats.schemas.drawingml.x2006.chart.STDispBlanksAs;
import org.openxmlformats.schemas.drawingml.x2006.chart.STGrouping;
import org.openxmlformats.schemas.drawingml.x2006.chart.STLegendPos;
import org.openxmlformats.schemas.drawingml.x2006.chart.STMarkerStyle;
import org.openxmlformats.schemas.drawingml.x2006.chart.STOrientation;
import org.openxmlformats.schemas.drawingml.x2006.chart.STTickLblPos;
/**
 * java利用poi生成excel图表
 *
 * @author chen
 *
 */
public class ExcelChartUtil {
    private static SXSSFWorkbook wb = new SXSSFWorkbook();
    private SXSSFSheet sheet = null;
    public static void main(String[] args) {
        // 字段名
        List<String> fldNameArr = new ArrayList<String>();
        // 标题
        List<String> titleArr = new ArrayList<String>();
        // 模拟数据
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataMap1 = new HashMap<String, Object>();
        dataMap1.put("value1", "股票");
        dataMap1.put("value2", Math.floor(Math.random() * 100) + "");
        dataMap1.put("value3", Math.floor(Math.random() * 100) + "");
        dataMap1.put("value4", Math.floor(Math.random() * 100) + "");
        Map<String, Object> dataMap2 = new HashMap<String, Object>();
        dataMap2.put("value1", "货币型基金");
        dataMap2.put("value2", Math.floor(Math.random() * 100) + "");
        dataMap2.put("value3", Math.floor(Math.random() * 100) + "");
        dataMap2.put("value4", Math.floor(Math.random() * 100) + "");
        Map<String, Object> dataMap3 = new HashMap<String, Object>();
        dataMap3.put("value1", "可转债");
        dataMap3.put("value2", Math.floor(Math.random() * 100) + "");
        dataMap3.put("value3", Math.floor(Math.random() * 100) + "");
        dataMap3.put("value4", Math.floor(Math.random() * 100) + "");
        Map<String, Object> dataMap4 = new HashMap<String, Object>();
        dataMap4.put("value1", "买入返售");
        dataMap4.put("value2", Math.floor(Math.random() * 100) + "");
        dataMap4.put("value3", Math.floor(Math.random() * 100) + "");
        dataMap4.put("value4", Math.floor(Math.random() * 100) + "");
        Map<String, Object> dataMap5 = new HashMap<String, Object>();
        dataMap5.put("value1", "通知存款");
        dataMap5.put("value2", Math.floor(Math.random() * 100) + "");
        dataMap5.put("value3", Math.floor(Math.random() * 100) + "");
        dataMap5.put("value4", Math.floor(Math.random() * 100) + "");
        Map<String, Object> dataMap6 = new HashMap<String, Object>();
        dataMap6.put("value1", "当月累计");
        dataMap6.put("value2", Math.floor(Math.random() * 100) + "");
        dataMap6.put("value3", Math.floor(Math.random() * 100) + "");
        dataMap6.put("value4", Math.floor(Math.random() * 100) + "");
        fldNameArr.add("value1");
        fldNameArr.add("value2");
        fldNameArr.add("value3");
        fldNameArr.add("value4");
        titleArr.add("类型");
        titleArr.add("买入");
        titleArr.add("卖出");
        titleArr.add("分红");
        dataList.add(dataMap1);
        dataList.add(dataMap2);
        dataList.add(dataMap3);
        dataList.add(dataMap4);
        dataList.add(dataMap5);
        dataList.add(dataMap6);
        ExcelChartUtil ecu = new ExcelChartUtil();
        try {
            // 创建柱状图
            ecu.createBarChart(titleArr, fldNameArr, dataList);
            // 创建饼状图
            ecu.createPieChart(titleArr, fldNameArr, dataList);
            // 创建折线图
            ecu.createTimeXYChar(titleArr, fldNameArr, dataList);
            // 创建面积图
            ecu.createAreaChart(titleArr, fldNameArr, dataList);
            //导出到文件
            FileOutputStream out = new FileOutputStream(new File("E:/" + System.currentTimeMillis() + ".xls"));
            wb.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建柱状图(堆积图，多组)
     *
     * @throws IOException
     */
    public void createBarChart(List<String> titleArr, List<String> fldNameArr, List<Map<String, Object>> dataList) {
        // 创建一个sheet页
        sheet = wb.createSheet("sheet0");
        // drawSheet0Table(sheet,titleArr,fldNameArr,dataList);
        // 堆积=STBarGrouping.STACKED 多组=STBarGrouping.CLUSTERED
        boolean result = drawSheet0Map(sheet, STBarGrouping.CLUSTERED, fldNameArr, dataList, titleArr);
        System.out.println("生成柱状图(堆积or多组)-->" + result);
    }
    /**
     * 生成柱状图
     *
     * @param sheet
     *            页签
     * @param group
     *            柱状图类型(堆积,多组)
     * @param fldNameArr
     *            坐标名称
     * @param dataList
     *            统计数据
     * @return
     */
    private boolean drawSheet0Map(SXSSFSheet sheet, Enum group, List<String> fldNameArr,
                                  List<Map<String, Object>> dataList, List<String> titleArr) {
        boolean result = false;
        // 获取sheet名称
        String sheetName = sheet.getSheetName();
        result = drawSheet0Table(sheet, titleArr, fldNameArr, dataList);
        // 创建一个画布
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        // 画一个图区域
        // 前四个默认0，从第8行到第25行,从第0列到第6列的区域
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 8, 6, 25);
        // 创建一个chart对象
        Chart chart = drawing.createChart(anchor);
        CTChart ctChart = ((XSSFChart) chart).getCTChart();
        CTPlotArea ctPlotArea = ctChart.getPlotArea();
        // 创建柱状图模型
        CTBarChart ctBarChart = ctPlotArea.addNewBarChart();
        CTBoolean ctBoolean = ctBarChart.addNewVaryColors();
        ctBarChart.getVaryColors().setVal(true);
        // 设置图类型
        ctBarChart.addNewGrouping().setVal(group);
        ctBoolean.setVal(true);
        ctBarChart.addNewBarDir().setVal(STBarDir.COL);
        // 是否添加左侧坐标轴
        ctChart.addNewDispBlanksAs().setVal(STDispBlanksAs.ZERO);
        ctChart.addNewShowDLblsOverMax().setVal(true);
        // 设置这两个参数是为了在STACKED模式下生成堆积模式；(standard)标准模式时需要将这两行去掉
        if ("stacked".equals(group.toString()) || "percentStacked".equals(group.toString())) {
            ctBarChart.addNewGapWidth().setVal(150);
            ctBarChart.addNewOverlap().setVal((byte) 100);
        }
        // 创建序列,并且设置选中区域
        for (int i = 0; i < fldNameArr.size() - 1; i++) {
            CTBarSer ctBarSer = ctBarChart.addNewSer();
            CTSerTx ctSerTx = ctBarSer.addNewTx();
            // 图例区
            CTStrRef ctStrRef = ctSerTx.addNewStrRef();
            // 选定区域第0行,第1,2,3列标题作为图例 //1 2 3
            String legendDataRange = new CellRangeAddress(0, 0, i + 1, i + 1).formatAsString(sheetName, true);
            ctStrRef.setF(legendDataRange);
            ctBarSer.addNewIdx().setVal(i);
            // 横坐标区
            CTAxDataSource cttAxDataSource = ctBarSer.addNewCat();
            ctStrRef = cttAxDataSource.addNewStrRef();
            // 选第0列,第1-6行作为横坐标区域
            String axisDataRange = new CellRangeAddress(1, dataList.size(), 0, 0).formatAsString(sheetName, true);
            ctStrRef.setF(axisDataRange);
            // 数据区域
            CTNumDataSource ctNumDataSource = ctBarSer.addNewVal();
            CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
            // 选第1-6行,第1-3列作为数据区域 //1 2 3
            String numDataRange = new CellRangeAddress(1, dataList.size(), i + 1, i + 1).formatAsString(sheetName,
                    true);
            System.out.println(numDataRange);
            ctNumRef.setF(numDataRange);
            // 添加柱状边框线
            ctBarSer.addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[] { 0, 0, 0 });
            // 设置负轴颜色不是白色
            ctBarSer.addNewInvertIfNegative().setVal(false);
            // 设置标签格式
            ctBoolean.setVal(false);
            CTDLbls newDLbls = ctBarSer.addNewDLbls();
            newDLbls.setShowLegendKey(ctBoolean);
            ctBoolean.setVal(true);
            newDLbls.setShowVal(ctBoolean);
            ctBoolean.setVal(false);
            newDLbls.setShowCatName(ctBoolean);
            newDLbls.setShowSerName(ctBoolean);
            newDLbls.setShowPercent(ctBoolean);
            newDLbls.setShowBubbleSize(ctBoolean);
            newDLbls.setShowLeaderLines(ctBoolean);
        }
        // 告诉BarChart它有坐标轴，并给它们id
        ctBarChart.addNewAxId().setVal(123456);
        ctBarChart.addNewAxId().setVal(123457);
        // 横坐标
        CTCatAx ctCatAx = ctPlotArea.addNewCatAx();
        ctCatAx.addNewAxId().setVal(123456); // id of the cat axis
        CTScaling ctScaling = ctCatAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctCatAx.addNewAxPos().setVal(STAxPos.B);
        ctCatAx.addNewCrossAx().setVal(123457); // id of the val axis
        ctCatAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        // 纵坐标
        CTValAx ctValAx = ctPlotArea.addNewValAx();
        ctValAx.addNewAxId().setVal(123457); // id of the val axis
        ctScaling = ctValAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        // 设置位置
        ctValAx.addNewAxPos().setVal(STAxPos.L);
        ctValAx.addNewCrossAx().setVal(123456); // id of the cat axis
        ctValAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        // 是否删除主左边轴
        ctValAx.addNewDelete().setVal(false);
        // 是否删除横坐标
        ctCatAx.addNewDelete().setVal(false);
        // legend图注
        // if(true){
        CTLegend ctLegend = ctChart.addNewLegend();
        ctLegend.addNewLegendPos().setVal(STLegendPos.B);
        ctLegend.addNewOverlay().setVal(false);
        // }
        return result;
    }
    /**
     * 创建横向柱状图
     *
     * @throws IOException
     */
    public void createAreaChart(List<String> titleArr, List<String> fldNameArr,
                                List<Map<String, Object>> dataList) {
        // 创建一个sheet页
        sheet = wb.createSheet("sheet1");
        boolean result = drawSheet1Map(sheet, "is3D", fldNameArr, dataList, titleArr);
        System.out.println("生成面积图-->" + result);
    }
    /**
     * 生成面积图
     * @param sheet
     * @param type
     * @param fldNameArr
     * @param dataList
     * @param titleArr
     * @return
     */
    private boolean drawSheet1Map(SXSSFSheet sheet, String type, List<String> fldNameArr,
                                  List<Map<String, Object>> dataList, List<String> titleArr) {
        boolean result = false;
        // 获取sheet名称
        String sheetName = sheet.getSheetName();
        result = drawSheet0Table(sheet, titleArr, fldNameArr, dataList);
        // 创建一个画布
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        // 画一个图区域
        // 前四个默认0，从第8行到第25行,从第0列到第6列的区域
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 8, 6, 25);
        // 创建一个chart对象
        Chart chart = drawing.createChart(anchor);
        CTChart ctChart = ((XSSFChart) chart).getCTChart();
        CTPlotArea ctPlotArea = ctChart.getPlotArea();
        CTAreaChart ctAreaChart = ctPlotArea.addNewAreaChart();
        CTBoolean ctBoolean = ctAreaChart.addNewVaryColors();
        ctAreaChart.addNewGrouping().setVal(STGrouping.STANDARD);
        // 创建序列,并且设置选中区域
        for (int i = 2; i < fldNameArr.size() - 1; i++) {
            CTAreaSer ctAreaSer = ctAreaChart.addNewSer();
            CTSerTx ctSerTx = ctAreaSer.addNewTx();
            // 图例区
            CTStrRef ctStrRef = ctSerTx.addNewStrRef();
            // 选定区域第0行,第1,2,3列标题作为图例 //1 2 3
            String legendDataRange = new CellRangeAddress(0, 0, i + 1, i + 1).formatAsString(sheetName, true);
            ctStrRef.setF(legendDataRange);
            ctAreaSer.addNewIdx().setVal(i);
            // 横坐标区
            CTAxDataSource cttAxDataSource = ctAreaSer.addNewCat();
            ctStrRef = cttAxDataSource.addNewStrRef();
            // 选第0列,第1-6行作为横坐标区域
            String axisDataRange = new CellRangeAddress(1, dataList.size(), 0, 0).formatAsString(sheetName, true);
            ctStrRef.setF(axisDataRange);
            // 数据区域
            CTNumDataSource ctNumDataSource = ctAreaSer.addNewVal();
            CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
            // 选第1-6行,第1-3列作为数据区域 //1 2 3
            String numDataRange = new CellRangeAddress(1, dataList.size(), i + 1, i + 1).formatAsString(sheetName,
                    true);
            System.out.println(numDataRange);
            ctNumRef.setF(numDataRange);
            // 设置标签格式
            ctBoolean.setVal(false);
            CTDLbls newDLbls = ctAreaSer.addNewDLbls();
            newDLbls.setShowLegendKey(ctBoolean);
            ctBoolean.setVal(true);
            newDLbls.setShowVal(ctBoolean);
            ctBoolean.setVal(false);
            newDLbls.setShowCatName(ctBoolean);
            newDLbls.setShowSerName(ctBoolean);
            newDLbls.setShowPercent(ctBoolean);
            newDLbls.setShowBubbleSize(ctBoolean);
            newDLbls.setShowLeaderLines(ctBoolean);
            /*
             * //是否是平滑曲线 CTBoolean addNewSmooth = ctAreaSer.addNewSmooth();
             * addNewSmooth.setVal(false); //是否是堆积曲线 CTMarker addNewMarker =
             * ctAreaSer.addNewMarker(); CTMarkerStyle addNewSymbol =
             * addNewMarker.addNewSymbol();
             * addNewSymbol.setVal(STMarkerStyle.NONE);
             */
        }
        // telling the BarChart that it has axes and giving them Ids
        ctAreaChart.addNewAxId().setVal(123456);
        ctAreaChart.addNewAxId().setVal(123457);
        // cat axis
        CTCatAx ctCatAx = ctPlotArea.addNewCatAx();
        ctCatAx.addNewAxId().setVal(123456); // id of the cat axis
        CTScaling ctScaling = ctCatAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctCatAx.addNewAxPos().setVal(STAxPos.B);
        ctCatAx.addNewCrossAx().setVal(123457); // id of the val axis
        ctCatAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        // val axis
        CTValAx ctValAx = ctPlotArea.addNewValAx();
        ctValAx.addNewAxId().setVal(123457); // id of the val axis
        ctScaling = ctValAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctValAx.addNewAxPos().setVal(STAxPos.L);
        ctValAx.addNewCrossAx().setVal(123456); // id of the cat axis
        ctValAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        // 是否删除主左边轴
        ctValAx.addNewDelete().setVal(false);
        // 是否删除横坐标
        ctCatAx.addNewDelete().setVal(false);
        // legend图注
        CTLegend ctLegend = ctChart.addNewLegend();
        ctLegend.addNewLegendPos().setVal(STLegendPos.B);
        ctLegend.addNewOverlay().setVal(false);
        return result;
    }
    /**
     * 创建饼状图
     *
     * @throws IOException
     */
    public void createPieChart(List<String> titleArr, List<String> fldNameArr, List<Map<String, Object>> dataList) {
        // 创建一个sheet页
        sheet = wb.createSheet("sheet2");
        boolean result = drawSheet2Map(sheet, "is3D", fldNameArr, dataList, titleArr);
        System.out.println("生成饼状图(普通or3D)-->" + result);
    }
    /**
     * 创建饼状图
     *
     * @param sheet
     *            页签
     * @param type
     *            图类型(3D或者普通)
     * @param fldNameArr
     *            (类标题)
     * @param dataList
     *            (填充数据)
     * @param titleArr
     *            (标题)
     * @return
     */
    private boolean drawSheet2Map(SXSSFSheet sheet, String type, List<String> fldNameArr,
                                  List<Map<String, Object>> dataList, List<String> titleArr) {
        boolean result = false;
        // 获取sheet名称
        String sheetName = sheet.getSheetName();
        result = drawSheet0Table(sheet, titleArr, fldNameArr, dataList);
        // 创建一个画布
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        // 画一个图区域
        // 前四个默认0，从第8行到第25行,从第0列到第6列的区域
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 8, 6, 25);
        // 创建一个chart对象
        Chart chart = drawing.createChart(anchor);
        CTChart ctChart = ((XSSFChart) chart).getCTChart();
        CTPlotArea ctPlotArea = ctChart.getPlotArea();
        CTBoolean ctBoolean = null;
        CTPie3DChart ctPie3DChart = null;
        CTPieChart ctPieChart = null;
        // 创建饼状图模型
        if (type.equals("is3D")) {
            ctPie3DChart = ctPlotArea.addNewPie3DChart();
            ctBoolean = ctPie3DChart.addNewVaryColors();
        } else {
            ctPieChart = ctPlotArea.addNewPieChart();
            ctBoolean = ctPieChart.addNewVaryColors();
        }
        // 创建序列,并且设置选中区域
        for (int i = 0; i < fldNameArr.size() - 1; i++) {
            CTPieSer ctPieSer = null;
            if (type.equals("is3D")) {
                ctPieSer = ctPie3DChart.addNewSer();
            } else {
                ctPieSer = ctPieChart.addNewSer();
            }
            CTSerTx ctSerTx = ctPieSer.addNewTx();
            // 图例区
            CTStrRef ctStrRef = ctSerTx.addNewStrRef();
            // 选定区域第0行,第1,2,3列标题作为图例 //1 2 3
            String legendDataRange = new CellRangeAddress(0, 0, i + 1, i + 1).formatAsString(sheetName, true);
            ctStrRef.setF(legendDataRange);
            ctPieSer.addNewIdx().setVal(i);
            // 横坐标区
            CTAxDataSource cttAxDataSource = ctPieSer.addNewCat();
            ctStrRef = cttAxDataSource.addNewStrRef();
            // 选第0列,第1-6行作为横坐标区域
            String axisDataRange = new CellRangeAddress(1, dataList.size(), 0, 0).formatAsString(sheetName, true);
            ctStrRef.setF(axisDataRange);
            // 数据区域
            CTNumDataSource ctNumDataSource = ctPieSer.addNewVal();
            CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
            // 选第1-6行,第1-3列作为数据区域 //1 2 3
            String numDataRange = new CellRangeAddress(1, dataList.size(), i + 1, i + 1).formatAsString(sheetName,
                    true);
            System.out.println(numDataRange);
            ctNumRef.setF(numDataRange);
            // 显示边框线
            ctPieSer.addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[] { 0, 0, 0 });
            // 设置标签格式
            ctBoolean.setVal(true);
        }
        // legend图注
        CTLegend ctLegend = ctChart.addNewLegend();
        ctLegend.addNewLegendPos().setVal(STLegendPos.B);
        ctLegend.addNewOverlay().setVal(true);
        return result;
    }
    /**
     * 创建折线图
     *
     * @throws IOException
     */
    public void createTimeXYChar(List<String> titleArr, List<String> fldNameArr, List<Map<String, Object>> dataList) {
        // 创建一个sheet页
        sheet = wb.createSheet("sheet3");
        // 第二个参数折线图类型:line=普通折线图,line-bar=折线+柱状图
        boolean result = drawSheet3Map(sheet, "line", fldNameArr, dataList, titleArr);
        System.out.println("生成折线图(折线图or折线图-柱状图)-->" + result);
    }
    /**
     * 生成折线图
     *
     * @param sheet
     *            页签
     * @param type
     *            类型
     * @param fldNameArr
     *            X轴标题
     * @param dataList
     *            填充数据
     * @param titleArr
     *            图例标题
     * @return
     */
    private boolean drawSheet3Map(SXSSFSheet sheet, String type, List<String> fldNameArr,
                                  List<Map<String, Object>> dataList, List<String> titleArr) {
        boolean result = false;
        // 获取sheet名称
        String sheetName = sheet.getSheetName();
        result = drawSheet0Table(sheet, titleArr, fldNameArr, dataList);
        // 创建一个画布
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        // 画一个图区域
        // 前四个默认0，从第8行到第25行,从第0列到第6列的区域
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 8, 6, 25);
        // 创建一个chart对象
        Chart chart = drawing.createChart(anchor);
        CTChart ctChart = ((XSSFChart) chart).getCTChart();
        CTPlotArea ctPlotArea = ctChart.getPlotArea();
        if (type.equals("line-bar")) {
            CTBarChart ctBarChart = ctPlotArea.addNewBarChart();
            CTBoolean ctBoolean = ctBarChart.addNewVaryColors();
            ctBarChart.getVaryColors().setVal(true);
            // 设置类型
            ctBarChart.addNewGrouping().setVal(STBarGrouping.CLUSTERED);
            ctBoolean.setVal(true);
            ctBarChart.addNewBarDir().setVal(STBarDir.COL);
            // 是否添加左侧坐标轴
            ctChart.addNewDispBlanksAs().setVal(STDispBlanksAs.ZERO);
            ctChart.addNewShowDLblsOverMax().setVal(true);
            // 创建序列,并且设置选中区域
            for (int i = 0; i < fldNameArr.size() - 1; i++) {
                CTBarSer ctBarSer = ctBarChart.addNewSer();
                CTSerTx ctSerTx = ctBarSer.addNewTx();
                // 图例区
                CTStrRef ctStrRef = ctSerTx.addNewStrRef();
                // 选定区域第0行,第1,2,3列标题作为图例 //1 2 3
                String legendDataRange = new CellRangeAddress(0, 0, i + 1, i + 1).formatAsString(sheetName, true);
                ctStrRef.setF(legendDataRange);
                ctBarSer.addNewIdx().setVal(i);
                // 横坐标区
                CTAxDataSource cttAxDataSource = ctBarSer.addNewCat();
                ctStrRef = cttAxDataSource.addNewStrRef();
                // 选第0列,第1-6行作为横坐标区域
                String axisDataRange = new CellRangeAddress(1, dataList.size(), 0, 0).formatAsString(sheetName, true);
                ctStrRef.setF(axisDataRange);
                // 数据区域
                CTNumDataSource ctNumDataSource = ctBarSer.addNewVal();
                CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
                // 选第1-6行,第1-3列作为数据区域 //1 2 3
                String numDataRange = new CellRangeAddress(1, dataList.size(), i + 1, i + 1).formatAsString(sheetName,
                        true);
                System.out.println(numDataRange);
                ctNumRef.setF(numDataRange);
                ctBarSer.addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[] { 0, 0, 0 });
                // 设置负轴颜色不是白色
                ctBarSer.addNewInvertIfNegative().setVal(false);
                // 设置标签格式
                ctBoolean.setVal(false);
                CTDLbls newDLbls = ctBarSer.addNewDLbls();
                newDLbls.setShowLegendKey(ctBoolean);
                ctBoolean.setVal(true);
                newDLbls.setShowVal(ctBoolean);
                ctBoolean.setVal(false);
                newDLbls.setShowCatName(ctBoolean);
                newDLbls.setShowSerName(ctBoolean);
                newDLbls.setShowPercent(ctBoolean);
                newDLbls.setShowBubbleSize(ctBoolean);
                newDLbls.setShowLeaderLines(ctBoolean);
            }
            // telling the BarChart that it has axes and giving them Ids
            ctBarChart.addNewAxId().setVal(123456);
            ctBarChart.addNewAxId().setVal(123457);
            // cat axis
            CTCatAx ctCatAx = ctPlotArea.addNewCatAx();
            ctCatAx.addNewAxId().setVal(123456); // id of the cat axis
            CTScaling ctScaling = ctCatAx.addNewScaling();
            ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
            ctCatAx.addNewAxPos().setVal(STAxPos.B);
            ctCatAx.addNewCrossAx().setVal(123457); // id of the val axis
            ctCatAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
            // val axis
            CTValAx ctValAx = ctPlotArea.addNewValAx();
            ctValAx.addNewAxId().setVal(123457); // id of the val axis
            ctScaling = ctValAx.addNewScaling();
            ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
            ctValAx.addNewAxPos().setVal(STAxPos.L);
            ctValAx.addNewCrossAx().setVal(123456); // id of the cat axis
            ctValAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        }
        // 折线图
        CTLineChart ctLineChart = ctPlotArea.addNewLineChart();
        CTBoolean ctBoolean = ctLineChart.addNewVaryColors();
        ctLineChart.addNewGrouping().setVal(STGrouping.STANDARD);
        // 创建序列,并且设置选中区域
        for (int i = 0; i < fldNameArr.size() - 1; i++) {
            CTLineSer ctLineSer = ctLineChart.addNewSer();
            CTSerTx ctSerTx = ctLineSer.addNewTx();
            // 图例区
            CTStrRef ctStrRef = ctSerTx.addNewStrRef();
            // 选定区域第0行,第1,2,3列标题作为图例 //1 2 3
            String legendDataRange = new CellRangeAddress(0, 0, i + 1, i + 1).formatAsString(sheetName, true);
            ctStrRef.setF(legendDataRange);
            ctLineSer.addNewIdx().setVal(i);
            // 横坐标区
            CTAxDataSource cttAxDataSource = ctLineSer.addNewCat();
            ctStrRef = cttAxDataSource.addNewStrRef();
            // 选第0列,第1-6行作为横坐标区域
            String axisDataRange = new CellRangeAddress(1, dataList.size(), 0, 0).formatAsString(sheetName, true);
            ctStrRef.setF(axisDataRange);
            // 数据区域
            CTNumDataSource ctNumDataSource = ctLineSer.addNewVal();
            CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
            // 选第1-6行,第1-3列作为数据区域 //1 2 3
            String numDataRange = new CellRangeAddress(1, dataList.size(), i + 1, i + 1).formatAsString(sheetName,
                    true);
            System.out.println(numDataRange);
            ctNumRef.setF(numDataRange);
            // 设置标签格式
            ctBoolean.setVal(false);
            CTDLbls newDLbls = ctLineSer.addNewDLbls();
            newDLbls.setShowLegendKey(ctBoolean);
            ctBoolean.setVal(true);
            newDLbls.setShowVal(ctBoolean);
            ctBoolean.setVal(false);
            newDLbls.setShowCatName(ctBoolean);
            newDLbls.setShowSerName(ctBoolean);
            newDLbls.setShowPercent(ctBoolean);
            newDLbls.setShowBubbleSize(ctBoolean);
            newDLbls.setShowLeaderLines(ctBoolean);
            // 是否是平滑曲线
            CTBoolean addNewSmooth = ctLineSer.addNewSmooth();
            addNewSmooth.setVal(false);
            // 是否是堆积曲线
            CTMarker addNewMarker = ctLineSer.addNewMarker();
            CTMarkerStyle addNewSymbol = addNewMarker.addNewSymbol();
            addNewSymbol.setVal(STMarkerStyle.NONE);
        }
        // telling the BarChart that it has axes and giving them Ids
        ctLineChart.addNewAxId().setVal(123456);
        ctLineChart.addNewAxId().setVal(123457);
        // cat axis
        CTCatAx ctCatAx = ctPlotArea.addNewCatAx();
        ctCatAx.addNewAxId().setVal(123456); // id of the cat axis
        CTScaling ctScaling = ctCatAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctCatAx.addNewAxPos().setVal(STAxPos.B);
        ctCatAx.addNewCrossAx().setVal(123457); // id of the val axis
        ctCatAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        // val axis
        CTValAx ctValAx = ctPlotArea.addNewValAx();
        ctValAx.addNewAxId().setVal(123457); // id of the val axis
        ctScaling = ctValAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctValAx.addNewAxPos().setVal(STAxPos.L);
        ctValAx.addNewCrossAx().setVal(123456); // id of the cat axis
        ctValAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        // 是否删除主左边轴
        ctValAx.addNewDelete().setVal(false);
        // 是否删除横坐标
        if (type.equals("line-bar")) {
            ctCatAx.addNewDelete().setVal(true);
        }
        CTLegend ctLegend = ctChart.addNewLegend();
        ctLegend.addNewLegendPos().setVal(STLegendPos.B);
        ctLegend.addNewOverlay().setVal(false);
        return result;
    }
    /**
     * 生成数据表
     *
     * @param sheet
     *            sheet页对象
     * @param titleArr
     *            表头字段
     * @param fldNameArr
     *            左边标题字段
     * @param dataList
     *            数据
     * @return 是否生成成功
     */
    private boolean drawSheet0Table(SXSSFSheet sheet, List<String> titleArr, List<String> fldNameArr,
                                    List<Map<String, Object>> dataList) {
        // 测试时返回值
        boolean result = true;
        // 初始化表格样式
        List<CellStyle> styleList = tableStyle();
        // 根据数据创建excel第一行标题行
        SXSSFRow row0 = sheet.createRow(0);
        for (int i = 0; i < titleArr.size(); i++) {
            // 设置标题
            row0.createCell(i).setCellValue(titleArr.get(i));
            // 设置标题行样式
            row0.getCell(i).setCellStyle(styleList.get(0));
        }
        // 填充数据
        for (int i = 0; i < dataList.size(); i++) {
            // 获取每一项的数据
            Map<String, Object> data = dataList.get(i);
            // 设置每一行的字段标题和数据
            SXSSFRow rowi = sheet.createRow(i + 1);
            for (int j = 0; j < data.size(); j++) {
                // 判断是否是标题字段列
                if (j == 0) {
                    rowi.createCell(j).setCellValue((String) data.get("value" + (j + 1)));
                    // 设置左边字段样式
                    sheet.getRow(i + 1).getCell(j).setCellStyle(styleList.get(0));
                } else {
                    rowi.createCell(j).setCellValue(Double.valueOf((String) data.get("value" + (j + 1))));
                    // 设置数据样式
                    sheet.getRow(i + 1).getCell(j).setCellStyle(styleList.get(2));
                }
            }
        }
        return result;
    }
    /**
     * 生成表格样式
     *
     * @return
     */
    private static List<CellStyle> tableStyle() {
        List<CellStyle> cellStyleList = new ArrayList<CellStyle>();
        // 样式准备
        // 标题样式
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN); // 下边框
        style.setBorderLeft(BorderStyle.THIN);// 左边框
        style.setBorderTop(BorderStyle.THIN);// 上边框
        style.setBorderRight(BorderStyle.THIN);// 右边框
        style.setAlignment(HorizontalAlignment.CENTER);
        cellStyleList.add(style);
        CellStyle style1 = wb.createCellStyle();
        style1.setBorderBottom(BorderStyle.THIN); // 下边框
        style1.setBorderLeft(BorderStyle.THIN);// 左边框
        style1.setBorderTop(BorderStyle.THIN);// 上边框
        style1.setBorderRight(BorderStyle.THIN);// 右边框
        style1.setAlignment(HorizontalAlignment.CENTER);
        cellStyleList.add(style1);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
        cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
        cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平对齐方式
        // cellStyle.setVerticalAlignment(VerticalAlignment.TOP);//垂直对齐方式
        cellStyleList.add(cellStyle);
        return cellStyleList;
    }
}
