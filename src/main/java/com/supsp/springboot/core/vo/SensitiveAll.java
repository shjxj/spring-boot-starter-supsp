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
@Schema(name = "SysObject", description = "数据对象")
public class SensitiveAll implements IVo {
    @Serial
    private static final long serialVersionUID = 1545174795854588315L;

    @Schema(title = "unix 时间戳")
    private Long timestamp;
    
}
