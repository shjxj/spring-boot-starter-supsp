package com.supsp.springboot.core.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractModelEntity<T extends AbstractModelEntity<?>> extends Model<T> implements IEntity {
    @Serial
    private static final long serialVersionUID = 7970511322954276316L;

    /**
     * unix 时间戳 [无需前端设置]
     */
    @TableField(exist = false)
    @Schema(title = "unix 时间戳", description = "无需前端设置")
    @JsonIgnore
    private Long currentTimestamp;
}
