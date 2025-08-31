package com.supsp.springboot.core.base;

import com.supsp.springboot.core.interfaces.IData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ActionResultData", description = "操作结果数据")
public class ActionData implements IData {
    @Serial
    private static final long serialVersionUID = -630536426397094374L;

    @Schema(title = "操作结果")
    protected Boolean result;

    @Schema(title = "数据库自增ID最新值")
    protected Long lastID;

    @Schema(title = "系统ID最新值")
    protected Long ID;
}
