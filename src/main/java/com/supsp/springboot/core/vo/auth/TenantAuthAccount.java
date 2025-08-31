package com.supsp.springboot.core.vo.auth;
import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.base.*;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.interfaces.IAuthAccount;
import com.supsp.springboot.core.vo.auth.tenant.TenantAccountOrg;
import com.supsp.springboot.core.vo.auth.tenant.TenantAccountPost;
import com.supsp.springboot.core.vo.auth.tenant.TenantAccountShop;
import com.supsp.springboot.core.vo.auth.tenant.TenantAccountStore;
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
public class TenantAuthAccount extends BaseAuthAccount implements IAuthAccount {

    @Serial
    private static final long serialVersionUID = 2721671749738146855L;

    @Schema(title = "登录用户类型")
    protected AuthMemberType memberType = AuthMemberType.tenant;

    @Schema(title = "组织列表")
    private List<TenantAccountOrg> orgs;

    @Schema(title = "门店列表")
    private List<TenantAccountStore> stores;

    @Schema(title = "岗位列表")
    private List<TenantAccountPost> posts;

    @Schema(title = "组织信息")
    private TenantAccountOrg org;

    @Schema(title = "门店信息")
    private TenantAccountStore store;

    @Schema(title = "店铺信息")
    private TenantAccountShop shop;

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
            this.orgs = (List<TenantAccountOrg>) orgs;
        } catch (Exception e) {
            // log
        }
    }

    @Override
    public <T extends BaseAccountStore> void setStores(List<T> stores) {
        try {
            this.stores = (List<TenantAccountStore>) stores;
        } catch (Exception e) {
            // log
        }
    }

    @Override
    public <T extends BaseAccountPost> void setPosts(List<T> posts) {
        try {
            this.posts = (List<TenantAccountPost>) posts;
        } catch (Exception e) {
            // log
        }
    }

    @Override
    public <T extends BaseAccountOrg> void setOrg(T org) {
        try {
            this.org = (TenantAccountOrg) org;
        } catch (Exception e) {
            // log
        }
    }

    @Override
    public <T extends BaseAccountStore> void setStore(T store) {
        try {
            this.store = (TenantAccountStore) store;
        } catch (Exception e) {
            // log
        }
    }

    @Override
    public <T extends BaseAccountShop> void setShop(T shop) {
        try {
            this.shop = (TenantAccountShop) shop;
        } catch (Exception e) {
            // log
        }
    }

}
