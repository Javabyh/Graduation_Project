package com.aiit.byh.service.common.utils.file.excel;

import com.aiit.byh.service.common.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用SAX模式解决XLSX文件，可以有效解决用户模式内存溢出的问题
 * 该模式是POI官方推荐的读取大数据的模式，
 * 在用户模式下，数据量较大、Sheet较多、或者是有很多无用的空行的情况，容易出现内存溢出
 * <p>
 * Created by pjwang2 on 2018\11\19 0019.
 */
public class XLSXReaderOfSAX {

    private final static Logger logger = LoggerFactory.getLogger(XLSXReaderOfSAX.class);

    /**
     * 读取多个excel文件
     *
     * @param fileNames
     * @param validSheetName 仅返回指定sheet名称表单,为空(或null)表示全返回
     * @return
     */
    public static Map<String, List<List<String>>> importExcel(List<String> fileNames, List<String> validSheetName) {
        if (CommonUtils.isEmpty(fileNames)) {
            return null;
        }
        Map<String, List<List<String>>> map = new HashMap<>();
        for (String fileName : fileNames) {
            Map<String, List<List<String>>> temp = importExcel(fileName, validSheetName);
            if (CommonUtils.isNotEmpty(temp)) {
                map.putAll(temp);
            }
        }
        return map;
    }

    /**
     * 读取excel文件
     *
     * @param fileName
     * @param validSheetName 仅返回指定sheet名称表单,为空(或null)表示全返回
     * @return
     */
    public static Map<String, List<List<String>>> importExcel(String fileName, List<String> validSheetName) {
        Map<String, List<List<String>>> sheetMap = new HashMap<>();

        OPCPackage opcPackage = null;
        try {
            opcPackage = OPCPackage.open(fileName, PackageAccess.READ);
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            StylesTable styles = xssfReader.getStylesTable();
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opcPackage);
            XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            while (iter.hasNext()) {
                InputStream stream = iter.next();
                String sheetName = iter.getSheetName();
                if (CommonUtils.isEmpty(validSheetName) || validSheetName.contains(sheetName)) {
                    SheetToList sheetToCSV = new SheetToList();
                    processSheet(sheetName, styles, strings, sheetToCSV, stream);
                    sheetMap.put(sheetName, sheetToCSV.getResults());
                }
                closeStream(stream);
            }
            opcPackage.close();
        } catch (Exception ex) {
            logger.error("****读取excel文件异常 FileName:{}****", fileName, ex);
        }

        return sheetMap;
    }

    private static void processSheet(String sheetName,
                                     StylesTable styles,
                                     ReadOnlySharedStringsTable strings,
                                     XSSFSheetXMLHandler.SheetContentsHandler sheetHandler,
                                     InputStream sheetInputStream) {
        try {
            InputSource sheetSource = new InputSource(sheetInputStream);
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxFactory.newSAXParser();
            XMLReader sheetParser = saxParser.getXMLReader();
            ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, sheetHandler, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch (Exception ex) {
            logger.error("****读取单个Sheet异常 SheetName:{}****", sheetName, ex);
        }
    }

    /**
     * 关闭流
     *
     * @param inputStream
     */
    private static void closeStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ex) {
                logger.error("****关闭数据流异常****", ex);
            }
        }
    }

    private static class SheetToList implements XSSFSheetXMLHandler.SheetContentsHandler {
        private int currentCol = -1;
        private List<String> rowlist = new ArrayList<>();
        private List<List<String>> results = new ArrayList<>();

        public SheetToList() {
        }

        public List<List<String>> getResults() {
            return results;
        }

        public void setResults(List<List<String>> results) {
            this.results = results;
        }

        @Override
        public void startRow(int rowNum) {
            currentCol = -1;
        }

        @Override
        public void endRow() {
            if (CommonUtils.isNotEmpty(rowlist)) {
                results.add(rowlist);
                rowlist = new ArrayList<>();
            }
        }

        @Override
        public void cell(String cellReference, String formattedValue) {
            if (cellReference == null) {
                return;
            }
            int thisCol = (new CellReference(cellReference)).getCol();
            int missedCols = thisCol - currentCol - 1;
            for (int i = 0; i < missedCols; i++) {
                rowlist.add(StringUtils.EMPTY);
            }
            currentCol = thisCol;
            rowlist.add(formattedValue);
        }

        @Override
        public void headerFooter(String s, boolean b, String s1) {

        }
    }

    /**
     * 获取读取行数据指定下标值，数据越界返回空串
     *
     * @param row
     * @param index
     * @return
     */
    public static String getDefaultString(List<String> row, int index) {
        if (CommonUtils.isEmpty(row) || row.size() <= index) {
            return StringUtils.EMPTY;
        }

        return row.get(index);
    }
}
