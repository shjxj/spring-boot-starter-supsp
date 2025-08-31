package com.supsp.springboot.core.consts;

public class DataKeys {

    public static final String TRACE_ID = "TRACE_ID";
    public static final String THREAD_ID = "THREAD_ID";
    public static final String SERVLET_PATH_FIRST = "SERVLET_PATH_FIRST";
    public static final String SERVLET_PATH_SECOND = "SERVLET_PATH_SECOND";

    public static final String SERVLET_PATH_MODULE = "SERVLET_PATH_MODULE";
    public static final String SERVLET_PATH_APP = "SERVLET_PATH_APP";

    public static final String AUTH_REALM_MODULE = "AUTH_REALM_MODULE";

    public static final String IGNORE_UPDATE = "ignoreUpdate";
    public static final String REQUEST_IP = "requestIp";
    public static final String REQUEST_REMOTE_ADDR = "remoteAddr";
    public static final String REQUEST_IP_INFO = "requestIpInfo";

    public static final String REQUEST_HEADER_NAMES = "headerNames";
    public static final String REQUEST_HEADERS = "headers";

    public static final String REQUEST_CACHE_CONTROL_HEADER = "Cache-Control";
    public static final String REQUEST_NO_CACHE_HEADER = "noCacheHeader";
    public static final String REQUEST_APP_NAME = "appName";
    public static final String REQUEST_APP_CODE = "appCode";
    public static final String REQUEST_PLATFORM_NAME = "platformName";
    public static final String REQUEST_DEVICE_SN = "deviceSN";
    public static final String REQUEST_VERSION = "appVer";
    public static final String REQUEST_FORCE_SUBMIT = "forceSubmit";
    public static final String REQUEST_OPERATOR_ID = "operatorId";
    public static final String REQUEST_CASHIER_ID = "cashierId";

//    public static final String AUTH_SCOPE = "authScope";
//
//    public static final String AUTH_OPERATOR = "authOperator";

//    public static final String AUTH_ACCOUNT = "authAccount";

    public static final String AUTH_ACCOUNT_ADMIN = "authAccountAdmin";
    public static final String AUTH_ACCOUNT_TENANT = "authAccountTenant";
    public static final String AUTH_ACCOUNT_MERCHANT = "authAccountMerchant";
    public static final String AUTH_ACCOUNT_CONSUMER = "authAccountConsumer";
    public static final String AUTH_ACCOUNT_API = "authAccountApi";


//    public static final String AUTH_MEMBER_TYPE = "authMemberType";
//    public static final String AUTH_MEMBER_ID = "authMemberId";
//    public static final String AUTH_MEMBER_NAME = "authMemberName";
//    public static final String AUTH_MEMBER_ACCOUNT = "authMemberAccount";
//    public static final String AUTH_ACCOUNT_TYPE = "authAccountType";

    // cache
//    public static final String CACHE_THREAD_DATA = "cacheThreadData";
//    public static final String CACHE_READ_TABLES = "cacheReadTables";
//    public static final String CACHE_WRITE_TABLES = "cacheWriteTables";
//    public static final String CACHE_SELECT_TABLES = "cacheSelectTables";
//    public static final String CACHE_INSERT_TABLES = "cacheInsertTables";
//    public static final String CACHE_UPSERT_TABLES = "cacheUpsertTables";
//    public static final String CACHE_UPDATE_TABLES = "cacheUpdateTables";
//    public static final String CACHE_DELETE_TABLES = "cacheDeleteTables";

    // mq
    public static final String MQ_SIDE = "mqSide";
    public static final String MQ_CONSUMER_SIDE = "mqConsumer";
    public static final String MQ_MEMBER_TYPE = "mqMemberType";
    public static final String MQ_APP_NAME = "APP_NAME";
    public static final String MQ_APP_MODULE = "APP_MODULE";
    public static final String MQ_APP_API = "APP_API";

    // logs
    public static final String SYS_LOG = "SYS_LOG";

    public static final String LOG_MODULE = "module";
    public static final String LOG_MODULE_NAME= "moduleName";
    public static final String LOG_APP = "app";
    public static final String LOG_APP_NAME = "appName";


    public static final String LOG_OPERATION = "operation";
    public static final String LOG_CHILD_OPERATION = "childOperation";
    public static final String LOG_OPERATION_NAME= "operationName";
    public static final String LOG_CHILD_OPERATION_NAME = "childOperationName";

    public static final String LOG_WAREHOUSE_ID = "warehouseId";
    public static final String LOG_DEPARTMENT_ID = "departmentId";
    public static final String LOG_ORG_CHILD_ID= "orgChildId";
    public static final String LOG_ORG_ID = "orgId";

    //
    public static final String LOG_DESC = "desc";
    public static final String LOG_DATA = "data";
    public static final String LOG_PARAMS = "params";
    public static final String LOG_EXTRA = "extra";
    public static final String LOG_HAS_EXCEPTION = "hasException";
    public static final String LOG_EXCEPTION = "exception";

    //
    public static final String LOG_AUTH_ACCOUNT = "authAccount";
    public static final String LOG_AUTH_OPERATOR_TYPE= "operatorType";
    public static final String LOG_AUTH_OPERATOR_ID= "operatorId";
    public static final String LOG_AUTH_OPERATOR_NAME= "operatorName";
    public static final String LOG_AUTH_OPERATOR_ACCOUNT= "operatorAccount";
    public static final String LOG_AUTH_OPERATOR_PHONE= "operatorPhone";
}
