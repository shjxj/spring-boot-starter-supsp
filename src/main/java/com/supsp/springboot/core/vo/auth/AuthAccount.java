package com.supsp.springboot.core.vo.auth;


import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.base.*;
import com.supsp.springboot.core.interfaces.IAuthAccount;
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
public class AuthAccount extends BaseAuthAccount implements IAuthAccount {

    @Serial
    private static final long serialVersionUID = -8143916037472920916L;

    @Schema(title = "组织列表")
    protected List<AccountOrg> orgs;

    @Schema(title = "门店列表")
    protected List<AccountStore> stores;

    @Schema(title = "岗位列表")
    protected List<AccountPost> posts;

    @Schema(title = "组织信息")
    protected AccountOrg org;

    @Schema(title = "门店信息")
    protected AccountStore store;

    @Schema(title = "店铺信息")
    protected AccountShop shop;

    @Override
    public <T extends BaseAccountOrg> void setOrgs(List<T> orgs) {
        try {
            this.orgs = (List<AccountOrg>) orgs;
        } catch (Exception e) {
            //
        }
    }

    @Override
    public <T extends BaseAccountStore> void setStores(List<T> stores) {
        try {
            this.stores = (List<AccountStore>) stores;
        } catch (Exception e) {
            //
        }
    }

    @Override
    public <T extends BaseAccountPost> void setPosts(List<T> posts) {
        try {
            this.posts = (List<AccountPost>) posts;
        } catch (Exception e) {
            //
        }
    }

    @Override
    public <T extends BaseAccountOrg> void setOrg(T org) {
        try {
            this.org = (AccountOrg) org;
        } catch (Exception e) {
            //
        }
    }

    @Override
    public <T extends BaseAccountStore> void setStore(T store) {
        try {
            this.store = (AccountStore) store;
        } catch (Exception e) {
            //
        }
    }

    @Override
    public <T extends BaseAccountShop> void setShop(T shop) {
        try {
            this.shop = (AccountShop) shop;
        } catch (Exception e) {
            //
        }
    }
}
