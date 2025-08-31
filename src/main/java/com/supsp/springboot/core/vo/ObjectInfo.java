package com.supsp.springboot.core.vo;

import com.supsp.springboot.core.enums.SysApp;
import com.supsp.springboot.core.enums.SysModule;
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
@Schema(name = "ObjectInfo", description = "对应数据对象")
public class ObjectInfo implements IVo {

    @Serial
    private static final long serialVersionUID = -8434715384240332770L;

    @Schema(title = "应用名称")
    private String sysName;

    @Schema(title = "模块")
    private SysModule sysModule;

    @Schema(title = "应用")
    private SysApp sysApp;

    @Schema(title = "对象")
    private String sysObject;

    @Schema(title = "对象ID")
    private Long objectId;

}
