package com.supsp.springboot.core.model;

import com.supsp.springboot.core.base.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseEntityVo<T extends BaseModelEntity<?>> extends BaseVo implements IEntityVo<T> {
    @Serial
    private static final long serialVersionUID = 8900970990077797793L;

    @Schema(title = "强制提交")
    private Boolean forceSubmit;

    @Schema(title = "批量名称")
    private String batchName;

}
