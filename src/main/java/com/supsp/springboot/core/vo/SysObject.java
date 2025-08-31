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
public class SysObject<T> implements IVo {
    @Serial
    private static final long serialVersionUID = 3364230500057415830L;

    @Schema(title = "应用名称")
    private Owner owner;

    @Schema(title = "应用信息")
    private ObjectInfo object;

    @Schema(title = "数据")
    private T data;

    public SysObject(T data) {
        this.data = data;
    }

    public static <T> SysObject<T> obj(T data) {
        return new SysObject<>(data);
    }
}
