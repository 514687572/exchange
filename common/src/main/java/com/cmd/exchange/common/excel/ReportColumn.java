package com.cmd.exchange.common.excel;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Acewill on 2017/3/17.
 */
public class ReportColumn {
    @ApiModelProperty(name = "字段显示名称", example = "销量")
    private String title;
    @ApiModelProperty(name = "后台字段名称", example = "quantity")
    private String fieldName;
    @ApiModelProperty(name = "后台字段类型", example = "date")
    private ReportColumnType type; //是整数，还是日期

    public ReportColumn(String title, String fieldName) {
        this.title = title;
        this.fieldName = fieldName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}
