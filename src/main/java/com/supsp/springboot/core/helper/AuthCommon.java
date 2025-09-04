package com.supsp.springboot.core.helper;

import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.consts.Constants;
import com.supsp.springboot.core.consts.DataKeys;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.enums.OrgAuthority;
import com.supsp.springboot.core.enums.SysModule;
import com.supsp.springboot.core.interfaces.IAuthAccount;
import com.supsp.springboot.core.threads.ConsumerData;
import com.supsp.springboot.core.threads.GlobalData;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.IpUtils;
import com.supsp.springboot.core.utils.RequestUtils;
import com.supsp.springboot.core.utils.StrUtils;
import com.supsp.springboot.core.vo.IpRegionInfo;
import com.supsp.springboot.core.vo.Operator;
import com.supsp.springboot.core.vo.Scope;
import com.supsp.springboot.core.vo.auth.AccountOrg;
import com.supsp.springboot.core.vo.auth.AccountPost;
import com.supsp.springboot.core.vo.auth.AccountShop;
import com.supsp.springboot.core.vo.auth.AccountStore;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class AuthCommon {

    @Resource
    private CoreProperties coreProperties;

    //
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public static Collection<? extends GrantedAuthority> getAuthorities() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getAuthorities();
    }

    public static Object getPrincipal() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return getAuthentication().getPrincipal();
    }

    // header
    public static String headerUserType(HttpServletRequest request) {
        if (request == null) {
            request = RequestUtils.getRequest();
        }
        if (request == null) {
            return null;
        }
        return request.getHeader(CoreProperties.tokenUserTypeHeaderName());
    }

    public static AuthMemberType authMemberType(HttpServletRequest request) {
        if (request == null) {
            request = RequestUtils.getRequest();
        }
        String authMemberType = headerUserType(request);
        if (StringUtils.isBlank(authMemberType)) {
            return AuthMemberType.none;
        }
        return AuthMemberType.getByCode(authMemberType);
    }

    //
    // ======== token headers
    // admin
    public static String adminAuthHeader() {
        return GlobalData.get(CoreProperties.HEADER_ADMIN_TOKEN_SID);
    }

    public static boolean hasAdminHeader() {
        return StrUtils.isNotBlank(adminAuthHeader());
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
        return StrUtils.isNotBlank(tenantAuthHeader());
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
        return StrUtils.isNotBlank(merchantAuthHeader());
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
        return StrUtils.isNotBlank(consumerAuthHeader());
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
        return StrUtils.isNotBlank(apiAuthHeader());
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

    //
    // TRACE_ID
    public static String traceId() {
        return GlobalData.get(DataKeys.TRACE_ID);
    }

    //
    public static SysModule currentModule() {
        if (isMq()) {
            return ConsumerData.get(DataKeys.MQ_APP_MODULE);
        }
        return SystemCommon.getModule();
    }

    //
    public static AuthMemberType authMemberType() {
        String authMemberType = headerUserType(RequestUtils.getRequest());
        if (StringUtils.isBlank(authMemberType)) {
            return AuthMemberType.none;
        }
        return AuthMemberType.getByCode(authMemberType);
    }

    //
    public static boolean isMq() {
        String mqSide = ConsumerData.get(DataKeys.MQ_SIDE);
        return StrUtils.isNotBlank(mqSide) && mqSide.equalsIgnoreCase(DataKeys.MQ_CONSUMER_SIDE);
    }

    //
    public static boolean isAdmin() {
        AuthMemberType memberType = authMemberType();
        return memberType != null && memberType.equals(AuthMemberType.admin);
    }

    public static boolean isTenant() {
        AuthMemberType memberType = authMemberType();
        return memberType != null && memberType.equals(AuthMemberType.tenant);
    }

    public static boolean isMerchant() {
        AuthMemberType memberType = authMemberType();
        return memberType != null && memberType.equals(AuthMemberType.merchant);
    }

    public static boolean isConsumer() {
        AuthMemberType memberType = authMemberType();
        return memberType != null && memberType.equals(AuthMemberType.consumer);
    }

    public static boolean isApi() {
        AuthMemberType memberType = authMemberType();
        return memberType != null && memberType.equals(AuthMemberType.api);
    }

    //
    public static IAuthAccount adminAuthAccount() {
        if (!isAdmin()) {
            return null;
        }
        if (isMq()) {
            return ConsumerData.get(DataKeys.AUTH_ACCOUNT_ADMIN);
        }
        return GlobalData.get(DataKeys.AUTH_ACCOUNT_ADMIN);
    }

    public static IAuthAccount tenantAuthAccount() {
        if (!isTenant()) {
            return null;
        }
        if (isMq()) {
            return ConsumerData.get(DataKeys.AUTH_ACCOUNT_TENANT);
        }
        return GlobalData.get(DataKeys.AUTH_ACCOUNT_TENANT);
    }

    public static IAuthAccount merchantAuthAccount() {
        if (!isMerchant()) {
            return null;
        }
        if (isMq()) {
            return ConsumerData.get(DataKeys.AUTH_ACCOUNT_MERCHANT);
        }
        return GlobalData.get(DataKeys.AUTH_ACCOUNT_MERCHANT);
    }

    public static IAuthAccount consumerAuthAccount() {
        AuthMemberType authMemberType = authMemberType();
        if (!isConsumer()) {
            return null;
        }
        if (isMq()) {
            return ConsumerData.get(DataKeys.AUTH_ACCOUNT_CONSUMER);
        }
        return GlobalData.get(DataKeys.AUTH_ACCOUNT_CONSUMER);
    }

    public static IAuthAccount apiAuthAccount() {
        if (!isApi()) {
            return null;
        }
        if (isMq()) {
            return ConsumerData.get(DataKeys.AUTH_ACCOUNT_API);
        }
        return GlobalData.get(DataKeys.AUTH_ACCOUNT_API);
    }

    public static IAuthAccount authAccount() {
        AuthMemberType authMemberType = authMemberType();
        if (authMemberType == null || authMemberType.equals(AuthMemberType.none)) {
            return null;
        }
        switch (authMemberType) {
            case admin -> {
                return adminAuthAccount();
            }
            case tenant -> {
                return tenantAuthAccount();
            }
            case merchant -> {
                return merchantAuthAccount();
            }
            case consumer -> {
                return consumerAuthAccount();
            }
            case api -> {
                return apiAuthAccount();
            }
        }
        return null;
    }

    //
    public static Long authMemberId() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return Constants.LONG_ZERO;
        }
        return account.getId();
    }

    public static Long authAdminId() {
        if (!isAdmin()) {
            return Constants.LONG_ZERO;
        }
        return authMemberId();
    }

    public static Long authTenantId() {
        if (!isTenant()) {
            return Constants.LONG_ZERO;
        }
        return authMemberId();
    }

    public static Long authMerchantId() {
        if (!isMerchant()) {
            return Constants.LONG_ZERO;
        }
        return authMemberId();
    }

    public static Long authConsumerId() {
        if (!isConsumer()) {
            return Constants.LONG_ZERO;
        }
        return authMemberId();
    }

    public static Long authApiId() {
        if (!isApi()) {
            return Constants.LONG_ZERO;
        }
        return authMemberId();
    }

    //
    public static Long getTenantId() {
        if (isAdmin()) {
            return Constants.LONG_ZERO;
        }
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getTenantId();
    }

    public static Long getMerchantId() {
        if (isAdmin()) {
            return Constants.LONG_ZERO;
        }
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getMerchantId();
    }


    //
    public static String authMemberName() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getName();
    }

    public static String authMemberAccount() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getAccount();
    }

    public static String authPhone() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getPhone();
    }

    //
    public static List<AccountOrg> authOrgs() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getOrgs();
    }

    public static AccountOrg authOrg() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getOrg();
    }

    public static Long authOrgId() {
        IAuthAccount account = authAccount();
        if (
                ObjectUtils.isEmpty(account)
                        || ObjectUtils.isEmpty(account.getOrg())
        ) {
            return Constants.LONG_ZERO;
        }
        return account.getOrg().getOrgId();
    }

    public static String authOrgName() {
        IAuthAccount account = authAccount();
        if (
                ObjectUtils.isEmpty(account)
                        || ObjectUtils.isEmpty(account.getOrg())
        ) {
            return null;
        }
        return account.getOrg().getOrgName();
    }

    //
    public static List<AccountStore> authStores() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getStores();
    }

    public static AccountStore authStore() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getStore();
    }

    public static Long authStoreId() {
        IAuthAccount account = authAccount();
        if (
                ObjectUtils.isEmpty(account)
                        || ObjectUtils.isEmpty(account.getStore())
        ) {
            return Constants.LONG_ZERO;
        }
        return account.getStore().getStoreId();
    }

    public static String authStoreName() {
        IAuthAccount account = authAccount();
        if (
                ObjectUtils.isEmpty(account)
                        || ObjectUtils.isEmpty(account.getStore())
        ) {
            return null;
        }
        return account.getStore().getStoreName();
    }

    //
    public static AccountShop getShop() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getShop();
    }

    public static Long authShopId() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return Constants.LONG_ZERO;
        }
        return account.getShopId();
    }

    //
    public static List<AccountPost> authPosts() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getPosts();
    }

    public static List<String> authPermissions() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getPermissions();
    }

    public static List<String> authRoles() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getRoles();
    }

    //
    public static String authArea() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return null;
        }
        return account.getArea();
    }

    //
    public static Long authProvince() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return Constants.LONG_ZERO;
        }
        return account.getProvince();
    }

    public static Long authCity() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return Constants.LONG_ZERO;
        }
        return account.getCity();
    }

    public static Long authDistrict() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return Constants.LONG_ZERO;
        }
        return account.getDistrict();
    }

    public static Long authStreet() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return Constants.LONG_ZERO;
        }
        return account.getStreet();
    }

    public static Long authCommunity() {
        IAuthAccount account = authAccount();
        if (ObjectUtils.isEmpty(account)) {
            return Constants.LONG_ZERO;
        }
        return account.getCommunity();
    }

    //
    public static Operator adminOperator(IAuthAccount account) {
        if (ObjectUtils.isEmpty(account)) {
            account = adminAuthAccount();
        }
        if (
                ObjectUtils.isEmpty(account)
                        || account.getMemberType() == null
                        || !account.getMemberType().equals(AuthMemberType.admin)
        ) {
            return null;
        }
        Operator operator = Operator.builder()
                .id(account.getId())
                .orgId(account.getOrgId())
                .authority(account.getAuthority())
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
                .requestIp(requestIp())
                .build();
        operator.setIpInfo(IpUtils.ipInfo(operator.getRequestIp()));
        try {
            AccountOrg org = account.getOrg();
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

    public static Operator tenantOperator(IAuthAccount account) {
        if (ObjectUtils.isEmpty(account)) {
            account = tenantAuthAccount();
        }
        if (
                ObjectUtils.isEmpty(account)
                        || account.getMemberType() == null
                        || !account.getMemberType().equals(AuthMemberType.tenant)
        ) {
            return null;
        }
        Operator operator = Operator.builder()
                .id(account.getId())
                .orgId(account.getOrgId())
                .authority(account.getAuthority())
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
                .requestIp(AuthCommon.requestIp())
                .build();
        operator.setIpInfo(IpUtils.ipInfo(operator.getRequestIp()));

        try {
            AccountOrg org = account.getOrg();
            if (ObjectUtils.isNotEmpty(org)) {
                operator.setOrgType(org.getOrgType())
                        .setParentOrgId(org.getParentId())
                        .setTopOrgId(org.getTopId());
            }
        } catch (Exception e) {
            // log
        }

        try {
            AccountStore store = account.getStore();
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

    public static Operator merchantOperator(IAuthAccount account) {
        if (ObjectUtils.isEmpty(account)) {
            account = merchantAuthAccount();
        }
        if (
                ObjectUtils.isEmpty(account)
                        || account.getMemberType() == null
                        || !account.getMemberType().equals(AuthMemberType.merchant)
        ) {
            return null;
        }
        Operator operator = Operator.builder()
                .id(account.getId())
                .orgId(account.getOrgId())
                .authority(account.getAuthority())
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
                .requestIp(AuthCommon.requestIp())
                .build();
        operator.setIpInfo(IpUtils.ipInfo(operator.getRequestIp()));

        try {
            AccountOrg org = account.getOrg();
            if (ObjectUtils.isNotEmpty(org)) {
                operator.setOrgType(org.getOrgType())
                        .setParentOrgId(org.getParentId())
                        .setTopOrgId(org.getTopId());
            }
        } catch (Exception e) {
            // log
        }

        try {
            AccountStore store = account.getStore();
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

    public static Operator consumerOperator(IAuthAccount account) {
        if (ObjectUtils.isEmpty(account)) {
            account = consumerAuthAccount();
        }
        if (
                ObjectUtils.isEmpty(account)
                        || account.getMemberType() == null
                        || !account.getMemberType().equals(AuthMemberType.consumer)
        ) {
            return null;
        }
        Operator operator = Operator.builder()
                .id(account.getId())
                .orgId(account.getOrgId())
                .authority(account.getAuthority())
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
                .requestIp(AuthCommon.requestIp())
                .build();
        operator.setIpInfo(IpUtils.ipInfo(operator.getRequestIp()));

        try {
            AccountOrg org = account.getOrg();
            if (ObjectUtils.isNotEmpty(org)) {
                operator.setOrgType(org.getOrgType())
                        .setParentOrgId(org.getParentId())
                        .setTopOrgId(org.getTopId());
            }
        } catch (Exception e) {
            // log
        }

        try {
            AccountStore store = account.getStore();
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

    public static Operator apiOperator(IAuthAccount account) {
        if (ObjectUtils.isEmpty(account)) {
            account = apiAuthAccount();
        }
        if (
                ObjectUtils.isEmpty(account)
                        || account.getMemberType() == null
                        || !account.getMemberType().equals(AuthMemberType.api)
        ) {
            return null;
        }
        Operator operator = Operator.builder()
                .id(account.getId())
                .orgId(account.getOrgId())
                .authority(account.getAuthority())
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
                .requestIp(AuthCommon.requestIp())
                .build();
        operator.setIpInfo(IpUtils.ipInfo(operator.getRequestIp()));

        try {
            AccountOrg org = account.getOrg();
            if (ObjectUtils.isNotEmpty(org)) {
                operator.setOrgType(org.getOrgType())
                        .setParentOrgId(org.getParentId())
                        .setTopOrgId(org.getTopId());
            }
        } catch (Exception e) {
            // log
        }

        try {
            AccountStore store = account.getStore();
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

    public static Operator authOperator() {
        IAuthAccount account = authAccount();
        if (
                ObjectUtils.isEmpty(account)
                        || account.getMemberType() == null
                        || account.getMemberType().equals(AuthMemberType.none)
        ) {
            return null;
        }
        switch (account.getMemberType()) {
            case admin -> {
                return adminOperator(account);
            }

            case tenant -> {
                return tenantOperator(account);
            }

            case merchant -> {
                return merchantOperator(account);
            }

            case consumer -> {
                return consumerOperator(account);
            }

            case api -> {
                return apiOperator(account);
            }
        }
        return null;
    }

    //
    public static Scope adminScope(IAuthAccount account) {
        if (ObjectUtils.isEmpty(account)) {
            account = adminAuthAccount();
        }
        if (
                ObjectUtils.isEmpty(account)
                        || account.getMemberType() == null
                        || !account.getMemberType().equals(AuthMemberType.admin)
        ) {
            return null;
        }
        return Scope.builder()
                .memberType(account.getMemberType())
                .orgAuthority(account.getAuthority())
                .province(account.getProvince())
                .city(account.getCity())
                .district(account.getDistrict())
                .street(account.getStreet())
                .community(account.getCommunity())
                .build();
    }

    public static Scope tenantScope(IAuthAccount account) {
        if (ObjectUtils.isEmpty(account)) {
            account = tenantAuthAccount();
        }
        if (
                ObjectUtils.isEmpty(account)
                        || account.getMemberType() == null
                        || !account.getMemberType().equals(AuthMemberType.tenant)
        ) {
            return null;
        }

        Scope scope = Scope.builder()
                .memberType(account.getMemberType())
                .province(account.getProvince())
                .city(account.getCity())
                .district(account.getDistrict())
                .street(account.getStreet())
                .community(account.getCommunity())
                .build();

        if (account.isLegalPerson()) {
            scope.setOrgAuthority(OrgAuthority.org);
            scope.setOrgId(account.getOrgId());
        } else if (account.isManagerPerson()) {
            scope.setOrgAuthority(OrgAuthority.store);
            scope.setStoreId(account.getStoreId());
        } else {
            scope.setOrgAuthority(OrgAuthority.none);
            scope.setStoreId(account.getStoreId());
        }
        scope.setShopId(account.getShopId());
        return scope;
    }

    public static Scope merchantScope(IAuthAccount account) {
        if (ObjectUtils.isEmpty(account)) {
            account = merchantAuthAccount();
        }
        if (
                ObjectUtils.isEmpty(account)
                        || account.getMemberType() == null
                        || !account.getMemberType().equals(AuthMemberType.merchant)
        ) {
            return null;
        }

        Scope scope = Scope.builder()
                .memberType(account.getMemberType())
                .province(account.getProvince())
                .city(account.getCity())
                .district(account.getDistrict())
                .street(account.getStreet())
                .community(account.getCommunity())
                .build();

        if (account.isLegalPerson()) {
            scope.setOrgAuthority(OrgAuthority.org);
            scope.setOrgId(account.getOrgId());
        } else if (account.isManagerPerson()) {
            scope.setOrgAuthority(OrgAuthority.store);
            scope.setStoreId(account.getStoreId());
        } else {
            scope.setOrgAuthority(OrgAuthority.none);
            scope.setStoreId(account.getStoreId());
        }
        scope.setShopId(account.getShopId());
        return scope;
    }

    public static Scope consumerScope(IAuthAccount account) {
        if (ObjectUtils.isEmpty(account)) {
            account = consumerAuthAccount();
        }
        if (
                ObjectUtils.isEmpty(account)
                        || account.getMemberType() == null
                        || !account.getMemberType().equals(AuthMemberType.consumer)
        ) {
            return null;
        }

        Scope scope = Scope.builder()
                .memberType(account.getMemberType())
                .province(account.getProvince())
                .city(account.getCity())
                .district(account.getDistrict())
                .street(account.getStreet())
                .community(account.getCommunity())
                .build();

        if (account.isLegalPerson()) {
            scope.setOrgAuthority(OrgAuthority.org);
            scope.setOrgId(account.getOrgId());
        } else if (account.isManagerPerson()) {
            scope.setOrgAuthority(OrgAuthority.store);
            scope.setStoreId(account.getStoreId());
        } else {
            scope.setOrgAuthority(OrgAuthority.none);
            scope.setStoreId(account.getStoreId());
        }
        scope.setShopId(account.getShopId());
        return scope;
    }

    public static Scope apiScope(IAuthAccount account) {
        if (ObjectUtils.isEmpty(account)) {
            account = apiAuthAccount();
        }
        if (
                ObjectUtils.isEmpty(account)
                        || account.getMemberType() == null
                        || !account.getMemberType().equals(AuthMemberType.api)
        ) {
            return null;
        }

        Scope scope = Scope.builder()
                .memberType(account.getMemberType())
                .province(account.getProvince())
                .city(account.getCity())
                .district(account.getDistrict())
                .street(account.getStreet())
                .community(account.getCommunity())
                .build();

        if (account.isLegalPerson()) {
            scope.setOrgAuthority(OrgAuthority.org);
            scope.setOrgId(account.getOrgId());
        } else if (account.isManagerPerson()) {
            scope.setOrgAuthority(OrgAuthority.store);
            scope.setStoreId(account.getStoreId());
        } else {
            scope.setOrgAuthority(OrgAuthority.none);
            scope.setStoreId(account.getStoreId());
        }
        scope.setShopId(account.getShopId());
        return scope;
    }

    public static Scope authScope() {
        IAuthAccount account = authAccount();
        if (
                ObjectUtils.isEmpty(account)
                        || account.getMemberType() == null
                        || account.getMemberType().equals(AuthMemberType.none)
        ) {
            return null;
        }
        switch (account.getMemberType()) {
            case admin -> {
                return adminScope(account);
            }
            case tenant -> {
                return tenantScope(account);
            }
            case merchant -> {
                return merchantScope(account);
            }

            case consumer -> {
                return consumerScope(account);
            }

            case api -> {
                return apiScope(account);
            }
        }
        return null;
    }


    //
    public static String requestIp() {
        if (isMq()) {
            return ConsumerData.get(DataKeys.REQUEST_IP);
        }
        return GlobalData.get(DataKeys.REQUEST_IP);
    }

    public static IpRegionInfo getIpInfo() {
        String ip = requestIp();
        if (StringUtils.isBlank(ip)) {
            return null;
        }
        return IpUtils.ipInfo(ip);
    }

    //
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
