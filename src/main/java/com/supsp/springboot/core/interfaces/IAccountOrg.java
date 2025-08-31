package com.supsp.springboot.core.interfaces;


import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.enums.OrgAuthority;

public interface IAccountOrg extends IData {

    Long getOrgId();

    void setOrgId(Long orgId);

    Long getParentId();

    void setParentId(Long parentId);

    Long getTopId();

    void setTopId(Long topId);

    String getOrgType();

    void setOrgType(String orgType);

    String getOrgName();

    void setOrgName(String orgName);

    String getOrgShortName();

    void setOrgShortName(String orgShortName);

    String getOrgCode();

    void setOrgCode(String orgCode);

    OrgAuthority getAuthority();

    void setAuthority(OrgAuthority authority);

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

    String getOrgTypeValue();

    void setOrgTypeValue(String orgTypeValue);

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

    Long getLegalPersonMemberId();

    void setLegalPersonMemberId(Long legalPersonMemberId);

    AuthMemberType getMemberType();

    void setMemberType(AuthMemberType memberType);

    Long getTenantId();

    void setTenantId(Long tenantId);

    Long getMerchantId();

    void setMerchantId(Long merchantId);

}
