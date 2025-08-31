package com.supsp.springboot.core.interfaces;

import java.util.List;

public interface IPagerData<T> extends IData {

    boolean isSuccess();

    void setSuccess(boolean success);

    List<T> getData();

    void setData(List<T> data);

    long getCurrentPage();

    void setCurrentPage(long currentPage);

    long getPageSize();

    void setPageSize(long pageSize);

    long getTotal();

    void setTotal(long total);

    long getPageTotal();

    void setPageTotal(long pageTotal);

    Object getExt();

    void setExt(Object ext);
}
