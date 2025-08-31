package com.supsp.springboot.core.vo;

import com.supsp.springboot.core.interfaces.IVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "OrgKindVo", description = "经营类型信息")
public class OrgKindVo implements IVo {

    @Serial
    private static final long serialVersionUID = -2050721738574633583L;

    @Schema(title = "经营类型")
    private String kind;

    @Schema(title = "一级类型名称")
    private String firstName;

    @Schema(title = "一级类型编码")
    private String firstCode;

    @Schema(title = "二级类型名称")
    private String secondName;

    @Schema(title = "二级类型编码")
    private String secondCode;

    @Schema(title = "三级类型名称")
    private String thirdName;

    @Schema(title = "三级类型编码")
    private String thirdCode;

    @Schema(title = "最终类型名称")
    private String kindName;

    @Schema(title = "最终类型编码")
    private String kindCode;
}