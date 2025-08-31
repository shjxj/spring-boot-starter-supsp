package com.supsp.springboot.core.consts;


import com.supsp.springboot.core.enums.SysColumnType;

import java.io.Serial;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelConstants {

    public final static List<String> SYS_COLUMN_LIST = Arrays.asList(
            Constants.COLUMNS_ORG_ROLE,
            Constants.COLUMNS_ACCOUNT_TYPE,
            Constants.COLUMNS_MEMBER_TYPE,

            Constants.COLUMNS_IS_DEFAULT,
            Constants.COLUMNS_IS_SYSTEM,
            Constants.COLUMNS_ENABLE_STATUS,
            Constants.COLUMNS_SHOW_STATUS,
            Constants.COLUMNS_REPORT_STATUS,
            Constants.COLUMNS_ORDER_SORT,

            Constants.COLUMNS_CREATED_AT,
            Constants.COLUMNS_CREATED_MEMBER_TYPE,
            Constants.COLUMNS_CREATED_MEMBER_ID,
            Constants.COLUMNS_CREATED_MEMBER_NAME,
            Constants.COLUMNS_CREATED_MEMBER_ACCOUNT,
            Constants.COLUMNS_CREATED_MEMBER_IP,

            Constants.COLUMNS_CREATED_ORG_ID,
            Constants.COLUMNS_CREATED_ORG_NAME,
            Constants.COLUMNS_CREATED_STORE_ID,
            Constants.COLUMNS_CREATED_STORE_NAME,

            Constants.COLUMNS_UPDATED_AT,
            Constants.COLUMNS_UPDATED_MEMBER_TYPE,
            Constants.COLUMNS_UPDATED_MEMBER_ID,
            Constants.COLUMNS_UPDATED_MEMBER_NAME,
            Constants.COLUMNS_UPDATED_MEMBER_ACCOUNT,
            Constants.COLUMNS_UPDATED_MEMBER_IP,

            Constants.COLUMNS_UPDATED_ORG_ID,
            Constants.COLUMNS_UPDATED_ORG_NAME,
            Constants.COLUMNS_UPDATED_STORE_ID,
            Constants.COLUMNS_UPDATED_STORE_NAME,

            Constants.COLUMNS_DELETED,
            Constants.COLUMNS_DELETED_MEMBER_TYPE,
            Constants.COLUMNS_DELETED_MEMBER_ID,
            Constants.COLUMNS_DELETED_MEMBER_NAME,
            Constants.COLUMNS_DELETED_MEMBER_ACCOUNT,
            Constants.COLUMNS_DELETED_MEMBER_IP,

            Constants.COLUMNS_DELETED_ORG_ID,
            Constants.COLUMNS_DELETED_ORG_NAME,
            Constants.COLUMNS_DELETED_STORE_ID,
            Constants.COLUMNS_DELETED_STORE_NAME,

            Constants.COLUMNS_DELETED_AT
    );

    public final static Map<String, SysColumnType> SYS_COLUMN_TYPE_MAP = new HashMap<>() {
        @Serial
        private static final long serialVersionUID = -2126330309355768733L;

        {

            //

            put(
                    Constants.COLUMNS_ORG_ROLE,
                    SysColumnType.ORG_ROLE
            );

            put(
                    Constants.COLUMNS_ACCOUNT_TYPE,
                    SysColumnType.ACCOUNT_TYPE
            );

            put(
                    Constants.COLUMNS_MEMBER_TYPE,
                    SysColumnType.MEMBER_TYPE
            );
            put(
                    Constants.COLUMNS_IS_DEFAULT,
                    SysColumnType.IS_DEFAULT
            );
            put(
                    Constants.COLUMNS_IS_SYSTEM,
                    SysColumnType.IS_SYSTEM
            );
            put(
                    Constants.COLUMNS_ENABLE_STATUS,
                    SysColumnType.ENABLE_STATUS
            );
            put(
                    Constants.COLUMNS_SHOW_STATUS,
                    SysColumnType.SHOW_STATUS
            );

            put(
                    Constants.COLUMNS_AUDIT_STATUS,
                    SysColumnType.AUDIT_STATUS
            );

            put(
                    Constants.COLUMNS_REPORT_STATUS,
                    SysColumnType.REPORT_STATUS
            );
            put(
                    Constants.COLUMNS_ORDER_SORT,
                    SysColumnType.ORDER_SORT
            );

            // CREATED
            put(
                    Constants.COLUMNS_CREATED_AT,
                    SysColumnType.CREATED_AT
            );
            put(
                    Constants.COLUMNS_CREATED_MEMBER_TYPE,
                    SysColumnType.CREATED_MEMBER_TYPE
            );
            put(
                    Constants.COLUMNS_CREATED_MEMBER_ID,
                    SysColumnType.CREATED_MEMBER_ID
            );
            put(
                    Constants.COLUMNS_CREATED_MEMBER_NAME,
                    SysColumnType.CREATED_MEMBER_NAME
            );
            put(
                    Constants.COLUMNS_CREATED_MEMBER_ACCOUNT,
                    SysColumnType.CREATED_MEMBER_ACCOUNT
            );
            put(
                    Constants.COLUMNS_CREATED_MEMBER_IP,
                    SysColumnType.CREATED_MEMBER_IP
            );

            put(
                    Constants.COLUMNS_CREATED_ORG_ID,
                    SysColumnType.CREATED_ORG_ID
            );
            put(
                    Constants.COLUMNS_CREATED_ORG_NAME,
                    SysColumnType.CREATED_ORG_NAME
            );
            put(
                    Constants.COLUMNS_CREATED_STORE_ID,
                    SysColumnType.CREATED_STORE_ID
            );
            put(
                    Constants.COLUMNS_CREATED_STORE_NAME,
                    SysColumnType.CREATED_STORE_NAME
            );

            // UPDATED

            put(
                    Constants.COLUMNS_UPDATED_AT,
                    SysColumnType.UPDATED_AT
            );
            put(
                    Constants.COLUMNS_UPDATED_MEMBER_TYPE,
                    SysColumnType.UPDATED_MEMBER_TYPE
            );
            put(
                    Constants.COLUMNS_UPDATED_MEMBER_ID,
                    SysColumnType.UPDATED_MEMBER_ID
            );
            put(
                    Constants.COLUMNS_UPDATED_MEMBER_NAME,
                    SysColumnType.UPDATED_MEMBER_NAME
            );
            put(
                    Constants.COLUMNS_UPDATED_MEMBER_ACCOUNT,
                    SysColumnType.UPDATED_MEMBER_ACCOUNT
            );
            put(
                    Constants.COLUMNS_UPDATED_MEMBER_IP,
                    SysColumnType.UPDATED_MEMBER_IP
            );

            put(
                    Constants.COLUMNS_UPDATED_ORG_ID,
                    SysColumnType.UPDATED_ORG_ID
            );
            put(
                    Constants.COLUMNS_UPDATED_ORG_NAME,
                    SysColumnType.UPDATED_ORG_NAME
            );
            put(
                    Constants.COLUMNS_UPDATED_STORE_ID,
                    SysColumnType.UPDATED_STORE_ID
            );
            put(
                    Constants.COLUMNS_UPDATED_STORE_NAME,
                    SysColumnType.UPDATED_STORE_NAME
            );

            // DELETED
            put(
                    Constants.COLUMNS_DELETED,
                    SysColumnType.DELETED
            );
            put(
                    Constants.COLUMNS_DELETED_AT,
                    SysColumnType.DELETED_AT
            );
            put(
                    Constants.COLUMNS_DELETED_MEMBER_TYPE,
                    SysColumnType.DELETED_MEMBER_TYPE
            );
            put(
                    Constants.COLUMNS_DELETED_MEMBER_ID,
                    SysColumnType.DELETED_MEMBER_ID
            );
            put(
                    Constants.COLUMNS_DELETED_MEMBER_NAME,
                    SysColumnType.DELETED_MEMBER_NAME
            );
            put(
                    Constants.COLUMNS_DELETED_MEMBER_ACCOUNT,
                    SysColumnType.DELETED_MEMBER_ACCOUNT
            );
            put(
                    Constants.COLUMNS_DELETED_MEMBER_IP,
                    SysColumnType.DELETED_MEMBER_IP
            );

            put(
                    Constants.COLUMNS_DELETED_ORG_ID,
                    SysColumnType.DELETED_ORG_ID
            );
            put(
                    Constants.COLUMNS_DELETED_ORG_NAME,
                    SysColumnType.DELETED_ORG_NAME
            );
            put(
                    Constants.COLUMNS_DELETED_STORE_ID,
                    SysColumnType.DELETED_STORE_ID
            );
            put(
                    Constants.COLUMNS_DELETED_STORE_NAME,
                    SysColumnType.DELETED_STORE_NAME
            );
        }
    };
}
