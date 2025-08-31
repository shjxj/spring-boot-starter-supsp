package com.supsp.springboot.core.model;

import java.io.Serializable;

public interface IParams extends Serializable {

    long getCurrentPage();

    void setCurrentPage(long currentPage);

    long getPageSize();

    void setPageSize(long pageSize);
}
