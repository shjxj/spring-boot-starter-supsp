package com.supsp.springboot.core.model;



import com.supsp.springboot.core.enums.AuditStatus;
import com.supsp.springboot.core.enums.EnableStatus;
import com.supsp.springboot.core.enums.ShowStatus;
import com.supsp.springboot.core.vo.Owner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IDataParams<T> extends IParams {

    ShowStatus getShowStatus();

    void setShowStatus(ShowStatus showStatus);

    EnableStatus getEnableStatus();

    void setEnableStatus(EnableStatus enableStatus);

    AuditStatus getAuditStatus();

    void setAuditStatus(AuditStatus auditStatus);

    List<LocalDateTime> getCreatedDateTime();

    void setCreatedDateTime(List<LocalDateTime> createdDateTime);

    List<LocalDateTime> getUpdatedDateTime();

    void setUpdatedDateTime(List<LocalDateTime> updatedDateTime);

    List<LocalDate> getCreatedDate();

    void setCreatedDate(List<LocalDate> createdDate);

    List<LocalDate> getUpdatedDate();

    void setUpdatedDate(List<LocalDate> updatedDate);

    List<String> getTags();

    void setTags(List<String> tags);

    Owner getOwner();

    void setOwner(Owner owner);
}
