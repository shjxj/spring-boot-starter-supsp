package com.supsp.springboot.core.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.interfaces.IAccountRole;
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
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
public abstract class BaseAccountRole implements IAccountRole {
    @Serial
    private static final long serialVersionUID = 8285523166193462959L;

    @Schema(title = "组织ID")
    protected Long orgId;

    @Schema(title = "门店ID")
    protected Long storeId;

    @Schema(title = "岗位ID")
    protected Long postId;

    @Schema(title = "角色ID")
    protected Long roleId;

    @Schema(title = "角色类型")
    protected String roleType;

    @Schema(title = "角色名称")
    protected Long roleName;

    @Schema(title = "角色编码")
    protected String roleCode;

    @Schema(title = "角色用户类型 none:默认; tenant:租户; merchant:商家; consumer:消费者; api:API; ")
    protected AuthMemberType memberType;
}
