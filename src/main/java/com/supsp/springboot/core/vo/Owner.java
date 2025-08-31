package com.supsp.springboot.core.vo;

import com.supsp.springboot.core.interfaces.IVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Builder
@Schema(name = "Owner", description = "所属信息")
public class Owner implements IVo {
    @Serial
    private static final long serialVersionUID = 3751895046989362746L;

    @Schema(title = "所有者类型")
    private String ownerType;

    @Schema(title = "所有者ID")
    private Long ownerId;

//    @Schema(title = "企业ID")
//    private Long orgId;
//
//    @Schema(title = "门店ID")
//    private Long storeId;
//
//    @Schema(title = "店铺ID")
//    private Long shopId;
//
//    @Schema(title = "用户ID")
//    private Long memberId;

    public Owner(String ownerType, Long ownerId) {
        this.ownerType = ownerType;
        this.ownerId = ownerId;
    }
}
