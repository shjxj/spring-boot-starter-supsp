package com.supsp.springboot.core.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.annotations.SensitiveField;
import com.supsp.springboot.core.enums.*;
import com.supsp.springboot.core.interfaces.IAuthAccount;
import com.supsp.springboot.core.vo.auth.AccountOrg;
import com.supsp.springboot.core.vo.auth.AccountPost;
import com.supsp.springboot.core.vo.auth.AccountShop;
import com.supsp.springboot.core.vo.auth.AccountStore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@SensitiveData
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
public abstract class BaseAuthAccount implements IAuthAccount {

    @Serial
    private static final long serialVersionUID = -2504578744815480393L;

    @Schema(title = "过期时间")
    @JsonIgnore
    protected Date issuedAt;

    @Schema(title = "过期时间")
    @JsonIgnore
    protected Date expiresAt;

    @Schema(title = "request sid")
    @JsonIgnore
    @SensitiveField(type = SensitiveType.SID)
    protected String sid;

    @Schema(title = "request ip")
    @SensitiveField(type = SensitiveType.IPV4)
    protected String ip;

    @Schema(title = "token")
    @JsonIgnore
    @SensitiveField(type = SensitiveType.TOKEN)
    protected String token;

    @Schema(title = "账号类型")
    private LoginType loginType = LoginType.account;

    @Schema(title = "用户ID")
    protected Long id;

    @Schema(title = "登录用户类型")
    protected AuthMemberType memberType;

    @Schema(title = "登录账号类型")
    protected AccountType type;

    @Schema(title = "登录账号")
    @SensitiveField(type = SensitiveType.ACCOUNT)
    protected String account;

    @Schema(title = "用户姓名")
    // @SensitiveField(type = SensitiveType.NAME)
    protected String name;

    @Schema(title = "联系手机号")
    @SensitiveField(type = SensitiveType.MOBILE)
    protected String phone;

    @Schema(title = "用户头像")
    protected Long avatar;

    @Schema(title = "用户认证")
    @SensitiveField(type = SensitiveType.PWD_VALUE)
    @JsonIgnore
    protected String auth;

    @Schema(title = "权限列表")
    protected List<String> permissions;

    @Schema(title = "角色列表")
    protected List<String> roles;

    @Schema(title = "组织ID")
    protected Long orgId;

    @Schema(title = "门店ID")
    protected Long storeId;

    @Schema(title = "组织权限类型 none:未设置; global: 全局; region: 地区;")
    protected OrgAuthority authority;

    @Schema(title = "地区信息")
    protected String area;

    @Schema(title = "省")
    protected Long province;

    @Schema(title = "市")
    protected Long city;

    @Schema(title = "区")
    protected Long district;

    @Schema(title = "街道")
    protected Long street;

    @Schema(title = "社区")
    protected Long community;

    @Schema(title = "一级类型编码")
    protected String kindCodeFirst;

    @Schema(title = "二级类型编码")
    protected String kindCodeSecond;

    @Schema(title = "三级类型编码")
    protected String kindCodeThird;

    @Schema(title = "经营类型编码")
    protected String kindCode;

    @Schema(title = "类型 数据结构[类型名称::类型编码||类型名称::类型编码]")
    protected String kindValue;

    @Schema(title = "是否企业法人")
    protected boolean legalPerson = false;

    @Schema(title = "是否部门负责人")
    protected boolean managerPerson = false;

    @Schema(title = "是否关键岗位")
    protected boolean pimaryPost = false;

    @Schema(title = "店铺ID")
    protected Long shopId;

    @Schema(title = "商户ID")
    protected Long merchantId;

    @Schema(title = "租户ID")
    protected Long tenantId;

    @Schema(title = "商户")
    protected Boolean merchant;

    @Schema(title = "租户")
    protected Boolean tenant;

    @Schema(title = "企业用户类型 none:默认; tenant:租户; merchant:商家; consumer:消费者; api:API; ")
    protected AuthMemberType orgMemberType;

    @Schema(title = "门店用户类型 none:默认; tenant:租户; merchant:商家; consumer:消费者; api:API; ")
    protected AuthMemberType storeMemberType;

    @Override
    public boolean isLegalPerson() {
        return legalPerson;
    }

    @Override
    public void setLegalPerson(boolean legalPerson) {
        this.legalPerson = legalPerson;
    }

    @Override
    public boolean isManagerPerson() {
        return managerPerson;
    }

    @Override
    public void setManagerPerson(boolean managerPerson) {
        this.managerPerson = managerPerson;
    }

    @Override
    public boolean isPimaryPost() {
        return pimaryPost;
    }

    @Override
    public void setPimaryPost(boolean pimaryPost) {
        this.pimaryPost = pimaryPost;
    }

    @Override
    public Long getShopId() {
        return shopId;
    }

    @Override
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Override
    public Long getTenantId() {
        return tenantId;
    }

    @Override
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public Long getMerchantId() {
        return merchantId;
    }

    @Override
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public Boolean getMerchant() {
        return merchant;
    }

    @Override
    public void setMerchant(Boolean merchant) {
        this.merchant = merchant;
    }

    @Override
    public Boolean getTenant() {
        return tenant;
    }

    @Override
    public void setTenant(Boolean tenant) {
        this.tenant = tenant;
    }

}
