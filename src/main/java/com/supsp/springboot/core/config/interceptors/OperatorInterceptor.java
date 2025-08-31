package com.supsp.springboot.core.config.interceptors;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.supsp.springboot.core.config.ModelConfig;
import com.supsp.springboot.core.consts.Constants;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.helper.AuthCommon;
import com.supsp.springboot.core.threads.ModelContext;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.Values;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OperatorInterceptor extends JsqlParserSupport implements InnerInterceptor {

    public static List<SqlCommandType> interceptorSqlCommandTypes = Arrays.asList(
            SqlCommandType.INSERT,
            SqlCommandType.UPDATE
    );

    public static List<String> insertColumns = Arrays.asList(
            Constants.COLUMNS_TENANT_ID,
            Constants.COLUMNS_MERCHANT_ID,

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
            Constants.COLUMNS_UPDATED_STORE_NAME

    );

    public static List<String> updateColumns = Arrays.asList(
            Constants.COLUMNS_UPDATED_AT,
            Constants.COLUMNS_UPDATED_MEMBER_TYPE,
            Constants.COLUMNS_UPDATED_MEMBER_ID,
            Constants.COLUMNS_UPDATED_MEMBER_NAME,
            Constants.COLUMNS_UPDATED_MEMBER_ACCOUNT,
            Constants.COLUMNS_UPDATED_MEMBER_IP,

            Constants.COLUMNS_UPDATED_ORG_ID,
            Constants.COLUMNS_UPDATED_ORG_NAME,
            Constants.COLUMNS_UPDATED_STORE_ID,
            Constants.COLUMNS_UPDATED_STORE_NAME
    );

    public static List<String> deleteColumns = Arrays.asList(
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

    public static Expression localDateTimeNowExpression() {
        return new StringValue(
                LocalDateTime.now()
                        .format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        )
        );
    }

    public static Expression tenantIdExpression() {
        Long tenantId = AuthCommon.getTenantId();
        if (tenantId == null) {
            tenantId = Constants.LONG_ZERO;
        }
        return new LongValue(tenantId);
    }

    public static Expression merchantIdExpression() {
        Long merchantId = AuthCommon.getMerchantId();
        if (merchantId == null) {
            merchantId = Constants.LONG_ZERO;
        }
        return new LongValue(merchantId);
    }

    public static Expression memberTypeExpression() {
        AuthMemberType memberType = AuthCommon.getMemberType();
        if (memberType == null) {
            memberType = AuthMemberType.none;
        }
        return new StringValue(memberType.getCode());
    }

    public static Expression memberIdExpression() {
        Long memberId = AuthCommon.getMemberId();
        if (memberId == null) {
            memberId = Constants.LONG_ZERO;
        }
        return new LongValue(memberId);
    }

    public static Expression memberNameExpression() {
        String memberName = AuthCommon.getMemberName();
        if (memberName == null) {
            memberName = Constants.STRING_EMPTY;
        }
        return new StringValue(memberName);
    }

    public static Expression memberAccountExpression() {
        String memberAccount = AuthCommon.getMemberAccount();
        if (memberAccount == null) {
            memberAccount = Constants.STRING_EMPTY;
        }
        return new StringValue(memberAccount);
    }

    public static Expression requestIpExpression() {
        String requestIp = AuthCommon.getRequestIp();
        if (requestIp == null) {
            requestIp = Constants.STRING_EMPTY;
        }
        return new StringValue(requestIp);
    }

    public static Expression orgIdExpression() {
        Long orgId = AuthCommon.getOrgId();
        if (orgId == null) {
            orgId = Constants.LONG_ZERO;
        }
        return new LongValue(orgId);
    }

    public static Expression orgNameExpression() {
        String orgName = AuthCommon.getOrgName();
        if (orgName == null) {
            orgName = Constants.STRING_EMPTY;
        }
        return new StringValue(orgName);
    }

    public static Expression storeIdExpression() {
        Long storeId = AuthCommon.getStoreId();
        if (storeId == null) {
            storeId = Constants.LONG_ZERO;
        }
        return new LongValue(storeId);
    }

    public static Expression storeNameExpression() {
        String storeName = AuthCommon.getStoreName();
        if (storeName == null) {
            storeName = Constants.STRING_EMPTY;
        }
        return new StringValue(storeName);
    }

    protected Map<String, Expression> getSetExpressions(
            List<String> tableColumns,
            List<String> columns,
            String tableName
    ) {
        if (
                CommonUtils.isEmpty(tableColumns)
                        || CommonUtils.isEmpty(columns)
        ) {
            return null;
        }
        Expression tenantIdExpression = tenantIdExpression();
        Expression merchantIdExpression = merchantIdExpression();

        Expression localDateTimeNowExpression = localDateTimeNowExpression();
        Expression memberTypeExpression = memberTypeExpression();
        Expression memberIdExpression = memberIdExpression();
        Expression memberNameExpression = memberNameExpression();
        Expression memberAccountExpression = memberAccountExpression();
        Expression requestIpExpression = requestIpExpression();

        Expression orgIdExpression = orgIdExpression();
        Expression orgNameExpression = orgNameExpression();
        Expression storeIdExpression = storeIdExpression();
        Expression storeNameExpression = storeNameExpression();

        Map<String, Expression> expressionMap = new HashMap<>();
        for (String column : columns) {
            if (
                    StrUtils.isBlank(column)
                            || !tableColumns.contains(column)
            ) {
                continue;
            }

            switch (column) {
                case Constants.COLUMNS_TENANT_ID -> {
                    if (
                            ModelConfig.MODEL_IGNORE_TENANT != null
                                    && ModelConfig.MODEL_IGNORE_TENANT.length > 0
                                    && !ArrayUtils.contains(
                                    ModelConfig.MODEL_IGNORE_TENANT,
                                    tableName
                            )
                    ) {
                        expressionMap.put(column, tenantIdExpression);
                    }
                }
                case Constants.COLUMNS_MERCHANT_ID -> {
                    if (
                            ModelConfig.MODEL_IGNORE_MERCHANT != null
                                    && ModelConfig.MODEL_IGNORE_MERCHANT.length > 0
                                    && !ArrayUtils.contains(
                                    ModelConfig.MODEL_IGNORE_MERCHANT,
                                    tableName
                            )
                    ) {
                        expressionMap.put(column, merchantIdExpression);
                    }
                }
                case Constants.COLUMNS_CREATED_AT, Constants.COLUMNS_UPDATED_AT, Constants.COLUMNS_DELETED_AT -> {
                    expressionMap.put(column, localDateTimeNowExpression);
                }
                case Constants.COLUMNS_CREATED_MEMBER_TYPE, Constants.COLUMNS_UPDATED_MEMBER_TYPE,
                     Constants.COLUMNS_DELETED_MEMBER_TYPE -> {
                    expressionMap.put(column, memberTypeExpression);
                }
                case Constants.COLUMNS_CREATED_MEMBER_ID, Constants.COLUMNS_UPDATED_MEMBER_ID,
                     Constants.COLUMNS_DELETED_MEMBER_ID -> {
                    expressionMap.put(column, memberIdExpression);
                }
                case Constants.COLUMNS_CREATED_MEMBER_NAME, Constants.COLUMNS_UPDATED_MEMBER_NAME,
                     Constants.COLUMNS_DELETED_MEMBER_NAME -> {
                    expressionMap.put(column, memberNameExpression);
                }
                case Constants.COLUMNS_CREATED_MEMBER_ACCOUNT, Constants.COLUMNS_UPDATED_MEMBER_ACCOUNT,
                     Constants.COLUMNS_DELETED_MEMBER_ACCOUNT -> {
                    expressionMap.put(column, memberAccountExpression);
                }
                case Constants.COLUMNS_CREATED_MEMBER_IP, Constants.COLUMNS_UPDATED_MEMBER_IP,
                     Constants.COLUMNS_DELETED_MEMBER_IP -> {
                    expressionMap.put(column, requestIpExpression);
                }

                case Constants.COLUMNS_CREATED_ORG_ID, Constants.COLUMNS_UPDATED_ORG_ID,
                     Constants.COLUMNS_DELETED_ORG_ID -> {
                    expressionMap.put(column, orgIdExpression);
                }

                case Constants.COLUMNS_CREATED_ORG_NAME, Constants.COLUMNS_UPDATED_ORG_NAME,
                     Constants.COLUMNS_DELETED_ORG_NAME -> {
                    expressionMap.put(column, orgNameExpression);
                }

                case Constants.COLUMNS_CREATED_STORE_ID, Constants.COLUMNS_UPDATED_STORE_ID,
                     Constants.COLUMNS_DELETED_STORE_ID -> {
                    expressionMap.put(column, storeIdExpression);
                }

                case Constants.COLUMNS_CREATED_STORE_NAME, Constants.COLUMNS_UPDATED_STORE_NAME,
                     Constants.COLUMNS_DELETED_STORE_NAME -> {
                    expressionMap.put(column, storeNameExpression);
                }
            }

        }
        return expressionMap;
    }

    protected Map<String, String> getTableColumns(TableInfo tableInfo) {
        if (tableInfo == null || CommonUtils.isEmpty(tableInfo.getFieldList())) {
            return null;
        }

        return tableInfo.getFieldList()
                .stream()
                .collect(
                        Collectors.toMap(
                                TableFieldInfo::getColumn,
                                TableFieldInfo::getProperty,
                                (oldVal, newVal) -> newVal
                        )
                );
    }

    protected Map<String, String> getTableColumns(String tableName) {
        if (StrUtils.isBlank(tableName)) {
            return null;
        }
        return getTableColumns(getTableInfo(tableName));
    }

    protected TableInfo getTableInfo(String tableName) {
        if (StrUtils.isBlank(tableName)) {
            return null;
        }

        return TableInfoHelper.getTableInfo(tableName);
    }

    protected boolean hasOperator() {
//        if (!CommonTools.isEnvProduct()) {
//            log.debug(
//                    """
//                            ■■■■■■■■■■ OperatorInterceptor
//                            Thread ID: {},
//                            SideType: {}
//                            MemberType: {}
//                            MemberName: {}
//                            MemberAccount: {}
//                            OrgId: {}
//                            StoreId: {}
//                            ShopId: {}
//                            CacheControl: {}
//                            NoCache: {}
//                            TRACE_ID: {}
//                            REQUEST_IP: {}
//                            APP_NAME: {}
//                            APP_CODE: {}
//                            PLATFORM: {}
//                            SN: {}
//                            FORCE_SUBMIT: {}
//                            OPERATOR_ID: {}
//                            """,
//                    Thread.currentThread().threadId(),
//                    DubboUtils.dubboSideType(),
//                    AuthCommon.getMemberType(),
//                    AuthCommon.getMemberName(),
//                    AuthCommon.getMemberAccount(),
//                    AuthCommon.getOrgId(),
//                    AuthCommon.getStoreId(),
//                    AuthCommon.getShopId(),
//                    AuthCommon.getRequestCacheControlHeader(),
//                    AuthCommon.getRequestNoCacheHeader(),
//                    AuthCommon.traceId(),
//                    AuthCommon.getRequestIp(),
//                    AuthCommon.getAppName(),
//                    AuthCommon.getAppCode(),
//                    AuthCommon.getPlatformName(),
//                    AuthCommon.getDeviceSN(),
//                    AuthCommon.getForceSubmit(),
//                    AuthCommon.getOperatorId()
//            );
//        }
        AuthMemberType memberType = AuthCommon.getMemberType();
        return ObjectUtils.isNotEmpty(memberType) && !memberType.equals(AuthMemberType.none);
    }

    //
    protected List<String> contextInsert() {
        return ModelContext.getInsert();
    }

    protected List<String> contextUpdate() {
        return ModelContext.getUpdate();
    }

    protected List<String> contextDelete() {
        return ModelContext.getDelete();
    }

    //
    protected boolean ignoreAll(List<String> list) {
        if (list == null || CommonUtils.isEmpty(list)) {
            return false;
        }
        return list.contains(Constants.ALL_STRING);
    }

    protected boolean ignoreInsert(String tableName) {
        if (StrUtils.isBlank(tableName)) {
            return true;
        }
        List<String> list = contextInsert();
        if (list == null || CommonUtils.isEmpty(list)) {
            return false;
        }
        return ignoreAll(list)
                || list.contains(tableName.trim().toLowerCase());
    }

    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {

        if (
                ignoreInsert(insert.getTable().getName())
                        || !hasOperator()
        ) {
            return;
        }

        List<Column> columns = insert.getColumns();
        Map<String, String> tableColumns = getTableColumns(insert.getTable().getName());
        if (CommonUtils.isEmpty(tableColumns)) {
            return;
        }

        List<String> tableColumnsList = tableColumns.keySet().stream().toList();
        List<String> insertColumnsList = columns.stream()
                .map(v -> v.getColumnName().trim().toLowerCase())
                .toList();
//        log.debug(
//                "\n■■■■■ [{}] Insert ColumnsList: {}",
//                insert.getTable().getName(),
//                JsonUtil.toJSONString(insertColumnsList)
//        );

        Map<String, Expression> setExpressions = getSetExpressions(
                tableColumnsList,
                insertColumns,
                insert.getTable().getName()
        );

        if (CommonUtils.isEmpty(setExpressions)) {
            return;
        }

        for (Map.Entry<String, Expression> entry : setExpressions.entrySet()) {
            if (
                    StrUtils.isBlank(entry.getKey())
                            || insertColumnsList.contains(entry.getKey().trim().toLowerCase())
            ) {
                continue;
            }
            columns.add(
                    new Column(entry.getKey())
            );
        }

        Values values = insert.getValues();
        try {
            @SuppressWarnings("unchecked")
            ExpressionList<Expression> expressions = (ExpressionList<Expression>) values.getExpressions();
            if (expressions instanceof ParenthesedExpressionList) {
                expressions.addAll(setExpressions.values());
            } else if (CollectionUtils.isNotEmpty(expressions)) {
                int len = expressions.size();

                for (int i = 0; i < len; ++i) {
                    Expression expression = (Expression) expressions.get(i);
                    if (expression instanceof Parenthesis) {
                        RowConstructor<Expression> rowConstructor = new RowConstructor<>();
                        rowConstructor.withExpressions(
                                ((Parenthesis) expression).getExpression()
                        );
                        rowConstructor.addExpressions(setExpressions.values());
                        expressions.set(i, rowConstructor);
                    } else if (expression instanceof ParenthesedExpressionList) {
                        ((ParenthesedExpressionList<Expression>) expression).addExpressions(setExpressions.values());
                    } else {
                        expressions.addExpressions(setExpressions.values());
                    }
                }
            } else {
                expressions.addExpressions(setExpressions.values());
            }
        } catch (Exception e) {
            // log
        }

    }


    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {
        // super.processDelete(delete, index, sql, obj);
    }

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        // super.processSelect(select, index, sql, obj);
    }

    protected boolean ignoreUpdate(
            String tableName,
            List<String> context
    ) {
        if (StrUtils.isBlank(tableName)) {
            return true;
        }

        if (context == null || CommonUtils.isEmpty(context)) {
            return false;
        }
        return ignoreAll(context)
                || context.contains(tableName.trim().toLowerCase());
    }

    protected boolean ignoreDelete(
            String tableName,
            List<String> context
    ) {
        if (StrUtils.isBlank(tableName)) {
            return true;
        }

        if (context == null || CommonUtils.isEmpty(context)) {
            return false;
        }
        return ignoreAll(context)
                || context.contains(tableName.trim().toLowerCase());
    }

    protected boolean ignoreUpdateTable(
            String tableName,
            String updateType,
            List<String> contextUpdate,
            List<String> contextDelete
    ) {
        if (StrUtils.isBlank(tableName)) {
            return true;
        }
        switch (updateType) {
            case Constants.SQL_STATEMENT_UPDATE -> {
                return ignoreUpdate(tableName, contextUpdate);
            }
            case Constants.SQL_STATEMENT_DELETE -> {
                return ignoreDelete(tableName, contextDelete);
            }
            default -> {
                return true;
            }
        }
    }

    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        if (!hasOperator()) {
            return;
        }

        Map<String, String> tableColumns = getTableColumns(update.getTable().getName());
        if (CommonUtils.isEmpty(tableColumns)) {
            return;
        }

        List<UpdateSet> updateSets = update.getUpdateSets();

        if (CommonUtils.isEmpty(updateSets)) {
            return;
        }
        List<String> updateColumnsList = new ArrayList<>();
        for (UpdateSet updateSet : updateSets) {
            if (updateSet.getColumns() == null || updateSet.getColumns().isEmpty()) {
                continue;
            }
            for (Column column : updateSet.getColumns()) {
                updateColumnsList.add(column.getColumnName());
            }
        }

