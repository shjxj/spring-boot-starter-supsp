package com.supsp.springboot.core.model;


import com.supsp.springboot.core.enums.AuditStatus;
import com.supsp.springboot.core.enums.EnableStatus;
import com.supsp.springboot.core.enums.ShowStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IDataFilter<T> extends IFilter {
    Set<EnableStatus> getEnableStatuses();

    void setEnableStatuses(Set<EnableStatus> enableStatuses);

    Set<ShowStatus> getShowStatuses();

    void setShowStatuses(Set<ShowStatus> showStatuses);

    List<AuditStatus> getAuditStatuses();

    void setAuditStatuses(List<AuditStatus> auditStatuses);

    Set<LocalDateTime> getCreatedAt();

    void setCreatedAt(Set<LocalDateTime> createdAt);

    Set<LocalDateTime> getUpdatedAt();

    void setUpdatedAt(Set<LocalDateTime> updatedAt);
}
