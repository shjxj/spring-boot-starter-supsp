package com.supsp.springboot.core.model;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.github.yulichang.base.MPJBaseService;

public interface IEntityService<T extends BaseModelEntity<?>> extends MPJBaseService<T> {

    IEntityMapper<T> getMapper();

    T getRow(Wrapper<T> queryWrapper);
}
