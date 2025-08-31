package com.supsp.springboot.core.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.supsp.springboot.core.annotations.EntityColumn;
import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.interfaces.IAccountShop;
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
abstract public class BaseAccountShop implements IAccountShop {

    @Serial
    private static final long serialVersionUID = 3279946856698703675L;

    @Schema(title = "店铺ID")
    @EntityColumn(select = true, defaultSelect = true, isDataId = true)
    private Long shopId;

    @Schema(title = "组织ID")
    private Long orgId;

    @Schema(title = "上级组织")
    private Long parentOrgId;

    @Schema(title = "顶级组织")
    private Long topOrgId;

    @Schema(title = "门店ID")
    private Long storeId;

    @Schema(title = "上级门店")
    private Long parentStoreId;

    @Schema(title = "顶级门店")
    private Long topStoreId;

    @Schema(title = "地区显数据结构[新疆维吾尔自治区::650000||乌鲁木齐市::650100||天山区::650102]")
    private String area;

    @Schema(title = "省")
    private Long province;

    @Schema(title = "市")
    private Long city;

    @Schema(title = "区")
    private Long district;

    @Schema(title = "街道")
    private Long street;

    @Schema(title = "社区")
    private Long community;

    @Schema(title = "店铺 来源数据字典 shopShopType")
    private String shopType;

    @Schema(title = "店铺类型数据字典信息 数据结构[字典名称::字典名称值]", description = "前端无需传入")
    private String shopTypeValue;

    @Schema(title = "门店")
    private String shopName;
}
