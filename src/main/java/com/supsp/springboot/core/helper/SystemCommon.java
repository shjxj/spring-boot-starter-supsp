package com.supsp.springboot.core.helper;

import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.consts.DataKeys;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.enums.SysApp;
import com.supsp.springboot.core.enums.SysModule;
import com.supsp.springboot.core.threads.CacheTables;
import com.supsp.springboot.core.threads.GlobalData;
import com.supsp.springboot.core.threads.ThreadData;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.RequestUtils;
import com.supsp.springboot.core.utils.StrUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SystemCommon {

    public static String getRequestModule(HttpServletRequest request) {
        if (ObjectUtils.isNotEmpty(request)) {
            return RequestUtils.pathModule(request);
        }
        return RequestUtils.pathModule(HttpServletContext.getRequest());
    }

    public static String getRequestApp(HttpServletRequest request) {
        if (ObjectUtils.isNotEmpty(request)) {
            return RequestUtils.pathApp(request);
        }
        return RequestUtils.pathApp(HttpServletContext.getRequest());
    }

    public static SysModule getModule() {
        String requestModule = GlobalData.get(DataKeys.SERVLET_PATH_MODULE);
        if (StrUtils.isBlank(requestModule)) {
            return SysModule.base;
        }
        SysModule module = SysModule.getByCode(requestModule.trim().toLowerCase());
        if (module != null) {
            return module;
        }
        return SysModule.base;
    }

    public static String getModuleName() {
        return getModule().name();
    }

    public static SysApp getApp() {
        String requestApp = GlobalData.get(DataKeys.SERVLET_PATH_APP);
        if (StrUtils.isBlank(requestApp)) {
            return SysApp.normal;
        }
        SysApp app = SysApp.getByCode(requestApp.trim().toLowerCase());
        if (app != null) {
            return app;
        }
        return SysApp.normal;
    }

    public static String getAppName() {
        return getApp().name();
    }

    // GlobalData
    //
    public static void initTrace(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String traceId = GlobalData.get(DataKeys.TRACE_ID);
        if (StrUtils.isBlank(traceId)) {
            CacheTables.remove();
            ThreadData.remove();
            GlobalData.remove();
            traceId = CommonUtils.traceId();
            MDC.put(DataKeys.TRACE_ID, traceId);
            MDC.put(DataKeys.THREAD_ID, String.valueOf(Thread.currentThread().threadId()));
            response.addHeader(
                    CoreProperties.HEADER_PREFIX + DataKeys.TRACE_ID,
                    traceId
            );
            GlobalData.set(DataKeys.TRACE_ID, traceId);
        }
    }

    //
    public static boolean isSetSid(String sid) {
        return StrUtils.isNotBlank(sid)
                && sid.length() >= 64;
    }

    // token
    public static void globalAdminTokenDataSet(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // === admin
        // SID
        String adminSid = request.getHeader(
                CoreProperties.tokenAdminHeaderName()
        );
        if (!isSetSid(adminSid)) {
            adminSid = GlobalData.get(CoreProperties.HEADER_ADMIN_TOKEN_SID);
        }
        if (!isSetSid(adminSid)) {
            adminSid = response.getHeader(CoreProperties.tokenAdminHeaderName());
        }
        if (!isSetSid(adminSid)) {
            adminSid = CommonUtils.randomAuthSid(
                    AuthMemberType.admin.getCode()
            );
            response.addHeader(
                    CoreProperties.tokenAdminHeaderName(),
                    adminSid
            );
        }
        GlobalData.set(CoreProperties.HEADER_ADMIN_TOKEN_SID, adminSid);

        // Org SID
        String adminOrgSid = request.getHeader(CoreProperties.tokenAdminOrgHeaderName());
        GlobalData.set(CoreProperties.HEADER_ADMIN_TOKEN_ORG, adminOrgSid);

        // Store SID
        String adminStoreSid = request.getHeader(CoreProperties.tokenAdminDepHeaderName());
        GlobalData.set(CoreProperties.HEADER_ADMIN_TOKEN_DEP, adminStoreSid);

        // Shop SID
        String adminShopSid = request.getHeader(CoreProperties.tokenAdminShopHeaderName());
        GlobalData.set(CoreProperties.HEADER_ADMIN_TOKEN_SHOP, adminShopSid);
    }

    public static void globalTenantTokenDataSet(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // === tenant
        // SID
        String tenantSid = request.getHeader(
                CoreProperties.tokenTenantHeaderName()
        );
        if (!isSetSid(tenantSid)) {
            tenantSid = GlobalData.get(CoreProperties.HEADER_TENANT_TOKEN_SID);
        }
        if (!isSetSid(tenantSid)) {
            tenantSid = response.getHeader(CoreProperties.tokenTenantHeaderName());
        }
        if (!isSetSid(tenantSid)) {
            tenantSid = CommonUtils.randomAuthSid(
                    AuthMemberType.tenant.getCode()
            );
            response.addHeader(
                    CoreProperties.tokenTenantHeaderName(),
                    tenantSid
            );
        }
        GlobalData.set(CoreProperties.HEADER_TENANT_TOKEN_SID, tenantSid);

        // Org SID
        String tenantOrgSid = request.getHeader(CoreProperties.tokenTenantOrgHeaderName());
        GlobalData.set(CoreProperties.HEADER_TENANT_TOKEN_ORG, tenantOrgSid);

        // Store SID
        String tenantStoreSid = request.getHeader(CoreProperties.tokenTenantDepHeaderName());
        GlobalData.set(CoreProperties.HEADER_TENANT_TOKEN_DEP, tenantStoreSid);

        // Shop SID
        String tenantShopSid = request.getHeader(CoreProperties.tokenTenantShopHeaderName());
        GlobalData.set(CoreProperties.HEADER_TENANT_TOKEN_SHOP, tenantShopSid);
    }

    public static void globalMerchantTokenDataSet(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // === merchant
        // SID
        String merchantSid = request.getHeader(
                CoreProperties.tokenMerchantHeaderName()
        );
        if (!isSetSid(merchantSid)) {
            merchantSid = GlobalData.get(CoreProperties.HEADER_MERCHANT_TOKEN_SID);
        }
        if (!isSetSid(merchantSid)) {
            merchantSid = response.getHeader(CoreProperties.tokenMerchantHeaderName());
        }
        if (!isSetSid(merchantSid)) {
            merchantSid = CommonUtils.randomAuthSid(
                    AuthMemberType.merchant.getCode()
            );
            response.addHeader(
                    CoreProperties.tokenMerchantHeaderName(),
                    merchantSid
            );
        }
        GlobalData.set(CoreProperties.HEADER_MERCHANT_TOKEN_SID, merchantSid);

        // Org SID
        String merchantOrgSid = request.getHeader(CoreProperties.tokenMerchantOrgHeaderName());
        GlobalData.set(CoreProperties.HEADER_MERCHANT_TOKEN_ORG, merchantOrgSid);

        // Store SID
        String merchantStoreSid = request.getHeader(CoreProperties.tokenMerchantDepHeaderName());
        GlobalData.set(CoreProperties.HEADER_MERCHANT_TOKEN_DEP, merchantStoreSid);

        // Shop SID
        String merchantShopSid = request.getHeader(CoreProperties.tokenMerchantShopHeaderName());
        GlobalData.set(CoreProperties.HEADER_MERCHANT_TOKEN_SHOP, merchantShopSid);
    }

    public static void globalConsumerTokenDataSet(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // === consumer
        // SID
        String consumerSid = request.getHeader(
                CoreProperties.tokenConsumerHeaderName()
        );
        if (!isSetSid(consumerSid)) {
            consumerSid = GlobalData.get(CoreProperties.HEADER_CONSUMER_TOKEN_SID);
        }
        if (!isSetSid(consumerSid)) {
            consumerSid = response.getHeader(CoreProperties.tokenConsumerHeaderName());
        }
        if (!isSetSid(consumerSid)) {
            consumerSid = CommonUtils.randomAuthSid(
                    AuthMemberType.consumer.getCode()
            );
            response.addHeader(
                    CoreProperties.tokenConsumerHeaderName(),
                    consumerSid
            );
        }
        GlobalData.set(CoreProperties.HEADER_CONSUMER_TOKEN_SID, consumerSid);

        // Org SID
        String consumerOrgSid = request.getHeader(CoreProperties.tokenConsumerOrgHeaderName());
        GlobalData.set(CoreProperties.HEADER_CONSUMER_TOKEN_ORG, consumerOrgSid);

        // Store SID
        String consumerStoreSid = request.getHeader(CoreProperties.tokenConsumerDepHeaderName());
        GlobalData.set(CoreProperties.HEADER_CONSUMER_TOKEN_DEP, consumerStoreSid);

        // Shop SID
        String consumerShopSid = request.getHeader(CoreProperties.tokenConsumerShopHeaderName());
        GlobalData.set(CoreProperties.HEADER_CONSUMER_TOKEN_SHOP, consumerShopSid);
    }

    public static void globalApiTokenDataSet(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // === api
        // SID
        String apiSid = request.getHeader(
                CoreProperties.tokenApiHeaderName()
        );
        if (!isSetSid(apiSid)) {
            apiSid = GlobalData.get(CoreProperties.HEADER_API_TOKEN_SID);
        }
        if (!isSetSid(apiSid)) {
            apiSid = response.getHeader(CoreProperties.tokenApiHeaderName());
        }
        if (!isSetSid(apiSid)) {
            apiSid = CommonUtils.randomAuthSid(
                    AuthMemberType.api.getCode()
            );
            response.addHeader(
                    CoreProperties.tokenApiHeaderName(),
                    apiSid
            );
        }
        GlobalData.set(CoreProperties.HEADER_API_TOKEN_SID, apiSid);

        // Org SID
        String apiOrgSid = request.getHeader(CoreProperties.tokenApiOrgHeaderName());
        GlobalData.set(CoreProperties.HEADER_API_TOKEN_ORG, apiOrgSid);

        // Store SID
        String apiStoreSid = request.getHeader(CoreProperties.tokenApiDepHeaderName());
        GlobalData.set(CoreProperties.HEADER_API_TOKEN_DEP, apiStoreSid);

        // Shop SID
        String apiShopSid = request.getHeader(CoreProperties.tokenApiShopHeaderName());
        GlobalData.set(CoreProperties.HEADER_API_TOKEN_SHOP, apiShopSid);
    }

    public static void globalTokenDataSet(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        AuthMemberType memberType = AuthCommon.authMemberType(request);
        if (memberType == null) {
            return;
        }
        switch (memberType) {
            case admin -> {
                globalAdminTokenDataSet(request, response);
            }
            case tenant -> {
                globalTenantTokenDataSet(request, response);
            }
            case merchant -> {
                globalMerchantTokenDataSet(request, response);
            }
            case consumer -> {
                globalConsumerTokenDataSet(request, response);
            }
            case api -> {
                globalApiTokenDataSet(request, response);
            }
        }

    }

    // base
    public static void globalBaseDataSet(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // SERVLET_PATH
        GlobalData.set(
                DataKeys.SERVLET_PATH_MODULE,
                SystemCommon.getRequestModule(request)
        );

        GlobalData.set(
                DataKeys.SERVLET_PATH_APP,
                SystemCommon.getRequestApp(request)
        );

//        GlobalData.set(
//                CoreProperties.HEADER_ADMIN_TOKEN_USER,
//                SystemCommon.getRequestUid(request)
//        );

        // cache headers
        RequestUtils.headerValue(
                request,
                DataKeys.REQUEST_CACHE_CONTROL_HEADER,
                DataKeys.REQUEST_CACHE_CONTROL_HEADER,
                true
        );

        // noCacheHeader
        RequestUtils.headerValue(
                request,
                CoreProperties.appHeaderNoCacheName(),
                DataKeys.REQUEST_NO_CACHE_HEADER,
                true
        );
    }

}
