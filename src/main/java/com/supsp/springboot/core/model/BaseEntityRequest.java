package com.supsp.springboot.core.model;

import com.supsp.springboot.core.consts.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
//@Schema(name = "BaseEntityRequest", description = "查询")
public abstract class BaseEntityRequest<P extends BaseEntityParams<T>, F extends BaseEntityFilter<T>, T extends BaseModelEntity<T>> extends BaseDataRequest<P, F> implements IEntityRequest<P, F, T> {
    @Serial
    private static final long serialVersionUID = -7784978992652787313L;

    /**
     * 查询数量
     */
    @Schema(title = "limit", description = "仅query查询使用")
    protected Long limit = Constants.DEFAULT_LIMIT;

    @Override
    public Long getLimit() {
        return limit;
    }

    @Override
    public void setLimit(Long limit) {
        this.limit = limit;
    }
}
