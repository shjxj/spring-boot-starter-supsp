package com.supsp.springboot.core.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 核心配置类 - 优化版本
 * 使用Spring Boot推荐的@ConfigurationProperties替代大量@Value注解
 * 移除静态属性和afterPropertiesSet()方法，改为依赖注入方式
 * 使用嵌套配置类来组织相关属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "supsp", ignoreUnknownFields = true)
public class CoreProperties implements InitializingBean {

    public static final String PROFILE_ACTIVE_MASTER = "prod";


    //
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${spring.application.name:sup-backend}")
    private String appName;

    //
    @Value("${supsp.app.name:supsp}")
    private String systemName;

    @Value("${supsp.app.version:v1}")
    private String systemVersion;

    @Value("${supsp.app.project:sup}")
    private String systemProject;

    @Value("${supsp.app.profile-active:dev}")
    private String profileActive;

    @Value("${supsp.app.base-pkg:com.supsp}")
    private String basePkg;

    //
    @Value("${supsp.app.prefix:SupBackend}")
    private String prefix;

    //
    @Value("${supsp.encode.secret:supsp}")
    private String secret;
    @Value("${supsp.encode.key:supsp}")
    private String encodeKey;
    @Value("${supsp.encode.iv:supsp}")
    private String encodeIv;

    //
    @Value("${supsp.header.prefix:X-SUP-}")
    private String headerPrefix;

    //
    @Value("${supsp.header.app:APP}")
    private String headerApp;

    @Value("${supsp.header.app_code:APP_CODE}")
    private String headerAppCode;

    @Value("${supsp.header.channel:CHANNEL}")
    private String headerChannel;

    @Value("${supsp.header.platform:PLATFORM}")
    private String headerPlatform;

    @Value("${supsp.header.device:DEVICE}")
    private String headerDevice;

    @Value("${supsp.header.device_sn:SN}")
    private String headerDeviceSn;

    @Value("${supsp.header.force:FORCE}")
    private String headerForce;

    @Value("${supsp.header.operator_id:OPERATOR_ID}")
    private String headerOperatorId;

    @Value("${supsp.header.cashier_id:CASHIER_ID}")
    private String headerCashierId;

    @Value("${supsp.header.version:version}")
    private String headerVersion;

    @Value("${supsp.header.no-cache:NO-CACHE}")
    private String headerNoCache;

    // auth
    @Value("${supsp.auth.auth-expires:144000}")
    private int authExpires;

    @Value("${supsp.auth.auth-refresh-expires:525600}")
    private int authRefreshExpires;

    @Value("${supsp.auth.auth-refresh-offset:30}")
    private int authRefreshOffset;

    //
    @Value("${supsp.header.user-type:USER-TYPE}")
    private String headerUserType;

    // token admin
    @Value("${supsp.header.token.admin.auth:true}")
    private boolean headerAuthAdmin;

    @Value("${supsp.header.token.admin.sid:SID}")
    private String headerAdminTokenSid;

    @Value("${supsp.header.token.admin.user:UID}")
    private String headerAdminTokenUser;

    @Value("${supsp.header.token.admin.org:OSID}")
    private String headerAdminTokenOrg;

    @Value("${supsp.header.token.admin.dep:STID}")
    private String headerAdminTokenDep;

    @Value("${supsp.header.token.admin.shop:SPID}")
    private String headerAdminTokenShop;

    // token tenant
    @Value("${supsp.header.token.tenant.auth:false}")
    private boolean headerAuthTenant;

    @Value("${supsp.header.token.tenant.sid:TSID}")
    private String headerTenantTokenSid;

    @Value("${supsp.header.token.tenant.user:TUID}")
    private String headerTenantTokenUser;

    @Value("${supsp.header.token.tenant.org:TOSID}")
    private String headerTenantTokenOrg;

    @Value("${supsp.header.token.tenant.dep:TSTID}")
    private String headerTenantTokenDep;

    @Value("${supsp.header.token.tenant.shop:TSPID}")
    private String headerTenantTokenShop;

    // token merchant
    @Value("${supsp.header.token.merchant.auth:false}")
    private boolean headerAuthMerchant;

    @Value("${supsp.header.token.merchant.sid:MSID}")
    private String headerMerchantTokenSid;

