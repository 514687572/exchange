package com.cmd.exchange.admin.excel;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ExcelReader {
    private String filePath;
    private String sheetName;
    private Workbook workBook;
    private Sheet sheet;
    private List<String> columnHeaderList;
    private List<List<String>> listData;
    private List<Map<String, String>> mapData;
    private boolean flag;

    public static Integer rowNumber;

    public static Integer cellNumber;

    public ExcelReader() {
    }

    public ExcelReader(String filePath, String sheetName) {
        this.filePath = filePath;
        this.sheetName = sheetName;
        this.flag = false;
        this.load();
    }


    private void load() {
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(new File(filePath));
            workBook = WorkbookFactory.create(inStream);
            sheet = workBook.getSheet(sheetName);
            rowNumber = sheet.getLastRowNum();
            cellNumber = sheet.getLastRowNum();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getCellValue(Cell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = formatter.formatCellValue(cell);
                    } else {
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue.trim();
    }


    private void getSheetData() {
        listData = new ArrayList<List<String>>();
        mapData = new ArrayList<Map<String, String>>();
        columnHeaderList = new ArrayList<String>();
        int numOfRows = sheet.getLastRowNum() + 1;
        for (int i = 0; i < numOfRows; i++) {
            Row row = sheet.getRow(i);
            Map<String, String> map = new HashMap<String, String>();
            List<String> list = new ArrayList<String>();
            if (row != null) {
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    if (i == 0) {
                        columnHeaderList.add(getCellValue(cell));
                    } else {
                        map.put(columnHeaderList.get(j), this.getCellValue(cell));
                    }
                    list.add(this.getCellValue(cell));
                }
            }
            if (i > 0) {
                mapData.add(map);
            }
            listData.add(list);
        }
        flag = true;
    }


    public String getCellData(int row, int col) {
        if (row <= 0 || col <= 0) {
            return null;
        }
        if (!flag) {
            this.getSheetData();
        }
        if (listData.size() >= row && listData.get(row - 1).size() >= col) {
            return listData.get(row - 1).get(col - 1);
        } else {
            return null;
        }
    }


    public String getCellData(int row, String headerName) {
        if (row <= 0) {
            return null;
        }
        if (!flag) {
            this.getSheetData();
        }
        if (mapData.size() >= row && mapData.get(row - 1).containsKey(headerName)) {
            return mapData.get(row - 1).get(headerName);
        } else {
            return null;
        }
    }


    /**
     * 获取标题
     *
     * @param eh
     * @param maxX
     * @return
     */
    public List<String> getTitleList(ExcelReader eh, int maxX) {
        List<String> result = new ArrayList<String>();
        for (int i = 1; i <= maxX; i++) {
            result.add(eh.getCellData(1, i));
        }
        return result;
    }


    /**
     * 获取单行对象
     *
     * @param
     * @param eh
     * @param
     * @param titles
     * @return
     */
    public Object getObject(String className, ExcelReader eh, int y, List<String> titles) throws Exception {
        Object bean = Class.forName(className).newInstance();
        int length = titles.size();
        for (int x = 0; x < length; x++) {
            try {
                Field field = bean.getClass().getDeclaredField(titles.get(x));
                field.setAccessible(true);
                field.set(bean, eh.getCellData(y, x + 1));
            } catch (Exception e) {
                System.out.println("没有对应的方法：" + e);
            }
        }
        return bean;
    }


    /**
     * 获取Excel数据列表
     *
     * @param
     * @param eh
     * @param x      每行有多少列数据
     * @param y      整个sheet有多少行数据
     * @param titles
     * @return
     */
    public List<Object> getDataList(Class<?> clazz, ExcelReader eh, int x, int y, List<String> titles) {
        List<Object> result = new ArrayList<Object>();

        String className = clazz.getName();
        try {
            for (int i = 2; i <= y; i++) {
                Object object = eh.getObject(className, eh, i, titles);
                result.add(object);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }


}
