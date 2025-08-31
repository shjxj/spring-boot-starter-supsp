package com.supsp.springboot.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supsp.springboot.core.enums.OrderType;
import com.supsp.springboot.core.vo.OperatorScope;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
//@Schema(name = "BaseDataRequest", description = "查询")
public abstract class BaseDataRequest<P extends BaseParams, F extends BaseFilter> extends BaseRequest implements IDataRequest<P, F> {
    @Serial
    private static final long serialVersionUID = -5555927425272946435L;

    @Schema(title = "查询参数")
    protected P params;

    @Schema(title = "筛选参数")
    protected F filter;

    @Schema(title = "排序参数")
    @JsonProperty("sort")
    protected HashMap<String, OrderType> sorter;

    @Schema(title = "查询列表字段")
    protected List<String> columns;

    @Schema(title = "添加查询列表字段")
    protected List<String> addColumns;

    @Schema(title = "操作员数据权限")
    protected OperatorScope operatorScope;

    @Override
    public P getParams() {
        return params;
    }

    @Override
    public void setParams(P params) {
        this.params = params;
    }

    @Override
    public F getFilter() {
        return filter;
    }

    @Override
    public void setFilter(F filter) {
        this.filter = filter;
    }

    @Override
    public HashMap<String, OrderType> getSorter() {
        return sorter;
    }

    @Override
    public void setSorter(HashMap<String, OrderType> sorter) {
        this.sorter = sorter;
    }

    @Override
    public List<String> getColumns() {
        return columns;
    }

    @Override
    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    @Override
    public List<String> getAddColumns() {
        return addColumns;
    }

    @Override
    public void setAddColumns(List<String> addColumns) {
        this.addColumns = addColumns;
    }

    @Override
    public OperatorScope getOperatorScope() {
        return operatorScope;
    }

    @Override
    public void setOperatorScope(OperatorScope operatorScope) {
        this.operatorScope = operatorScope;
    }
}
