package com.supsp.springboot.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supsp.springboot.core.enums.AuditStatus;
import com.supsp.springboot.core.enums.EnableStatus;
import com.supsp.springboot.core.enums.ShowStatus;
import com.supsp.springboot.core.vo.Owner;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
//@Schema(name = "BaseDataParams", description = "请求参数")
public abstract class BaseDataParams<T> extends BaseParams implements IDataParams<T> {
    @Serial
    private static final long serialVersionUID = 2937471053255662840L;

    @Schema(title = "显示状态")
    protected ShowStatus showStatus;

    @Schema(title = "启用状态")
    protected EnableStatus enableStatus;

    @Schema(title = "审核状态")
    protected AuditStatus auditStatus;

    @Schema(title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected List<LocalDateTime> createdDateTime;

    @Schema(title = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected List<LocalDateTime> updatedDateTime;

    @Schema(title = "创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected List<LocalDate> createdDate;

    @Schema(title = "更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected List<LocalDate> updatedDate;

    @Schema(title = "标签")
    protected List<String> tags;

    @Schema(title = "所有者")
    protected Owner owner;

    @Override
    public ShowStatus getShowStatus() {
        return showStatus;
    }

    @Override
    public void setShowStatus(ShowStatus showStatus) {
        this.showStatus = showStatus;
    }

    @Override
    public EnableStatus getEnableStatus() {
        return enableStatus;
    }

    @Override
    public void setEnableStatus(EnableStatus enableStatus) {
        this.enableStatus = enableStatus;
    }

    @Override
    public AuditStatus getAuditStatus() {
        return auditStatus;
    }

    @Override
    public void setAuditStatus(AuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Override
    public List<LocalDateTime> getCreatedDateTime() {
        return createdDateTime;
    }

    @Override
    public void setCreatedDateTime(List<LocalDateTime> createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @Override
    public List<LocalDateTime> getUpdatedDateTime() {
        return updatedDateTime;
    }

    @Override
    public void setUpdatedDateTime(List<LocalDateTime> updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    @Override
    public List<LocalDate> getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(List<LocalDate> createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public List<LocalDate> getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(List<LocalDate> updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    @Override
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public Owner getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
