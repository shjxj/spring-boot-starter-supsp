package com.supsp.springboot.core.vo;

import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.enums.OrgAuthority;
import com.supsp.springboot.core.interfaces.IVo;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class Scope implements IVo {
    @Serial
    private static final long serialVersionUID = 1795970783426367658L;

    @Schema(title = "登录用户类型")
    protected AuthMemberType memberType;

    @Schema(title = "组织权限类型N(none)未设置 G(Global)全局 R(Region)地区")
    private OrgAuthority orgAuthority;

    @Schema(title = "企业ID")
    private Long orgId;

    @Schema(title = "门店ID")
    private Long storeId;

    @Schema(title = "店铺ID")
    private Long shopId;

    @Schema(title = "省")
    private Long province;

    @Schema(title = "市")
    private Long city;

    @Schema(title = "区/县")
    private Long district;

    @Schema(title = "街道")
    private Long street;

    @Schema(title = "社区")
    private Long community;
}
