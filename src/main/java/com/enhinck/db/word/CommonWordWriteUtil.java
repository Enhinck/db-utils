package com.enhinck.db.word;

import com.enhinck.db.entity.ExcelCell;
import com.enhinck.db.entity.ExcelFile;
import com.enhinck.db.entity.ExcelRow;
import com.enhinck.db.entity.ExcelSheet;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.List;

@Slf4j
public class CommonWordWriteUtil {


    public static void write(ExcelFile excelFile) {
        XWPFDocument document = createDocument();
        List<ExcelSheet> sheets = excelFile.getSheets();
        if (sheets.size() > 0) {
            for (int i = 0; i < sheets.size(); i++) {
                ExcelSheet sheet = sheets.get(i);
                // 标题
                XWPFParagraph tableTitle = document.createParagraph();
                XWPFRun title1Run = tableTitle.createRun();
                title1Run.setText(sheet.getComment() + " "+ sheet.getSheetName());
                title1Run.setFontSize(20);
                title1Run.setBold(true);
                List<ExcelRow> excelRows = sheet.getExcelRows();
                // 表格
                XWPFTable tab = document.createTable(excelRows.size(), 4);
                tab.getRow(0).getCell(0).setColor("87CEFA");
                tab.getRow(0).getCell(1).setColor("87CEFA");
                tab.getRow(0).getCell(2).setColor("87CEFA");
                tab.getRow(0).getCell(3).setColor("87CEFA");
                for (int j = 0; j < excelRows.size(); j++) {
                    ExcelRow excelRow = excelRows.get(j);
                    List<ExcelCell> cells = excelRow.getCells();

                    tab.getRow(j).getCell(0).setText(cells.get(0).getColumnValue().toString());
                    tab.getRow(j).getCell(1).setText(cells.get(1).getColumnValue().toString());
                    tab.getRow(j).getCell(2).setText(cells.get(2).getColumnValue().toString());
                    tab.getRow(j).getCell(3).setText(cells.get(3).getColumnValue().toString());
                }
                XWPFParagraph blank = document.createParagraph();
                XWPFRun blankRun = blank.createRun();
            }
        }
        try {
            saveDocument(document, excelFile.getDocFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建一个word对象
     *
     * @return
     * @Author Huangxiaocong 2018年12月1日 上午11:56:35
     */
    public static XWPFDocument createDocument() {
        XWPFDocument document = new XWPFDocument();
        return document;
    }

    /**
     * 打开word文档
     *
     * @param path 文档所在路径
     * @return
     * @throws IOException
     * @Author Huangxiaocong 2018年12月1日 下午12:30:07
     */
    public static XWPFDocument openDoc(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        return new XWPFDocument(is);
    }

    /**
     * 保存word文档
     *
     * @param document 文档对象
     * @param savePath 保存路径
     * @throws IOException
     * @Author Huangxiaocong 2018年12月1日 下午12:32:37
     */
    public static void saveDocument(XWPFDocument document, String savePath) throws IOException {
        OutputStream os = new FileOutputStream(savePath);
        document.write(os);
        os.close();
    }


    /**
     * 复制表格
     *
     * @param targetTable
     * @param sourceTable
     * @Author Huangxiaocong 2018年12月1日 下午1:40:01
     */
    public static void copyTable(XWPFTable targetTable, XWPFTable sourceTable) {
        //复制表格属性
        targetTable.getCTTbl().setTblPr(sourceTable.getCTTbl().getTblPr());
        //复制行
        for (int i = 0; i < sourceTable.getRows().size(); i++) {
            XWPFTableRow targetRow = targetTable.getRow(i);
            XWPFTableRow sourceRow = sourceTable.getRow(i);
            if (targetRow == null) {
                targetTable.addRow(sourceRow);
            } else {
                copyTableRow(targetRow, sourceRow);
            }
        }
    }

    /**
     * 复制单元格
     *
     * @param targetRow
     * @param sourceRow
     * @Author Huangxiaocong 2018年12月1日 下午1:33:22
     */
    public static void copyTableRow(XWPFTableRow targetRow, XWPFTableRow sourceRow) {
        //复制样式
        if (sourceRow != null) {
            targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
        }
        //复制单元格
        for (int i = 0; i < sourceRow.getTableCells().size(); i++) {
            XWPFTableCell tCell = targetRow.getCell(i);
            XWPFTableCell sCell = sourceRow.getCell(i);
            if (tCell == sCell) {
                tCell = targetRow.addNewTableCell();
            }
            copyTableCell(tCell, sCell);
        }
    }

    /**
     * 复制单元格（列） 从sourceCell到targetCell
     *
     * @param targetCell
     * @param sourceCell
     * @Author Huangxiaocong 2018年12月1日 下午1:27:38
     */
    public static void copyTableCell(XWPFTableCell targetCell, XWPFTableCell sourceCell) {
        //表格属性
        if (sourceCell.getCTTc() != null) {
            targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
        }
        //删除段落
        for (int pos = 0; pos < targetCell.getParagraphs().size(); pos++) {
            targetCell.removeParagraph(pos);
        }
        //添加段落
        for (XWPFParagraph sourceParag : sourceCell.getParagraphs()) {
            XWPFParagraph targetParag = targetCell.addParagraph();
            copyParagraph(targetParag, sourceParag);
        }
    }

    /**
     * 复制段落，从sourceParag到targetParag
     *
     * @param targetParag
     * @param sourceParag
     * @Author Huangxiaocong 2018年12月1日 下午1:16:26
     */
    public static void copyParagraph(XWPFParagraph targetParag, XWPFParagraph sourceParag) {
        //设置段落样式
        targetParag.getCTP().setPPr(sourceParag.getCTP().getPPr());
        //移除所有的run
        for (int pos = targetParag.getRuns().size() - 1; pos >= 0; pos--) {
            targetParag.removeRun(pos);
        }
        //copy新的run
        for (XWPFRun sRun : sourceParag.getRuns()) {
            XWPFRun tarRun = targetParag.createRun();
            copyRun(tarRun, sRun);
        }
    }

    /**
     * 复制XWPFRun 从sourceRun到targetRun
     *
     * @param targetRun
     * @param sourceRun
     * @Author Huangxiaocong 2018年12月1日 下午12:56:53
     */
    public static void copyRun(XWPFRun targetRun, XWPFRun sourceRun) {
        //设置targetRun属性
        targetRun.getCTR().setRPr(sourceRun.getCTR().getRPr());
        //设置文本
        targetRun.setText(sourceRun.text());
        //处理图片
        List<XWPFPicture> pictures = sourceRun.getEmbeddedPictures();
        for (XWPFPicture picture : pictures) {
            try {
                copyPicture(targetRun, picture);
            } catch (InvalidFormatException e) {
                log.warn("copyRun:{}", e);
            } catch (IOException e) {
                log.warn("copyRun:{}", e);
            }
        }
    }

    /**
     * 复制图片从sourcePicture到 targetRun（XWPFPicture --> XWPFRun）
     *
     * @param targetRun
     * @param sourcePicture
     * @throws IOException
     * @throws InvalidFormatException
     * @Author Huangxiaocong 2018年12月1日 下午12:57:33
     */
    public static void copyPicture(XWPFRun targetRun, XWPFPicture sourcePicture) throws InvalidFormatException, IOException {
        XWPFPictureData picData = sourcePicture.getPictureData();
        //图片的名称
        String fileName = picData.getFileName();
        InputStream picInIsData = new ByteArrayInputStream(picData.getData());
        int picType = picData.getPictureType();
        int width = (int) sourcePicture.getCTPicture().getSpPr().getXfrm().getExt().getCx();
        int height = (int) sourcePicture.getCTPicture().getSpPr().getXfrm().getExt().getCy();
        targetRun.addPicture(picInIsData, picType, fileName, width, height);
//        targetRun.addBreak();//分行
    }
}
