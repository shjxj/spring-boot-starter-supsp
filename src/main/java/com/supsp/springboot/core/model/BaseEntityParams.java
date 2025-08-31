package com.supsp.springboot.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supsp.springboot.core.annotations.DbParamField;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.enums.QueryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
//@Schema(name = "BaseEntityParams", description = "请求参数")
public abstract class BaseEntityParams<T extends BaseModelEntity<?>> extends BaseDataParams<T> implements IEntityParams<T> {
    @Serial
    private static final long serialVersionUID = -6343372234596642930L;

    protected Boolean __API__;

    @Schema(title = "DataId", description = "data id 列表")
    protected List<Long> ids;

    @Schema(title = "搜索关键字", description = "自定义关键字查询, 需自行写查询脚本")
    protected String keyword;

    @Schema(title = "DataId", description = "data id 列表, 配合 keywords")
    protected List<Long> withIds;

    @Schema(title = "搜索关键字", description = "系统关键字查询, 无需手动写查询脚本")
    protected String keywords;

    @Schema(title = "创建时间")
    @DbParamField(property = "createdAt", column = "created_at", type = QueryType.eq)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime createdAt;

    @Schema(title = "创建人类型")
    @DbParamField(property = "createdMemberType", column = "created_member_type", type = QueryType.eq)
    protected AuthMemberType createdMemberType;

    @Schema(title = "创建人ID")
    @DbParamField(property = "createdMemberId", column = "created_member_id", type = QueryType.eq)
    protected Long createdMemberId;

    @Schema(title = "创建人姓名")
    @DbParamField(property = "createdMemberName", column = "created_member_name", type = QueryType.eq)
    protected String createdMemberName;

    @Schema(title = "创建人账号")
    @DbParamField(property = "createdMemberAccount", column = "created_member_account", type = QueryType.eq)
    protected String createdMemberAccount;

    @Schema(title = "创建人IP")
    @DbParamField(property = "createdMemberIp", column = "created_member_ip", type = QueryType.eq)
    protected String createdMemberIp;

    @Schema(title = "更新时间")
    @DbParamField(property = "updatedAt", column = "updated_at", type = QueryType.eq)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime updatedAt;

    @Schema(title = "更新人类型")
    @DbParamField(property = "updatedMemberType", column = "updated_member_type", type = QueryType.eq)
    protected AuthMemberType updatedMemberType;

    @Schema(title = "更新人ID")
    @DbParamField(property = "updatedMemberId", column = "updated_member_id", type = QueryType.eq)
    protected Long updatedMemberId;

    @Schema(title = "更新人姓名")
    @DbParamField(property = "updatedMemberName", column = "updated_member_name", type = QueryType.eq)
    protected String updatedMemberName;

    @Schema(title = "更新人账号")
    @DbParamField(property = "updatedMemberAccount", column = "updated_member_account", type = QueryType.eq)
    protected String updatedMemberAccount;

    @Schema(title = "更新人IP")
    @DbParamField(property = "updatedMemberIp", column = "updated_member_ip", type = QueryType.eq)
    protected String updatedMemberIp;

    @Schema(title = "店铺ID")
    @DbParamField(property = "shopId", column = "shop_id", type = QueryType.eq)
    private Long shopId;

    @Schema(title = "企业ID")
    @DbParamField(property = "orgId", column = "org_id", type = QueryType.eq)
    private Long orgId;

    @Schema(title = "门店ID")
    @DbParamField(property = "storeId", column = "store_id", type = QueryType.eq)
    private Long storeId;

    @Schema(title = "用户ID")
    @DbParamField(property = "memberId", column = "member_id", type = QueryType.eq)
    private Long memberId;

    @Schema(title = "操作人 组织ID")
    private Long userOrgId;

    @Schema(title = "操作人 直属库ID")
    private Long userOrgChildId;

    @Schema(title = "操作人 部门ID")
    private Long userDepartmentId;

    @Schema(title = "操作人 仓房ID")
    private Long userWarehouseId;

    @Schema(title = "操作人 ID")
    private Long uid;

    @Schema(title = "仓房人员")
    private Long warehouseMember;

    @Schema(title = "有效")
    private Boolean isValid;

    @Schema(title = "待办")
    private Boolean isTask;

    @Schema(title = "查询日期类型")
    private String queryTimeType;

    @Schema(title = "日期")
    private LocalDateTime searchDay;

    @Schema(title = "月份")
    private LocalDateTime searchMonth;

    @Schema(title = "年份")
    private LocalDateTime searchYear;

    @Schema(title = "日期区间")
    private List<LocalDate> dateRange;

    @Schema(title = "时间区间")
    private List<LocalTime> timeRange;

    @Schema(title = "时间日期区间")
    private List<LocalDateTime> dateTimeRange;

    @Schema(title = "获取作废数据")
    private Boolean queryInvalid;

    @Schema(title = "获取Options")
    private Boolean isOptions;

    @Schema(title = "获取作废数据")
    private Boolean inFinder;

    @Override
    public Boolean get__API__() {
        return __API__;
    }