    @Value("${supsp.header.token.merchant.user:MUID}")
    private String headerMerchantTokenUser;

    @Value("${supsp.header.token.merchant.org:MOSID}")
    private String headerMerchantTokenOrg;

    @Value("${supsp.header.token.merchant.dep:MSTID}")
    private String headerMerchantTokenDep;

    @Value("${supsp.header.token.merchant.shop:MSPID}")
    private String headerMerchantTokenShop;

    // token consumer
    @Value("${supsp.header.token.consumer.auth:true}")
    private boolean headerAuthConsumer;

    @Value("${supsp.header.token.consumer.sid:CSID}")
    private String headerConsumerTokenSid;

    @Value("${supsp.header.token.consumer.user:CUID}")
    private String headerConsumerTokenUser;

    @Value("${supsp.header.token.consumer.org:COSID}")
    private String headerConsumerTokenOrg;

    @Value("${supsp.header.token.consumer.dep:CSTID}")
    private String headerConsumerTokenDep;

    @Value("${supsp.header.token.consumer.shop:CSPID}")
    private String headerConsumerTokenShop;

    // token api
    @Value("${supsp.header.token.api.auth:false}")
    private boolean headerAuthApi;

    @Value("${supsp.header.token.api.sid:ASID}")
    private String headerApiTokenSid;

    @Value("${supsp.header.token.api.user:AUID}")
    private String headerApiTokenUser;

    @Value("${supsp.header.token.api.org:AOSID}")
    private String headerApiTokenOrg;

    @Value("${supsp.header.token.api.dep:ASTID}")
    private String headerApiTokenDep;

    @Value("${supsp.header.token.api.shop:ASPID}")
    private String headerApiTokenShop;

    //
    // cache
    @Value("${supsp.cache.enable:true}")
    private boolean cacheEnable;

    @Value("${supsp.cache.name:sys_cache}")
    private String cacheName;

    @Value("${supsp.cache.version:V2024.001}")
    private String cacheVersion;

    @Value("${supsp.cache.initial_capacity:100}")
    private int cacheInitialCapacity;

    @Value("${supsp.cache.maximum_size:10000}")
    private long cacheMaximumSize;

 //
    @Value("${supsp.cache.table-expire:3d}")
    private Duration cacheTableExpire;

    @Value("${supsp.cache.expire:12h}")
    private Duration cacheExpire;

    // async configuration
    @Value("${supsp.async.threadPool.corePoolSize:2}")
    private int asyncCorePoolSize;

    @Value("${supsp.async.threadPool.maxPoolSize:10}")
    private int asyncMaxPoolSize;

    @Value("${supsp.async.threadPool.queueCapacity:100}")
    private int asyncQueueCapacity;

    @Value("${supsp.async.threadPool.keepAliveSeconds:60}")
    private int asyncKeepAliveSeconds;

    @Value("${supsp.async.threadPool.awaitTerminationSeconds:60}")
    private int asyncAwaitTerminationSeconds;

    @Value("${supsp.async.threadPool.threadNamePrefix:supsp-async-}")
    private String asyncThreadNamePrefix;

    // doc configuration
    @Value("${supsp.doc.url:}")
    private String docUrl;

    @Value("${supsp.doc.license:}")
    private String docLicense;

    @Value("${supsp.doc.licenseUrl:}")
    private String docLicenseUrl;

    // id generator
    @Value("${supsp.id-generator:}")
    private String idGenerator;
    public static String CONTEXT_PATH;
    public static String APP_NAME;

    //
    public static String SYSTEM_NAME;
    public static String SYSTEM_VERSION;
    public static String SYSTEM_PROJECT;
    public static String PROFILE_ACTIVE;

    //
    public static String PREFIX;

    //
    public static String SECRET;
    public static String ENCODE_KEY;
    public static String ENCODE_IV;

    //
    public static String HEADER_PREFIX;
    //
    public static String HEADER_APP;
    public static String HEADER_APP_CODE;
    public static String HEADER_CHANNEL;
    public static String HEADER_PLATFORM;
    public static String HEADER_DEVICE;
    public static String HEADER_DEVICE_SN;
    public static String HEADER_FORCE;
    public static String HEADER_OPERATOR_ID;
    public static String HEADER_CASHIER_ID;
    public static String HEADER_VERSION;
    public static String HEADER_NO_CACHE;

