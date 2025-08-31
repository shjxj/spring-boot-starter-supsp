package com.supsp.springboot.core.vo.auth;

import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.interfaces.IData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@SensitiveData
public class AuthDevice implements IData {
    @Serial
    private static final long serialVersionUID = -1157855569463275258L;

    @Schema(title = "设备ID")
    private Long id;

    @Schema(title = "设备编码")
    private String code;

    @Schema(title = "设备标识")
    private String ident;

    @Schema(title = "用户终端标识")
    private String terminalNo;

    @Schema(title = "应用版本")
    private String version;
}