    @Override
    public void set__API__(Boolean __API__) {
        this.__API__ = __API__;
    }

    @Override
    public List<Long> getIds() {
        return ids;
    }

    @Override
    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    @Override
    public String getKeyword() {
        return keyword;
    }

    @Override
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public List<Long> getWithIds() {
        return withIds;
    }

    @Override
    public void setWithIds(List<Long> withIds) {
        this.withIds = withIds;
    }

    @Override
    public String getKeywords() {
        return keywords;
    }

    @Override
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public AuthMemberType getCreatedMemberType() {
        return createdMemberType;
    }

    @Override
    public void setCreatedMemberType(AuthMemberType createdMemberType) {
        this.createdMemberType = createdMemberType;
    }

    @Override
    public Long getCreatedMemberId() {
        return createdMemberId;
    }

    @Override
    public void setCreatedMemberId(Long createdMemberId) {
        this.createdMemberId = createdMemberId;
    }

    @Override
    public String getCreatedMemberName() {
        return createdMemberName;
    }

    @Override
    public void setCreatedMemberName(String createdMemberName) {
        this.createdMemberName = createdMemberName;
    }

    @Override
    public String getCreatedMemberAccount() {
        return createdMemberAccount;
    }

    @Override
    public void setCreatedMemberAccount(String createdMemberAccount) {
        this.createdMemberAccount = createdMemberAccount;
    }

    @Override
    public String getCreatedMemberIp() {
        return createdMemberIp;
    }

    @Override
    public void setCreatedMemberIp(String createdMemberIp) {
        this.createdMemberIp = createdMemberIp;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public AuthMemberType getUpdatedMemberType() {
        return updatedMemberType;
    }

    @Override
    public void setUpdatedMemberType(AuthMemberType updatedMemberType) {
        this.updatedMemberType = updatedMemberType;
    }

    @Override
    public Long getUpdatedMemberId() {
        return updatedMemberId;
    }

    @Override
    public void setUpdatedMemberId(Long updatedMemberId) {
        this.updatedMemberId = updatedMemberId;
    }

    @Override
    public String getUpdatedMemberName() {
        return updatedMemberName;
    }

    @Override
    public void setUpdatedMemberName(String updatedMemberName) {
        this.updatedMemberName = updatedMemberName;
    }

    @Override
    public String getUpdatedMemberAccount() {
        return updatedMemberAccount;
    }

    @Override
    public void setUpdatedMemberAccount(String updatedMemberAccount) {
        this.updatedMemberAccount = updatedMemberAccount;
    }

    @Override
    public String getUpdatedMemberIp() {
        return updatedMemberIp;
    }

    @Override
    public void setUpdatedMemberIp(String updatedMemberIp) {
        this.updatedMemberIp = updatedMemberIp;
    }

    @Override
    public Long getShopId() {
        return shopId;
    }

    @Override
    public Long getOrgId() {
        return orgId;
    }

    @Override
    public Long getStoreId() {
        return storeId;
    }

    @Override
    public Long getMemberId() {
        return memberId;
    }

    @Override
    public Long getUid() {
        return uid;
    }

    @Override
    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public Long getUserOrgId() {
        return userOrgId;
    }

    @Override
    public void setUserOrgId(Long userOrgId) {
        this.userOrgId = userOrgId;
    }

    @Override
    public Long getUserOrgChildId() {
        return userOrgChildId;
    }

    @Override
    public void setUserOrgChildId(Long userOrgChildId) {
        this.userOrgChildId = userOrgChildId;
    }

    @Override
    public Long getUserDepartmentId() {
        return userDepartmentId;
    }

    @Override
    public void setUserDepartmentId(Long userDepartmentId) {
        this.userDepartmentId = userDepartmentId;
    }

    @Override
    public Long getUserWarehouseId() {
        return userWarehouseId;
    }

    @Override
    public void setUserWarehouseId(Long userWarehouseId) {
        this.userWarehouseId = userWarehouseId;
    }

    @Override
    public Long getWarehouseMember() {
        return warehouseMember;
    }

    @Override
    public void setWarehouseMember(Long warehouseMember) {
        this.warehouseMember = warehouseMember;
    }

    @Override
    public Boolean getValid() {
        return isValid;
    }

    @Override
    public void setValid(Boolean valid) {
        isValid = valid;
    }

    @Override
    public Boolean getTask() {
        return isTask;
    }

    @Override
    public void setTask(Boolean task) {
        isTask = task;
    }

    @Override
    public Boolean getQueryInvalid() {
        return queryInvalid;
    }

    @Override
    public void setQueryInvalid(Boolean queryInvalid) {
        this.queryInvalid = queryInvalid;
    }

    @Override
    public Boolean getOptions() {
        return isOptions;
    }

    @Override
    public void setOptions(Boolean options) {
        isOptions = options;
    }

    @Override
    public Boolean getInFinder() {
        return inFinder;
    }

    @Override
    public void setInFinder(Boolean inFinder) {
        this.inFinder = inFinder;
    }
}
