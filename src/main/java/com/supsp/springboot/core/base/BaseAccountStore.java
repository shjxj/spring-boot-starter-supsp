package com.supsp.springboot.core.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.annotations.SensitiveField;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.enums.SensitiveType;
import com.supsp.springboot.core.interfaces.IAccountStore;
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
public abstract class BaseAccountStore implements IAccountStore {
    @Serial
    private static final long serialVersionUID = 8570256240983993485L;

    @Schema(title = "组织ID")
    protected Long orgId;

    @Schema(title = "门店ID")
    protected Long storeId;

    @Schema(title = "上级门店")
    protected Long parentId;

    @Schema(title = "顶级门店")
    protected Long topId;

    @Schema(title = "默认门店")
    protected Boolean isDefault;

    @Schema(title = "门店类型")
    protected String storeType;

    @Schema(title = "组织类型数据字典信息 数据结构[字典名称::字典名称值]")
    protected String storeTypeValue;

    @Schema(title = "门店名称")
    @SensitiveField(type = SensitiveType.STORE_NAME)
    protected String storeName;

    @Schema(title = "门店简称")
    @SensitiveField(type = SensitiveType.STORE_NAME)
    protected String storeShortName;

    @Schema(title = "门店编码")
    protected String storeCode;

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

    @Schema(title = "门店负责人账号ID")
    protected Long managerPersonMemberId;

    @Schema(title = "门店用户类型 none:默认; tenant:租户; merchant:商家; consumer:消费者; api:API; ")
    protected AuthMemberType memberType;

    @Schema(title = "商户ID")
    protected Long merchantId;

    @Schema(title = "租户ID")
    protected Long tenantId;

    @Override
    public Boolean getDefault() {
        return null;
    }

    @Override
    public void setDefault(Boolean aDefault) {

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
}
