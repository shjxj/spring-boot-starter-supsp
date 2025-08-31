package com.supsp.springboot.core.vo;

import com.supsp.springboot.core.enums.AccountType;
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
public class Operator implements IVo {
    @Serial
    private static final long serialVersionUID = 810671151057104538L;

    @Schema(title = "用户ID")
    protected Long id;

    @Schema(title = "企业ID")
    private Long orgId;

    @Schema(title = "企业类型编码")
    private String orgType;

    @Schema(title = "上级门店")
    private Long parentOrgId;

    @Schema(title = "顶级组织ID")
    private Long topOrgId;

    @Schema(title = "门店ID")
    private Long storeId;

    @Schema(title = "门店类型")
    protected String storeType;

    @Schema(title = "上级门店")
    private Long parentStoreId;

    @Schema(title = "顶级门店")
    private Long topStoreId;

    @Schema(title = "组织权限类型N(none)未设置 G(Global)全局 R(Region)地区")
    private OrgAuthority orgAuthority;

    @Schema(title = "登录用户类型")
    protected AuthMemberType memberType;

    @Schema(title = "登录账号")
    protected String account;

    @Schema(title = "登录账号类型")
    protected AccountType type;

    @Schema(title = "用户姓名")
    protected String name;

    @Schema(title = "联系手机号")
    private String phone;

    @Schema(title = "用户头像")
    protected Long avatar;

    @Schema(title = "地区信息")
    protected String area;

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

    @Schema(title = "店铺ID")
    private Long shopId;

    @Schema(title = "用户IP")
    private String requestIp;

    @Schema(title = "IP 信息")
    private IpRegionInfo ipInfo;

}
