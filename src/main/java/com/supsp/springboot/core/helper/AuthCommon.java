package com.supsp.springboot.core.helper;

import com.supsp.springboot.core.base.*;
import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.consts.Constants;
import com.supsp.springboot.core.consts.DataKeys;
import com.supsp.springboot.core.enums.AccountType;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.enums.OrgAuthority;
import com.supsp.springboot.core.enums.SysModule;
import com.supsp.springboot.core.interfaces.IAuthAccount;
import com.supsp.springboot.core.threads.ConsumerData;
import com.supsp.springboot.core.threads.GlobalData;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.IpUtils;
import com.supsp.springboot.core.utils.StrUtils;
import com.supsp.springboot.core.vo.IpRegionInfo;
import com.supsp.springboot.core.vo.Operator;
import com.supsp.springboot.core.vo.Scope;
import com.supsp.springboot.core.vo.auth.*;
import com.supsp.springboot.core.vo.auth.admin.AdminAccountOrg;
import com.supsp.springboot.core.vo.auth.api.ApiAccountOrg;
import com.supsp.springboot.core.vo.auth.api.ApiAccountStore;
import com.supsp.springboot.core.vo.auth.consumer.ConsumerAccountOrg;
import com.supsp.springboot.core.vo.auth.consumer.ConsumerAccountStore;
import com.supsp.springboot.core.vo.auth.merchant.MerchantAccountOrg;
import com.supsp.springboot.core.vo.auth.merchant.MerchantAccountStore;
import com.supsp.springboot.core.vo.auth.tenant.TenantAccountOrg;
import com.supsp.springboot.core.vo.auth.tenant.TenantAccountStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AuthCommon {

    @Resource
    private CoreProperties coreProperties;

    // ======== token headers
    // admin
    public static String adminAuthHeader() {
        return GlobalData.get(CoreProperties.HEADER_ADMIN_TOKEN_SID);
    }

    public static Long appUidHeader() {
        String uid = GlobalData.get(CoreProperties.HEADER_ADMIN_TOKEN_USER);
        if (StrUtils.isBlank(uid)) {
            return null;
        }
        return CommonUtils.toLong(uid);
    }

    public static boolean hasAdminHeader() {
        String val = adminAuthHeader();
        return StrUtils.isNotBlank(val);
    }

    public static String adminAuthOrgHeader() {
        return GlobalData.get(CoreProperties.HEADER_ADMIN_TOKEN_ORG);
    }

    public static String adminAuthStoreHeader() {
        return GlobalData.get(CoreProperties.HEADER_ADMIN_TOKEN_DEP);
    }

    public static String adminAuthShopHeader() {
        return GlobalData.get(CoreProperties.HEADER_ADMIN_TOKEN_SHOP);
    }

    // tenant
    public static String tenantAuthHeader() {
        return GlobalData.get(CoreProperties.HEADER_TENANT_TOKEN_SID);
    }

    public static boolean hasTenantHeader() {
        String val = tenantAuthHeader();
        return StrUtils.isNotBlank(val);
    }

    public static String tenantAuthOrgHeader() {
        return GlobalData.get(CoreProperties.HEADER_TENANT_TOKEN_ORG);
    }

    public static String tenantAuthStoreHeader() {
        return GlobalData.get(CoreProperties.HEADER_TENANT_TOKEN_DEP);
    }

    public static String tenantAuthShopHeader() {
        return GlobalData.get(CoreProperties.HEADER_TENANT_TOKEN_SHOP);
    }

    // merchant
    public static String merchantAuthHeader() {
        return GlobalData.get(CoreProperties.HEADER_MERCHANT_TOKEN_SID);
    }

    public static boolean hasMerchantHeader() {
        String val = merchantAuthHeader();
        return StrUtils.isNotBlank(val);
    }

    public static String merchantAuthOrgHeader() {
        return GlobalData.get(CoreProperties.HEADER_MERCHANT_TOKEN_ORG);
    }

    public static String merchantAuthStoreHeader() {
        return GlobalData.get(CoreProperties.HEADER_MERCHANT_TOKEN_DEP);
    }

    public static String merchantAuthShopHeader() {
        return GlobalData.get(CoreProperties.HEADER_MERCHANT_TOKEN_SHOP);
    }

    // consumer
    public static String consumerAuthHeader() {
        return GlobalData.get(CoreProperties.HEADER_CONSUMER_TOKEN_SID);
    }

    public static boolean hasConsumerHeader() {
        String val = consumerAuthHeader();
        return StrUtils.isNotBlank(val);
    }

    public static String consumerAuthOrgHeader() {
        return GlobalData.get(CoreProperties.HEADER_CONSUMER_TOKEN_ORG);
    }

    public static String consumerAuthStoreHeader() {
        return GlobalData.get(CoreProperties.HEADER_CONSUMER_TOKEN_DEP);
    }

    public static String consumerAuthShopHeader() {
        return GlobalData.get(CoreProperties.HEADER_CONSUMER_TOKEN_SHOP);
    }

    // api
    public static String apiAuthHeader() {
        return GlobalData.get(CoreProperties.HEADER_API_TOKEN_SID);
    }

    public static boolean hasApiHeader() {
        String val = apiAuthHeader();
        return StrUtils.isNotBlank(val);
    }

    public static String apiAuthOrgHeader() {
        return GlobalData.get(CoreProperties.HEADER_API_TOKEN_ORG);
    }

    public static String apiAuthStoreHeader() {
        return GlobalData.get(CoreProperties.HEADER_API_TOKEN_DEP);
    }

    public static String apiAuthShopHeader() {
        return GlobalData.get(CoreProperties.HEADER_API_TOKEN_SHOP);
    }


    // current
    public static SysModule currentModule() {
        if (isMq()) {
            return ConsumerData.get(DataKeys.MQ_APP_MODULE);
        }
        return SystemCommon.getModule();
    }

    // 通过 header 获取
    public static AuthMemberType currentAuthMemberType() {
        if (hasAdminHeader()) {
            AdminAuthAccount adminAuthAccount = adminAuthAccount();
            if (ObjectUtils.isNotEmpty(adminAuthAccount)) {
                return AuthMemberType.admin;
            }
        }
        if (hasTenantHeader()) {
            TenantAuthAccount tenantAuthAccount = tenantAuthAccount();
            if (ObjectUtils.isNotEmpty(tenantAuthAccount)) {
                return AuthMemberType.tenant;
            }
        }
        if (hasMerchantHeader()) {
            MerchantAuthAccount merchantAuthAccount = merchantAuthAccount();
            if (ObjectUtils.isNotEmpty(merchantAuthAccount)) {
                return AuthMemberType.merchant;
            }
        }
        if (hasConsumerHeader()) {
            ConsumerAuthAccount consumerAuthAccount = consumerAuthAccount();
            if (ObjectUtils.isNotEmpty(consumerAuthAccount)) {
                return AuthMemberType.consumer;
            }
        }
        if (hasApiHeader()) {
            ApiAuthAccount apiAuthAccount = apiAuthAccount();
            if (ObjectUtils.isNotEmpty(apiAuthAccount)) {
                return AuthMemberType.api;
            }
        }
        return AuthMemberType.none;
    }

    // 通过 module 获取
    public static AuthMemberType moduleMemberType() {
        switch (currentModule()) {
            case admin -> {
                return AuthMemberType.admin;
            }
            case tenant -> {
                return AuthMemberType.tenant;
            }
            case merchant -> {
                return AuthMemberType.merchant;
            }
            case consumer -> {
                return AuthMemberType.consumer;
            }
            case api -> {
                return AuthMemberType.api;
            }
            default -> {
                return currentAuthMemberType();
            }
        }
    }

    // 通过 data key 获取
    public static boolean isMq() {
        String mqSide = ConsumerData.get(DataKeys.MQ_SIDE);
        return StrUtils.isNotBlank(mqSide) && mqSide.equalsIgnoreCase(DataKeys.MQ_CONSUMER_SIDE);
    }

    public static AuthMemberType currentMemberType() {
        AuthMemberType authMemberType = null;
        if (isMq()) {
            authMemberType = ConsumerData.get(DataKeys.MQ_MEMBER_TYPE);
            if (authMemberType == null) {
                authMemberType = AuthMemberType.none;
            }
            return authMemberType;
        }
        authMemberType = GlobalData.get(DataKeys.AUTH_REALM_MODULE);
        if (authMemberType == null || authMemberType.equals(AuthMemberType.none)) {
            return moduleMemberType();
        }
        return authMemberType;
    }

    // TRACE_ID
    public static String traceId() {
        return GlobalData.get(DataKeys.TRACE_ID);
    }

    //
    public static AdminAuthAccount adminAuthAccount(IAuthAccount account) {
        if (
                ObjectUtils.isEmpty(account)
                        || !account.getMemberType().equals(AuthMemberType.admin)
        ) {
            return null;
        }
        try {
            return (AdminAuthAccount) account;
        } catch (Exception e) {
            //
        }
        return null;
    }

    public static TenantAuthAccount tenantAuthAccount(IAuthAccount account) {
        if (
                ObjectUtils.isEmpty(account)
                        || !account.getMemberType().equals(AuthMemberType.tenant)
        ) {
            return null;
        }
        try {
            return (TenantAuthAccount) account;
        } catch (Exception e) {
            //
        }
        return null;
    }

    public static MerchantAuthAccount merchantAuthAccount(IAuthAccount account) {
        if (
                ObjectUtils.isEmpty(account)
                        || !account.getMemberType().equals(AuthMemberType.merchant)
        ) {
            return null;
        }
        try {
            return (MerchantAuthAccount) account;
        } catch (Exception e) {
            //
        }
        return null;
    }

    public static ConsumerAuthAccount consumerAuthAccount(IAuthAccount account) {
        if (
                ObjectUtils.isEmpty(account)
                        || !account.getMemberType().equals(AuthMemberType.consumer)
        ) {
            return null;
        }
        try {
            return (ConsumerAuthAccount) account;
        } catch (Exception e) {
            //
        }
        return null;
    }

    public static ApiAuthAccount apiAuthAccount(IAuthAccount account) {
        if (
                ObjectUtils.isEmpty(account)
                        || !account.getMemberType().equals(AuthMemberType.api)
        ) {
            return null;
        }
        try {
            return (ApiAuthAccount) account;
        } catch (Exception e) {
            //
        }
        return null;
    }

    //
    public static IAuthAccount getAdminAuthAccount() {
        if (isMq()){
            return ConsumerData.get(DataKeys.AUTH_ACCOUNT_ADMIN);
        }
        return GlobalData.get(DataKeys.AUTH_ACCOUNT_ADMIN);
    }

    public static IAuthAccount getTenantAuthAccount() {
        if (isMq()){
            return ConsumerData.get(DataKeys.AUTH_ACCOUNT_TENANT);
        }
        return GlobalData.get(DataKeys.AUTH_ACCOUNT_TENANT);
    }

    public static IAuthAccount getMerchantAuthAccount() {
        if (isMq()) {
            return ConsumerData.get(DataKeys.AUTH_ACCOUNT_MERCHANT);
        }
        return GlobalData.get(DataKeys.AUTH_ACCOUNT_MERCHANT);
    }

    public static IAuthAccount getConsumerAuthAccount() {
        if (isMq()){
            return ConsumerData.get(DataKeys.AUTH_ACCOUNT_CONSUMER);
        }
        return GlobalData.get(DataKeys.AUTH_ACCOUNT_CONSUMER);
    }

    public static IAuthAccount getApiAuthAccount() {
        if (isMq()){
            return ConsumerData.get(DataKeys.AUTH_ACCOUNT_API);
        }
        return GlobalData.get(DataKeys.AUTH_ACCOUNT_API);
    }

    public static IAuthAccount getAuthAccount() {
        AuthMemberType memberType = currentMemberType();
        switch (memberType) {
            case admin -> {
                return getAdminAuthAccount();
            }
            case tenant -> {
                return getTenantAuthAccount();
            }
            case merchant -> {
                return getMerchantAuthAccount();
            }
            case consumer -> {
                return getConsumerAuthAccount();
            }
            case api -> {
                return getApiAuthAccount();
            }
        }
        return null;
    }

    //
    public static AdminAuthAccount adminAuthAccount() {
        IAuthAccount account = getAdminAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return adminAuthAccount(account);
    }

    public static TenantAuthAccount tenantAuthAccount() {
        IAuthAccount account = getTenantAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return tenantAuthAccount(account);
    }

    public static MerchantAuthAccount merchantAuthAccount() {
        IAuthAccount account = getMerchantAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return merchantAuthAccount(account);
    }

    public static ConsumerAuthAccount consumerAuthAccount() {
        IAuthAccount account = getConsumerAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return consumerAuthAccount(account);
    }

    public static ApiAuthAccount apiAuthAccount() {
        IAuthAccount account = getApiAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return apiAuthAccount(account);
    }

    public static <T extends BaseAuthAccount> T getAccount() {
        try {
            return (T) getAuthAccount();
        } catch (Exception e) {
            // log.error();
        }
        return null;
    }

    public static AuthMemberType getMemberType() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return AuthMemberType.none;
        }
        return account.getMemberType();
    }

    public static String getArea() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getArea();
    }

    public static Long getProvince() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getProvince();
    }

    public static Long getCity() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getCity();
    }

    public static Long getDistrict() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getDistrict();
    }

    public static Long getStreet() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getStreet();
    }

    public static Long getCommunity() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getCommunity();
    }


    public static Operator getAdminOperator(IAuthAccount account) {
        AdminAuthAccount authAccount = adminAuthAccount(account);
        if (ObjectUtils.isEmpty(authAccount)) {
            return null;
        }
        Operator operator = Operator.builder()
                .id(account.getId())
                .orgId(account.getOrgId())
                .orgAuthority(account.getOrgAuthority())
                .memberType(account.getMemberType())
                .account(account.getAccount())
                .type(account.getType())
                .name(account.getName())
                .phone(account.getPhone())
                .avatar(account.getAvatar())
                .area(account.getArea())
                .province(account.getProvince())
                .city(account.getCity())
                .district(account.getDistrict())
                .street(account.getStreet())
                .community(account.getCommunity())
                .requestIp(getRequestIp())
                .build();
        operator.setIpInfo(IpUtils.ipInfo(operator.getRequestIp()));
        try {
            AdminAccountOrg org = (AdminAccountOrg) account.getOrg();
            if (ObjectUtils.isNotEmpty(org)) {
                operator.setOrgType(org.getOrgType())
                        .setParentOrgId(org.getParentId())
                        .setTopOrgId(org.getTopId());
            }
        } catch (Exception e) {
            // log
        }
        return operator;
    }

    public static Operator getTenantOperator(IAuthAccount account) {
        TenantAuthAccount authAccount = tenantAuthAccount(account);
        if (ObjectUtils.isEmpty(authAccount)) {
            return null;
        }
        Operator operator = Operator.builder()
                .id(account.getId())
                .orgId(account.getOrgId())
                .orgAuthority(account.getOrgAuthority())
                .memberType(account.getMemberType())
                .account(account.getAccount())
                .type(account.getType())
                .name(account.getName())
                .phone(account.getPhone())
                .avatar(account.getAvatar())
                .area(account.getArea())
                .province(account.getProvince())
                .city(account.getCity())
                .district(account.getDistrict())
                .street(account.getStreet())
                .community(account.getCommunity())
                .requestIp(AuthCommon.getRequestIp())
                .build();
        operator.setIpInfo(IpUtils.ipInfo(operator.getRequestIp()));

        try {
            TenantAccountOrg org = (TenantAccountOrg) account.getOrg();
            if (ObjectUtils.isNotEmpty(org)) {
                operator.setOrgType(org.getOrgType())
                        .setParentOrgId(org.getParentId())
                        .setTopOrgId(org.getTopId());
            }
        } catch (Exception e) {
            // log
        }

        try {
            TenantAccountStore store = (TenantAccountStore) account.getStore();
            if (ObjectUtils.isNotEmpty(store)) {
                operator.setStoreType(store.getStoreType())
                        .setParentStoreId(store.getParentId())
                        .setTopStoreId(store.getTopId());
            }
        } catch (Exception e) {
            // log
        }

        operator.setShopId(account.getShopId());
        return operator;
    }

    public static Operator getmerchantOperator(IAuthAccount account) {
        MerchantAuthAccount authAccount = merchantAuthAccount(account);
        if (ObjectUtils.isEmpty(authAccount)) {
            return null;
        }
        Operator operator = Operator.builder()
                .id(account.getId())
                .orgId(account.getOrgId())
                .orgAuthority(account.getOrgAuthority())
                .memberType(account.getMemberType())
                .account(account.getAccount())
                .type(account.getType())
                .name(account.getName())
                .phone(account.getPhone())
                .avatar(account.getAvatar())
                .area(account.getArea())
                .province(account.getProvince())
                .city(account.getCity())
                .district(account.getDistrict())
                .street(account.getStreet())
                .community(account.getCommunity())
                .requestIp(AuthCommon.getRequestIp())
                .build();
        operator.setIpInfo(IpUtils.ipInfo(operator.getRequestIp()));

        try {
            MerchantAccountOrg org = (MerchantAccountOrg) account.getOrg();
            if (ObjectUtils.isNotEmpty(org)) {
                operator.setOrgType(org.getOrgType())
                        .setParentOrgId(org.getParentId())
                        .setTopOrgId(org.getTopId());
            }
        } catch (Exception e) {
            // log
        }

        try {
            MerchantAccountStore store = (MerchantAccountStore) account.getStore();
            if (ObjectUtils.isNotEmpty(store)) {
                operator.setStoreType(store.getStoreType())
                        .setParentStoreId(store.getParentId())
                        .setTopStoreId(store.getTopId());
            }
        } catch (Exception e) {
            // log
        }

        operator.setShopId(account.getShopId());
        return operator;
    }

    public static Operator getConsumerOperator(IAuthAccount account) {
        ConsumerAuthAccount authAccount = consumerAuthAccount(account);
        if (ObjectUtils.isEmpty(authAccount)) {
            return null;
        }
        Operator operator = Operator.builder()
                .id(account.getId())
                .orgId(account.getOrgId())
                .orgAuthority(account.getOrgAuthority())
                .memberType(account.getMemberType())
                .account(account.getAccount())
                .type(account.getType())
                .name(account.getName())
                .phone(account.getPhone())
                .avatar(account.getAvatar())
                .area(account.getArea())
                .province(account.getProvince())
                .city(account.getCity())
                .district(account.getDistrict())
                .street(account.getStreet())
                .community(account.getCommunity())
                .requestIp(AuthCommon.getRequestIp())
                .build();
        operator.setIpInfo(IpUtils.ipInfo(operator.getRequestIp()));

        try {
            ConsumerAccountOrg org = (ConsumerAccountOrg) account.getOrg();
            if (ObjectUtils.isNotEmpty(org)) {
                operator.setOrgType(org.getOrgType())
                        .setParentOrgId(org.getParentId())
                        .setTopOrgId(org.getTopId());
            }
        } catch (Exception e) {
            // log
        }

        try {
            ConsumerAccountStore store = (ConsumerAccountStore) account.getStore();
            if (ObjectUtils.isNotEmpty(store)) {
                operator.setStoreType(store.getStoreType())
                        .setParentStoreId(store.getParentId())
                        .setTopStoreId(store.getTopId());
            }
        } catch (Exception e) {
            // log
        }

        operator.setShopId(account.getShopId());
        return operator;
    }

    public static Operator getApiOperator(IAuthAccount account) {
        ApiAuthAccount authAccount = apiAuthAccount(account);
        if (ObjectUtils.isEmpty(authAccount)) {
            return null;
        }
        Operator operator = Operator.builder()
                .id(account.getId())
                .orgId(account.getOrgId())
                .orgAuthority(account.getOrgAuthority())
                .memberType(account.getMemberType())
                .account(account.getAccount())
                .type(account.getType())
                .name(account.getName())
                .phone(account.getPhone())
                .avatar(account.getAvatar())
                .area(account.getArea())
                .province(account.getProvince())
                .city(account.getCity())
                .district(account.getDistrict())
                .street(account.getStreet())
                .community(account.getCommunity())
                .requestIp(AuthCommon.getRequestIp())
                .build();
        operator.setIpInfo(IpUtils.ipInfo(operator.getRequestIp()));

        try {
            ApiAccountOrg org = (ApiAccountOrg) account.getOrg();
            if (ObjectUtils.isNotEmpty(org)) {
                operator.setOrgType(org.getOrgType())
                        .setParentOrgId(org.getParentId())
                        .setTopOrgId(org.getTopId());
            }
        } catch (Exception e) {
            // log
        }

        try {
            ApiAccountStore store = (ApiAccountStore) account.getStore();
            if (ObjectUtils.isNotEmpty(store)) {
                operator.setStoreType(store.getStoreType())
                        .setParentStoreId(store.getParentId())
                        .setTopStoreId(store.getTopId());
            }
        } catch (Exception e) {
            // log
        }

        operator.setShopId(account.getShopId());
        return operator;
    }

    public static Operator getAuthOperator() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        switch (account.getMemberType()) {
            case admin -> {
                return getAdminOperator(account);
            }

            case tenant -> {
                return getTenantOperator(account);
            }

            case merchant -> {
                return getmerchantOperator(account);
            }

            case consumer -> {
                return getConsumerOperator(account);
            }

            case api -> {
                return getApiOperator(account);
            }
        }
        return null;
    }

    public static Scope getAdminScope(IAuthAccount account) {
        AdminAuthAccount authAccount = adminAuthAccount(account);
        if (ObjectUtils.isEmpty(authAccount)) {
            return null;
        }
        return Scope.builder()
                .memberType(authAccount.getMemberType())
                .orgAuthority(authAccount.getOrgAuthority())
                .province(authAccount.getProvince())
                .city(authAccount.getCity())
                .district(authAccount.getDistrict())
                .street(authAccount.getStreet())
                .community(authAccount.getCommunity())
                .build();
    }

    public static Scope getTenantScope(IAuthAccount account) {
        TenantAuthAccount authAccount = tenantAuthAccount(account);
        if (ObjectUtils.isEmpty(authAccount)) {
            return null;
        }

        Scope scope = Scope.builder()
                .memberType(authAccount.getMemberType())
                .province(authAccount.getProvince())
                .city(authAccount.getCity())
                .district(authAccount.getDistrict())
                .street(authAccount.getStreet())
                .community(authAccount.getCommunity())
                .build();

        if (authAccount.isLegalPerson()) {
            scope.setOrgAuthority(OrgAuthority.org);
            scope.setOrgId(authAccount.getOrgId());
        } else if (authAccount.isManagerPerson()) {
            scope.setOrgAuthority(OrgAuthority.store);
            scope.setStoreId(authAccount.getStoreId());
        } else {
            scope.setOrgAuthority(OrgAuthority.none);
            scope.setStoreId(authAccount.getStoreId());
        }
        scope.setShopId(account.getShopId());
        return scope;
    }

    public static Scope getmerchantScope(IAuthAccount account) {
        MerchantAuthAccount authAccount = merchantAuthAccount(account);
        if (ObjectUtils.isEmpty(authAccount)) {
            return null;
        }

        Scope scope = Scope.builder()
                .memberType(authAccount.getMemberType())
                .province(authAccount.getProvince())
                .city(authAccount.getCity())
                .district(authAccount.getDistrict())
                .street(authAccount.getStreet())
                .community(authAccount.getCommunity())
                .build();

        if (authAccount.isLegalPerson()) {
            scope.setOrgAuthority(OrgAuthority.org);
            scope.setOrgId(authAccount.getOrgId());
        } else if (authAccount.isManagerPerson()) {
            scope.setOrgAuthority(OrgAuthority.store);
            scope.setStoreId(authAccount.getStoreId());
        } else {
            scope.setOrgAuthority(OrgAuthority.none);
            scope.setStoreId(authAccount.getStoreId());
        }
        scope.setShopId(account.getShopId());
        return scope;
    }

    public static Scope getConsumerScope(IAuthAccount account) {
        ConsumerAuthAccount authAccount = consumerAuthAccount(account);
        if (ObjectUtils.isEmpty(authAccount)) {
            return null;
        }

        Scope scope = Scope.builder()
                .memberType(authAccount.getMemberType())
                .province(authAccount.getProvince())
                .city(authAccount.getCity())
                .district(authAccount.getDistrict())
                .street(authAccount.getStreet())
                .community(authAccount.getCommunity())
                .build();

        if (authAccount.isLegalPerson()) {
            scope.setOrgAuthority(OrgAuthority.org);
            scope.setOrgId(authAccount.getOrgId());
        } else if (authAccount.isManagerPerson()) {
            scope.setOrgAuthority(OrgAuthority.store);
            scope.setStoreId(authAccount.getStoreId());
        } else {
            scope.setOrgAuthority(OrgAuthority.none);
            scope.setStoreId(authAccount.getStoreId());
        }
        scope.setShopId(account.getShopId());
        return scope;
    }

    public static Scope getApiScope(IAuthAccount account) {
        ApiAuthAccount authAccount = apiAuthAccount(account);
        if (ObjectUtils.isEmpty(authAccount)) {
            return null;
        }

        Scope scope = Scope.builder()
                .memberType(authAccount.getMemberType())
                .province(authAccount.getProvince())
                .city(authAccount.getCity())
                .district(authAccount.getDistrict())
                .street(authAccount.getStreet())
                .community(authAccount.getCommunity())
                .build();

        if (authAccount.isLegalPerson()) {
            scope.setOrgAuthority(OrgAuthority.org);
            scope.setOrgId(authAccount.getOrgId());
        } else if (authAccount.isManagerPerson()) {
            scope.setOrgAuthority(OrgAuthority.store);
            scope.setStoreId(authAccount.getStoreId());
        } else {
            scope.setOrgAuthority(OrgAuthority.none);
            scope.setStoreId(authAccount.getStoreId());
        }
        scope.setShopId(account.getShopId());
        return scope;
    }

    public static Scope getAuthScope() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        switch (account.getMemberType()) {
            case admin -> {
                return getAdminScope(account);
            }
            case tenant -> {
                return getTenantScope(account);
            }
            case merchant -> {
                return getmerchantScope(account);
            }

            case consumer -> {
                return getConsumerScope(account);
            }

            case api -> {
                return getApiScope(account);
            }
        }
        return null;
    }

    public static String getRequestIp() {
        if (isMq()){
            return ConsumerData.get(DataKeys.REQUEST_IP);
        }
        return GlobalData.get(DataKeys.REQUEST_IP);
    }

    public static IpRegionInfo getIpInfo() {
        String ip = getRequestIp();
        if (StringUtils.isBlank(ip)) {
            return null;
        }
        return IpUtils.ipInfo(ip);
    }

    public static Long getMemberId() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getId();
    }

    public static long memberId() {
        Long memberId = getMemberId();
        if (CommonUtils.isNotID(memberId)) {
            return Constants.LONG_ZERO;
        }
        return memberId;
    }

    public static String getMemberName() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getName();
    }

    public static String getMemberAccount() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getAccount();
    }

    public static AccountType getMemberAccountType() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getType();
    }

    public static String getPhone() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getPhone();
    }

    public static <T extends BaseAccountOrg> List<T> getOrgs() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getOrgs();
    }

    public static <T extends BaseAccountOrg> T getOrg() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getOrg();
    }

    public static Long getOrgId() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getOrgId();
    }

    public static long orgId() {
        Long orgId = getOrgId();
        if (CommonUtils.isNotID(orgId)) {
            return Constants.LONG_ZERO;
        }
        return orgId;
    }

    public static String getOrgName() {
        IAuthAccount account = getAuthAccount();
        if (
                ObjectUtils.isEmpty(account)
                        || ObjectUtils.isEmpty(account.getOrg())
        ) {
            return null;
        }
        return account.getOrg().getOrgName();
    }

    public static String orgName() {
        String name = getOrgName();
        if (name == null) {
            return Constants.STRING_EMPTY;
        }
        return name;
    }

    public static <T extends BaseAccountStore> List<T> getStores() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getStores();
    }

    public static <T extends BaseAccountStore> T getStore() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getStore();
    }

    public static Long getStoreId() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getStoreId();
    }

    public static long storeId() {
        Long storeId = getStoreId();
        if (CommonUtils.isNotID(storeId)) {
            return Constants.LONG_ZERO;
        }
        return storeId;
    }

    public static String getStoreName() {
        IAuthAccount account = getAuthAccount();
        if (
                ObjectUtils.isEmpty(account)
                        || ObjectUtils.isEmpty(account.getStore())
        ) {
            return null;
        }
        return account.getStore().getStoreName();
    }

    public static String storeName() {
        String name = getStoreName();
        if (name == null) {
            return Constants.STRING_EMPTY;
        }
        return name;
    }

    public static <T extends BaseAccountShop> T getShop() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getShop();
    }

    public static Long getShopId() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getShopId();
    }

    public static long shopId() {
        Long shopId = getShopId();
        if (CommonUtils.isNotID(shopId)) {
            return Constants.LONG_ZERO;
        }
        return shopId;
    }


    public static <T extends BaseAccountPost> List<T> getPosts() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getPosts();
    }

    public static List<String> getPermissions() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getPermissions();
    }

    public static List<String> getRoles() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getRoles();
    }

    // Cache-Control
    public static String getRequestCacheControlHeader() {
        return GlobalData.get(DataKeys.REQUEST_CACHE_CONTROL_HEADER);
    }

    // noCacheHeader
    public static String getRequestNoCacheHeader() {
        return GlobalData.get(DataKeys.REQUEST_NO_CACHE_HEADER);
    }

    // 应用名称
    public static String getAppName() {
        return GlobalData.get(DataKeys.REQUEST_APP_NAME);
    }

    // 应用类型编码 字典 sysVersionType
    public static String getAppCode() {
        return GlobalData.get(DataKeys.REQUEST_APP_CODE);
    }

    // 应用平台 pc android ios ...
    public static String getPlatformName() {
        return GlobalData.get(DataKeys.REQUEST_PLATFORM_NAME);
    }

    // 设备 SN
    public static String getDeviceSN() {
        return GlobalData.get(DataKeys.REQUEST_DEVICE_SN);
    }

    // 应用版本
    public static String getVersion() {
        return GlobalData.get(DataKeys.REQUEST_VERSION);
    }

    // 强制更新
    public static String getForceSubmit() {
        return GlobalData.get(DataKeys.REQUEST_FORCE_SUBMIT);
    }

    // 强制更新
    public static boolean isForceSubmit() {
        String forceSubmit = getForceSubmit();
        if (StrUtils.isBlank(forceSubmit)) {
            return false;
        }
        return StringUtils.equalsIgnoreCase(forceSubmit, "yes") || StringUtils.equalsIgnoreCase(forceSubmit, "true");
    }

    // 操作人
    public static String getOperatorId() {
        return GlobalData.get(DataKeys.REQUEST_OPERATOR_ID);
    }

    public static long operatorId() {
        String operatorId = getOperatorId();
        if (StrUtils.isBlank(operatorId)) {
            return Constants.LONG_ZERO;
        }
        return CommonUtils.longValue(operatorId);
    }

    // 收银员
    // 目前无效,请使用 ShopCashierCommon 静态方法获取
    @Deprecated()
    public static String getCashierId() {
        return GlobalData.get(DataKeys.REQUEST_CASHIER_ID);
    }

    // 目前无效,请使用 ShopCashierCommon 静态方法获取
    @Deprecated()
    public static long cashierId() {
        String cashierId = getCashierId();
        if (StrUtils.isBlank(cashierId)) {
            return Constants.LONG_ZERO;
        }
        return CommonUtils.longValue(cashierId);
    }

    //
    public static boolean isAdmin() {
        AuthMemberType memberType = getMemberType();
        if (memberType == null) {
            return false;
        }
        return memberType.equals(AuthMemberType.admin);
    }

    public static boolean isTenant() {
        AuthMemberType memberType = getMemberType();
        if (memberType == null) {
            return true;
        }
        return memberType.equals(AuthMemberType.tenant);
    }

    public static boolean isMerchant() {
        AuthMemberType memberType = getMemberType();
        if (memberType == null) {
            return true;
        }
        return memberType.equals(AuthMemberType.merchant);
    }

    public static boolean isConsumer() {
        AuthMemberType memberType = getMemberType();
        if (memberType == null) {
            return true;
        }
        return memberType.equals(AuthMemberType.consumer);
    }

    public static boolean isApi() {
        AuthMemberType memberType = getMemberType();
        if (memberType == null) {
            return true;
        }
        return memberType.equals(AuthMemberType.api);
    }

    //
    public static Long getTenantId() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getTenantId();
    }

    public static long tenantId() {
        Long tenantId = getTenantId();
        if (CommonUtils.isNotID(tenantId)) {
            return Constants.LONG_ZERO;
        }
        return tenantId;
    }

    public static Long getMerchantId() {
        IAuthAccount account = getAuthAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getMerchantId();
    }

    public static Long merchantId() {
        Long merchantId = getMerchantId();
        if (CommonUtils.isNotID(merchantId)) {
            return Constants.LONG_ZERO;
        }
        return merchantId;
    }

    //

    // app
    public static boolean isApp() {
        String platform = getPlatformName();
        if (StrUtils.isBlank(platform)) {
            return false;
        }
        return "android".equalsIgnoreCase(platform) || "ios".equalsIgnoreCase(platform);
    }

    // 微信小程序
    public static boolean isWx() {
        String platform = getPlatformName();
        if (StrUtils.isBlank(platform)) {
            return false;
        }
        return "wxapp".equalsIgnoreCase(platform);
    }

    // pc
    public static boolean isPc() {
        String platform = getPlatformName();
        if (StrUtils.isBlank(platform)) {
            return true;
        }
        return "pc".equalsIgnoreCase(platform);
    }

}
