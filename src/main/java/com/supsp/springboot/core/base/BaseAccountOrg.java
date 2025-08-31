package com.supsp.springboot.core.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.annotations.SensitiveField;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.enums.OrgAuthority;
import com.supsp.springboot.core.enums.SensitiveType;
import com.supsp.springboot.core.interfaces.IAccountOrg;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

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
public abstract class BaseAccountOrg implements IAccountOrg {

    @Serial
    private static final long serialVersionUID = 7431725864248583569L;

    @Schema(title = "组织ID")
    protected Long orgId;

    @Schema(title = "上级组织")
    protected Long parentId;

    @Schema(title = "顶级组织")
    protected Long topId;

    @Schema(title = "组织类型")
    protected String orgType;

    @Schema(title = "组织类型数据字典信息 数据结构[字典名称::字典名称值]")
    protected String orgTypeValue;

    @Schema(title = "组织名称")
    @SensitiveField(type = SensitiveType.ORG_NAME)
    protected String orgName;

    @Schema(title = "组织简称")
    @SensitiveField(type = SensitiveType.ORG_NAME)
    protected String orgShortName;

    @Schema(title = "组织编码")
    protected String orgCode;

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
    @TableField(exist = false)
    protected String kindCode;

    @Schema(title = "类型 数据结构[类型名称::类型编码||类型名称::类型编码]")
    protected String kindValue;

    @Schema(title = "法人账号ID")
    protected Long legalPersonMemberId;

    @Schema(title = "企业用户类型 none:默认; tenant:租户; merchant:商家; consumer:消费者; api:API; ")
    protected AuthMemberType memberType;

    @Schema(title = "商户ID")
    protected Long merchantId;

    @Schema(title = "租户ID")
    protected Long tenantId;

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
}
