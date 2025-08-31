package com.supsp.springboot.core.interfaces;

import com.supsp.springboot.core.enums.AuthMemberType;

public interface IAccountRole extends IData {


    Long getOrgId();

    void setOrgId(Long orgId);

    Long getStoreId();

    void setStoreId(Long storeId);

    Long getPostId();

    void setPostId(Long postId);

    Long getRoleId();

    void setRoleId(Long roleId);

    String getRoleType();

    void setRoleType(String roleType);

    Long getRoleName();

    void setRoleName(Long roleName);

    String getRoleCode();

    void setRoleCode(String roleCode);

    AuthMemberType getMemberType();

    void setMemberType(AuthMemberType memberType);

}
