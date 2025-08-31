package com.supsp.springboot.core.interfaces;



import com.supsp.springboot.core.base.BaseAccountOrg;
import com.supsp.springboot.core.base.BaseAccountPost;
import com.supsp.springboot.core.base.BaseAccountShop;
import com.supsp.springboot.core.base.BaseAccountStore;
import com.supsp.springboot.core.enums.AccountType;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.enums.OrgAuthority;

import java.time.LocalDateTime;
import java.util.List;

public interface IAuthAccount extends IData {

    String getSid();

    void setSid(String sid);

    String getIp();

    void setIp(String ip);

    String getToken();

    void setToken(String token);

    Long getId();

    void setId(Long id);

    String getAccount();

    void setAccount(String account);

    AccountType getType();

    void setType(AccountType type);

    String getName();

    void setName(String name);

    String getPhone();

    void setPhone(String memberPhone);

    Long getAvatar();

    void setAvatar(Long avatar);

    String getAuth();

    void setAuth(String auth);

    AuthMemberType getMemberType();

    void setMemberType(AuthMemberType memberType);

    LocalDateTime getExpiresAt();

    void setExpiresAt(LocalDateTime expiresAt);

    List<String> getPermissions();

    void setPermissions(List<String> permissions);

    List<String> getRoles();

    void setRoles(List<String> roles);

    <T extends BaseAccountOrg> List<T> getOrgs();

    <T extends BaseAccountOrg> void setOrgs(List<T> orgs);

    <T extends BaseAccountStore> List<T> getStores();

    <T extends BaseAccountStore> void setStores(List<T> stores);

    <T extends BaseAccountPost> List<T> getPosts();

    <T extends BaseAccountPost> void setPosts(List<T> posts);

    <T extends BaseAccountOrg> T getOrg();

    <T extends BaseAccountOrg> void setOrg(T org);

    Long getOrgId();

    void setOrgId(Long orgId);

    Long getOrgChildId();

    void setOrgChildId(Long orgChildId);

    Long getDepartmentId();

    void setDepartmentId(Long departmentId);

    <T extends BaseAccountStore> T getStore();

    <T extends BaseAccountStore> void setStore(T store);

    <T extends BaseAccountShop> T getShop();

    <T extends BaseAccountShop> void setShop(T shop);

    Long getStoreId();

    void setStoreId(Long storeId);

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

    OrgAuthority getOrgAuthority();

    void setOrgAuthority(OrgAuthority orgAuthority);

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

    boolean isLegalPerson();

    void setLegalPerson(boolean legalPerson);

    boolean isManagerPerson();

    void setManagerPerson(boolean managerPerson);

    boolean isPimaryPost();

    void setPimaryPost(boolean pimaryPost);

    Long getShopId();

    void setShopId(Long shopId);

    Long getTenantId();

    void setTenantId(Long tenantId);

    Long getMerchantId();

    void setMerchantId(Long merchantId);

    Boolean getMerchant();

    void setMerchant(Boolean merchant);

    Boolean getTenant();

    void setTenant(Boolean tenant);

    AuthMemberType getStoreMemberType();

    void setStoreMemberType(AuthMemberType storeMemberType);

    AuthMemberType getOrgMemberType();

    void setOrgMemberType(AuthMemberType orgMemberType);
}