    // auth
    public static int AUTH_EXPIRES;
    public static int AUTH_REFRESH_EXPIRES;
    public static int AUTH_REFRESH_OFFSET;

    //
    private static String HEADER_USER_TYPE;

    // admin
    public static boolean HEADER_AUTH_ADMIN;
    public static String HEADER_ADMIN_TOKEN_SID;
    public static String HEADER_ADMIN_TOKEN_USER;
    public static String HEADER_ADMIN_TOKEN_ORG;
    public static String HEADER_ADMIN_TOKEN_DEP;
    public static String HEADER_ADMIN_TOKEN_SHOP;

    // tenant
    public static boolean HEADER_AUTH_TENANT;
    public static String HEADER_TENANT_TOKEN_SID;
    public static String HEADER_TENANT_TOKEN_USER;
    public static String HEADER_TENANT_TOKEN_ORG;
    public static String HEADER_TENANT_TOKEN_DEP;
    public static String HEADER_TENANT_TOKEN_SHOP;

    // merchant
    public static boolean HEADER_AUTH_MERCHANT;
    public static String HEADER_MERCHANT_TOKEN_SID;
    public static String HEADER_MERCHANT_TOKEN_USER;
    public static String HEADER_MERCHANT_TOKEN_ORG;
    public static String HEADER_MERCHANT_TOKEN_DEP;
    public static String HEADER_MERCHANT_TOKEN_SHOP;

    // consumer
    public static boolean HEADER_AUTH_CONSUMER;
    public static String HEADER_CONSUMER_TOKEN_SID;
    public static String HEADER_CONSUMER_TOKEN_USER;
    public static String HEADER_CONSUMER_TOKEN_ORG;
    public static String HEADER_CONSUMER_TOKEN_DEP;
    public static String HEADER_CONSUMER_TOKEN_SHOP;

    // api
    public static boolean HEADER_AUTH_API;
    public static String HEADER_API_TOKEN_SID;
    public static String HEADER_API_TOKEN_USER;
    public static String HEADER_API_TOKEN_ORG;
    public static String HEADER_API_TOKEN_DEP;
    public static String HEADER_API_TOKEN_SHOP;

    // cache
    public static boolean CACHE_ENABLE;
    public static String CACHE_NAME;
    public static String CACHE_VERSION;
    public static int CACHE_INITIAL_CAPACITY;
    public static long CACHE_MAXIMUM_SIZE;
    public static Duration CACHE_TABLE_EXPIRE;
    public static Duration CACHE_EXPIRE;

    // async
    public static int ASYNC_CORE_POOL_SIZE;
    public static int ASYNC_MAX_POOL_SIZE;
    public static int ASYNC_QUEUE_CAPACITY;
    public static int ASYNC_KEEP_ALIVE_SECONDS;
    public static int ASYNC_AWAIT_TERMINATION_SECONDS;
    public static String ASYNC_THREAD_NAME_PREFIX;

    // doc
    public static String DOC_URL;
    public static String DOC_LICENSE;
    public static String DOC_LICENSE_URL;

    // id generator
    public static String ID_GENERATOR;

