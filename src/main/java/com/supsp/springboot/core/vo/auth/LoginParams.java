package com.supsp.springboot.core.vo.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.config.ValidGroup;
import com.supsp.springboot.core.enums.AccountType;
import com.supsp.springboot.core.enums.LoginType;
import com.supsp.springboot.core.interfaces.IVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Accessors(chain = true)
@Schema(name = "LoginParams", description = "登录请求")
@SensitiveData
public class LoginParams implements IVo {
    @Serial
    private static final long serialVersionUID = 5659843302508571956L;

    @Schema(title = "账号类型")
    private LoginType loginType = LoginType.account;

    @Schema(title = "登录账号")
    @NotBlank(message = "登录账号错误", groups = {ValidGroup.AccountLogin.class, ValidGroup.MobileCodeLogin.class})
    @JsonProperty("username")
    private String account;

    @Schema(title = "登录密码")
    @NotBlank(message = "登录密码错误", groups = {ValidGroup.AccountLogin.class, ValidGroup.DeviceLogin.class})
    private String password;

    @Schema(title = "账号类型")
    @JsonProperty("type")
    private AccountType accountType;

    @Schema(title = "自动登录")
    private Boolean autoLogin;

    @Schema(title = "手机验证码")
    @NotBlank(message = "请填写手机验证码", groups = ValidGroup.MobileCodeLogin.class)
    private String mobileCode;
}
