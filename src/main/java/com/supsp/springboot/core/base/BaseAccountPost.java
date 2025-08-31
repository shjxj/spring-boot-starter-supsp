package com.supsp.springboot.core.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.interfaces.IAccountPost;
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
public abstract class BaseAccountPost implements IAccountPost {
    @Serial
    private static final long serialVersionUID = 4145156486014919342L;

    @Schema(title = "组织ID")
    protected Long orgId;

    @Schema(title = "门店ID")
    protected Long storeId;

    @Schema(title = "岗位ID")
    protected Long postId;

    @Schema(title = "岗位类型")
    protected String postType;

    @Schema(title = "岗位名称")
    protected String postName;

    @Schema(title = "岗位编码")
    protected String postCode;

    @Schema(title = "岗位用户类型 none:默认; tenant:租户; merchant:商家; consumer:消费者; api:API; ")
    protected AuthMemberType memberType;

}
