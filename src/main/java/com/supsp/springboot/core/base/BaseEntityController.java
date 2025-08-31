package com.supsp.springboot.core.base;

import com.supsp.springboot.core.model.BaseEntityServiceImpl;
import com.supsp.springboot.core.model.BaseModelEntity;
import com.supsp.springboot.core.model.IEntityController;
import com.supsp.springboot.core.model.IEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseEntityController<S extends BaseEntityServiceImpl<M, T>, M extends IEntityMapper<T>, T extends BaseModelEntity<?>> extends BaseController implements IEntityController<T> {

    @Autowired
    protected S service;

    @Autowired
    protected M mapper;


}
