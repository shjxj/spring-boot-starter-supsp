package com.supsp.springboot.core.consts;


import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final long DEFAULT_IDEMPOTENT_SECONDS = 2;

    public static final String TEXT_SPLIT = "::";
    public static final String TEXT_SPLIT_SINGLE = ":";
    public static final String TEXT_SPLIT_INFO = "||";
    public static final String EMPTY_PRODUCT_IDENT = "-";

    public static final String PERMISSION_SPLIT = "::";
    public static final String PERMISSIONS_ALL_SUFFIX = "::ALL";
    public static final String PERMISSION_ANY = "ANY";
    public static final String PERMISSION_QUERY = "QUERY";
    public static final String CGLIB_CLASS_SEPARATOR = "$$";
    public static final String REGEX_CGLIB_CLASS_SEPARATOR = "\\$\\$";

    public static final String PERMISSION_ROLE_ORG_AUTHORITY_PREFIX = "OA::";
    public static final String PERMISSION_ROLE_ORG_TYPE_PREFIX = "OT::";
    public static final String PERMISSION_ROLE_DEPARTMENTTYPE_TYPE_PREFIX = "DT::";
    public static final String PERMISSION_ROLE_POST_TYPE_PREFIX = "PT::";

    public static final String PERMISSION_ROLE_PREFIX = "ROLE_";
    public static final String PERMISSION_ROLE_TYPE_PREFIX = "RT::";
    public static final String PERMISSION_ROLE_CODE_PREFIX = "RC::";
    public static final String PERMISSION_ROLE_PERSON_PREFIX = "RP::";

    // public static final String PERMISSION_ROLE_MEMBER = "member";
    public static final String PERMISSION_ROLE_ADMIN = "admin";
    public static final String PERMISSION_ROLE_TENANT = "tenant";
    public static final String PERMISSION_ROLE_MERCHANT = "merchant";
    public static final String PERMISSION_ROLE_CONSUMER = "consumer";
    public static final String PERMISSION_ROLE_API = "api";
    public static final String PERMISSION_ROLE_USER = "user";

    public static final String ROLE_ADMIN = PERMISSION_ROLE_PREFIX + PERMISSION_ROLE_ADMIN;
    public static final String ROLE_TENANT = PERMISSION_ROLE_PREFIX + PERMISSION_ROLE_TENANT;
    public static final String ROLE_MERCHANT = PERMISSION_ROLE_PREFIX + PERMISSION_ROLE_MERCHANT;
    public static final String ROLE_CONSUMER = PERMISSION_ROLE_PREFIX + PERMISSION_ROLE_CONSUMER;
    public static final String ROLE_API = PERMISSION_ROLE_PREFIX + PERMISSION_ROLE_API;
    public static final String ROLE_USER = PERMISSION_ROLE_PREFIX + PERMISSION_ROLE_USER;

    //
    public static final String ROLE_DEP_MANAGER = PERMISSION_ROLE_DEPARTMENTTYPE_TYPE_PREFIX + "manager";
    public static final String ROLE_DEP_ORG = PERMISSION_ROLE_DEPARTMENTTYPE_TYPE_PREFIX + "org";
    public static final String ROLE_DEP_CHILD = PERMISSION_ROLE_DEPARTMENTTYPE_TYPE_PREFIX + "child";
    public static final String ROLE_DEP_DEPARTMENT = PERMISSION_ROLE_DEPARTMENTTYPE_TYPE_PREFIX + "department";
    public static final String ROLE_DEP_WAREHOUSE = PERMISSION_ROLE_DEPARTMENTTYPE_TYPE_PREFIX + "warehouse";


    public static final String PERMISSION_ROLE_LEGAL = "LegalPerson";

    public static final String PERMISSION_ROLE_MANAGER = "ManagerPerson";

    public static final LocalDate DEFAULT_LOCAL_DATE = LocalDate.of(1800, 1, 1);
    public static final LocalDate MIN_LOCAL_DATE = LocalDate.of(1900, 1, 1);

    public static final long DEFAULT_CURRENT_PAGE = 1;
    public static final long DEFAULT_PAGE_SIZE = 20;
    public static final long DEFAULT_LIMIT = 50;

    public static final Integer DEFAULT_ORDER = 20000;

    public static final String FILE_SEPARATOR = File.separator;

    public static final String DEFAULT_MODULE = "_MODULE_";
    public static final String DEFAULT_OBJECT = "_OBJECT_";
    public static final String DEFAULT_NAME = "_NAME_";

    public static final String CONTROLLER_FIX = "Controller";

    public static final String TYPE_MINUTE = "M";
    public static final String TYPE_SECONDS = "S";
    public static final String TYPE_DAY = "D";

    public static final long SECOND_OF_HOUR = 3600L;
    public static final long RESERVE_INTERVAL = 21600L;
    public static final long RESERVE_OVERDUE = 30L;

    public static final long MINUTE_TIME_OUT = 300L;
    public static final long SECONDS_TIME_OUT = 120L;
    public static final long DAY_TIME_OUT = 172800L;

    public static final Long LONG_ZERO = 0L;

    public static final Long LONG_LOWER = -1L;

    public static final Integer INT_ZERO = 0;

    public static final Integer INT_LOWER = -1;

    public static final Integer INT_TRUE = 1;

    public static final Short SHORT_ZERO = 0;

    public static final Byte BYTE_ZERO = 0;

    public static final BigDecimal BIG_DECIMAL_ZERO = BigDecimal.ZERO;

    public static final BigDecimal DISCOUNT_TEN = BigDecimal.TEN;

    public static final Long DISCOUNT_HUNDRED = 100L;

    public static final Float FLOAT_ZERO = (float) 0;

    public static final Double DOUBLE_ZERO = (double) 0;

    public static final String STRING_ZERO = "0";

    public static final Long LONG_TRUE = 1L;

    public static final Short SHORT_TRUE = 1;

    public static final Byte BYTE_TRUE = 1;

    public static final String STRING_TRUE = "true";

    public static final String STRING_FALSE = "false";
    public static final String STRING_EMPTY = "";
    public static final String BASE_TABLE_ALIAS = "t";
    public static final String STRING_ALL = "all";

    public static final String STRING_YES = "yes";
    public static final String STRING_NO = "no";
    public static final String NO_CACHE = "no-cache";

    public static final String STRING_LOCAL = "local";
    public static final String STRING_DOCKER_LOCAL = "docker-local";
    public static final String STRING_WLAN = "wlan";
    public static final String STRING_DEV = "dev";
    public static final String STRING_TEST = "test";
    public static final String STRING_MASTER = "master";

    public static final String STORAGE_LOCAL = "local";

    public static final List<String> IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "bmp", "gif", "png", "webp");


    public static final String COLUMNS_ID = "id";

    public static final String COLUMNS_ORG_ROLE = "org_role";
    public static final String COLUMNS_ACCOUNT_TYPE = "account_type";
    public static final String COLUMNS_MEMBER_TYPE = "member_type";

    public static final String COLUMNS_IS_SYSTEM = "is_system";
    public static final String COLUMNS_IS_DEFAULT = "is_default";
    public static final String COLUMNS_ENABLE_STATUS = "enable_status";
    public static final String COLUMNS_SHOW_STATUS = "show_status";
    public static final String COLUMNS_AUDIT_STATUS = "audit_status";
    public static final String COLUMNS_REPORT_STATUS = "report_status";
    public static final String COLUMNS_ORDER_SORT = "order_sort";

    //
    public static final String ALL_STRING = "all";

    //
    public static final String SQL_STATEMENT_INSERT = "insert";
    public static final String SQL_STATEMENT_UPDATE = "update";
    public static final String SQL_STATEMENT_DELETE = "delete";
    // created
    public static final String COLUMNS_CREATED_AT = "created_at";
    public static final String COLUMNS_CREATED_MEMBER_TYPE = "created_member_type";
    public static final String COLUMNS_CREATED_MEMBER_ID = "created_member_id";
    public static final String COLUMNS_CREATED_MEMBER_NAME = "created_member_name";
    public static final String COLUMNS_CREATED_MEMBER_ACCOUNT = "created_member_account";
    public static final String COLUMNS_CREATED_MEMBER_IP = "created_member_ip";

    public static final String COLUMNS_CREATED_ORG_ID = "created_org_id";
    public static final String COLUMNS_CREATED_ORG_NAME = "created_org_name";
    public static final String COLUMNS_CREATED_STORE_ID = "created_store_id";
    public static final String COLUMNS_CREATED_STORE_NAME = "created_store_name";

    // updated
    public static final String COLUMNS_UPDATED_AT = "updated_at";
    public static final String COLUMNS_UPDATED_MEMBER_TYPE = "updated_member_type";
    public static final String COLUMNS_UPDATED_MEMBER_ID = "updated_member_id";
    public static final String COLUMNS_UPDATED_MEMBER_NAME = "updated_member_name";
    public static final String COLUMNS_UPDATED_MEMBER_ACCOUNT = "updated_member_account";
    public static final String COLUMNS_UPDATED_MEMBER_IP = "updated_member_ip";

    public static final String COLUMNS_UPDATED_ORG_ID = "updated_org_id";
    public static final String COLUMNS_UPDATED_ORG_NAME = "updated_org_name";
    public static final String COLUMNS_UPDATED_STORE_ID = "updated_store_id";
    public static final String COLUMNS_UPDATED_STORE_NAME = "updated_store_name";

    // deleted
    public static final String COLUMNS_DELETED = "deleted";
    public static final String COLUMNS_DELETED_MEMBER_TYPE = "deleted_member_type";
    public static final String COLUMNS_DELETED_MEMBER_ID = "deleted_member_id";
    public static final String COLUMNS_DELETED_MEMBER_NAME = "deleted_member_name";
    public static final String COLUMNS_DELETED_MEMBER_ACCOUNT = "deleted_member_account";
    public static final String COLUMNS_DELETED_MEMBER_IP = "deleted_member_ip";

    public static final String COLUMNS_DELETED_ORG_ID = "deleted_org_id";
    public static final String COLUMNS_DELETED_ORG_NAME = "deleted_org_name";
    public static final String COLUMNS_DELETED_STORE_ID = "deleted_store_id";
    public static final String COLUMNS_DELETED_STORE_NAME = "deleted_store_name";

    public static final String COLUMNS_DELETED_AT = "deleted_at";

    public static final String COLUMNS_TENANT_ID = "tenant_id";
    public static final String COLUMNS_MERCHANT_ID = "merchant_id";

    public static final String COLUMNS_ORG_ID = "org_id";
    public static final String COLUMNS_STORE_ID = "store_id";
    public static final String COLUMNS_SHOP_ID = "shop_id";
    public static final String COLUMNS_MEMBER_ID = "member_id";

    //
    public static final String PROPERTY_ENABLE_STATUS = "enableStatus";
    public static final String PROPERTY_SHOW_STATUS = "showStatus";
    public static final String PROPERTY_CREATED_AT = "createdAt";
    public static final String PROPERTY_UPDATED_AT = "updatedAt";

    //

    public static final String OWNER_TYPE_SYSTEM = "system";
    public static final String OWNER_TYPE_TENANT = "tenant";
    public static final String OWNER_TYPE_MERCHANT = "merchant";
    public static final String OWNER_TYPE_CONSUMER = "consumer";
    public static final String OWNER_TYPE_API = "api";
    public static final String OWNER_TYPE_ORG = "org";
    public static final String OWNER_TYPE_STORE = "store";
    public static final String OWNER_TYPE_SHOP = "shop";

    public static final String OWNER_TYPE_MEMBER = "member";

    public static final String OWNER_TYPE_ORG_KIND = "orgKind";

    public static final String OWNER_TYPE_STORE_KIND = "storeKind";

    public static final String OWNER_TYPE_POST = "post";

    public static final String SPEC_NAME_SPLIT = "/";

    public static final String LOCK_KEY_PREFIX = "LOCK::";

    public static final String BASE_OPERATION = "OPERATION";

    public static final String OPERATION_CREATE = "create";
    public static final String OPERATION_EDIT = "edit";

    public static final String GOODS_CODE_PREFIX = "69";
    public static final int GOOD_CODE_LEN = 13;
    public static final String PRODUCT_CODE_PREFIX = "69";
    public static final int PRODUCT_CODE_LEN = 13;

    public static final String INVENTORY_KEY = "inventory";
    public static final String STOCK_KEY = "stock";
    public static final String FREEZE_KEY = "freeze";
    public static final String AVAILABLE_KEY = "available";

    public final static String CAPTCHA_HEADER = "Captcha-Key";
    public final static String CACHED_HEADER = "Cached";

    public final static long DEVICE_MIN_COUNT = 91527;
    public final static String DEVICE_POST = "cashPos";

    public static final String ORDER_ITEM_TMP_NAME = "临时商品";
    public static final String PROMOTION_TYPE_ORDER = "order";
    public static final String PROMOTION_TYPE_ORDER_ITEM = "order_item";

    public static final String BILL_MODULE_DEFAULT = "oms";
    public static final String BILL_OBJECT_DEFAULT = "order";
    public static final Long BILL_TIME_OUT = 600L;
    public static final Long BILL_CASHIER_TIME_OUT = 43200L;

    // 金额转积分比例 100分钱 换算为 1积分
    public static final Long PRICE_TO_INTEGRAL_RATIO = 100L;

    // 积分转金额比例 1积分 换算为 1分钱
    public static final Long INTEGRAL_TO_PRICE_RATIO = 1L;

    // 支付码最小长度
    public static final int PAY_CODE_MIN_LEN = 16;

    // sysOrgDepartment
    public static final String SYS_ORG_DEPARTMENT = "sysOrgDepartment";

    public static final String POLITICS_COMMUNIST = "communist";

    public static final List<String> IGNORE_METHODS = List.of(
            "setOrder",
            "setOrder",
            "batchSetOrder",
            //
            "show",
            "batchShow",
            "hidden",
            "batchHidden",
            //
            "saveTags",
            "getTags",
            "getTagList"
    );


    //
    public static final String EXAMINE_OTHER_VALUE = "OTH-VAL";
    public static final String EXAMINE_OTHER_LABEL = "其它";

    public static final List<String> DEV_ENVS = List.of(
            STRING_LOCAL,
            STRING_WLAN,
            STRING_DEV
    );
}
