package com.supsp.springboot.core.base;

import com.supsp.springboot.core.exceptions.ExceptionCodes;
import com.supsp.springboot.core.interfaces.IResult;
import com.supsp.springboot.core.utils.CommonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Accessors(chain = true)
public class Result<T> implements IResult<T> {
    @Serial
    private static final long serialVersionUID = -2842290619776507475L;

    @Schema(title = "返回标识", description = "默认不可修改")
    private Boolean _R_ = true;

    @Schema(title = "错误码")
    protected int code = 0;

    @Schema(title = "错误提示")
    protected String message = "OK";

    @Schema(title = "数据")
    protected T data;

    @Schema(title = "时间戳")
    protected long timestamp;

    public Result(T data) {
        this.data = data;
        this.timestamp = CommonUtils.timestamp();
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = CommonUtils.timestamp();
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ExceptionCodes.OPERATION_FAILURE.getCode(), ExceptionCodes.OPERATION_FAILURE.getMessage());
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(ExceptionCodes.OPERATION_FAILURE.getCode(), message);
    }

    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message);
    }

}
