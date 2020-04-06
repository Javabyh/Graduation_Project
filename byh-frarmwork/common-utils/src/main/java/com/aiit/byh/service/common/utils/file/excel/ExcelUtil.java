package com.aiit.byh.service.common.utils.file.excel;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * excel导出类
 *
 * @author dsqin
 */
public class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 导出文件至excel文件中
     *
     * @param title      excel内容标题
     * @param fileName   文件名
     * @param headerList 表头
     * @param dataList   表内容
     * @param response
     */
    public static void export(String title, String fileName, List<String> headerList, List<List<Object>> dataList, HttpServletResponse response) {
        ExportExcel ee = new ExportExcel(title, headerList);
        for (int i = 0; i < dataList.size(); i++) {
            Row row = ee.addRow();
            for (int j = 0; j < dataList.get(i).size(); j++) {
                ee.addCell(row, j, dataList.get(i).get(j));
            }
        }
        try {
            ee = ee.write(response, fileName);
        } catch (IOException e) {
            logger.error("****导出文件至excel异常 title:{} fileName:{}****", title, fileName, e);
        }
        ee.dispose();
    }

    /**
     * 导出文件至excel文件中
     *
     * @param title      excel内容标题
     * @param fileName   文件名
     * @param headerList 表头
     * @param dataList   表内容
     */
    public static void exportFile(String title, String fileName, List<String> headerList, List<List<Object>> dataList) {
        ExportExcel ee = new ExportExcel(title, headerList);
        for (int i = 0; i < dataList.size(); i++) {
            Row row = ee.addRow();
            for (int j = 0; j < dataList.get(i).size(); j++) {
                ee.addCell(row, j, dataList.get(i).get(j));
            }
        }

        try {
            ee = ee.writeFile(fileName);
        } catch (IOException e) {
            logger.error("****导出文件至excel异常 title:{} fileName:{}****", title, fileName, e);
        }
        ee.dispose();
    }

    /**
     * excel文件读取
     *
     * @param inputStream
     * @return
     */
    public static List<Sheet> importExcel(InputStream inputStream) {
        List<Sheet> sheets = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(inputStream); //这种方式 Excel 2003/2007/2010 都是可以处理的
            int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量
            //遍历每个Sheet
            for (int s = 0; s < sheetCount; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                sheets.add(sheet);
            }

        } catch (Exception e) {
            logger.error("****读取excel文件异常 ****", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sheets;
    }

    public static Object getValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        int cellType = cell.getCellType();
        Object cellValue = null;
        switch (cellType) {
            case Cell.CELL_TYPE_STRING: //文本
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC: //数字、日期
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue(); //日期型
                } else {
                    cellValue = cell.getNumericCellValue(); //数字
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN: //布尔型
                cellValue = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK: //空白
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_ERROR: //错误
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = null;
                break;
            default:
                cellValue = null;
        }
        return cellValue;
    }

    public static String getStringCellValue(Cell cell) {
        try {
            Object object = getValue(cell);
            if(object != null){
                return object.toString();
            }else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String getStringCellValueTrim(Cell cell) {
        try {
            Object object = getValue(cell);
            if(object != null){
                return object.toString().trim();
            }else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取标题的行解析列头的索引
     * @param headerRow
     * @return
     */
    public static Map<String, Integer> getHeaderIndex(Row headerRow) {
        try {
            if(headerRow != null){
                Map<String, Integer> key2index = new HashMap<>();
                for(int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++){
                    String value = getStringCellValue(headerRow.getCell(i));
                    if(StringUtils.isNotBlank(value)){
                        key2index.put(value.trim(), i);
                    }else {
                        break;
                    }
                }
                return key2index;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 获取某标题对应列所在单元格的值
     * @param row
     * @param key
     * @param headerIndex
     * @return
     */
    public static String getStringCellValue(Row row, String key,
            Map<String, Integer> headerIndex) {
        try {
            if(row != null && StringUtils.isNotBlank(key) && MapUtils.isNotEmpty(headerIndex)){
                Integer index = headerIndex.get(key);
                if(index != null){
                    return getStringCellValueTrim(row.getCell(index));
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * @param cell
     * @return
     */
    public static Date getDateCellValue(Cell cell) {
        try {
            return (Date) getValue(cell);
        } catch (Exception e) {
            return null;
        }
    }

    public static Double getDoubleCellValue(Cell cell) {
        try {
            return (double) getValue(cell);
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean getBooleanCellValue(Cell cell) {
        try {
            return (boolean) getValue(cell);
        } catch (Exception e) {
            return null;
        }
    }
}
