package com.supsp.springboot.core.model;

import com.supsp.springboot.core.enums.AuditStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
//@Schema(name = "BaseEntityFilter", description = "请求筛选参数")
public abstract class BaseEntityFilter<T extends BaseModelEntity<?>> extends BaseDataFilter<T> implements IEntityFilter<T> {
    @Serial
    private static final long serialVersionUID = -8360878798363366196L;

    @Schema(title = "审核状态")
    protected List<AuditStatus> auditStatus;

    @Schema(title = "忽略审核状态")
    protected List<AuditStatus> ignoreAuditStatus;

    @Schema(title = "审核状态 [排除]")
    protected List<AuditStatus> excludeAuditStatus;

    @Schema(title = "责任分解审核状态")
    protected List<AuditStatus> dutyAuditStatus;

    @Schema(title = "责任分解审核状态 [排除]")
    protected List<AuditStatus> excludeDutyAuditStatus;
}
