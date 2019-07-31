package com.cmd.exchange.common.excel;

import java.util.List;

/**
 * Created by Acewill on 2017/3/8.
 */
public class ReportData {
    private ReportDefinition definition;
    private List data; //里面的内容不同的报表是不一样的

    public ReportData(ReportDefinition definition, List data) {
        this.definition = definition;
        this.data = data;
    }

    public ReportDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(ReportDefinition definition) {
        this.definition = definition;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
