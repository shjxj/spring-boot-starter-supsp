package com.supsp.springboot.core.model;



public interface IEntityRequest<P extends BaseEntityParams<T>, F extends BaseEntityFilter<T>, T extends BaseModelEntity<T>> extends IDataRequest<P, F> {

    Long getLimit();

    void setLimit(Long limit);
    
}
