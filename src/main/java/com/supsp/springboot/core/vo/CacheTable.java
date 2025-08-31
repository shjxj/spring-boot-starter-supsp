package com.supsp.springboot.core.vo;

import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.interfaces.IData;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.StrUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "CacheValue", description = "缓存数据")
public class CacheTable implements IData {
    @Serial
    private static final long serialVersionUID = -37545444406139255L;

    @Schema(title = "unix 时间戳")
    private Long timestamp;

    @Schema(title = "到期 时间戳")
    private Long expiresAt;

    @Schema(title = "表名")
    private String table;

    @Schema(title = "版本")
    private String version;

    @Resource
    private CoreProperties properties;

    public CacheTable(String table) {
        this.table = table;
        this.timestamp = CommonUtils.timestamp();
        this.expiresAt = this.timestamp + CoreProperties.CACHE_TABLE_EXPIRE.getSeconds();
        this.version = CoreProperties.CACHE_VERSION;
    }

    public static CacheTable cacheTable(String table) {
        if (StrUtils.isBlank(table)) {
            return null;
        }
        return new CacheTable(table);
    }

}