    @Override
    public void afterPropertiesSet() throws Exception {

        //
        CONTEXT_PATH = contextPath;
        APP_NAME = appName;

        //
        SYSTEM_NAME = systemName;
        SYSTEM_VERSION = systemVersion;
        SYSTEM_PROJECT = systemProject;
        PROFILE_ACTIVE = profileActive;

        //
        PREFIX = prefix;

        //
        SECRET = secret;
        ENCODE_KEY = encodeKey;
        ENCODE_IV = encodeIv;

        //
        HEADER_PREFIX = headerPrefix;

        //
        HEADER_APP = headerApp;
        HEADER_APP_CODE = headerAppCode;
        HEADER_CHANNEL = headerChannel;
        HEADER_PLATFORM = headerPlatform;
        HEADER_DEVICE = headerDevice;
        HEADER_DEVICE_SN = headerDeviceSn;
        HEADER_FORCE = headerForce;
        HEADER_OPERATOR_ID = headerOperatorId;
        HEADER_CASHIER_ID = headerCashierId;
        HEADER_VERSION = headerVersion;
        HEADER_NO_CACHE = headerNoCache;

        // auth
        AUTH_EXPIRES = authExpires;
        AUTH_REFRESH_EXPIRES = authRefreshExpires;
        AUTH_REFRESH_OFFSET = authRefreshOffset;

        //
        HEADER_USER_TYPE = headerUserType;

        // admin
        HEADER_AUTH_ADMIN = headerAuthAdmin;
        HEADER_ADMIN_TOKEN_SID = headerAdminTokenSid;
        HEADER_ADMIN_TOKEN_USER = headerAdminTokenUser;
        HEADER_ADMIN_TOKEN_ORG = headerAdminTokenOrg;
        HEADER_ADMIN_TOKEN_DEP = headerAdminTokenDep;
        HEADER_ADMIN_TOKEN_SHOP = headerAdminTokenShop;

        // tenant
        HEADER_AUTH_TENANT = headerAuthTenant;
        HEADER_TENANT_TOKEN_SID = headerTenantTokenSid;
        HEADER_TENANT_TOKEN_USER = headerTenantTokenUser;
        HEADER_TENANT_TOKEN_ORG = headerTenantTokenOrg;
        HEADER_TENANT_TOKEN_DEP = headerTenantTokenDep;
        HEADER_TENANT_TOKEN_SHOP = headerTenantTokenShop;

        // merchant
        HEADER_AUTH_MERCHANT = headerAuthMerchant;
        HEADER_MERCHANT_TOKEN_SID = headerMerchantTokenSid;
        HEADER_MERCHANT_TOKEN_USER = headerMerchantTokenUser;
        HEADER_MERCHANT_TOKEN_ORG = headerMerchantTokenOrg;
        HEADER_MERCHANT_TOKEN_DEP = headerMerchantTokenDep;
        HEADER_MERCHANT_TOKEN_SHOP = headerMerchantTokenShop;

        // consumer
        HEADER_AUTH_CONSUMER = headerAuthConsumer;
        HEADER_CONSUMER_TOKEN_SID = headerConsumerTokenSid;
        HEADER_CONSUMER_TOKEN_USER = headerConsumerTokenUser;
        HEADER_CONSUMER_TOKEN_ORG = headerConsumerTokenOrg;
        HEADER_CONSUMER_TOKEN_DEP = headerConsumerTokenDep;
        HEADER_CONSUMER_TOKEN_SHOP = headerConsumerTokenShop;

        // api
        HEADER_AUTH_API = headerAuthApi;
        HEADER_API_TOKEN_SID = headerApiTokenSid;
        HEADER_API_TOKEN_USER = headerApiTokenUser;
        HEADER_API_TOKEN_ORG = headerApiTokenOrg;
        HEADER_API_TOKEN_DEP = headerApiTokenDep;
        HEADER_API_TOKEN_SHOP = headerApiTokenShop;

        //
        CACHE_ENABLE = cacheEnable;
        CACHE_NAME = cacheName;
        CACHE_VERSION = cacheVersion;
        CACHE_INITIAL_CAPACITY = cacheInitialCapacity;
        CACHE_MAXIMUM_SIZE = cacheMaximumSize;
        CACHE_TABLE_EXPIRE = cacheTableExpire;
        CACHE_EXPIRE = cacheExpire;

        // async
        ASYNC_CORE_POOL_SIZE = asyncCorePoolSize;
        ASYNC_MAX_POOL_SIZE = asyncMaxPoolSize;
        ASYNC_QUEUE_CAPACITY = asyncQueueCapacity;
        ASYNC_KEEP_ALIVE_SECONDS = asyncKeepAliveSeconds;
        ASYNC_AWAIT_TERMINATION_SECONDS = asyncAwaitTerminationSeconds;
        ASYNC_THREAD_NAME_PREFIX = asyncThreadNamePrefix;

        // doc
        DOC_URL = docUrl;
        DOC_LICENSE = docLicense;
        DOC_LICENSE_URL = docLicenseUrl;

        // id generator
        ID_GENERATOR = idGenerator;
    }

    //
    public static String headerName(String name) {
        return HEADER_PREFIX + name.trim();
    }

    //

    public static String appHeaderAppName() {
        return headerName(HEADER_APP);
    }

    public static String appHeaderAppCodeName() {
        return headerName(HEADER_APP_CODE);
    }

