package com.supsp.springboot.core.interfaces;

import com.supsp.springboot.core.enums.AuthMemberType;

public interface IAccountPost extends IData {

    Long getOrgId();

    void setOrgId(Long orgId);

    Long getStoreId();

    void setStoreId(Long storeId);

    Long getPostId();

    void setPostId(Long postId);

    String getPostType();

    void setPostType(String postType);

    String getPostName();

    void setPostName(String postName);

    String getPostCode();

    void setPostCode(String postCode);

    AuthMemberType getMemberType();

    void setMemberType(AuthMemberType memberType);
}
