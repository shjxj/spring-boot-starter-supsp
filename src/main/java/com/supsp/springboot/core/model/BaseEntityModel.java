package com.supsp.springboot.core.model;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.supsp.springboot.core.annotations.*;
import com.supsp.springboot.core.base.ActionResult;
import com.supsp.springboot.core.base.PagerData;
import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.consts.Constants;
import com.supsp.springboot.core.enums.*;
import com.supsp.springboot.core.exceptions.ExceptionCodes;
import com.supsp.springboot.core.exceptions.ModelException;
import com.supsp.springboot.core.helper.AuthCommon;
import com.supsp.springboot.core.helper.SystemCommon;
import com.supsp.springboot.core.service.IIdGeneratorService;
import com.supsp.springboot.core.service.ISensitiveCryptService;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.NumUtils;
import com.supsp.springboot.core.utils.StrUtils;
import com.supsp.springboot.core.utils.TagUtils;
import com.supsp.springboot.core.vo.*;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public abstract class BaseEntityModel<S extends BaseEntityServiceImpl<M, T>, M extends IEntityMapper<T>, T extends BaseModelEntity<T>> extends BaseModel<T> implements IEntityModel<S, M, T> {

    @Autowired
    protected S service;

    @Autowired
    protected M mapper;

    @Value("${spring.application.name:null}")
    protected String appName;

    @Resource(name = "${supsp.app.id-generator:redisID}")
    protected IIdGeneratorService idGenerator;

    protected Class<T> entityClass;

    protected TableInfo tableInfo;
    protected String tableName;
    protected List<TableFieldInfo> fieldInfos;
    protected HashMap<String, TableFieldInfo> fields;
    protected HashMap<String, String> fieldsProperty;
    protected HashMap<String, String> fieldsColumn;
    protected HashMap<String, String> propertyName;

    protected HashMap<String, Boolean> sensitiveFields;
    protected HashMap<String, String> keywordsFields;

    protected HashMap<String, String> selectColumn;
    protected HashMap<String, String> defaultSelectColumn;
    protected HashMap<String, String> apiSelectColumn;
    protected HashMap<String, String> defaultApiSelectColumn;

    protected DbModel dbModel;
    protected DBEntity dbEntity;
    protected SensitiveData dbSensitive;
    protected Boolean isSensitive;

    protected HashMap<String, String> dataIdMapProperty;
    protected HashMap<String, String> dataIdMapColumn;
    protected String dataIdProperty;
    protected String dataIdColumn;

    @Resource
    protected ISensitiveCryptService cryptService;

    @Resource
    private TagUtils tagUtils;

    protected Owner owner;

    protected String ownerType;

    protected Long ownerId;

    protected String objectName;

    public static List<String> ignoreSelectColumns = Arrays.asList(

            Constants.COLUMNS_CREATED_ORG_ID,
            Constants.COLUMNS_CREATED_ORG_NAME,
            Constants.COLUMNS_CREATED_STORE_ID,
            Constants.COLUMNS_CREATED_STORE_NAME,

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
            Constants.COLUMNS_DELETED_AT,
            Constants.COLUMNS_DELETED_ORG_ID,
            Constants.COLUMNS_DELETED_ORG_NAME,
            Constants.COLUMNS_DELETED_STORE_ID,
            Constants.COLUMNS_DELETED_STORE_NAME
    );

    @PostConstruct
    public void init() {
        entityClass = this.service.getEntityClass();
        tableInfo = SqlHelper.table(this.service.getEntityClass());
        if (ObjectUtils.isEmpty(tableInfo) || entityClass == null) {
            return;
        }

        dbModel = AnnotationUtils.getAnnotation(this.getClass(), DbModel.class);
        dbEntity = AnnotationUtils.getAnnotation(entityClass, DBEntity.class);
        dbSensitive = AnnotationUtils.getAnnotation(entityClass, SensitiveData.class);
        isSensitive = dbSensitive != null;

        this.tableName = tableInfo.getTableName();
        List<TableFieldInfo> fieldInfos = tableInfo.getFieldList();
        if (CommonUtils.isEmpty(fieldInfos)) {
            return;
        }
        fields = new HashMap<>();
        fieldsProperty = new HashMap<>();
        fieldsColumn = new HashMap<>();
        if (
                tableInfo != null
                        && StrUtils.isNotBlank(tableInfo.getKeyColumn())
        ) {
            fieldsProperty.put(
                    tableInfo.getKeyProperty(),
                    tableInfo.getKeyColumn()
            );
            fieldsColumn.put(
                    tableInfo.getKeyColumn(),
                    tableInfo.getKeyProperty()
            );
        }

        for (TableFieldInfo fieldInfo : fieldInfos) {
            fields.put(fieldInfo.getProperty(), fieldInfo);
            fieldsProperty.put(fieldInfo.getProperty(), fieldInfo.getColumn());
            fieldsColumn.put(fieldInfo.getColumn(), fieldInfo.getProperty());
        }

        if (CommonUtils.isEmpty(this.fieldsProperty)) {
            return;
        }

        Field[] classFields = this.entityClass.getDeclaredFields();
        if (ArrayUtils.isEmpty(classFields)) {
            return;
        }

        if (this.propertyName == null) {
            this.propertyName = new HashMap<>();
        }

        if (this.selectColumn == null) {
            this.selectColumn = new HashMap<>();
        }

        if (this.defaultSelectColumn == null) {
            this.defaultSelectColumn = new HashMap<>();
        }

        if (this.apiSelectColumn == null) {
            this.apiSelectColumn = new HashMap<>();
        }

        if (this.defaultApiSelectColumn == null) {
            this.defaultApiSelectColumn = new HashMap<>();
        }

        if (this.dataIdMapProperty == null) {
            this.dataIdMapProperty = new HashMap<>();
        }

        if (this.dataIdMapColumn == null) {
            this.dataIdMapColumn = new HashMap<>();
        }

        this.sensitiveFields = new HashMap<>();
        this.keywordsFields = new HashMap<>();
        for (Field clzField : classFields) {
            TableField tableField = clzField.isAnnotationPresent(TableField.class) ? clzField.getAnnotation(TableField.class) : null;
            if (ObjectUtils.isEmpty(tableField) || !tableField.exist()) {
                continue;
            }
            String currPropertyName = clzField.getName();
            EntityColumn entityColumn = clzField.isAnnotationPresent(EntityColumn.class) ? clzField.getAnnotation(EntityColumn.class) : null;
            SensitiveField sensitiveField = clzField.isAnnotationPresent(SensitiveField.class) ? clzField.getAnnotation(SensitiveField.class) : null;
            String currColumnName = this.fieldsProperty.getOrDefault(currPropertyName, null);
            if (StrUtils.isBlank(currPropertyName) || StrUtils.isBlank(currColumnName)) {
                continue;
            }

            if (isSensitive && sensitiveField != null) {
                this.sensitiveFields.put(
                        currColumnName,
                        sensitiveField.db()
                );
            }

            JsonProperty jsonProperty = clzField.isAnnotationPresent(JsonProperty.class) ? clzField.getAnnotation(JsonProperty.class) : null;
            String jsonName = null;
            if (jsonProperty != null) {
                jsonName = jsonProperty.value();
            }

            this.propertyName.put(
                    StrUtils.isNotBlank(jsonName) ? jsonName : currPropertyName,
                    currPropertyName
            );

            boolean doSelect = true;
            boolean defaultSelect = true;
            boolean doApiSelect = true;
            boolean defaultApiSelect = true;
            if (entityColumn != null) {
//                                || entityColumn.isCode()
//                        || entityColumn.isDataId()
                if (
                        entityColumn.isKeywords()
                                || entityColumn.isTitle()
                ) {
                    this.keywordsFields.put(
                            currColumnName,
                            currPropertyName
                    );
                }
                doSelect = entityColumn.select();
                defaultSelect = entityColumn.defaultSelect();
                if (!doSelect) {
                    defaultSelect = false;
                }

                doApiSelect = entityColumn.apiSelect();
                if (!doSelect) {
                    doApiSelect = false;
                }

                defaultApiSelect = entityColumn.defaultApiSelect();
                if (!doApiSelect) {
                    defaultApiSelect = false;
                }
            }

            if (doSelect) {
                this.selectColumn.put(currPropertyName, currColumnName);
            }
            if (defaultSelect) {
                this.defaultSelectColumn.put(currPropertyName, currColumnName);
            }

            if (doApiSelect) {
                this.apiSelectColumn.put(currPropertyName, currColumnName);
            }
            if (defaultApiSelect) {
                this.defaultApiSelectColumn.put(currPropertyName, currColumnName);
            }

            if (!clzField.isAnnotationPresent(DataId.class)) {
                continue;
            }

            DataId currDataId = clzField.getAnnotation(DataId.class);
            if (currDataId == null) {
                continue;
            }
            if (
                    !this.fieldsProperty.containsKey(currPropertyName)
            ) {
                continue;
            }

            if (StrUtils.isBlank(dataIdProperty)) {
                this.dataIdProperty = currPropertyName;
                this.dataIdColumn = currColumnName;
            }

            this.dataIdMapProperty.put(
                    currPropertyName,
                    currColumnName
            );

            this.dataIdMapColumn.put(
                    currColumnName,
                    currPropertyName
            );
        }

        if (
                CommonUtils.isNotEmpty(this.dataIdMapProperty)
                        && this.dataIdMapProperty.size() > 1
        ) {
            this.dataIdProperty = null;
            this.dataIdColumn = null;
        }
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }


    @Override
    public TableInfo getTableInfo() {
        return tableInfo;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String tmpOperationName(String operation) {
        if (StrUtils.isBlank(operation)) {
            operation = Constants.BASE_OPERATION;
        }
        return SystemCommon.getModuleName()
                + Constants.TEXT_SPLIT
                + operation.trim()
                + Constants.TEXT_SPLIT
                + getTableName();
    }

    @Override
    public String tmpOperationCreateName() {
        return tmpOperationName(Constants.OPERATION_EDIT);
    }

    @Override
    public String tmpOperationEditName() {
        return tmpOperationName(Constants.OPERATION_EDIT);
    }

    @Override
    public List<TableFieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    @Override
    public HashMap<String, TableFieldInfo> getFields() {
        return fields;
    }

    @Override
    public HashMap<String, String> getFieldsProperty() {
        return fieldsProperty;
    }

    @Override
    public HashMap<String, String> getFieldsColumn() {
        return fieldsColumn;
    }

    @Override
    public HashMap<String, String> getPropertyName() {
        return propertyName;
    }

    @Override
    public HashMap<String, Boolean> getSensitiveFields() {
        return sensitiveFields;
    }

    @Override
    public void setSensitiveFields(HashMap<String, Boolean> sensitiveFields) {
        this.sensitiveFields = sensitiveFields;
    }

    @Override
    public HashMap<String, String> getKeywordsFields() {
        return keywordsFields;
    }

    @Override
    public void setKeywordsFields(HashMap<String, String> keywordsFields) {
        this.keywordsFields = keywordsFields;
    }


    @Override
    public HashMap<String, String> getSelectColumn() {
        return selectColumn;
    }

    @Override
    public HashMap<String, String> getDefaultSelectColumn() {
        return defaultSelectColumn;
    }

    @Override
    public HashMap<String, String> getApiSelectColumn() {
        return apiSelectColumn;
    }

    @Override
    public HashMap<String, String> getDefaultApiSelectColumn() {
        return defaultApiSelectColumn;
    }

    @Override
    public boolean isFieldProperty(String property) {
        if (
                CommonUtils.isEmpty(getFieldsProperty())
                        || StrUtils.isBlank(property)
        ) {
            return false;
        }
        return getFieldsProperty().containsKey(property);
    }

    @Override
    public boolean isFieldColumn(String column) {
        if (
                CommonUtils.isEmpty(getFieldsColumn())
                        || StrUtils.isBlank(column)
        ) {
            return false;
        }
        return getFieldsColumn().containsKey(column);
    }

    @Override
    public String getFieldColumnByProperty(String property) {
        if (
                CommonUtils.isEmpty(getFieldsProperty())
                        || StrUtils.isBlank(property)
                        || !getFieldsProperty().containsKey(property)
        ) {
            return null;
        }
        return getFieldsProperty().get(property);
    }

    @Override
    public String getFieldPropertyByColumn(String column) {
        if (
                CommonUtils.isEmpty(getFieldsColumn())
                        || StrUtils.isBlank(column)
                        || !getFieldsColumn().containsKey(column)
        ) {
            return null;
        }
        return getFieldsColumn().get(column);
    }

    @Override
    public <X> String getFieldPropertyFromLambda(Func1<X, ?> func) {
        if (func == null) {
            return null;
        }
        String property = null;
        try {
            return LambdaUtil.getFieldName(func);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <X> String getFieldColumnFromLambda(Func1<X, ?> func) {
        String property = getFieldPropertyFromLambda(func);
        if (StrUtils.isBlank(property)) {
            return null;
        }
        return getFieldColumnByProperty(property);
    }

    @Override
    public TableFieldInfo getFieldInfoByProperty(String property) {
        if (
                CommonUtils.isEmpty(getFields())
                        || StrUtils.isBlank(property)
                        || !getFields().containsKey(property)
        ) {
            return null;
        }
        return getFields().get(property);
    }

    @Override
    public TableFieldInfo getFieldInfoByColumn(String column) {
        return this.getFieldInfoByProperty(
                this.getFieldPropertyByColumn(column)
        );
    }

    @Override
    public DBEntity getDbEntity() {
        return dbEntity;
    }

    @Override
    public SensitiveData getDbSensitive() {
        return dbSensitive;
    }

    @Override
    public void setDbSensitive(SensitiveData dbSensitive) {
        this.dbSensitive = dbSensitive;
    }

    @Override
    public Boolean getSensitive() {
        return isSensitive;
    }

    @Override
    public void setSensitive(Boolean sensitive) {
        isSensitive = sensitive;
    }

    @Override
    public HashMap<String, String> getDataIdMapProperty() {
        return dataIdMapProperty;
    }

    @Override
    public HashMap<String, String> getDataIdMapColumn() {
        return dataIdMapColumn;
    }

    @Override
    public String getDataIdProperty() {
        return dataIdProperty;
    }

    @Override
    public String getDataIdColumn() {
        return dataIdColumn;
    }

    @Override
    public boolean hasColumn(String column) {
        if (
                StrUtils.isBlank(column)
                        || CommonUtils.isEmpty(this.fieldsColumn)
        ) {
            return false;
        }
//        if (this.fieldsColumn.containsKey(column.trim())) {
//            return true;
//        }
//        if (
//                this.tableInfo != null
//                        && StrUtils.isNotBlank(this.tableInfo.getKeyColumn())
//        ) {
//            return column.trim().equalsIgnoreCase(this.tableInfo.getKeyColumn());
//        }
//        return false;
        return this.fieldsColumn.containsKey(column.trim());
    }

    @Override
    public boolean hasProperty(String property) {
        if (
                StrUtils.isBlank(property)
                        || CommonUtils.isEmpty(this.fieldsProperty)
        ) {
            return false;
        }
//        if (this.fieldsProperty.containsKey(property.trim())) {
//            return true;
//        }
//        if (
//                this.tableInfo != null
//                        && StrUtils.isNotBlank(this.tableInfo.getKeyProperty())
//        ) {
//            return property.trim().equalsIgnoreCase(this.tableInfo.getKeyProperty());
//        }
//        return false;
        return this.fieldsProperty.containsKey(property.trim());
    }

    @Override
    public void setProperty(Object bean, String name, Object value) {
        if (
                bean == null
                        || StrUtils.isBlank(name)
                        || bean.getClass() != this.entityClass
                        || CommonUtils.isEmpty(this.fieldsProperty)
                        || !this.fieldsProperty.containsKey(name)
        ) {
            return;
        }
        try {
            BeanUtil.setProperty(bean, name, value);
        } catch (Exception e) {
            // log.error
        }
    }

    /* -------------- owner -------------- */
    @Override
    public Owner getOwner() {
        if (ObjectUtils.isNotEmpty(owner)) {
            return owner;
        }
        this.setOwner(
                Owner.builder()
                        .ownerType(this.getOwnerType())
                        .ownerId(this.getOwnerId())
                        .build()
        );
        return owner;
    }

    @Override
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String getOwnerType() {
        if (StrUtils.isNotBlank(ownerType)) {
            return ownerType;
        }
        SysModule sysModule = SystemCommon.getModule();
        switch (sysModule) {
            case admin -> {
                this.setOwnerType(Constants.OWNER_TYPE_SYSTEM);
            }
            case tenant -> {
                this.setOwnerType(Constants.OWNER_TYPE_TENANT);
            }
            case merchant -> {
                this.setOwnerType(Constants.OWNER_TYPE_MERCHANT);
            }
            case consumer -> {
                this.setOwnerType(Constants.OWNER_TYPE_CONSUMER);
            }
            case api -> {
                this.setOwnerType(Constants.OWNER_TYPE_STORE);
            }
            case null, default -> {
                this.setOwnerType(null);
            }
        }
        return ownerType;
    }

    @Override
    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    @Override
    public Long getOwnerId() {
        if (ObjectUtils.isNotEmpty(ownerId)) {
            return ownerId;
        }
        SysModule sysModule = SystemCommon.getModule();
        switch (sysModule) {
            case admin -> {
                this.setOwnerId(Constants.LONG_ZERO);
            }
            case tenant -> {
                this.setOwnerId(AuthCommon.getOrgId());
            }
            case merchant -> {
                this.setOwnerId(AuthCommon.getStoreId());
            }
            case consumer -> {
                this.setOwnerId(AuthCommon.getMemberId());
            }
            case api -> {
                switch (this.getOwnerType()) {
                    case Constants.OWNER_TYPE_ORG -> {
                        this.setOwnerId(AuthCommon.getOrgId());
                    }
                    case Constants.OWNER_TYPE_STORE -> {
                        this.setOwnerId(AuthCommon.getStoreId());
                    }
                    case Constants.OWNER_TYPE_SHOP -> {
                        this.setOwnerId(AuthCommon.getShopId());
                    }
                    default -> {
                        // case Constants.OWNER_TYPE_STORE, null,
                        this.setOwnerId(AuthCommon.getStoreId());
                    }
                }
            }
            case null, default -> {
                this.setOwnerId(null);
            }
        }
        return ownerId;
    }

    @Override
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String getObjectName() {
        if (StrUtils.isNotBlank(objectName)) {
            return objectName;
        }
        String objName = this.getTableName();
        if (StrUtils.isNotBlank(objName)) {
            String[] arr = objName.split("_");
            if (arr.length > 0) {
                arr = ArrayUtils.remove(arr, 0);
            }
            objName = StrUtils.join(Arrays.asList(arr), "_");
        }
        this.setObjectName(objName);
        return objectName;
    }

    @Override
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /* -------------- 缓存相关 -------------- */

    /**
     * 缓存 key
     *
     * @param name
     * @return
     */
    @Override
    public String cacheKey(@NotBlank String name) {
        return CommonUtils.cacheKeyName(
                this.appName,
                this.getTableName(),
                name
        );
    }

    @Override
    public String cacheKey(@NotBlank String name, long id) {
        if (CommonUtils.isNotID(id)) {
            return null;
        }
        return this.cacheKey(name + "::" + id);
    }

    @Override
    public String cacheKey(@NotBlank String name, Long id) {
        return cacheKey(name, CommonUtils.getID(id));
    }

    @Override
    public String allCacheKey() {
        return this.cacheKey("all");
    }

    /**
     * data 缓存 key
     *
     * @param id
     * @return
     */
    @Override
    public String dataCacheKey(long id) {
        if (CommonUtils.isNotID(id)) {
            return null;
        }
        return this.cacheKey("data", id);
    }

    @Override
    public String dataCacheKey(Long id) {
        return dataCacheKey(CommonUtils.getID(id));
    }

    @Override
    public Long countById(Long id) {
        if (CommonUtils.isNotID(id)) {
            return null;
        }
        if (StrUtils.isBlank(this.getDataIdColumn())) {
            return null;
        }
        QueryWrapper<T> queryWrapper = this.getQueryWrapper();
        queryWrapper.eq(this.getDataIdColumn(), id);
        return this.service.count(queryWrapper);
    }

    @Override
    public boolean idExists(Long id) {
        return NumUtils.gtZero(countById(id));
    }

    @Override
    public long applyID() {
        return idGenerator.id(appName, getTableName());
    }

    @Override
    public LambdaQueryWrapper<T> getLambdaQueryWrapper() {
        return new LambdaQueryWrapper<>();
    }

    @Override
    public QueryWrapper<T> getQueryWrapper() {
        return new QueryWrapper<>();
    }

    @Override
    public LambdaUpdateWrapper<T> getLambdaUpdateWrapper() {
        return new LambdaUpdateWrapper<>();
    }

    @Override
    public UpdateWrapper<T> getUpdateWrapper() {
        return new UpdateWrapper<>();
    }

    @Override
    public <E> MPJLambdaWrapper<E> getMPJLambdaWrapper(Class<E> clz) {
        return new MPJLambdaWrapper<>(clz);
    }

    @Override
    public MPJLambdaWrapper<T> getMPJLambdaWrapper() {
        return new MPJLambdaWrapper<T>(this.entityClass);
    }

    @Override
    public MPJLambdaWrapper<T> getMPJLambdaWrapper(String alias) {
        return new MPJLambdaWrapper<T>(this.entityClass, alias);
    }

    public String aliasColumn(
            String alias,
            String column
    ) {
        if (StringUtils.isBlank(column)) {
            return Constants.STRING_EMPTY;
        }
        if (StringUtils.isBlank(alias)) {
            return column.trim();
        }
        return alias.trim() + "." + column.trim();
    }

    public String aliasColumn(
            String column
    ) {
        return aliasColumn(Constants.BASE_TABLE_ALIAS, column);
    }

    protected void paramFieldQueryWrapper(
            QueryWrapper<T> wrapper,
            String column,
            QueryType type,
            Object value,
            SensitiveField sensitiveField
    ) {
        if (ObjectUtils.isEmpty(wrapper) || StrUtils.isEmpty(column) || ObjectUtils.isEmpty(value)) {
            return;
        }
        try {
            String sqlColumn = column;
            if (
                    ObjectUtils.isNotEmpty(sensitiveField)
                            && sensitiveField.db()
            ) {
                sqlColumn = sensitiveColumn(column);
            }
            switch (type) {
                case custom -> {
                }
                case sensitive -> {
                    wrapper.like(sqlColumn, value);
                }
                case ne -> {
                    wrapper.ne(sqlColumn, value);
                }
                case gt -> {
                    wrapper.gt(sqlColumn, value);
                }
                case ge -> {
                    wrapper.ge(sqlColumn, value);
                }
                case lt -> {
                    wrapper.lt(sqlColumn, value);
                }
                case le -> {
                    wrapper.le(sqlColumn, value);
                }
//                case between -> {
//                    wrapper.between(sqlColumn, value);
//                }
//                case notBetween -> {
//                    wrapper.notBetween(sqlColumn, value);
//                }
                case like -> {
                    wrapper.like(sqlColumn, value);
                }
                case notLike -> {
                    wrapper.notLike(sqlColumn, value);
                }
                case likeLeft -> {
                    wrapper.likeLeft(sqlColumn, value);
                }
                case notLikeLeft -> {
                    wrapper.notLikeLeft(sqlColumn, value);
                }
                case likeRight -> {
                    wrapper.likeRight(sqlColumn, value);
                }
                case notLikeRight -> {
                    wrapper.notLikeRight(sqlColumn, value);
                }
                case isNull -> {
                    wrapper.isNull(column);
                }
                case isNotNull -> {
                    wrapper.isNotNull(column);
                }
                case in -> {
                    wrapper.in(sqlColumn, value);
                }
                case notIn -> {
                    wrapper.notIn(sqlColumn, value);
                }
                case inSql -> {
                    wrapper.inSql(sqlColumn, value.toString());
                }
                case notInSql -> {
                    wrapper.notInSql(sqlColumn, value.toString());
                }
                case exists -> {
                    wrapper.exists(sqlColumn, value);
                }
                case notExists -> {
                    wrapper.notExists(sqlColumn, value);
                }
                default -> {
                    wrapper.eq(sqlColumn, value);
                }
            }
        } catch (Exception e) {
            // log
        }
    }

    protected void paramFieldQueryWrapper(
            MPJLambdaWrapper<T> wrapper,
            String alias,
            String column,
            QueryType type,
            Object value,
            SensitiveField sensitiveField
    ) {
        if (ObjectUtils.isEmpty(wrapper) || StrUtils.isEmpty(column) || ObjectUtils.isEmpty(value)) {
            return;
        }
        try {
            String sqlColumn = aliasColumn(alias, column);
            if (
                    ObjectUtils.isNotEmpty(sensitiveField)
                            && sensitiveField.db()
            ) {
                sqlColumn = sensitiveColumn(column);
            }
            switch (type) {
                case ne -> {
                    wrapper.ne(sqlColumn, value);
                }
                case gt -> {
                    wrapper.gt(sqlColumn, value);
                }
                case ge -> {
                    wrapper.ge(sqlColumn, value);
                }
                case lt -> {
                    wrapper.lt(sqlColumn, value);
                }
                case le -> {
                    wrapper.le(sqlColumn, value);
                }
//                case between -> {
//                    wrapper.between(sqlColumn, value);
//                }
//                case notBetween -> {
//                    wrapper.notBetween(sqlColumn, value);
//                }
                case like -> {
                    wrapper.like(sqlColumn, value);
                }
                case notLike -> {
                    wrapper.notLike(sqlColumn, value);
                }
                case likeLeft -> {
                    wrapper.likeLeft(sqlColumn, value);
                }
                case notLikeLeft -> {
                    wrapper.notLikeLeft(sqlColumn, value);
                }
                case likeRight -> {
                    wrapper.likeRight(sqlColumn, value);
                }
                case notLikeRight -> {
                    wrapper.notLikeRight(sqlColumn, value);
                }
                case isNull -> {
                    wrapper.isNull(column);
                }
                case isNotNull -> {
                    wrapper.isNotNull(column);
                }
                case in -> {
                    wrapper.in(sqlColumn, value);
                }
                case notIn -> {
                    wrapper.notIn(sqlColumn, value);
                }
                case inSql -> {
                    wrapper.inSql(sqlColumn, value.toString());
                }
                case notInSql -> {
                    wrapper.notInSql(sqlColumn, value.toString());
                }
                case exists -> {
                    wrapper.exists(sqlColumn, value);
                }
                case notExists -> {
                    wrapper.notExists(sqlColumn, value);
                }
                default -> {
                    wrapper.eq(sqlColumn, value);
                }
            }
        } catch (Exception e) {
            // log
        }
    }

    protected void paramFieldQueryWrapper(
            MPJLambdaWrapper<T> wrapper,
            String column,
            QueryType type,
            Object value,
            SensitiveField sensitiveField
    ) {
        paramFieldQueryWrapper(
                wrapper,
                Constants.BASE_TABLE_ALIAS,
                column,
                type,
                value,
                sensitiveField
        );
    }

    protected void scopNoneQueryWrapper(
            QueryWrapper<T> wrapper,
            Scope scope
    ) {
        if (
                ObjectUtils.isNotEmpty(this.dbModel)
                        && this.dbModel.scope() != null
                        && !this.dbModel.scope().equals(DataScope.True)
        ) {
            return;
        }
    }

    protected void scopeNoneQueryWrapper(
            MPJLambdaWrapper<T> wrapper,
            String alias,
            Scope scope
    ) {
        if (
                ObjectUtils.isNotEmpty(this.dbModel)
                        && this.dbModel.scope() != null
                        && !this.dbModel.scope().equals(DataScope.True)
        ) {
            return;
        }
    }

    protected void scopeNoneQueryWrapper(
            MPJLambdaWrapper<T> wrapper,
            Scope scope
    ) {
        scopeNoneQueryWrapper(wrapper, Constants.BASE_TABLE_ALIAS, scope);
    }

    protected void scopeAdminQueryWrapper(
            QueryWrapper<T> wrapper,
            Scope scope
    ) {
        if (
                ObjectUtils.isNotEmpty(this.dbModel)
                        && this.dbModel.scope() != null
                        && !this.dbModel.scope().equals(DataScope.True)
        ) {
            return;
        }
        OrgAuthority authority = scope.getOrgAuthority();
        if (ObjectUtils.isEmpty(authority)) {
            authority = OrgAuthority.none;
        }

        if (authority.equals(OrgAuthority.global)) {
            return;
        }

        switch (authority) {
            case region -> {
                if (
                        this.isFieldColumn("province")
                                && CommonUtils.isID(scope.getProvince())
                ) {
                    wrapper.eq("province", scope.getProvince());
                }
                if (
                        this.isFieldColumn("city")
                                && CommonUtils.isID(scope.getCity())
                ) {
                    wrapper.eq("city", scope.getCity());
                }
                if (
                        this.isFieldColumn("district")
                                && CommonUtils.isID(scope.getDistrict())
                ) {
                    wrapper.eq("district", scope.getDistrict());
                }
                if (
                        this.isFieldColumn("street")
                                && CommonUtils.isID(scope.getStreet())
                ) {
                    wrapper.eq("street", scope.getStreet());
                }
                if (
                        this.isFieldColumn("community")
                                && CommonUtils.isID(scope.getCommunity())
                ) {
                    wrapper.eq("community", scope.getCommunity());
                }
            }
            case org -> {
                if (this.isFieldColumn("org_id")) {
                    wrapper.eq("org_id", scope.getOrgId());
                }
            }
            case store -> {
                if (this.isFieldColumn("store_id")) {
                    wrapper.eq("store_id", scope.getStoreId());
                }
            }
        }
    }

    protected void scopeAdminQueryWrapper(
            MPJLambdaWrapper<T> wrapper,
            String alias,
            Scope scope
    ) {
        if (
                ObjectUtils.isNotEmpty(this.dbModel)
                        && this.dbModel.scope() != null
                        && !this.dbModel.scope().equals(DataScope.True)
        ) {
            return;
        }
        OrgAuthority authority = scope.getOrgAuthority();
        if (ObjectUtils.isEmpty(authority)) {
            authority = OrgAuthority.none;
        }

        if (authority.equals(OrgAuthority.global)) {
            return;
        }

        switch (authority) {
            case region -> {
                if (
                        this.isFieldColumn("province")
                                && CommonUtils.isID(scope.getProvince())
                ) {
                    wrapper.eq(aliasColumn(alias, "province"), scope.getProvince());
                }
                if (
                        this.isFieldColumn("city")
                                && CommonUtils.isID(scope.getCity())
                ) {
                    wrapper.eq(aliasColumn(alias, "city"), scope.getCity());
                }
                if (
                        this.isFieldColumn("district")
                                && CommonUtils.isID(scope.getDistrict())
                ) {
                    wrapper.eq(aliasColumn(alias, "district"), scope.getDistrict());
                }
                if (
                        this.isFieldColumn("street")
                                && CommonUtils.isID(scope.getStreet())
                ) {
                    wrapper.eq(aliasColumn(alias, "street"), scope.getStreet());
                }
                if (
                        this.isFieldColumn("community")
                                && CommonUtils.isID(scope.getCommunity())
                ) {
                    wrapper.eq(aliasColumn(alias, "community"), scope.getCommunity());
                }
            }
            case org -> {
                if (this.isFieldColumn("org_id")) {
                    wrapper.eq(aliasColumn(alias, "org_id"), scope.getOrgId());
                }
            }
            case store -> {
                if (this.isFieldColumn("store_id")) {
                    wrapper.eq(aliasColumn(alias, "store_id"), scope.getStoreId());
                }
            }
        }
    }

    protected void scopeAdminQueryWrapper(
            MPJLambdaWrapper<T> wrapper,
            Scope scope
    ) {
        scopeAdminQueryWrapper(wrapper, Constants.BASE_TABLE_ALIAS, scope);
    }

    protected void scopeUserQueryWrapper(
            QueryWrapper<T> wrapper,
            Scope scope
    ) {
        if (
                ObjectUtils.isNotEmpty(this.dbModel)
                        && this.dbModel.scope() != null
                        && !this.dbModel.scope().equals(DataScope.True)
        ) {
            return;
        }
        OrgAuthority authority = scope.getOrgAuthority();
        if (ObjectUtils.isEmpty(authority)) {
            authority = OrgAuthority.none;
        }

        if (authority.equals(OrgAuthority.global)) {
            return;
        }

        switch (authority) {
            case region -> {
                if (
                        this.isFieldColumn("province")
                                && CommonUtils.isID(scope.getProvince())
                ) {
                    wrapper.eq("province", scope.getProvince());
                }
                if (
                        this.isFieldColumn("city")
                                && CommonUtils.isID(scope.getCity())
                ) {
                    wrapper.eq("city", scope.getCity());
                }
                if (
                        this.isFieldColumn("district")
                                && CommonUtils.isID(scope.getDistrict())
                ) {
                    wrapper.eq("district", scope.getDistrict());
                }
                if (
                        this.isFieldColumn("street")
                                && CommonUtils.isID(scope.getStreet())
                ) {
                    wrapper.eq("street", scope.getStreet());
                }
                if (
                        this.isFieldColumn("community")
                                && CommonUtils.isID(scope.getCommunity())
                ) {
                    wrapper.eq("community", scope.getCommunity());
                }
            }
            case org -> {
                if (this.isFieldColumn("org_id")) {
                    wrapper.eq("org_id", scope.getOrgId());
                }
            }
            case store -> {
                if (this.isFieldColumn("store_id")) {
                    wrapper.eq("store_id", scope.getStoreId());
                }
            }
        }
    }

    protected void scopeUserQueryWrapper(
            MPJLambdaWrapper<T> wrapper,
            String alias,
            Scope scope
    ) {
        if (
                ObjectUtils.isNotEmpty(this.dbModel)
                        && this.dbModel.scope() != null
                        && !this.dbModel.scope().equals(DataScope.True)
        ) {
            return;
        }
        OrgAuthority authority = scope.getOrgAuthority();
        if (ObjectUtils.isEmpty(authority)) {
            authority = OrgAuthority.none;
        }

        if (authority.equals(OrgAuthority.global)) {
            return;
        }

        switch (authority) {
            case region -> {
                if (
                        this.isFieldColumn("province")
                                && CommonUtils.isID(scope.getProvince())
                ) {
                    wrapper.eq(aliasColumn(alias, "province"), scope.getProvince());
                }
                if (
                        this.isFieldColumn("city")
                                && CommonUtils.isID(scope.getCity())
                ) {
                    wrapper.eq(aliasColumn(alias, "city"), scope.getCity());
                }
                if (
                        this.isFieldColumn("district")
                                && CommonUtils.isID(scope.getDistrict())
                ) {
                    wrapper.eq(aliasColumn(alias, "district"), scope.getDistrict());
                }
                if (
                        this.isFieldColumn("street")
                                && CommonUtils.isID(scope.getStreet())
                ) {
                    wrapper.eq(aliasColumn(alias, "street"), scope.getStreet());
                }
                if (
                        this.isFieldColumn("community")
                                && CommonUtils.isID(scope.getCommunity())
                ) {
                    wrapper.eq(aliasColumn(alias, "community"), scope.getCommunity());
                }
            }
            case org -> {
                if (this.isFieldColumn("org_id")) {
                    wrapper.eq(aliasColumn(alias, "org_id"), scope.getOrgId());
                }
            }
            case store -> {
                if (this.isFieldColumn("store_id")) {
                    wrapper.eq(aliasColumn(alias, "store_id"), scope.getStoreId());
                }
            }
        }
    }

    protected void scopeUserQueryWrapper(
            MPJLambdaWrapper<T> wrapper,
            Scope scope
    ) {
        scopeUserQueryWrapper(wrapper, Constants.BASE_TABLE_ALIAS, scope);
    }

    @Override
    public void queryWrapperTags(
            QueryWrapper<T> wrapper,
            @Nullable IEntityParams<T> params
    ) {
        if (
                ObjectUtils.isEmpty(wrapper)
                        || this.getDbModel() == null
                        || !this.getDbModel().tag()
                        || StrUtils.isBlank(this.getDataIdColumn())
                        || CommonUtils.isEmpty(this.getFieldsColumn())
                        || ObjectUtils.isEmpty(params)
                        || CommonUtils.isEmpty(params.getTags())
        ) {
            return;
        }

        Owner paramsOwner = params.getOwner();
        if (ObjectUtils.isEmpty(paramsOwner)) {
            paramsOwner = this.getOwner();
        }

        List<Long> ids = null;
        try {
            if (
                    ObjectUtils.isNotEmpty(paramsOwner)
                            || StrUtils.isNotBlank(paramsOwner.getOwnerType())
                            || ObjectUtils.isNotEmpty(paramsOwner.getOwnerId())
            ) {
                ids = this.getObjects(params.getTags(), paramsOwner);
            }
        } catch (Exception e) {
            // log
        }
        if (CommonUtils.isEmpty(ids)) {
            ids = List.of(Constants.LONG_LOWER);
        }
        wrapper.in(this.getDataIdColumn(), ids);
    }

    @Override
    public void queryWrapperTags(
            MPJLambdaWrapper<T> wrapper,
            String alias,
            @Nullable IEntityParams<T> params
    ) {
        if (
                ObjectUtils.isEmpty(wrapper)
                        || this.getDbModel() == null
                        || !this.getDbModel().tag()
                        || StrUtils.isBlank(this.getDataIdColumn())
                        || CommonUtils.isEmpty(this.getFieldsColumn())
                        || ObjectUtils.isEmpty(params)
                        || CommonUtils.isEmpty(params.getTags())
        ) {
            return;
        }

        Owner paramsOwner = params.getOwner();
        if (ObjectUtils.isEmpty(paramsOwner)) {
            paramsOwner = this.getOwner();
        }

        List<Long> ids = null;
        try {
            if (
                    ObjectUtils.isNotEmpty(paramsOwner)
                            || StrUtils.isNotBlank(paramsOwner.getOwnerType())
                            || ObjectUtils.isNotEmpty(paramsOwner.getOwnerId())
            ) {
                ids = this.getObjects(params.getTags(), paramsOwner);
            }
        } catch (Exception e) {
            // log
        }
        if (CommonUtils.isEmpty(ids)) {
            ids = List.of(Constants.LONG_LOWER);
        }
        wrapper.in(aliasColumn(alias, this.getDataIdColumn()), ids);
    }

    @Override
    public void queryWrapperTags(
            MPJLambdaWrapper<T> wrapper,
            @Nullable IEntityParams<T> params
    ) {
        queryWrapperTags(wrapper, Constants.BASE_TABLE_ALIAS, params);
    }

    @Override
    public void scopeQueryWrapper(
            QueryWrapper<T> wrapper
    ) {
        if (ObjectUtils.isEmpty(wrapper)) {
            return;
        }
        if (
                ObjectUtils.isNotEmpty(this.dbModel)
                        && this.dbModel.scope() != null
                        && !this.dbModel.scope().equals(DataScope.True)
        ) {
            return;
        }
        Scope scope = AuthCommon.getAuthScope();
        if (ObjectUtils.isEmpty(scope)) {
            scopNoneQueryWrapper(wrapper, scope);
            return;
        }
        AuthMemberType memberType = scope.getMemberType();
        if (ObjectUtils.isEmpty(memberType)) {
            memberType = AuthMemberType.none;
        }
        switch (memberType) {
            case admin -> {
                scopeAdminQueryWrapper(wrapper, scope);
            }
            case api -> {
                scopeUserQueryWrapper(wrapper, scope);
            }
            default -> {
                scopNoneQueryWrapper(wrapper, scope);
            }
        }
    }

    @Override
    public void scopeQueryWrapper(
            MPJLambdaWrapper<T> wrapper,
            String alias
    ) {
        if (ObjectUtils.isEmpty(wrapper)) {
            return;
        }
        if (
                ObjectUtils.isNotEmpty(this.dbModel)
                        && this.dbModel.scope() != null
                        && !this.dbModel.scope().equals(DataScope.True)
        ) {
            return;
        }
        Scope scope = AuthCommon.getAuthScope();
        if (ObjectUtils.isEmpty(scope)) {
            scopeNoneQueryWrapper(wrapper, alias, scope);
            return;
        }
        AuthMemberType memberType = scope.getMemberType();
        if (ObjectUtils.isEmpty(memberType)) {
            memberType = AuthMemberType.none;
        }
        switch (memberType) {
            case admin -> {
                scopeAdminQueryWrapper(wrapper, alias, scope);
            }
            case api -> {
                scopeUserQueryWrapper(wrapper, alias, scope);
            }
            default -> {
                scopeNoneQueryWrapper(wrapper, alias, scope);
            }
        }
    }

    @Override
    public void scopeQueryWrapper(
            MPJLambdaWrapper<T> wrapper
    ) {
        scopeQueryWrapper(wrapper, Constants.BASE_TABLE_ALIAS);
    }

    @Override
    public void queryWrapperIds(
            QueryWrapper<T> wrapper,
            @Nullable IEntityParams<T> params
    ) {
        if (
                ObjectUtils.isEmpty(wrapper)
                        || StrUtils.isBlank(dataIdColumn)
                        || ObjectUtils.isEmpty(params)
                        || params.getIds() == null
                        || CommonUtils.isEmpty(params.getIds())
        ) {
            return;
        }

        wrapper.in(dataIdColumn, params.getIds());

    }

    @Override
    public void queryWrapperIds(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityParams<T> params) {
        if (
                ObjectUtils.isEmpty(wrapper)
                        || StrUtils.isBlank(dataIdColumn)
                        || ObjectUtils.isEmpty(params)
                        || params.getIds() == null
                        || CommonUtils.isEmpty(params.getIds())
        ) {
            return;
        }

        wrapper.in(aliasColumn(alias, dataIdColumn), params.getIds());
    }

    @Override
    public void queryWrapperIds(MPJLambdaWrapper<T> wrapper, @Nullable IEntityParams<T> params) {
        queryWrapperIds(wrapper, Constants.BASE_TABLE_ALIAS, params);
    }

    @Override
    public void queryWrapperKeywords(
            QueryWrapper<T> wrapper,
            @Nullable IEntityParams<T> params
    ) {
        if (
                ObjectUtils.isEmpty(wrapper)
                        || CommonUtils.isEmpty(this.getFieldsColumn())
                        || ObjectUtils.isEmpty(params)

        ) {
            return;
        }
        if (
                (
                        keywordsFields == null
                                || CommonUtils.isEmpty(keywordsFields)
                                || StrUtils.isBlank(params.getKeywords())
                )
                        && (
                        StrUtils.isBlank(dataIdColumn)
                                || params.getWithIds() == null
                                || CommonUtils.isEmpty(params.getWithIds()
                        )
                )
        ) {
            return;
        }

        wrapper.nested(i -> {
            boolean hasNested = false;
            if (
                    StrUtils.isNotBlank(dataIdColumn)
                            && params.getWithIds() != null
                            && CommonUtils.isNotEmpty(params.getWithIds())
            ) {
                hasNested = true;
                i.in(dataIdColumn, params.getWithIds());
            }
            if (StrUtils.isNotBlank(params.getKeywords())) {
                String kwd = params.getKeywords().trim();
                for (String col : keywordsFields.keySet()) {
                    String column = col.trim();
                    if (CommonUtils.isTrue(columnSensitive(col))) {
                        column = sensitiveColumn(column);
                    }
                    if (!hasNested) {
                        hasNested = true;
                        i.like(column, kwd);
                        continue;
                    }
                    i.or().like(column, kwd);
                }
            }
        });
    }

    @Override
    public void queryWrapperKeywords(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityParams<T> params) {
        if (
                ObjectUtils.isEmpty(wrapper)
                        || CommonUtils.isEmpty(this.getFieldsColumn())
                        || ObjectUtils.isEmpty(params)

        ) {
            return;
        }
        if (
                (
                        keywordsFields == null
                                || CommonUtils.isEmpty(keywordsFields)
                                || StrUtils.isBlank(params.getKeywords())
                )
                        && (
                        StrUtils.isBlank(dataIdColumn)
                                || params.getWithIds() == null
                                || CommonUtils.isEmpty(params.getWithIds()
                        )
                )
        ) {
            return;
        }

        wrapper.nested(i -> {
            boolean hasNested = false;
            if (
                    StrUtils.isNotBlank(dataIdColumn)
                            && params.getWithIds() != null
                            && CommonUtils.isNotEmpty(params.getWithIds())
            ) {
                hasNested = true;
                i.in(aliasColumn(alias, dataIdColumn), params.getWithIds());
            }
            if (StrUtils.isNotBlank(params.getKeywords())) {
                String kwd = params.getKeywords().trim();
                for (String col : keywordsFields.keySet()) {
                    String column = aliasColumn(alias, col.trim());
                    if (CommonUtils.isTrue(columnSensitive(col))) {
                        column = sensitiveColumn(column);
                    }
                    if (!hasNested) {
                        hasNested = true;
                        i.like(column, kwd);
                        continue;
                    }
                    i.or().like(column, kwd);
                }
            }
        });
    }

    @Override
    public void queryWrapperKeywords(MPJLambdaWrapper<T> wrapper, @Nullable IEntityParams<T> params) {
        queryWrapperKeywords(wrapper, Constants.BASE_TABLE_ALIAS, params);
    }

    @Override
    public void wrapperKeyword(QueryWrapper<T> wrapper, @Nullable IEntityParams<T> params) {
        if (params == null || wrapper == null || StrUtils.isBlank(params.getKeyword())) {
            return;
        }
        // 开发人员自定
    }

    @Override
    public void wrapperKeyword(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityParams<T> params) {
        if (params == null || wrapper == null || StrUtils.isBlank(params.getKeyword())) {
            return;
        }
        // 开发人员自定
    }

    @Override
    public void wrapperKeyword(MPJLambdaWrapper<T> wrapper, @Nullable IEntityParams<T> params) {
        wrapperKeyword(wrapper, Constants.BASE_TABLE_ALIAS, params);
    }

    @Override
    public void wrapperParamsBase(QueryWrapper<T> wrapper, @Nullable IEntityParams<T> params) {
        if (params == null || wrapper == null) {
            return;
        }

        ReflectionUtils.doWithFields(
                params.getClass(),
                field -> {
                    DbParamField dbParamField = AnnotationUtils.getAnnotation(field, DbParamField.class);
                    if (ObjectUtils.isEmpty(dbParamField) || (dbParamField.type() != null && dbParamField.type().equals(QueryType.custom))) {
                        return;
                    }
                    String column = dbParamField.column();
                    String property = dbParamField.property();

                    if (StrUtils.isBlank(column) && StrUtils.isBlank(property)) {
                        return;
                    }
                    if (
                            !StrUtils.isBlank(column)
                                    && !isFieldColumn(column)
                    ) {
                        return;
                    }
                    if (
                            !StrUtils.isBlank(property)
                                    && !isFieldProperty(property)
                    ) {
                        return;
                    }
                    if (StrUtils.isBlank(column)) {
                        column = getFieldColumnByProperty(property);
                    }
                    if (StrUtils.isBlank(column)) {
                        return;
                    }
                    Field queryField = ReflectUtil.getField(this.entityClass, property);
                    try {
                        QueryType queryType = dbParamField.type();
                        ReflectionUtils.makeAccessible(field);
                        Object value = ReflectionUtils.getField(field, params);
                        if (value == null) {
                            return;
                        }

                        SensitiveField sensitiveField = null;
                        if (isSensitive && queryField.isAnnotationPresent(SensitiveField.class)) {
                            sensitiveField = queryField.getAnnotation(SensitiveField.class);
//                            if (
//                                    ObjectUtils.isNotEmpty(sensitiveField)
//                                            && sensitiveField.db()
//                            ) {
//                                queryType = QueryType.sensitive;
//                            }
                        }

                        paramFieldQueryWrapper(
                                wrapper,
                                column,
                                queryType,
                                value,
                                sensitiveField
                        );
                    } catch (Exception e) {
                        // log
                    }
                }
        );

        scopeQueryWrapper(wrapper);

        queryWrapperKeywords(wrapper, params);
        wrapperKeyword(wrapper, params);
        queryWrapperIds(wrapper, params);

        queryWrapperTags(wrapper, params);

        // 启用状态
        if (params.getEnableStatus() != null && this.isFieldColumn(Constants.COLUMNS_ENABLE_STATUS)) {
            wrapper.eq(Constants.COLUMNS_ENABLE_STATUS, params.getEnableStatus());
        }

        // 显示状态
        if (params.getShowStatus() != null && this.isFieldColumn(Constants.COLUMNS_SHOW_STATUS)) {
            wrapper.eq(Constants.COLUMNS_SHOW_STATUS, params.getShowStatus());
        }

        // 审核状态
        if (params.getAuditStatus() != null && this.isFieldColumn(Constants.COLUMNS_AUDIT_STATUS)) {
            wrapper.eq(Constants.COLUMNS_AUDIT_STATUS, params.getAuditStatus());
        }

        // 创建时间
        if (CommonUtils.isNotEmpty(params.getCreatedDateTime()) && this.isFieldProperty(Constants.COLUMNS_CREATED_AT)) {
            if (params.getCreatedDateTime().getFirst() != null) {
                wrapper.ge(Constants.COLUMNS_CREATED_AT, params.getCreatedDateTime().getFirst());
            }
            if (params.getCreatedDateTime().size() > 1 && params.getCreatedDateTime().get(1) != null) {
                wrapper.le(Constants.COLUMNS_CREATED_AT, params.getCreatedDateTime().get(1));
            }
        }

        // 编辑时间
        if (CommonUtils.isNotEmpty(params.getUpdatedDateTime()) && this.isFieldProperty(Constants.COLUMNS_UPDATED_AT)) {
            if (params.getUpdatedDateTime().getFirst() != null) {
                wrapper.ge(Constants.COLUMNS_UPDATED_AT, params.getUpdatedDateTime().getFirst());
            }
            if (params.getUpdatedDateTime().size() > 1 && params.getUpdatedDateTime().get(1) != null) {
                wrapper.le(Constants.COLUMNS_UPDATED_AT, params.getUpdatedDateTime().get(1));
            }
        }

        // 创建时间
        if (CommonUtils.isNotEmpty(params.getCreatedDate()) && this.isFieldColumn(Constants.COLUMNS_CREATED_AT)) {
            if (params.getCreatedDate().getFirst() != null) {
                wrapper.ge(
                        Constants.COLUMNS_CREATED_AT,
                        DateUtil.format(
                                DateUtil.date(params.getCreatedDate().getFirst()),
                                "yyyy-MM-dd 00:00:00"
                        )
                );
            }
            if (params.getCreatedDate().size() > 1 && params.getCreatedDate().get(1) != null) {
                wrapper.le(
                        Constants.COLUMNS_CREATED_AT,
                        DateUtil.format(
                                DateUtil.date(params.getCreatedDate().get(1)),
                                "yyyy-MM-dd 23:59:59"
                        )
                );
            }
        }

        // 编辑时间
        if (CommonUtils.isNotEmpty(params.getUpdatedDate()) && this.isFieldColumn(Constants.COLUMNS_UPDATED_AT)) {
            if (params.getUpdatedDate().getFirst() != null) {
                wrapper.ge(
                        Constants.COLUMNS_UPDATED_AT,
                        DateUtil.format(
                                DateUtil.date(params.getUpdatedDate().getFirst()),
                                "yyyy-MM-dd 00:00:00"
                        )
                );
            }
            if (params.getUpdatedDate().size() > 1 && params.getUpdatedDate().get(1) != null) {
                wrapper.le(
                        Constants.COLUMNS_UPDATED_AT,
                        DateUtil.format(
                                DateUtil.date(params.getUpdatedDate().get(1)),
                                "yyyy-MM-dd 23:59:59"
                        )
                );
            }
        }

        this.wrapperParams(wrapper, params);
    }

    @Override
    public void wrapperParamsBase(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityParams<T> params) {
        if (params == null || wrapper == null) {
            return;
        }

        ReflectionUtils.doWithFields(
                params.getClass(),
                field -> {
                    DbParamField dbParamField = AnnotationUtils.getAnnotation(field, DbParamField.class);
                    if (ObjectUtils.isEmpty(dbParamField)) {
                        return;
                    }
                    String column = dbParamField.column();
                    String property = dbParamField.property();

                    if (StrUtils.isBlank(column) && StrUtils.isBlank(property)) {
                        return;
                    }
                    if (
                            !StrUtils.isBlank(column)
                                    && !isFieldColumn(column)
                    ) {
                        return;
                    }
                    if (
                            !StrUtils.isBlank(property)
                                    && !isFieldProperty(property)
                    ) {
                        return;
                    }
                    if (StrUtils.isBlank(column)) {
                        column = getFieldColumnByProperty(property);
                    }
                    if (StrUtils.isBlank(column)) {
                        return;
                    }
                    Field queryField = ReflectUtil.getField(this.entityClass, property);
                    try {
                        QueryType queryType = dbParamField.type();
                        ReflectionUtils.makeAccessible(field);
                        Object value = ReflectionUtils.getField(field, params);
                        if (value == null) {
                            return;
                        }

                        SensitiveField sensitiveField = null;
                        if (queryField.isAnnotationPresent(SensitiveField.class)) {
                            sensitiveField = queryField.getAnnotation(SensitiveField.class);
//                            if (
//                                    ObjectUtils.isNotEmpty(sensitiveField)
//                                            && sensitiveField.db()
//                            ) {
//                                queryType = QueryType.sensitive;
//                            }
                        }

                        paramFieldQueryWrapper(wrapper, alias, column, queryType, value, sensitiveField);
                    } catch (Exception e) {
                        // log
                    }
                }
        );

        scopeQueryWrapper(wrapper, alias);

        queryWrapperKeywords(wrapper, alias, params);
        wrapperKeyword(wrapper, alias, params);
        queryWrapperIds(wrapper, alias, params);

        queryWrapperTags(wrapper, alias, params);

        /// /

        // 启用状态
        if (params.getEnableStatus() != null && this.isFieldColumn(Constants.COLUMNS_ENABLE_STATUS)) {
            wrapper.eq(
                    aliasColumn(alias, Constants.COLUMNS_ENABLE_STATUS),
                    params.getEnableStatus()
            );
        }

        // 显示状态
        if (params.getShowStatus() != null && this.isFieldColumn(Constants.COLUMNS_SHOW_STATUS)) {
            wrapper.eq(
                    aliasColumn(alias, Constants.COLUMNS_SHOW_STATUS),
                    params.getShowStatus()
            );
        }

        // 审核状态
        if (params.getAuditStatus() != null && this.isFieldColumn(Constants.COLUMNS_AUDIT_STATUS)) {
            wrapper.eq(
                    aliasColumn(alias, Constants.COLUMNS_AUDIT_STATUS),
                    params.getAuditStatus()
            );
        }

        // 创建时间
        if (CommonUtils.isNotEmpty(params.getCreatedDateTime()) && this.isFieldProperty(Constants.COLUMNS_CREATED_AT)) {
            String createdAtColumn = aliasColumn(
                    alias,
                    Constants.COLUMNS_CREATED_AT
            );
            if (params.getCreatedDateTime().getFirst() != null) {
                wrapper.ge(createdAtColumn, params.getCreatedDateTime().getFirst());
            }
            if (params.getCreatedDateTime().size() > 1 && params.getCreatedDateTime().get(1) != null) {
                wrapper.le(createdAtColumn, params.getCreatedDateTime().get(1));
            }
        }

        // 编辑时间
        if (CommonUtils.isNotEmpty(params.getUpdatedDateTime()) && this.isFieldProperty(Constants.COLUMNS_UPDATED_AT)) {
            String updatedAtColumn = aliasColumn(
                    alias,
                    Constants.COLUMNS_UPDATED_AT
            );
            if (params.getUpdatedDateTime().getFirst() != null) {
                wrapper.ge(updatedAtColumn, params.getUpdatedDateTime().getFirst());
            }
            if (params.getUpdatedDateTime().size() > 1 && params.getUpdatedDateTime().get(1) != null) {
                wrapper.le(updatedAtColumn, params.getUpdatedDateTime().get(1));
            }
        }

        // 创建时间
        if (CommonUtils.isNotEmpty(params.getCreatedDate()) && this.isFieldColumn(Constants.COLUMNS_CREATED_AT)) {
            String createdAtCol = aliasColumn(
                    alias,
                    Constants.COLUMNS_CREATED_AT
            );
            if (params.getCreatedDate().getFirst() != null) {
                wrapper.ge(
                        createdAtCol,
                        DateUtil.format(
                                DateUtil.date(params.getCreatedDate().getFirst()),
                                "yyyy-MM-dd 00:00:00"
                        )
                );
            }
            if (params.getCreatedDate().size() > 1 && params.getCreatedDate().get(1) != null) {
                wrapper.le(
                        createdAtCol,
                        DateUtil.format(
                                DateUtil.date(params.getCreatedDate().get(1)),
                                "yyyy-MM-dd 23:59:59"
                        )
                );
            }
        }

        // 编辑时间
        if (CommonUtils.isNotEmpty(params.getUpdatedDate()) && this.isFieldColumn(Constants.COLUMNS_UPDATED_AT)) {
            String updatedAtCol = aliasColumn(
                    alias,
                    Constants.COLUMNS_UPDATED_AT
            );
            if (params.getUpdatedDate().getFirst() != null) {
                wrapper.ge(
                        updatedAtCol,
                        DateUtil.format(
                                DateUtil.date(params.getUpdatedDate().getFirst()),
                                "yyyy-MM-dd 00:00:00"
                        )
                );
            }
            if (params.getUpdatedDate().size() > 1 && params.getUpdatedDate().get(1) != null) {
                wrapper.le(
                        updatedAtCol,
                        DateUtil.format(
                                DateUtil.date(params.getUpdatedDate().get(1)),
                                "yyyy-MM-dd 23:59:59"
                        )
                );
            }
        }

        this.wrapperParams(wrapper, alias, params);
    }

    @Override
    public void wrapperParamsBase(MPJLambdaWrapper<T> wrapper, @Nullable IEntityParams<T> params) {
        wrapperParamsBase(wrapper, Constants.BASE_TABLE_ALIAS, params);
    }

    @Override
    public void filterBase(QueryWrapper<T> wrapper, @Nullable IEntityFilter<T> filter) {
        if (filter == null || wrapper == null) {
            return;
        }

        ReflectionUtils.doWithFields(
                filter.getClass(),
                field -> {
                    DbFilterField filterField = AnnotationUtils.getAnnotation(field, DbFilterField.class);
                    String fieldProperty = field.getName();
                    String column = "";
                    String property = field.getName();
                    QueryType queryType = QueryType.in;
                    if (filterField != null) {
                        if (StrUtils.isBlank(filterField.property())) {
                            property = filterField.property();
                        }
                        if (StrUtils.isBlank(filterField.column())) {
                            column = filterField.column();
                        }
                        queryType = filterField.type();
                    }
                    if (
                            StrUtils.isNotBlank(property)
                                    && StrUtils.isBlank(column)
                    ) {
                        column = getFieldColumnByProperty(property);
                    }
                    if (StrUtils.isBlank(column)) {
                        return;
                    }
                    if (!isFieldColumn(column)) {
                        return;
                    }

                    if (filterField != null && filterField.sensitive()) {
                        column = sensitiveColumn(column);
                    }
                    try {
                        ReflectionUtils.makeAccessible(field);
                        Object value = ReflectionUtils.getField(field, filter);
                        if (
                                value == null
                                        || CollectionUtils.isEmpty((Collection<?>) value)
                        ) {
                            return;
                        }

                        switch (queryType) {
                            case in -> {
                                wrapper.in(column, (Collection<?>) value);
                            }
                            case notIn -> {
                                wrapper.notIn(column, (Collection<?>) value);
                            }
                        }
                    } catch (Exception e) {
                        // log
                    }
                }
        );
    }

    @Override
    public void wrapperFilterBase(QueryWrapper<T> wrapper, @Nullable IEntityFilter<T> filter) {
        if (filter == null || wrapper == null) {
            return;
        }

        // 启用状态
        if (CommonUtils.isNotEmpty(filter.getEnableStatuses()) && this.isFieldColumn(Constants.COLUMNS_ENABLE_STATUS)) {
            wrapper.in(Constants.COLUMNS_ENABLE_STATUS, filter.getEnableStatuses());
        }

        // 显示状态
        if (CommonUtils.isNotEmpty(filter.getShowStatuses()) && this.isFieldColumn(Constants.COLUMNS_SHOW_STATUS)) {
            wrapper.in(Constants.COLUMNS_SHOW_STATUS, filter.getShowStatuses());
        }

        // 审核状态
        if (CommonUtils.isNotEmpty(filter.getAuditStatuses()) && this.isFieldColumn(Constants.COLUMNS_AUDIT_STATUS)) {
            wrapper.in(Constants.COLUMNS_AUDIT_STATUS, filter.getAuditStatuses());
        }

        filterBase(wrapper, filter);
        this.wrapperFilter(wrapper, filter);
    }

    @Override
    public void filterBase(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityFilter<T> filter) {
        if (filter == null || wrapper == null) {
            return;
        }
        ReflectionUtils.doWithFields(
                filter.getClass(),
                field -> {
                    DbFilterField filterField = AnnotationUtils.getAnnotation(field, DbFilterField.class);
                    String fieldProperty = field.getName();
                    String column = "";
                    String property = field.getName();
                    QueryType queryType = QueryType.in;
                    if (filterField != null) {
                        if (StrUtils.isBlank(filterField.property())) {
                            property = filterField.property();
                        }
                        if (StrUtils.isBlank(filterField.column())) {
                            column = filterField.column();
                        }
                        queryType = filterField.type();
                    }
                    if (
                            StrUtils.isNotBlank(property)
                                    && StrUtils.isBlank(column)
                    ) {
                        column = getFieldColumnByProperty(property);
                    }
                    if (StrUtils.isBlank(column)) {
                        return;
                    }
                    if (!isFieldColumn(column)) {
                        return;
                    }
                    column = aliasColumn(alias, column);
                    if (filterField != null && filterField.sensitive()) {
                        column = sensitiveColumn(column);
                    }
                    try {
                        ReflectionUtils.makeAccessible(field);
                        Collection<?> value = (Collection<?>) ReflectionUtils.getField(field, filter);
                        if (
                                value == null
                                        || CollectionUtils.isEmpty(value)
                        ) {
                            return;
                        }


                        switch (queryType) {
                            case in -> {
                                wrapper.in(column, value);
                            }
                            case notIn -> {
                                wrapper.notIn(column, value);
                            }
                        }
                    } catch (Exception e) {
                        // log
                    }
                }
        );
    }

    @Override
    public void filterBase(MPJLambdaWrapper<T> wrapper, @Nullable IEntityFilter<T> filter) {
        filterBase(wrapper, Constants.BASE_TABLE_ALIAS, filter);
    }

    @Override
    public void wrapperFilterBase(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityFilter<T> filter) {
        if (filter == null || wrapper == null) {
            return;
        }

        // 启用状态
        if (CommonUtils.isNotEmpty(filter.getEnableStatuses()) && this.isFieldColumn(Constants.COLUMNS_ENABLE_STATUS)) {
            wrapper.in(
                    aliasColumn(alias, Constants.COLUMNS_ENABLE_STATUS),
                    filter.getEnableStatuses()
            );
        }

        // 显示状态
        if (CommonUtils.isNotEmpty(filter.getShowStatuses()) && this.isFieldColumn(Constants.COLUMNS_SHOW_STATUS)) {
            wrapper.in(
                    aliasColumn(alias, Constants.COLUMNS_SHOW_STATUS),
                    filter.getShowStatuses()
            );
        }

        // 审核状态
        if (CommonUtils.isNotEmpty(filter.getAuditStatuses()) && this.isFieldColumn(Constants.COLUMNS_AUDIT_STATUS)) {
            wrapper.in(
                    aliasColumn(alias, Constants.COLUMNS_AUDIT_STATUS),
                    filter.getAuditStatuses()
            );
        }

        filterBase(wrapper, alias, filter);
        this.wrapperFilter(wrapper, alias, filter);
    }

    @Override
    public void wrapperFilterBase(MPJLambdaWrapper<T> wrapper, @Nullable IEntityFilter<T> filter) {
        wrapperFilterBase(wrapper, Constants.BASE_TABLE_ALIAS, filter);
    }

    @Override
    public void wrapperSorter(QueryWrapper<T> wrapper, @Nullable HashMap<String, OrderType> sorter) {
        if (
                wrapper == null
                        || sorter == null
                        || CommonUtils.isEmpty(sorter)
        ) {
            return;
        }

        for (Map.Entry<String, OrderType> entry : sorter.entrySet()) {
            if (entry.getValue() != null && this.isFieldProperty(entry.getKey())) {
                wrapper.orderBy(
                        true,
                        entry.getValue() == OrderType.ascend,
                        this.getFieldColumnByProperty(entry.getKey())
                );
            }
        }
    }

    @Override
    public void wrapperSorter(MPJLambdaWrapper<T> wrapper, String alias, @Nullable HashMap<String, OrderType> sorter) {
        if (
                wrapper == null
                        || sorter == null
                        || CommonUtils.isEmpty(sorter)
        ) {
            return;
        }
        for (Map.Entry<String, OrderType> entry : sorter.entrySet()) {
            if (entry.getValue() != null && this.isFieldProperty(entry.getKey())) {
                wrapper.orderBy(
                        true,
                        entry.getValue() == OrderType.ascend,
                        aliasColumn(
                                alias,
                                this.getFieldColumnByProperty(entry.getKey())
                        )
                );
            }
        }
    }

    @Override
    public void wrapperSorter(MPJLambdaWrapper<T> wrapper, @Nullable HashMap<String, OrderType> sorter) {
        wrapperSorter(wrapper, Constants.BASE_TABLE_ALIAS, sorter);
    }

    @Override
    public void wrapperParams(QueryWrapper<T> wrapper, @Nullable IEntityParams<T> params) {
        if (params == null || wrapper == null) {
            return;
        }
        // 开发人员自定
    }

    @Override
    public void wrapperParams(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityParams<T> params) {
        if (params == null || wrapper == null) {
            return;
        }
        // 开发人员自定
    }

    @Override
    public void wrapperParams(MPJLambdaWrapper<T> wrapper, @Nullable IEntityParams<T> params) {
        wrapperParams(wrapper, Constants.BASE_TABLE_ALIAS, params);
    }

    @Override
    public void wrapperFilter(QueryWrapper<T> wrapper, @Nullable IEntityFilter<T> filter) {
        if (filter == null || wrapper == null) {
            return;
        }
        // 开发人员自定义
    }

    @Override
    public void wrapperFilter(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityFilter<T> filter) {
        if (filter == null || wrapper == null) {
            return;
        }
        // 开发人员自定义
    }

    @Override
    public void wrapperFilter(MPJLambdaWrapper<T> wrapper, @Nullable IEntityFilter<T> filter) {
        wrapperFilter(wrapper, Constants.BASE_TABLE_ALIAS, filter);
    }

    /**
     * 设置启用状态
     *
     * @param id
     * @param status
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult setEnableStatus(long id, EnableStatus status) throws ModelException {
        if (CommonUtils.isNotID(id)) {
            throw new ModelException(ExceptionCodes.PARAM_ERROR);
        }

        if (
                StrUtils.isBlank(this.getDataIdColumn())
                        || CommonUtils.isEmpty(this.getFieldsColumn())
                        || !this.hasColumn(Constants.COLUMNS_ENABLE_STATUS)
        ) {
            return ActionResult.fail();
        }

        UpdateWrapper<T> wrapper = this.getUpdateWrapper();
        wrapper.eq(this.dataIdColumn, id);

        if (this.hasColumn(Constants.COLUMNS_IS_SYSTEM)) {
            wrapper.eq(Constants.COLUMNS_IS_SYSTEM, 0);
        }

        wrapper.set(Constants.COLUMNS_ENABLE_STATUS, status);

        if (!this.service.update(wrapper)) {
            return ActionResult.fail();
        }

        return ActionResult.success(true);
    }

    /**
     * 批量设置启用状态
     *
     * @param idList
     * @param status
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult batchSetEnableStatus(Set<Long> idList, EnableStatus status) throws ModelException {
        if (CommonUtils.isEmpty(idList)) {
            throw new ModelException(ExceptionCodes.PARAMS_EMPTY);
        }

        if (
                StrUtils.isBlank(this.getDataIdColumn())
                        || CommonUtils.isEmpty(this.getFieldsColumn())
                        || !this.hasColumn(Constants.COLUMNS_ENABLE_STATUS)
        ) {
            return ActionResult.fail();
        }

        UpdateWrapper<T> wrapper = this.getUpdateWrapper();
        wrapper.in(this.dataIdColumn, idList);

        if (this.hasColumn(Constants.COLUMNS_IS_SYSTEM)) {
            wrapper.eq(Constants.COLUMNS_IS_SYSTEM, 0);
        }

        wrapper.set(Constants.COLUMNS_ENABLE_STATUS, status);

        if (!this.service.update(wrapper)) {
            return ActionResult.fail();
        }

        return ActionResult.success(true);
    }

    /**
     * 启用
     *
     * @param id
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult enable(long id) throws ModelException {
        return this.setEnableStatus(id, EnableStatus.enable);
    }

    /**
     * 批量启用
     *
     * @param idList
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult batchEnable(Set<Long> idList) throws ModelException {
        return this.batchSetEnableStatus(idList, EnableStatus.enable);
    }

    /**
     * 禁用
     *
     * @param id
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult disable(long id) throws ModelException {
        return this.setEnableStatus(id, EnableStatus.disable);
    }

    /**
     * 批量禁用
     *
     * @param idList
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult batchDisable(Set<Long> idList) throws ModelException {
        return this.batchSetEnableStatus(idList, EnableStatus.disable);
    }

    /**
     * 设置显示状态
     *
     * @param id
     * @param status
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult setShowStatus(long id, ShowStatus status) throws ModelException {
        if (CommonUtils.isNotID(id)) {
            throw new ModelException(ExceptionCodes.PARAM_ERROR);
        }

        if (
                StrUtils.isBlank(this.getDataIdColumn())
                        || CommonUtils.isEmpty(this.getFieldsColumn())
                        || !this.hasColumn(Constants.COLUMNS_SHOW_STATUS)
        ) {
            return ActionResult.fail();
        }

        UpdateWrapper<T> wrapper = this.getUpdateWrapper();
        wrapper.eq(this.dataIdColumn, id);

//        if (this.hasColumn(Constants.COLUMNS_IS_SYSTEM)) {
//            wrapper.eq(Constants.COLUMNS_IS_SYSTEM, 0);
//        }

        wrapper.set(Constants.COLUMNS_SHOW_STATUS, status);

        if (!this.service.update(wrapper)) {
            return ActionResult.fail();
        }

        return ActionResult.success(true);
    }

    /**
     * 批量设置显示状态
     *
     * @param idList
     * @param status
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult batchSetShowStatus(Set<Long> idList, ShowStatus status) throws ModelException {
        if (CommonUtils.isEmpty(idList)) {
            throw new ModelException(ExceptionCodes.PARAMS_EMPTY);
        }

        if (
                StrUtils.isBlank(this.getDataIdColumn())
                        || CommonUtils.isEmpty(this.getFieldsColumn())
                        || !this.hasColumn(Constants.COLUMNS_SHOW_STATUS)
        ) {
            return ActionResult.fail();
        }

        UpdateWrapper<T> wrapper = this.getUpdateWrapper();
        wrapper.in(this.dataIdColumn, idList);

//        if (this.hasColumn(Constants.COLUMNS_IS_SYSTEM)) {
//            wrapper.eq(Constants.COLUMNS_IS_SYSTEM, 0);
//        }

        wrapper.set(Constants.COLUMNS_SHOW_STATUS, status);

        if (!this.service.update(wrapper)) {
            return ActionResult.fail();
        }

        return ActionResult.success(true);
    }

    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult show(long id) throws ModelException {
        return this.setShowStatus(id, ShowStatus.show);
    }

    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult batchShow(Set<Long> idList) throws ModelException {
        return this.batchSetShowStatus(idList, ShowStatus.show);
    }

    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult hidden(long id) throws ModelException {
        return this.setShowStatus(id, ShowStatus.hidden);
    }

    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult batchHidden(Set<Long> idList) throws ModelException {
        return this.batchSetShowStatus(idList, ShowStatus.hidden);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult delete(long id) throws ModelException {
        if (CommonUtils.isNotID(id)) {
            throw new ModelException(ExceptionCodes.PARAM_ERROR);
        }

        if (StrUtils.isBlank(this.getDataIdColumn())) {
            return ActionResult.fail();
        }

        QueryWrapper<T> queryWrapper = this.getQueryWrapper();
        queryWrapper.eq(this.dataIdColumn, id);

        if (this.hasColumn(Constants.COLUMNS_IS_SYSTEM)) {
            queryWrapper.eq(Constants.COLUMNS_IS_SYSTEM, 0);
        }

        this.service.remove(queryWrapper);

        return ActionResult.success(true);
    }

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult batchDelete(Set<Long> idList) throws ModelException {
        if (CommonUtils.isEmpty(idList)) {
            throw new ModelException(ExceptionCodes.PARAMS_EMPTY);
        }

        if (StrUtils.isBlank(this.getDataIdColumn())) {
            return ActionResult.fail();
        }

        QueryWrapper<T> queryWrapper = this.getQueryWrapper();
        queryWrapper.in(this.dataIdColumn, idList);

        if (this.hasColumn(Constants.COLUMNS_IS_SYSTEM)) {
            queryWrapper.eq(Constants.COLUMNS_IS_SYSTEM, 0);
        }

        this.service.remove(queryWrapper);

        return ActionResult.success(true);
    }

    /**
     * 设置排序权重
     *
     * @param id
     * @param order
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult setOrder(long id, int order) throws ModelException {
        if (CommonUtils.isNotID(id)) {
            throw new ModelException(ExceptionCodes.PARAM_ERROR);
        }

        if (order < 0) {
            throw new ModelException(ExceptionCodes.PARAM_ERROR);
        }

        if (
                StrUtils.isBlank(this.getDataIdColumn())
                        || CommonUtils.isEmpty(this.getFieldsColumn())
                        || !this.hasColumn(Constants.COLUMNS_ORDER_SORT)
        ) {
            return ActionResult.fail();
        }

        UpdateWrapper<T> updateWrapper = this.getUpdateWrapper();
        updateWrapper.eq(this.dataIdColumn, id);
        updateWrapper.set(Constants.COLUMNS_ORDER_SORT, order);

        if (!this.service.update(updateWrapper)) {
            return ActionResult.fail();
        }

        return ActionResult.success(true);
    }

    /**
     * 批量设置排序权重
     *
     * @param data
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult batchSetOrder(HashMap<Long, Integer> data) throws ModelException {
        if (CommonUtils.isEmpty(data)) {
            throw new ModelException(ExceptionCodes.PARAMS_EMPTY);
        }

        if (
                StrUtils.isBlank(this.getDataIdColumn())
                        || CommonUtils.isEmpty(this.getFieldsColumn())
                        || !this.hasColumn(Constants.COLUMNS_ORDER_SORT)
        ) {
            return ActionResult.fail();
        }

        for (Map.Entry<Long, Integer> entry : data.entrySet()) {
            if (
                    entry.getKey() == null
                            || entry.getKey().compareTo(0L) < 1
                            || entry.getValue() == null
                            || entry.getValue() < 0
            ) {
                continue;
            }

            UpdateWrapper<T> updateWrapper = this.getUpdateWrapper();
            updateWrapper.eq(this.dataIdColumn, entry.getKey());
            updateWrapper.set(Constants.COLUMNS_ORDER_SORT, entry.getValue());

            if (!this.service.update(updateWrapper)) {
                throw new ModelException(ExceptionCodes.SYSTEM_ERROR);
            }
        }

        return ActionResult.success(true);
    }

    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> IPage<T> pageInstance(@Nullable R entityRequest) {
        long current = Constants.DEFAULT_CURRENT_PAGE;
        long pageSize = Constants.DEFAULT_PAGE_SIZE;
        IEntityParams<T> params = null;
        if (entityRequest != null) {
            params = entityRequest.getParams();
        }
        if (params != null) {
            current = params.getCurrentPage();
            pageSize = params.getPageSize();
        }
        if (current < 1) {
            current = Constants.DEFAULT_CURRENT_PAGE;
        }
        if (pageSize < 1) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        return new Page<>(current, pageSize);
    }

    @Override
    public <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>> IPage<E> getPageInstance(@Nullable R entityRequest) throws ModelException {
        long current = Constants.DEFAULT_CURRENT_PAGE;
        long pageSize = Constants.DEFAULT_PAGE_SIZE;
        IEntityParams<E> params = null;
        if (entityRequest != null) {
            params = entityRequest.getParams();
        }
        if (params != null) {
            current = params.getCurrentPage();
            pageSize = params.getPageSize();
        }
        if (current < 1) {
            current = Constants.DEFAULT_CURRENT_PAGE;
        }
        if (pageSize < 1) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }
        return new Page<E>(current, pageSize);
    }


    /**
     * 查询 Wrapper
     *
     * @param entityRequest
     * @return
     */
    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> QueryWrapper<T> queryWrapper(@Nullable R entityRequest) {
        QueryWrapper<T> wrapper = this.getQueryWrapper();

        // params
        IEntityParams<T> params = entityRequest != null ? entityRequest.getParams() : null;
        if (params != null) {
            this.wrapperParamsBase(wrapper, params);
        }

        // filter
        IEntityFilter<T> filter = entityRequest != null ? entityRequest.getFilter() : null;
        if (filter != null) {
            this.wrapperFilterBase(wrapper, filter);
        }

        // sorter
        this.wrapperSorter(
                wrapper,
                entityRequest != null ? entityRequest.getSorter() : null
        );

        if (this.hasColumn(Constants.COLUMNS_ORDER_SORT)) {
            wrapper.orderByDesc(Constants.COLUMNS_ORDER_SORT);
        }
        if (this.hasColumn(Constants.COLUMNS_ID)) {
            wrapper.orderByDesc(Constants.COLUMNS_ID);
        }

        return wrapper;
    }

    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> MPJLambdaWrapper<T> MPJQueryWrapper(String alias, @Nullable R entityRequest) {
        MPJLambdaWrapper<T> wrapper = this.getMPJLambdaWrapper();

        // params
        IEntityParams<T> params = entityRequest != null ? entityRequest.getParams() : null;
        if (params != null) {
            this.wrapperParamsBase(wrapper, alias, params);
        }

        // filter
        IEntityFilter<T> filter = entityRequest != null ? entityRequest.getFilter() : null;
        if (filter != null) {
            this.wrapperFilterBase(wrapper, alias, filter);
        }

        // sorter
        this.wrapperSorter(
                wrapper,
                alias,
                entityRequest != null ? entityRequest.getSorter() : null
        );
        if (this.hasColumn(Constants.COLUMNS_ORDER_SORT)) {
            wrapper.orderByDesc(aliasColumn(alias, Constants.COLUMNS_ORDER_SORT));
        }
        if (this.hasColumn(Constants.COLUMNS_ID)) {
            wrapper.orderByDesc(aliasColumn(alias, Constants.COLUMNS_ID));
        }

        return wrapper;
    }

    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> MPJLambdaWrapper<T> MPJQueryWrapper(@Nullable R entityRequest) {
        return MPJQueryWrapper(Constants.BASE_TABLE_ALIAS, entityRequest);
    }

    @Override
    public List<String> getAllColumnList() {
        if (CommonUtils.isEmpty(this.fieldsColumn)) {
            return null;
        }
        return new ArrayList<>(this.fieldsColumn.values());
    }

    @Override
    public List<String> getDefaultSelectColumnList() {
        if (CommonUtils.isEmpty(this.defaultSelectColumn)) {
            return null;
        }
        return new ArrayList<>(this.defaultSelectColumn.values());
    }

    @Override
    public List<String> getAllSelectColumnList() {
        if (CommonUtils.isEmpty(this.selectColumn)) {
            return null;
        }
        return new ArrayList<>(this.selectColumn.values());
    }

    @Override
    public List<String> getAllPropertyList() {
        if (CommonUtils.isEmpty(this.fieldsProperty)) {
            return null;
        }
        return new ArrayList<>(this.fieldsProperty.keySet());
    }

    @Override
    public Boolean propertySensitive(String property) {
        if (
                StrUtils.isBlank(property)
                        || !isSensitive
                        || sensitiveFields == null
                        || CommonUtils.isEmpty(sensitiveFields)
        ) {
            return null;
        }
        String column = getFieldColumnByProperty(property);
        if (StrUtils.isBlank(column) || !sensitiveFields.containsKey(column)) {
            return null;
        }
        return sensitiveFields.get(column);
    }

    @Override
    public Boolean columnSensitive(String column) {
        if (
                StrUtils.isBlank(column)
                        || !isSensitive
                        || sensitiveFields == null
                        || CommonUtils.isEmpty(sensitiveFields)
                        || !sensitiveFields.containsKey(column)
        ) {
            return null;
        }
        return sensitiveFields.get(column);
    }

    @Override
    public boolean propertyIsSensitive(String property) {
        if (
                StrUtils.isBlank(property)
                        || !isSensitive
                        || sensitiveFields == null
                        || CommonUtils.isEmpty(sensitiveFields)
        ) {
            return false;
        }
        String column = getFieldColumnByProperty(property);
        if (StrUtils.isBlank(column)) {
            return false;
        }
        return sensitiveFields.containsKey(column);
    }

    @Override
    public boolean columnIsSensitive(String column) {
        if (
                StrUtils.isBlank(column)
                        || !isSensitive
                        || sensitiveFields == null
                        || CommonUtils.isEmpty(sensitiveFields)
        ) {
            return false;
        }
        return sensitiveFields.containsKey(column);
    }

    @Override
    public boolean propertyIsKeywords(String property) {
        if (
                StrUtils.isBlank(property)
                        || keywordsFields == null
                        || CommonUtils.isEmpty(keywordsFields)
        ) {
            return false;
        }
        return keywordsFields.containsValue(property);
    }

    @Override
    public boolean columnIsKeywords(String column) {
        if (
                StrUtils.isBlank(column)
                        || keywordsFields == null
                        || CommonUtils.isEmpty(keywordsFields)
        ) {
            return false;
        }
        return keywordsFields.containsKey(column);
    }

    @Override
    public List<String> getDefaultSelectPropertyList() {
        if (CommonUtils.isEmpty(this.defaultSelectColumn)) {
            return null;
        }
        return new ArrayList<>(this.defaultSelectColumn.keySet());
    }

    @Override
    public List<String> getAllSelectPropertyList() {
        if (CommonUtils.isEmpty(this.selectColumn)) {
            return null;
        }
        return new ArrayList<>(this.selectColumn.keySet());
    }

    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> List<String> getSelectPropertyList(@Nullable R entityRequest) {
        List<String> selectColumns = entityRequest != null ? entityRequest.getColumns() : null;
        List<String> addClumns = entityRequest != null ? entityRequest.getAddColumns() : null;

        if (CommonUtils.isEmpty(selectColumns)) {
            // selectColumns = getDefaultSelectPropertyList();
            selectColumns = this.propertyName.entrySet().stream()
                    .filter(v -> this.defaultSelectColumn != null && this.defaultSelectColumn.containsKey(v.getValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
        if (selectColumns == null) {
            selectColumns = new ArrayList<>();
        }
        if (addClumns != null && !addClumns.isEmpty()) {
            selectColumns.addAll(addClumns);
        }

        if (CommonUtils.isEmpty(selectColumns)) {
            return null;
        }
        return selectColumns.stream().filter(v -> this.propertyName.containsKey(v) || this.fieldsColumn.containsKey(v)).map(v -> {
            if (this.propertyName.containsKey(v)) {
                return this.propertyName.get(v);
            }
            return this.fieldsColumn.get(v);
        }).collect(Collectors.toList());
    }

    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> List<String> getSelectColumnList(@Nullable R entityRequest) {
        List<String> selectColumns = getSelectPropertyList(entityRequest);
        if (CommonUtils.isEmpty(selectColumns)) {
            return null;
        }

        return this.fieldsProperty.entrySet().stream()
                .filter(v -> selectColumns.contains(v.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public void wrapperSelectByColumn(QueryWrapper<T> wrapper, @Nullable List<String> columnList) {
        if (wrapper == null || columnList == null || columnList.isEmpty()) {
            return;
        }
        wrapper.select(columnList);
    }

    @Override
    public void wrapperSelectByColumn(MPJLambdaWrapper<T> wrapper, String alias, @Nullable List<String> columnList) {
        if (wrapper == null || columnList == null || columnList.isEmpty()) {
            return;
        }
        String[] columns = columnList.stream().map(v -> aliasColumn(alias, v.trim())).toArray(String[]::new);
        wrapper.select(columns);
    }

    @Override
    public void wrapperSelectByColumn(MPJLambdaWrapper<T> wrapper, @Nullable List<String> columnList) {
        this.wrapperSelectByColumn(wrapper, Constants.BASE_TABLE_ALIAS, columnList);
    }

    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> void wrapperColumnSelect(QueryWrapper<T> wrapper, @Nullable R entityRequest) {
        if (wrapper == null) {
            return;
        }
        List<String> selectColumns = getSelectColumnList(entityRequest);

        if (CommonUtils.isEmpty(selectColumns)) {
            return;
        }

        wrapperSelectByColumn(wrapper, selectColumns);
    }

    @Override
    public void wrapperColumnSelect(QueryWrapper<T> wrapper) {
        wrapperColumnSelect(wrapper, null);
    }

    @Override
    public void wrapperSelectByProperty(LambdaQueryWrapper<T> wrapper, @Nullable List<String> propertyList) {
        if (wrapper == null || propertyList == null || propertyList.isEmpty()) {
            return;
        }
        wrapper.select(this.entityClass, new Predicate<TableFieldInfo>() {

            @Override
            public boolean test(TableFieldInfo tableFieldInfo) {
                return propertyList.contains(tableFieldInfo.getProperty());
            }
        });
    }

    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> void wrapperColumnSelect(LambdaQueryWrapper<T> wrapper, @Nullable R entityRequest) {
        if (wrapper == null) {
            return;
        }

        List<String> selectColumns = getSelectColumnList(entityRequest);

        if (CommonUtils.isEmpty(selectColumns)) {
            return;
        }

        wrapperSelectByProperty(wrapper, selectColumns);
    }

    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> void wrapperColumnSelect(LambdaQueryWrapper<T> wrapper) {
        wrapperColumnSelect(wrapper, null);
    }

    /**
     * 详情
     *
     * @param id
     * @return
     * @throws ModelException
     */
    @Cacher
    @Override
    public T detail(long id) throws ModelException {
        if (CommonUtils.isNotID(id)) {
            throw new ModelException(ExceptionCodes.PARAMS_EMPTY);
        }
        if (StrUtils.isBlank(this.getDataIdColumn())) {
            return null;
        }

        QueryWrapper<T> wrapper = this.getQueryWrapper();
        wrapper.eq(this.getDataIdColumn(), id);
        wrapperSelectByColumn(wrapper, this.getAllSelectColumnList());

        return this.service.getOne(wrapper, false);
    }

    /**
     * 分页查询
     *
     * @param queryWrapper
     * @param entityRequest
     * @param <R>
     * @return
     * @throws ModelException
     */
    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> PagerData<T> queryList(QueryWrapper<T> queryWrapper, @Nullable R entityRequest) throws ModelException {
        IPage<T> pager = this.pageInstance(entityRequest);

        wrapperColumnSelect(queryWrapper, entityRequest);
        IPage<T> pageRes = this.service.page(pager, queryWrapper);

        PagerData<T> data = new PagerData<>();
        data.setSuccess(true);
        data.setTotal(pageRes.getTotal());
        data.setCurrentPage(pager.getCurrent());
        data.setPageSize(pager.getSize());
        data.setPageTotal(pageRes.getPages());
        data.setData(pageRes.getRecords());
        return data;
    }

    @Override
    public <E extends BaseModelEntity<E>, ES extends BaseEntityServiceImpl<? extends BaseMapper<E>, E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>> PagerData<E> queryList(QueryWrapper<E> queryWrapper, @Nullable R entityRequest, ES entityService) throws ModelException {
        IPage<E> pager = this.getPageInstance(entityRequest);

        IPage<E> pageRes = entityService.page(pager, queryWrapper);

        PagerData<E> data = new PagerData<>();
        data.setSuccess(true);
        data.setTotal(pageRes.getTotal());
        data.setCurrentPage(pager.getCurrent());
        data.setPageSize(pager.getSize());
        data.setPageTotal(pageRes.getPages());
        data.setData(pageRes.getRecords());
        return data;
    }

    /**
     * jion 查询
     *
     * @param queryWrapper
     * @param entityRequest
     * @param entityClz
     * @param entityMapper
     * @param <E>
     * @param <R>
     * @param <EM>
     * @return
     * @throws ModelException
     */
    @Override
    public <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, EM extends IEntityMapper<E>> PagerData<E> queryList(MPJLambdaWrapper<E> queryWrapper, @Nullable R entityRequest, Class<E> entityClz, EM entityMapper) throws ModelException {
        IPage<E> pager = this.getPageInstance(entityRequest);

        IPage<E> pageRes = entityMapper.selectJoinPage(pager, entityClz, queryWrapper);

        PagerData<E> data = new PagerData<>();
        data.setSuccess(true);
        data.setTotal(pageRes.getTotal());
        data.setCurrentPage(pager.getCurrent());
        data.setPageSize(pager.getSize());
        data.setPageTotal(pageRes.getPages());
        data.setData(pageRes.getRecords());
        return data;
    }

    /**
     * jion 查询
     *
     * @param queryWrapper
     * @param entityRequest
     * @param entityClz
     * @param entityService
     * @param <E>
     * @param <R>
     * @param <ES>
     * @return
     * @throws ModelException
     */
    @Override
    public <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, ES extends IEntityService<E>> PagerData<E> queryList(MPJLambdaWrapper<E> queryWrapper, @Nullable R entityRequest, Class<E> entityClz, ES entityService) throws ModelException {
        return queryList(queryWrapper, entityRequest, entityClz, entityService.getMapper());
    }

    /**
     * 分页查询
     *
     * @param queryWrapper
     * @param <R>
     * @return
     * @throws ModelException
     */
    @Cacher
    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> PagerData<T> queryList(QueryWrapper<T> queryWrapper) throws ModelException {
        return this.queryList(queryWrapper, null);
    }

    /**
     * 分页查询
     *
     * @param entityRequest
     * @return
     * @throws ModelException
     */
    @Cacher
    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> PagerData<T> queryList(@Nullable R entityRequest) throws ModelException {
        return this.queryList(this.queryWrapper(entityRequest), entityRequest);
    }

    /**
     * 按条件查询
     *
     * @param queryWrapper
     * @param entityRequest
     * @param <R>
     * @return
     * @throws ModelException
     */
    @Cacher
    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> List<T> query(QueryWrapper<T> queryWrapper, @Nullable R entityRequest) throws ModelException {
        Long limit = Constants.DEFAULT_LIMIT;
        if (entityRequest != null) {
            limit = entityRequest.getLimit();
        }
        if (NumUtils.leZero(limit)) {
            limit = Constants.DEFAULT_LIMIT;
        }
        try {
            queryWrapper.last("limit " + limit);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            log.error("set limit error" + e);
        }
        wrapperColumnSelect(queryWrapper, entityRequest);

        return this.service.list(queryWrapper);
    }

    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> List<T> queryAll(QueryWrapper<T> queryWrapper, @Nullable R entityRequest) throws ModelException {
        wrapperColumnSelect(queryWrapper, entityRequest);
        return this.service.list(queryWrapper);
    }

    /**
     * 按条件查询
     *
     * @param queryWrapper
     * @param <R>
     * @return
     * @throws ModelException
     */
    @Cacher
    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> List<T> query(QueryWrapper<T> queryWrapper) throws ModelException {
        return this.query(queryWrapper, null);
    }

    /**
     * 按条件查询 join
     *
     * @param queryWrapper
     * @param entityClz
     * @param entityMapper
     * @param <E>
     * @param <R>
     * @param <EM>
     * @return
     * @throws ModelException
     */
    @Override
    public <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, EM extends IEntityMapper<E>> List<E> query(MPJLambdaWrapper<E> queryWrapper, Class<E> entityClz, EM entityMapper) throws ModelException {
        return entityMapper.selectJoinList(entityClz, queryWrapper);
    }

    @Override
    public <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, EM extends IEntityMapper<E>> List<E> query(MPJLambdaWrapper<E> queryWrapper, @Nullable R entityRequest, Class<E> entityClz, EM entityMapper) throws ModelException {
        Long limit = Constants.DEFAULT_LIMIT;
        if (entityRequest != null) {
            limit = entityRequest.getLimit();
        }
        if (NumUtils.leZero(limit)) {
            limit = Constants.DEFAULT_LIMIT;
        }
        try {
            queryWrapper.last("limit " + limit);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            log.error("set limit error" + e);
        }

        return query(queryWrapper, entityClz, entityMapper);
    }

    @Override
    public <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, ES extends IEntityService<E>> List<E> query(MPJLambdaWrapper<E> queryWrapper, @Nullable R entityRequest, Class<E> entityClz, ES entityService) throws ModelException {
        return query(queryWrapper, entityRequest, entityClz, entityService.getMapper());
    }

    @Override
    public <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, EM extends IEntityMapper<E>> List<E> queryAll(MPJLambdaWrapper<E> queryWrapper, Class<E> entityClz, EM entityMapper) throws ModelException {
        return query(queryWrapper, entityClz, entityMapper);
    }

    @Override
    public <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, ES extends IEntityService<E>> List<E> queryAll(MPJLambdaWrapper<E> queryWrapper, Class<E> entityClz, ES entityService) throws ModelException {
        return query(queryWrapper, entityClz, entityService.getMapper());
    }

    @Override
    public <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, EM extends IEntityMapper<E>> List<E> queryAll(MPJLambdaWrapper<E> queryWrapper, @Nullable R entityRequest, Class<E> entityClz, EM entityMapper) throws ModelException {
        return query(queryWrapper, entityClz, entityMapper);
    }

    @Override
    public <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, ES extends IEntityService<E>> List<E> queryAll(MPJLambdaWrapper<E> queryWrapper, @Nullable R entityRequest, Class<E> entityClz, ES entityService) throws ModelException {
        return queryAll(queryWrapper, entityRequest, entityClz, entityService.getMapper());
    }

    /**
     * 按条件查询
     *
     * @param entityRequest
     * @return
     * @throws ModelException
     */
    @Cacher
    @Override
    public <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> List<T> query(@Nullable R entityRequest) throws ModelException {
        return this.query(this.queryWrapper(entityRequest), entityRequest);
    }

    /**
     * 新建
     *
     * @param data
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult create(T data) throws ModelException {
        if (data == null) {
            throw new ModelException(ExceptionCodes.PARAMS_EMPTY);
        }

        if (
                StrUtils.isBlank(this.getDataIdColumn())
                        || CommonUtils.isEmpty(this.getFieldsColumn())
        ) {
            return ActionResult.fail();
        }

        boolean result = this.service.save(data);
        if (!result) {
            return ActionResult.fail();
        }

        return ActionResult.success(true);
    }

    /**
     * 编辑
     *
     * @param id
     * @param data
     * @return
     * @throws ModelException
     */
    @Override
    @Transactional(rollbackFor = {ModelException.class})
    public ActionResult edit(long id, T data) throws ModelException {
        if (CommonUtils.isNotID(id)) {
            throw new ModelException(ExceptionCodes.PARAM_ERROR);
        }
        if (data == null) {
            throw new ModelException(ExceptionCodes.PARAMS_EMPTY);
        }

        if (
                StrUtils.isBlank(this.getDataIdColumn())
                        || CommonUtils.isEmpty(this.getFieldsColumn())
        ) {
            return ActionResult.fail();
        }

        QueryWrapper<T> wrapper = this.getQueryWrapper();
        wrapper.eq(this.dataIdColumn, id);
        T entity = this.service.getOne(wrapper);
        if (entity == null) {
            throw new ModelException(ExceptionCodes.DATA_NOT_EXIST);
        }

        UpdateWrapper<T> updateWrapper = this.getUpdateWrapper();
        updateWrapper.eq(this.dataIdColumn, id);

        if (!this.service.update(data, updateWrapper)) {
            return ActionResult.fail();
        }

        return ActionResult.success(true);
    }

    /**
     * object 信息
     *
     * @return
     */
    @Override
    public ObjectInfo getObjectInfo() {
        return ObjectInfo.builder()
                .sysName(CoreProperties.APP_NAME)
                .sysModule(SystemCommon.getModule())
                .sysApp(SystemCommon.getApp())
                .sysObject(getObjectName())
                .build();
    }

    /**
     * 保存数据标签
     *
     * @param id
     * @param tags
     * @param owner
     * @return
     * @throws ModelException
     */
    @Override
    public ActionResult saveOjbectTags(
            long id,
            List<String> tags,
            Owner owner
    ) throws ModelException {
        if (
                this.getDbModel() == null
                        || !this.getDbModel().tag()
                        || ObjectUtils.isEmpty(owner)
                        || StrUtils.isBlank(owner.getOwnerType())
                        || ObjectUtils.isEmpty(owner.getOwnerId())
        ) {
            throw new ModelException(ExceptionCodes.REQUEST_ERROR);
        }
        return tagUtils.saveOjbectTags(
                id,
                SysObject.<List<String>>builder()
                        .data(tags)
                        .owner(owner)
                        .object(getObjectInfo())
                        .build()
        );
    }

    /**
     * 保存数据标签
     *
     * @param id
     * @param tags
     * @return
     * @throws ModelException
     */
    @Override
    public ActionResult saveOjbectTags(
            long id,
            List<String> tags
    ) throws ModelException {
        if (
                this.getDbModel() == null
                        || !this.getDbModel().tag()
        ) {
            throw new ModelException(ExceptionCodes.REQUEST_ERROR);
        }
        return saveOjbectTags(
                id,
                tags,
                this.getOwner()
        );
    }

    /**
     * 获取数据标签
     *
     * @param id
     * @param owner
     * @return
     * @throws ModelException
     */
    @Override
    public List<String> getOjbectTags(
            long id,
            Owner owner
    ) throws ModelException {
        if (
                this.getDbModel() == null
                        || !this.getDbModel().tag()
                        || ObjectUtils.isEmpty(owner)
                        || StrUtils.isBlank(owner.getOwnerType())
                        || ObjectUtils.isEmpty(owner.getOwnerId())
        ) {
            throw new ModelException(ExceptionCodes.REQUEST_ERROR);
        }
        return tagUtils.getOjbectTags(
                id,
                SysObject.builder()
                        .owner(owner)
                        .object(getObjectInfo())
                        .build()
        );
    }

    /**
     * 获取数据标签
     *
     * @param id
     * @return
     * @throws ModelException
     */
    @Override
    public List<String> getOjbectTags(
            long id
    ) throws ModelException {
        if (
                this.getDbModel() == null
                        || !this.getDbModel().tag()
        ) {
            throw new ModelException(ExceptionCodes.REQUEST_ERROR);
        }
        return getOjbectTags(id, this.getOwner());
    }

    /**
     * 获取数据可用标签
     *
     * @param id
     * @param owner
     * @return
     * @throws ModelException
     */
    @Override
    public List<TagInfo> getObjectTagList(
            long id,
            Owner owner
    ) throws ModelException {
        if (
                this.getDbModel() == null
                        || !this.getDbModel().tag()
                        || ObjectUtils.isEmpty(owner)
                        || StrUtils.isBlank(owner.getOwnerType())
                        || ObjectUtils.isEmpty(owner.getOwnerId())
        ) {
            throw new ModelException(ExceptionCodes.REQUEST_ERROR);
        }
        return tagUtils.getObjectTagList(
                id,
                SysObject.builder()
                        .owner(owner)
                        .object(getObjectInfo())
                        .build()
        );
    }

    /**
     * 获取数据可用标签
     *
     * @param id
     * @return
     * @throws ModelException
     */
    @Override
    public List<TagInfo> getObjectTagList(
            long id
    ) throws ModelException {
        if (
                this.getDbModel() == null
                        || !this.getDbModel().tag()
        ) {
            throw new ModelException(ExceptionCodes.REQUEST_ERROR);
        }
        return getObjectTagList(
                id,
                getOwner()
        );
    }

    /**
     * 获取标签下数据
     *
     * @param tags
     * @param owner
     * @return
     * @throws ModelException
     */
    @Override
    public List<Long> getObjects(
            List<String> tags,
            Owner owner
    ) throws ModelException {
        if (
                this.getDbModel() == null
                        || !this.getDbModel().tag()
                        || ObjectUtils.isEmpty(owner)
                        || StrUtils.isBlank(owner.getOwnerType())
                        || ObjectUtils.isEmpty(owner.getOwnerId())
        ) {
            throw new ModelException(ExceptionCodes.REQUEST_ERROR);
        }
        return tagUtils.getObjects(
                SysObject.<List<String>>builder()
                        .data(tags)
                        .owner(owner)
                        .object(getObjectInfo())
                        .build()
        );
    }

    /**
     * 获取标签下数据
     *
     * @param tags
     * @return
     * @throws ModelException
     */
    @Override
    public List<Long> getObjects(
            List<String> tags
    ) throws ModelException {
        if (
                this.getDbModel() == null
                        || !this.getDbModel().tag()
        ) {
            throw new ModelException(ExceptionCodes.REQUEST_ERROR);
        }
        return getObjects(tags, this.getOwner());
    }

    @Override
    public String encryptKey() {
        return cryptService.encryptKey();
    }

    /**
     * 加密字符串
     *
     * @param value
     * @return
     */
    @Override
    public String encrypt(String value) {
        return cryptService.encryptString(value);
    }

    /**
     * 解密字符串
     *
     * @param value
     * @return
     */
    @Override
    public String decrypt(String value) {
        return cryptService.decryptString(value);
    }

    @Override
    public String sensitiveColumn(String column) {
        return "AES_DECRYPT(from_base64(" + column + "), '" + encryptKey() + "')";
    }

    // sensitive
    // QueryWrapper
    @Override
    public void sensitiveEq(
            QueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.eq(
                sensitiveColumn(column),
                value
        );
    }

    @Override
    public void sensitiveNe(
            QueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.ne(
                sensitiveColumn(column),
                value
        );
    }

    @Override
    public void sensitiveLike(
            QueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.like(
                sensitiveColumn(column),
                value
        );
    }

    @Override
    public void sensitiveNotLike(
            QueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.notLike(
                sensitiveColumn(column),
                value
        );
    }

    @Override
    public void sensitiveLikeLeft(
            QueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.likeLeft(
                sensitiveColumn(column),
                value
        );
    }

    @Override
    public void sensitiveNotLikeLeft(
            QueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.notLikeLeft(
                sensitiveColumn(column),
                value
        );
    }

    @Override
    public void sensitiveLikeRight(
            QueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.likeRight(
                sensitiveColumn(column),
                value
        );
    }

    @Override
    public void sensitiveNotLikeRight(
            QueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.notLikeRight(
                sensitiveColumn(column),
                value
        );
    }

    // LambdaQueryWrapper
    @Override
    public void sensitiveEq(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " = {0}",
                value.trim()
        );
    }

    @Override
    public void sensitiveNe(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " != {0}",
                value.trim()
        );
    }

    @Override
    public void sensitiveLike(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " LIKE '%" + value.trim() + "%'"
        );
    }

    @Override
    public void sensitiveNotLike(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " NOT LIKE '%" + value.trim() + "%'"
        );
    }

    @Override
    public void sensitiveLikeLeft(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " LIKE '%" + value.trim() + "'"
        );
    }

    @Override
    public void sensitiveNotLikeLeft(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " NOT LIKE '%" + value.trim() + "'"
        );
    }

    @Override
    public void sensitiveLikeRight(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " LIKE '" + value.trim() + "%'"
        );
    }

    @Override
    public void sensitiveNotLikeRight(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " NOT LIKE '" + value.trim() + "%'"
        );
    }

    // UpdateWrapper
    @Override
    public void sensitiveEq(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " = {0}",
                value.trim()
        );
    }

    @Override
    public void sensitiveNe(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " != {0}",
                value.trim()
        );
    }

    @Override
    public void sensitiveLike(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " LIKE '%" + value.trim() + "%'"
        );
    }

    @Override
    public void sensitiveNotLike(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " NOT LIKE '%" + value.trim() + "%'"
        );
    }

    @Override
    public void sensitiveLikeLeft(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " LIKE '%" + value.trim() + "'"
        );
    }

    @Override
    public void sensitiveNotLikeLeft(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " NOT LIKE '%" + value.trim() + "'"
        );
    }

    @Override
    public void sensitiveLikeRight(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " LIKE '" + value.trim() + "%'"
        );
    }

    @Override
    public void sensitiveNotLikeRight(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " NOT LIKE '" + value.trim() + "%'"
        );
    }

    // LambdaUpdateWrapper
    @Override
    public void sensitiveEq(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " = {0}",
                value.trim()
        );
    }

    @Override
    public void sensitiveNe(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " != {0}",
                value.trim()
        );
    }

    @Override
    public void sensitiveLike(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " LIKE '%" + value.trim() + "%'"
        );
    }

    @Override
    public void sensitiveNotLike(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " NOT LIKE '%" + value.trim() + "%'"
        );
    }

    @Override
    public void sensitiveLikeLeft(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " LIKE '%" + value.trim() + "'"
        );
    }

    @Override
    public void sensitiveNotLikeLeft(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " NOT LIKE '%" + value.trim() + "'"
        );
    }

    @Override
    public void sensitiveLikeRight(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " LIKE '" + value.trim() + "%'"
        );
    }

    @Override
    public void sensitiveNotLikeRight(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    ) {
        if (wrapper == null) {
            return;
        }
        wrapper.apply(
                sensitiveColumn(column) + " NOT LIKE '" + value.trim() + "%'"
        );
    }
}
