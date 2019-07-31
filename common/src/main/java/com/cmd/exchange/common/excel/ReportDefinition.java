package com.cmd.exchange.common.excel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 报表定义是所有商户共享的
 * Created by Acewill on 2017/3/17.
 */

public class ReportDefinition {
    private static ObjectMapper mapper = new ObjectMapper();

    @ApiModelProperty(name = "id字段")
    private Long id;
    private Integer seq;  //显示排序
    @ApiModelProperty(name = "是否为统计类报表", example = "true： 统计报表， false: 流水报表")
    private Boolean summary;
    @ApiModelProperty(name = "报表的内部名称", example = "daily_sales")
    private String key;
    @ApiModelProperty(name = "报表的显示名称", example = "日销售统计")
    private String name;
    @JsonIgnore
    private String columnListJson;


    @ApiModelProperty(name = "列的定义", example = "")
    private List<ReportColumn> columnList;

    public ReportDefinition(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReportColumn> getColumnList() throws IOException {
        if (columnList != null) {
            return columnList;
        }

        if (this.columnListJson == null || columnListJson.isEmpty()) {
            return new ArrayList<>();
        }

        //如果数据库中配置的json里有Java对象中不存在的字段名称，则忽略它
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        columnList = mapper.readValue(columnListJson, new TypeReference<List<ReportColumn>>() {
        });
        return columnList;
    }

    public void addColumn(ReportColumn column) {
        if (columnList == null) {
            columnList = new ArrayList<>();
        }

        columnList.add(column);
    }

    public void setColumnList(List<ReportColumn> columnList) {
        this.columnList = columnList;
    }

    public String getColumnListJson() {
        return columnListJson;
    }

    public void setColumnListJson(String columnListJson) {
        this.columnListJson = columnListJson;
    }

    public Boolean getSummary() {
        return summary;
    }

    public void setSummary(Boolean summary) {
        this.summary = summary;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