//        log.debug(
//                "\n■■■■■ [{}] Update ColumnsList: {}",
//                update.getTable().getName(),
//                JsonUtil.toJSONString(updateColumnsList)
//        );

        boolean hasDeleted = tableColumns.containsKey(Constants.COLUMNS_DELETED);
        Map<String, Table> updateSetTables = new HashMap<>();
        List<String> updateSetColumns = new ArrayList<>();
        for (UpdateSet updateSet : updateSets) {
            for (Column tmpColumn : updateSet.getColumns()) {
                updateSetColumns.add(tmpColumn.getColumnName());
                if (
                        tmpColumn.getTable() != null
                                && !updateSetTables.containsKey(tmpColumn.getTable().getName())
                ) {
                    updateSetTables.put(tmpColumn.getTable().getName(), tmpColumn.getTable());
                }
            }
        }
        List<String> currentSetColumns = updateColumns;
        String updateType = Constants.SQL_STATEMENT_UPDATE;
        if (hasDeleted && updateSetColumns.contains(Constants.COLUMNS_DELETED)) {
            currentSetColumns = deleteColumns;
            updateType = Constants.SQL_STATEMENT_DELETE;
        }


        List<String> contextUpdate = contextUpdate();
        List<String> contextDelete = contextDelete();
        switch (updateType) {
            case Constants.SQL_STATEMENT_UPDATE -> {
                if (ignoreAll(contextUpdate)) {
                    return;
                }
            }
            case Constants.SQL_STATEMENT_DELETE -> {
                if (ignoreAll(contextDelete)) {
                    return;
                }
            }
            default -> {
                return;
            }
        }

        List<String> tableColumnsList = tableColumns.keySet().stream().toList();

        Map<String, Expression> setExpressions = getSetExpressions(
                tableColumnsList,
                currentSetColumns,
                update.getTable().getName()
        );

        if (CommonUtils.isEmpty(setExpressions)) {
            return;
        }

        for (Map.Entry<String, Expression> entry : setExpressions.entrySet()) {
            if (CommonUtils.isNotEmpty(updateSetTables)) {
                for (Map.Entry<String, Table> upTab : updateSetTables.entrySet()) {
                    if (ignoreUpdateTable(
                            upTab.getValue().getName(),
                            updateType,
                            contextUpdate,
                            contextDelete
                    )) {
                        continue;
                    }
                    UpdateSet updateSet = new UpdateSet();
                    updateSet.add(
                            new Column(
                                    upTab.getValue(),
                                    entry.getKey()
                            ),
                            entry.getValue()
                    );
                    updateSets.add(updateSet);
                }
            } else {
                if (ignoreUpdateTable(
                        update.getTable().getName(),
                        updateType,
                        contextUpdate,
                        contextDelete
                )) {
                    continue;
                }
//                if (
//                        StrUtils.isBlank(entry.getKey())
//                                || updateColumnsList.contains(entry.getKey().trim())
//                ) {
//                    continue;
//                }
                UpdateSet updateSet = new UpdateSet();

                updateSet.add(
                        new Column(entry.getKey()),
                        entry.getValue()
                );
                updateSets.add(updateSet);
            }

        }

    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        SqlCommandType sct = ms.getSqlCommandType();
        if (!interceptorSqlCommandTypes.contains(sct)) {
            return;
        }
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        mpBs.sql(this.parserSingle(mpBs.sql(), mpBs));
    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        // InnerInterceptor.super.beforePrepare(sh, connection, transactionTimeout);
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        if (!interceptorSqlCommandTypes.contains(sct)) {
            return;
        }
        PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
        mpBs.sql(this.parserMulti(mpBs.sql(), mpBs));
    }
}
