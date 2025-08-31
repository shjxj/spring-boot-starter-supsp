package com.supsp.springboot.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supsp.springboot.core.enums.AuditStatus;
import com.supsp.springboot.core.enums.EnableStatus;
import com.supsp.springboot.core.enums.ShowStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
//@Schema(name = "BaseDataFilter", description = "请求筛选参数")
public abstract class BaseDataFilter<T> extends BaseFilter implements IDataFilter<T> {
    @Serial
    private static final long serialVersionUID = 5287265371558352940L;

    @Schema(title = "启用状态")
    @JsonProperty("enableStatus")
    protected Set<EnableStatus> enableStatuses;

    @Schema(title = "启用状态")
    @JsonProperty("showStatus")
    protected Set<ShowStatus> showStatuses;

    @Schema(title = "审核状态")
    @JsonProperty("auditStatus")
    protected List<AuditStatus> auditStatuses;

    @Schema(title = "创建时间")
    protected Set<LocalDateTime> createdAt;

    @Schema(title = "更新时间")
    protected Set<LocalDateTime> updatedAt;

    @Override
    public Set<EnableStatus> getEnableStatuses() {
        return enableStatuses;
    }

    @Override
    public void setEnableStatuses(Set<EnableStatus> enableStatuses) {
        this.enableStatuses = enableStatuses;
    }

    @Override
    public Set<ShowStatus> getShowStatuses() {
        return showStatuses;
    }

    @Override
    public void setShowStatuses(Set<ShowStatus> showStatuses) {
        this.showStatuses = showStatuses;
    }

    @Override
    public List<AuditStatus> getAuditStatuses() {
        return auditStatuses;
    }

    @Override
    public void setAuditStatuses(List<AuditStatus> auditStatuses) {
        this.auditStatuses = auditStatuses;
    }

    @Override
    public Set<LocalDateTime> getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Set<LocalDateTime> createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Set<LocalDateTime> getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Set<LocalDateTime> updatedAt) {
        this.updatedAt = updatedAt;
    }
}
