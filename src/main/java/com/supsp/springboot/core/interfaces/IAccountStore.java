package com.supsp.springboot.core.interfaces;

import com.supsp.springboot.core.enums.AuthMemberType;

public interface IAccountStore extends IData {


    Long getOrgId();

    void setOrgId(Long orgId);

    Long getStoreId();

    void setStoreId(Long storeId);

    Long getParentId();

    void setParentId(Long parentId);

    Long getTopId();

    void setTopId(Long topId);

    Boolean getDefault();

    void setDefault(Boolean aDefault);

    String getStoreType();

    void setStoreType(String storeType);

    String getStoreName();

    void setStoreName(String storeName);

    String getStoreShortName();

    void setStoreShortName(String storeShortName);

    String getStoreCode();

    void setStoreCode(String storeCode);

    String getArea();

    void setArea(String area);

    Long getProvince();

    void setProvince(Long province);

    Long getCity();

    void setCity(Long city);

    Long getDistrict();

    void setDistrict(Long district);

    Long getStreet();

    void setStreet(Long street);

    Long getCommunity();

    void setCommunity(Long community);

    String getStoreTypeValue();

    void setStoreTypeValue(String storeTypeValue);

    String getKindCodeFirst();

    void setKindCodeFirst(String kindCodeFirst);

    String getKindCodeSecond();

    void setKindCodeSecond(String kindCodeSecond);

    String getKindCodeThird();

    void setKindCodeThird(String kindCodeThird);

    String getKindCode();

    void setKindCode(String kindCode);

    String getKindValue();

    void setKindValue(String kindValue);

    Long getManagerPersonMemberId();

    void setManagerPersonMemberId(Long managerPersonMemberId);

    AuthMemberType getMemberType();

    void setMemberType(AuthMemberType memberType);

    Long getTenantId();

    void setTenantId(Long tenantId);

    Long getMerchantId();

    void setMerchantId(Long merchantId);
}
