package com.supsp.springboot.core.model;



import com.supsp.springboot.core.enums.OrderType;
import com.supsp.springboot.core.vo.OperatorScope;

import java.util.HashMap;
import java.util.List;

public interface IDataRequest<P, F> extends IRequest {

    P getParams();

    void setParams(P params);

    F getFilter();

    void setFilter(F filter);

    HashMap<String, OrderType> getSorter();

    void setSorter(HashMap<String, OrderType> sorter);

    List<String> getColumns();

    void setColumns(List<String> columns);

    List<String> getAddColumns();

    void setAddColumns(List<String> addClumns);

    OperatorScope getOperatorScope();

    void setOperatorScope(OperatorScope operatorScope);
}
