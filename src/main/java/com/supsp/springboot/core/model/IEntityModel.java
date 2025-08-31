package com.supsp.springboot.core.model;

import cn.hutool.core.lang.func.Func1;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.supsp.springboot.core.annotations.DBEntity;
import com.supsp.springboot.core.annotations.DbModel;
import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.base.ActionResult;
import com.supsp.springboot.core.base.PagerData;
import com.supsp.springboot.core.enums.EnableStatus;
import com.supsp.springboot.core.enums.OrderType;
import com.supsp.springboot.core.enums.ShowStatus;
import com.supsp.springboot.core.exceptions.ModelException;
import com.supsp.springboot.core.service.IIdGeneratorService;
import com.supsp.springboot.core.vo.ObjectInfo;
import com.supsp.springboot.core.vo.Owner;
import com.supsp.springboot.core.vo.TagInfo;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface IEntityModel<S extends BaseEntityServiceImpl<M, T>, M extends IEntityMapper<T>, T extends BaseModelEntity<T>> extends IModel<T> {

    S getService();

    M getMapper();

    String getAppName();

    IIdGeneratorService getIdGenerator();

    Class<T> getEntityClass();

    TableInfo getTableInfo();

    String getTableName();

    String tmpOperationName(String operation);

    String tmpOperationCreateName();

    String tmpOperationEditName();

    List<TableFieldInfo> getFieldInfos();

    HashMap<String, TableFieldInfo> getFields();

    HashMap<String, String> getFieldsProperty();

    HashMap<String, String> getFieldsColumn();

    HashMap<String, String> getPropertyName();

    HashMap<String, Boolean> getSensitiveFields();

    void setSensitiveFields(HashMap<String, Boolean> sensitiveFields);

    HashMap<String, String> getKeywordsFields();

    void setKeywordsFields(HashMap<String, String> keywordsFields);

    HashMap<String, String> getSelectColumn();

    HashMap<String, String> getDefaultSelectColumn();

    HashMap<String, String> getApiSelectColumn();

    HashMap<String, String> getDefaultApiSelectColumn();

    boolean isFieldProperty(String property);

    boolean isFieldColumn(String column);

    String getFieldColumnByProperty(String property);

    String getFieldPropertyByColumn(String column);

    <X> String getFieldPropertyFromLambda(Func1<X, ?> func);

    <X> String getFieldColumnFromLambda(Func1<X, ?> func);

    TableFieldInfo getFieldInfoByProperty(String property);

    TableFieldInfo getFieldInfoByColumn(String column);

    DbModel getDbModel();

    void setDbModel(DbModel dbModel);

    DBEntity getDbEntity();

    void setDbEntity(DBEntity dbEntity);

    SensitiveData getDbSensitive();

    void setDbSensitive(SensitiveData dbSensitive);

    Boolean getSensitive();

    void setSensitive(Boolean sensitive);

    HashMap<String, String> getDataIdMapProperty();

    void setDataIdMapProperty(HashMap<String, String> dataIdMapProperty);

    HashMap<String, String> getDataIdMapColumn();

    void setDataIdMapColumn(HashMap<String, String> dataIdMapColumn);

    String getDataIdProperty();

    void setDataIdProperty(String dataIdProperty);

    String getDataIdColumn();

    void setDataIdColumn(String dataIdColumn);

    boolean hasColumn(String column);

    boolean hasProperty(String property);

    void setProperty(Object bean, String name, Object value);

    Owner getOwner();

    void setOwner(Owner owner);

    String getOwnerType();

    void setOwnerType(String ownerType);

    Long getOwnerId();

    void setOwnerId(Long ownerId);

    String getObjectName();

    void setObjectName(String sysObject);

    String cacheKey(@NotBlank String name);

    String cacheKey(@NotBlank String name, long id);

    String cacheKey(@NotBlank String name, Long id);

    String allCacheKey();

    String dataCacheKey(long id);

    String dataCacheKey(Long id);

    Long countById(Long id);

    boolean idExists(Long id);

    long applyID();

    LambdaQueryWrapper<T> getLambdaQueryWrapper();

    QueryWrapper<T> getQueryWrapper();

    LambdaUpdateWrapper<T> getLambdaUpdateWrapper();

    UpdateWrapper<T> getUpdateWrapper();

    <E> MPJLambdaWrapper<E> getMPJLambdaWrapper(Class<E> tClass);

    MPJLambdaWrapper<T> getMPJLambdaWrapper();

    MPJLambdaWrapper<T> getMPJLambdaWrapper(String alias);

    void queryWrapperTags(
            QueryWrapper<T> wrapper,
            @Nullable IEntityParams<T> params
    );

    void queryWrapperTags(
            MPJLambdaWrapper<T> wrapper,
            String alias,
            @Nullable IEntityParams<T> params
    );

    void queryWrapperTags(
            MPJLambdaWrapper<T> wrapper,
            @Nullable IEntityParams<T> params
    );

    void scopeQueryWrapper(
            QueryWrapper<T> wrapper
    );

    void scopeQueryWrapper(
            MPJLambdaWrapper<T> wrapper,
            String alias
    );

    void scopeQueryWrapper(
            MPJLambdaWrapper<T> wrapper
    );

    void wrapperKeyword(QueryWrapper<T> wrapper, @Nullable IEntityParams<T> params);

    void wrapperKeyword(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityParams<T> params);

    void wrapperKeyword(MPJLambdaWrapper<T> wrapper, @Nullable IEntityParams<T> params);

    void queryWrapperIds(
            QueryWrapper<T> wrapper,
            @Nullable IEntityParams<T> params
    );

    void queryWrapperIds(
            MPJLambdaWrapper<T> wrapper,
            String alias,
            @Nullable IEntityParams<T> params
    );

    void queryWrapperIds(
            MPJLambdaWrapper<T> wrapper,
            @Nullable IEntityParams<T> params
    );

    void queryWrapperKeywords(QueryWrapper<T> wrapper, @Nullable IEntityParams<T> params);

    void queryWrapperKeywords(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityParams<T> params);

    void queryWrapperKeywords(MPJLambdaWrapper<T> wrapper, @Nullable IEntityParams<T> params);

    void wrapperParamsBase(QueryWrapper<T> wrapper, @Nullable IEntityParams<T> params);

    void wrapperParamsBase(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityParams<T> params);

    void wrapperParamsBase(MPJLambdaWrapper<T> wrapper, @Nullable IEntityParams<T> params);

    void wrapperFilter(QueryWrapper<T> wrapper, @Nullable IEntityFilter<T> filter);

    void filterBase(QueryWrapper<T> wrapper, @Nullable IEntityFilter<T> filter);

    void filterBase(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityFilter<T> filter);

    void filterBase(MPJLambdaWrapper<T> wrapper, @Nullable IEntityFilter<T> filter);

    void wrapperFilterBase(QueryWrapper<T> wrapper, @Nullable IEntityFilter<T> filter);

    void wrapperFilterBase(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityFilter<T> filter);

    void wrapperFilterBase(MPJLambdaWrapper<T> wrapper, @Nullable IEntityFilter<T> filter);

    void wrapperSorter(QueryWrapper<T> wrapper, @Nullable HashMap<String, OrderType> sorter);

    void wrapperSorter(MPJLambdaWrapper<T> wrapper, String alias, @Nullable HashMap<String, OrderType> sorter);

    void wrapperSorter(MPJLambdaWrapper<T> wrapper, @Nullable HashMap<String, OrderType> sorter);

    void wrapperParams(QueryWrapper<T> wrapper, @Nullable IEntityParams<T> params);

    void wrapperParams(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityParams<T> params);

    void wrapperParams(MPJLambdaWrapper<T> wrapper, @Nullable IEntityParams<T> params);

    void wrapperFilter(MPJLambdaWrapper<T> wrapper, String alias, @Nullable IEntityFilter<T> filter);

    void wrapperFilter(MPJLambdaWrapper<T> wrapper, @Nullable IEntityFilter<T> filter);

    ActionResult setEnableStatus(long id, EnableStatus status) throws ModelException;

    ActionResult batchSetEnableStatus(Set<Long> idList, EnableStatus status) throws ModelException;

    ActionResult enable(long id) throws ModelException;

    ActionResult batchEnable(Set<Long> idList) throws ModelException;

    ActionResult disable(long id) throws ModelException;

    ActionResult batchDisable(Set<Long> idList) throws ModelException;

    ActionResult setShowStatus(long id, ShowStatus status) throws ModelException;

    ActionResult batchSetShowStatus(Set<Long> idList, ShowStatus status) throws ModelException;

    ActionResult show(long id) throws ModelException;

    ActionResult batchShow(Set<Long> idList) throws ModelException;

    ActionResult hidden(long id) throws ModelException;

    ActionResult batchHidden(Set<Long> idList) throws ModelException;

    ActionResult delete(long id) throws ModelException;

    ActionResult batchDelete(Set<Long> idList) throws ModelException;

    ActionResult setOrder(long id, int order) throws ModelException;

    ActionResult batchSetOrder(HashMap<Long, Integer> data) throws ModelException;

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> IPage<T> pageInstance(@Nullable R entityRequest);

    <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>> IPage<E> getPageInstance(@Nullable R entityRequest) throws ModelException;

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> QueryWrapper<T> queryWrapper(@Nullable R entityRequest);

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> MPJLambdaWrapper<T> MPJQueryWrapper(String alias, @Nullable R entityRequest);

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> MPJLambdaWrapper<T> MPJQueryWrapper(@Nullable R entityRequest);

    List<String> getAllColumnList();

    List<String> getDefaultSelectColumnList();

    List<String> getAllSelectColumnList();

    List<String> getAllPropertyList();

    Boolean propertySensitive(String property);

    Boolean columnSensitive(String column);

    boolean propertyIsSensitive(String property);

    boolean columnIsSensitive(String column);

    boolean propertyIsKeywords(String property);

    boolean columnIsKeywords(String column);

    List<String> getDefaultSelectPropertyList();

    List<String> getAllSelectPropertyList();

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> List<String> getSelectPropertyList(@Nullable R entityRequest);

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> List<String> getSelectColumnList(@Nullable R entityRequest);

    void wrapperSelectByColumn(QueryWrapper<T> wrapper, @Nullable List<String> columnList);

    void wrapperSelectByColumn(MPJLambdaWrapper<T> wrapper, String alias, @Nullable List<String> columnList);

    void wrapperSelectByColumn(MPJLambdaWrapper<T> wrapper, @Nullable List<String> columnList);

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> void wrapperColumnSelect(QueryWrapper<T> wrapper, @Nullable R entityRequest);

    void wrapperColumnSelect(QueryWrapper<T> wrapper);

    void wrapperSelectByProperty(LambdaQueryWrapper<T> wrapper, @Nullable List<String> propertyList);

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> void wrapperColumnSelect(LambdaQueryWrapper<T> wrapper, @Nullable R entityRequest);

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> void wrapperColumnSelect(LambdaQueryWrapper<T> wrapper);

    T detail(long id) throws ModelException;

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> PagerData<T> queryList(QueryWrapper<T> queryWrapper, @Nullable R entityRequest) throws ModelException;

    // S extends BaseEntityServiceImpl<M, T>
    <E extends BaseModelEntity<E>, ES extends BaseEntityServiceImpl<? extends BaseMapper<E>, E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>> PagerData<E> queryList(QueryWrapper<E> queryWrapper, @Nullable R entityRequest, ES entityService) throws ModelException;

    <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, EM extends IEntityMapper<E>> PagerData<E> queryList(MPJLambdaWrapper<E> queryWrapper, @Nullable R entityRequest, Class<E> entityClz, EM entityMapper) throws ModelException;

    <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, ES extends IEntityService<E>> PagerData<E> queryList(MPJLambdaWrapper<E> queryWrapper, @Nullable R entityRequest, Class<E> entityClz, ES entityService) throws ModelException;

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> PagerData<T> queryList(QueryWrapper<T> queryWrapper) throws ModelException;

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> PagerData<T> queryList(@Nullable R entityRequest) throws ModelException;

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> List<T> query(QueryWrapper<T> queryWrapper, @Nullable R entityRequest) throws ModelException;

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> List<T> queryAll(QueryWrapper<T> queryWrapper, @Nullable R entityRequest) throws ModelException;

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> List<T> query(QueryWrapper<T> queryWrapper) throws ModelException;

    <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, EM extends IEntityMapper<E>> List<E> query(MPJLambdaWrapper<E> queryWrapper, Class<E> entityClz, EM entityMapper) throws ModelException;

    <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, EM extends IEntityMapper<E>> List<E> query(MPJLambdaWrapper<E> queryWrapper, @Nullable R entityRequest, Class<E> entityClz, EM entityMapper) throws ModelException;

    <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, ES extends IEntityService<E>> List<E> query(MPJLambdaWrapper<E> queryWrapper, @Nullable R entityRequest, Class<E> entityClz, ES entityService) throws ModelException;

    <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, EM extends IEntityMapper<E>> List<E> queryAll(MPJLambdaWrapper<E> queryWrapper, Class<E> entityClz, EM entityMapper) throws ModelException;

    <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, ES extends IEntityService<E>> List<E> queryAll(MPJLambdaWrapper<E> queryWrapper, Class<E> entityClz, ES entityService) throws ModelException;

    <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, EM extends IEntityMapper<E>> List<E> queryAll(MPJLambdaWrapper<E> queryWrapper, @Nullable R entityRequest, Class<E> entityClz, EM entityMapper) throws ModelException;

    <E extends BaseModelEntity<E>, R extends BaseEntityRequest<? extends BaseEntityParams<E>, ? extends BaseEntityFilter<E>, E>, ES extends IEntityService<E>> List<E> queryAll(MPJLambdaWrapper<E> queryWrapper, @Nullable R entityRequest, Class<E> entityClz, ES entityService) throws ModelException;

    <R extends BaseEntityRequest<? extends BaseEntityParams<T>, ? extends BaseEntityFilter<T>, T>> List<T> query(@Nullable R entityRequest) throws ModelException;

    ActionResult create(T data) throws ModelException;

    ActionResult edit(long id, T data) throws ModelException;

    ObjectInfo getObjectInfo();

    ActionResult saveOjbectTags(
            long id,
            List<String> tags,
            Owner owner
    ) throws ModelException;

    ActionResult saveOjbectTags(
            long id,
            List<String> tags
    ) throws ModelException;

    List<String> getOjbectTags(
            long id,
            Owner owner
    ) throws ModelException;

    List<String> getOjbectTags(
            long id
    ) throws ModelException;

    List<TagInfo> getObjectTagList(
            long id
    ) throws ModelException;

    List<TagInfo> getObjectTagList(
            long id,
            Owner owner
    ) throws ModelException;

    List<Long> getObjects(
            List<String> tags,
            Owner owner
    ) throws ModelException;

    List<Long> getObjects(
            List<String> tags
    ) throws ModelException;

    String encryptKey();

    String encrypt(String value);

    String decrypt(String value);

    String sensitiveColumn(String column);

    // sensitive
    // QueryWrapper
    void sensitiveEq(
            QueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNe(
            QueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveLike(
            QueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNotLike(
            QueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveLikeLeft(
            QueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNotLikeLeft(
            QueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveLikeRight(
            QueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNotLikeRight(
            QueryWrapper<?> wrapper,
            String column,
            String value
    );

    // LambdaQueryWrapper
    void sensitiveEq(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNe(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveLike(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNotLike(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveLikeLeft(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNotLikeLeft(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveLikeRight(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNotLikeRight(
            LambdaQueryWrapper<?> wrapper,
            String column,
            String value
    );

    // UpdateWrapper
    void sensitiveEq(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNe(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveLike(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNotLike(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveLikeLeft(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNotLikeLeft(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveLikeRight(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNotLikeRight(
            UpdateWrapper<?> wrapper,
            String column,
            String value
    );

    // LambdaUpdateWrapper
    void sensitiveEq(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNe(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveLike(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNotLike(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveLikeLeft(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNotLikeLeft(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveLikeRight(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    );

    void sensitiveNotLikeRight(
            LambdaUpdateWrapper<?> wrapper,
            String column,
            String value
    );
}
