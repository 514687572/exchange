package com.cmd.exchange.common.excel;


import com.cmd.exchange.common.utils.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExcelGenerator {
    static final Logger logger = LoggerFactory.getLogger(ExcelGenerator.class);


    HSSFWorkbook workbook; //对应的POI对象
    private HSSFCellStyle titleStyle; //标题行的样式
    private HSSFCellStyle dataStyle; //数据行的样式

    public ExcelGenerator() {
        this.workbook = new HSSFWorkbook();
    }

    public void write(OutputStream output) throws IOException {
        this.workbook.write(output);
    }

    //初始化样式
    private void initStyle() {
        titleStyle = workbook.createCellStyle();
        HSSFFont font1 = workbook.createFont();
        font1.setFontName("微软雅黑");   //设置字体名称
        font1.setFontHeightInPoints((short) 12);  //设置字体字号
        font1.setColor(HSSFColor.WHITE.index);    //设置字体颜色
        titleStyle.setFont(font1);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);  //水平居中
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //垂直居中
        titleStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);//设置图案颜色
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//设置图案样式
        titleStyle.setBorderBottom(BorderStyle.THIN);  //下边框
        titleStyle.setBorderLeft(BorderStyle.THIN); //左边框
        titleStyle.setBorderRight(BorderStyle.THIN);  //右边框
        titleStyle.setBorderTop(BorderStyle.THIN);   //上边框
        titleStyle.setBottomBorderColor(HSSFColor.WHITE.index);   //设置边框的颜色
        titleStyle.setLeftBorderColor(HSSFColor.WHITE.index);
        titleStyle.setTopBorderColor(HSSFColor.WHITE.index);
        titleStyle.setRightBorderColor(HSSFColor.WHITE.index);

        dataStyle = workbook.createCellStyle();
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("微软雅黑");
        font2.setFontHeightInPoints((short) 11);
        dataStyle.setFont(font2);
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    }


    public void addSheet(ReportData data) throws IOException, NoSuchFieldException, IllegalAccessException {
        HSSFSheet sheet = workbook.createSheet(data.getDefinition().getName());

        //实际内容开始行
        int rowIndex = 6;
        //创建标题行
        HSSFRow row = sheet.createRow(rowIndex++);
        for (int i = 0; i < data.getDefinition().getColumnList().size(); ++i) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(data.getDefinition().getColumnList().get(i).getTitle());
            cell.setCellStyle(titleStyle);
        }

        //创建数据行
        if (!CollectionUtils.isEmpty(data.getData())) {
            for (Object o : data.getData()) {
                row = sheet.createRow(rowIndex++);
                udpateRow(data.getDefinition(), row, o);
            }
        }
    }

    private void udpateRow(ReportDefinition definition, HSSFRow row, Object rowData) throws IOException, NoSuchFieldException, IllegalAccessException {
        for (int i = 0; i < definition.getColumnList().size(); ++i) {
            HSSFCell cell = row.createCell(i);
            ReportColumn column = definition.getColumnList().get(i);

            //注意这里不用getDeclaredMethods是因为有些字段是从父类继承的，比如DishSummaryReportDataByUser
            Method[] methods = rowData.getClass().getMethods();
            Map<String, Method> methodMap = new HashMap<>();
            for (Method m : methods) {
                methodMap.put(m.getName().toLowerCase(), m);
            }

            Method method = methodMap.get("get" + column.getFieldName().toLowerCase());
            if (method != null) {
                try {
                    //需要通过get方法来获取字段的值，因为有些字段是没有值的，必须通过get方法才会触发计算
                    //比如DishSummaryReportData中的reduce
                    updateCellValue(cell, method.invoke(rowData));
                } catch (InvocationTargetException e) {
                    logger.error("failed to invoke get method for field: {}", column.getFieldName());
                }
            } else {
                logger.error("failed to find get method for field: {}", column.getFieldName());
            }


            cell.setCellStyle(dataStyle);
        }
    }

    private void updateCellValue(HSSFCell cell, Object value) {
        if (value == null) {
            return;
        }

        if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else if (value instanceof Integer) {
            cell.setCellValue(((Integer) value).intValue());
        } else if (value instanceof Date) {
            cell.setCellValue(DateUtil.getDateTimeString((Date) value));
        } else {
            cell.setCellValue(value.toString());
        }
    }
}
