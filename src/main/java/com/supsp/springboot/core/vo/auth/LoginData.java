package com.supsp.springboot.core.vo.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supsp.springboot.core.interfaces.IAuthAccount;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "LoginResultData", description = "登录结果数据")
public class LoginData implements IVo {
    @Serial
    private static final long serialVersionUID = -5920392328790252035L;

    @Schema(title = "登录结果")
    protected Boolean result;

    @Schema(title = "用户ID")
    protected Long memberId;

    @Schema(title = "Token")
    @JsonIgnore
    protected String token;

    @Schema(title = "sid")
    @JsonIgnore
    protected String sid;

    @Schema(title = "账号")
    @JsonIgnore
    protected IAuthAccount account;
}