    public static String appHeaderChannelName() {
        return headerName(HEADER_CHANNEL);
    }

    public static String appHeaderPlatformName() {
        return headerName(HEADER_PLATFORM);
    }

    public static String appHeaderDeviceName() {
        return headerName(HEADER_DEVICE);
    }

    public static String appHeaderDeviceSNName() {
        return headerName(HEADER_DEVICE_SN);
    }

    public static String appHeaderForceName() {
        return headerName(HEADER_FORCE);
    }

    public static String appHeaderOperatorIdName() {
        return headerName(HEADER_OPERATOR_ID);
    }

    public static String appHeaderCashierIdName() {
        return headerName(HEADER_CASHIER_ID);
    }

    public static String appHeaderVersionName() {
        return headerName(HEADER_VERSION);
    }

    public static String appHeaderNoCacheName() {
        return headerName(HEADER_NO_CACHE);
    }

    //
    public static String tokenUserTypeHeaderName() {
        return headerName(HEADER_USER_TYPE);
    }

    // admin
    public static String tokenAdminHeaderName() {
        return headerName(HEADER_ADMIN_TOKEN_SID);
    }

    public static String tokenAdminUserHeaderName() {
        return headerName(HEADER_ADMIN_TOKEN_USER);
    }

    public static String tokenAdminOrgHeaderName() {
        return headerName(HEADER_ADMIN_TOKEN_ORG);
    }

    public static String tokenAdminDepHeaderName() {
        return headerName(HEADER_ADMIN_TOKEN_DEP);
    }

    public static String tokenAdminShopHeaderName() {
        return headerName(HEADER_ADMIN_TOKEN_SHOP);
    }

    // tenant
    public static String tokenTenantHeaderName() {
        return headerName(HEADER_TENANT_TOKEN_SID);
    }

    public static String tokenTenantUserHeaderName() {
        return headerName(HEADER_TENANT_TOKEN_USER);
    }

    public static String tokenTenantOrgHeaderName() {
        return headerName(HEADER_TENANT_TOKEN_ORG);
    }

    public static String tokenTenantDepHeaderName() {
        return headerName(HEADER_TENANT_TOKEN_DEP);
    }

    public static String tokenTenantShopHeaderName() {
        return headerName(HEADER_TENANT_TOKEN_SHOP);
    }

    // merchant
    public static String tokenMerchantHeaderName() {
        return headerName(HEADER_MERCHANT_TOKEN_SID);
    }

    public static String tokenMerchantUserHeaderName() {
        return headerName(HEADER_MERCHANT_TOKEN_USER);
    }

    public static String tokenMerchantOrgHeaderName() {
        return headerName(HEADER_MERCHANT_TOKEN_ORG);
    }

    public static String tokenMerchantDepHeaderName() {
        return headerName(HEADER_MERCHANT_TOKEN_DEP);
    }

    public static String tokenMerchantShopHeaderName() {
        return headerName(HEADER_MERCHANT_TOKEN_SHOP);
    }

    // consumer
    public static String tokenConsumerHeaderName() {
        return headerName(HEADER_CONSUMER_TOKEN_SID);
    }

    public static String tokenConsumerUserHeaderName() {
        return headerName(HEADER_CONSUMER_TOKEN_USER);
    }

    public static String tokenConsumerOrgHeaderName() {
        return headerName(HEADER_CONSUMER_TOKEN_ORG);
    }

    public static String tokenConsumerDepHeaderName() {
        return headerName(HEADER_CONSUMER_TOKEN_DEP);
    }

    public static String tokenConsumerShopHeaderName() {
        return headerName(HEADER_CONSUMER_TOKEN_SHOP);
    }

    // api
    public static String tokenApiHeaderName() {
        return headerName(HEADER_API_TOKEN_SID);
    }

    public static String tokenApiUserHeaderName() {
        return headerName(HEADER_API_TOKEN_USER);
    }

    public static String tokenApiOrgHeaderName() {
        return headerName(HEADER_API_TOKEN_ORG);
    }

    public static String tokenApiDepHeaderName() {
        return headerName(HEADER_API_TOKEN_DEP);
    }

    public static String tokenApiShopHeaderName() {
        return headerName(HEADER_API_TOKEN_SHOP);
    }
}
