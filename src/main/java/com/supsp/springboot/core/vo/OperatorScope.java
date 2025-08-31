package com.supsp.springboot.core.vo;

import com.supsp.springboot.core.interfaces.IVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

@Data
public class OperatorScope implements IVo {
    @Serial
    private static final long serialVersionUID = -7504020527464721474L;

    @Schema(title = "省")
    private Long province;

    @Schema(title = "市")
    private Long city;

    @Schema(title = "区/县")
    private Long district;

    @Schema(title = "街道")
    private Long street;

    @Schema(title = "社区")
    private Long community;
}
