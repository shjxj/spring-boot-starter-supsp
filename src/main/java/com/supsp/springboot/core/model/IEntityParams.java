package com.supsp.springboot.core.model;



import com.supsp.springboot.core.enums.AuthMemberType;

import java.time.LocalDateTime;
import java.util.List;

public interface IEntityParams<T extends BaseModelEntity<?>> extends IDataParams<T> {

    Boolean get__API__();

    void set__API__(Boolean __API__);

    List<Long> getIds();

    void setIds(List<Long> ids);

    String getKeyword();

    void setKeyword(String keyword);

    List<Long> getWithIds();

    void setWithIds(List<Long> withIds);

    String getKeywords();

    void setKeywords(String keywords);

    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    AuthMemberType getCreatedMemberType();

    void setCreatedMemberType(AuthMemberType createdMemberType);

    Long getCreatedMemberId();

    void setCreatedMemberId(Long createdMemberId);

    String getCreatedMemberName();

    void setCreatedMemberName(String createdMemberName);

    String getCreatedMemberAccount();

    void setCreatedMemberAccount(String createdMemberAccount);

    String getCreatedMemberIp();

    void setCreatedMemberIp(String createdMemberIp);

    LocalDateTime getUpdatedAt();

    void setUpdatedAt(LocalDateTime updatedAt);

    AuthMemberType getUpdatedMemberType();

    void setUpdatedMemberType(AuthMemberType updatedMemberType);

    Long getUpdatedMemberId();

    void setUpdatedMemberId(Long updatedMemberId);

    String getUpdatedMemberName();

    void setUpdatedMemberName(String updatedMemberName);

    String getUpdatedMemberAccount();

    void setUpdatedMemberAccount(String updatedMemberAccount);

    String getUpdatedMemberIp();

    void setUpdatedMemberIp(String updatedMemberIp);

    Long getShopId();

    Long getOrgId();

    Long getStoreId();

    Long getMemberId();

    Long getUid();

    void setUid(Long uid);

    Long getUserOrgId();

    void setUserOrgId(Long userOrgId);

    Long getUserOrgChildId();

    void setUserOrgChildId(Long userOrgChildId);

    Long getUserDepartmentId();

    void setUserDepartmentId(Long userDepartmentId);

    Long getUserWarehouseId();

    void setUserWarehouseId(Long userWarehouseId);

    Long getWarehouseMember();

    void setWarehouseMember(Long warehouseMember);

    Boolean getValid();

    void setValid(Boolean valid);

    Boolean getTask();

    void setTask(Boolean task);

    Boolean getQueryInvalid();

    void setQueryInvalid(Boolean queryInvalid);

    Boolean getOptions();

    void setOptions(Boolean options);
    Boolean getInFinder();

    void setInFinder(Boolean inFinder);

}
