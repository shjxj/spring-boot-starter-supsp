package com.supsp.springboot.core.vo;

import com.supsp.springboot.core.interfaces.IData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "CacheValue", description = "缓存数据")
public class CacheData<T> implements IData {
    @Serial
    private static final long serialVersionUID = 4475896021487386355L;

    @Schema(title = "unix 时间戳")
    private Long timestamp;

    @Schema(title = "到期 时间戳")
    private Long expiresAt;

    @Schema(title = "数据")
    private T data;

    @Schema(title = "模块")
    private String module;

    @Schema(title = "模块")
    private String app;

    @Schema(title = "相关表")
    private List<String> tables;

    @Schema(title = "版本")
    private String version;
}
