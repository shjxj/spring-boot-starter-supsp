package com.supsp.springboot.core.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public abstract class BaseModelEntity<T extends BaseModelEntity<?>> extends AbstractModelEntity<T> implements IModelEntity<T> {
    @Serial
    private static final long serialVersionUID = -8115631492219550850L;

    @Schema(title = "数据操作")
    @TableField(exist = false)
    private Boolean __DB__;

    @Schema(title = "批量名称")
    @TableField(exist = false)
    private String batchName;

//    @Schema(title = "强制提交")
//    @TableField(exist = false)
//    @JsonIgnore
//    private Boolean forceSubmit;
}
