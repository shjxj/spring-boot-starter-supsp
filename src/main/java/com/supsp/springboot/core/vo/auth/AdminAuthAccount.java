package com.supsp.springboot.core.vo.auth;


import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.base.*;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.interfaces.IAuthAccount;
import com.supsp.springboot.core.vo.auth.admin.AdminAccountOrg;
import com.supsp.springboot.core.vo.auth.admin.AdminAccountPost;
import com.supsp.springboot.core.vo.auth.admin.AdminAccountShop;
import com.supsp.springboot.core.vo.auth.admin.AdminAccountStore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Accessors(chain = true)
@SensitiveData
public class AdminAuthAccount extends BaseAuthAccount implements IAuthAccount {

    @Serial
    private static final long serialVersionUID = -8143916037472920916L;

    @Schema(title = "组织列表")
    private List<AdminAccountOrg> orgs;

    @Schema(title = "门店列表")
    private List<AdminAccountStore> stores;

    @Schema(title = "岗位列表")
    private List<AdminAccountPost> posts;

    @Schema(title = "组织信息")
    private AdminAccountOrg org;

    @Schema(title = "门店信息")
    private AdminAccountStore store;

    @Schema(title = "店铺信息")
    private AdminAccountShop shop;

    @Schema(title = "登录用户类型")
    protected AuthMemberType memberType = AuthMemberType.admin;

    @Override
    public AuthMemberType getMemberType() {
        return memberType;
    }

    @Override
    public void setMemberType(AuthMemberType memberType) {
        this.memberType = memberType;
    }

    @Override
    public <T extends BaseAccountOrg> void setOrgs(List<T> orgs) {
        try {
            this.orgs = (List<AdminAccountOrg>) orgs;
        } catch (Exception e) {
            // log
        }
    }

    @Override
    public <T extends BaseAccountStore> void setStores(List<T> stores) {
        try {
            this.stores = (List<AdminAccountStore>) stores;
        } catch (Exception e) {
            // log
        }
    }

    @Override
    public <T extends BaseAccountPost> void setPosts(List<T> posts) {
        try {
            this.posts = (List<AdminAccountPost>) posts;
        } catch (Exception e) {
            // log
        }
    }

    @Override
    public <T extends BaseAccountOrg> void setOrg(T org) {
        try {
            this.org = (AdminAccountOrg) org;
        } catch (Exception e) {
            // log
        }
    }

    @Override
    public <T extends BaseAccountStore> void setStore(T store) {
        try {
            this.store = (AdminAccountStore) store;
        } catch (Exception e) {
            // log
        }
    }

    @Override
    public <T extends BaseAccountShop> void setShop(T shop) {
        this.shop = (AdminAccountShop) shop;
    }

    @Override
    public AuthMemberType getStoreMemberType() {
        return AuthMemberType.admin;
    }

    @Override
    public void setStoreMemberType(AuthMemberType storeMemberType) {
        this.orgMemberType = AuthMemberType.admin;
    }

    @Override
    public AuthMemberType getOrgMemberType() {
        return AuthMemberType.admin;
    }

    @Override
    public void setOrgMemberType(AuthMemberType orgMemberType) {
        this.storeMemberType = AuthMemberType.admin;
    }
}
