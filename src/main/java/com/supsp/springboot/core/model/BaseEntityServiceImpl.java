package com.supsp.springboot.core.model;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseEntityServiceImpl<M extends IEntityMapper<T>, T extends BaseModelEntity<?>> extends MPJBaseServiceImpl<M, T> implements IEntityService<T> {

    @Autowired
    protected M mapper;

    @Override
    public T getOne(Wrapper<T> queryWrapper) {
        return super.getOne(queryWrapper, false);
    }

    @Override
    public T getRow(Wrapper<T> queryWrapper) {
        return super.getOne(queryWrapper, false);
    }
}
