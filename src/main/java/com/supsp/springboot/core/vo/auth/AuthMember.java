package com.supsp.springboot.core.vo.auth;

import com.supsp.springboot.core.enums.AccountType;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.enums.LoginType;
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
@Schema(name = "AuthMember", description = "地区信息")
public class AuthMember implements IVo {


    @Serial
    private static final long serialVersionUID = -456595474399350439L;

    @Schema(title = "账号类型")
    private LoginType loginType = LoginType.account;

    @Schema(title = "登录用户类型")
    protected AuthMemberType memberType;

    @Schema(title = "用户ID")
    private Long memberId;

    @Schema(title = "account")
    private AccountType accountType;

    @Schema(title = "登录账号")
    private String loginAccount;

    @Schema(title = "登录密码")
    private String loginPwd;
}
