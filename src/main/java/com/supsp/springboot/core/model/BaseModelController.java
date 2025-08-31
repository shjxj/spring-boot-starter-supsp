package com.supsp.springboot.core.model;


import com.supsp.springboot.core.base.ActionResult;
import com.supsp.springboot.core.base.BaseEntityController;
import com.supsp.springboot.core.consts.Constants;
import com.supsp.springboot.core.enums.SysModule;
import com.supsp.springboot.core.exceptions.ModelException;
import com.supsp.springboot.core.helper.AuthCommon;
import com.supsp.springboot.core.helper.SystemCommon;
import com.supsp.springboot.core.utils.StrUtils;
import com.supsp.springboot.core.vo.Owner;
import com.supsp.springboot.core.vo.TagInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseModelController<O extends BaseEntityModel<S, M, T>, S extends BaseEntityServiceImpl<M, T>, M extends IEntityMapper<T>, T extends BaseModelEntity<T>> extends BaseEntityController<S, M, T> implements IModelController<O, S, M, T> {

    @Autowired
    protected O model;

    @Autowired
    protected S service;

    @Autowired
    protected M mapper;

    protected Owner owner;

    protected String ownerType;

    protected Long ownerId;

    @Override
    public Owner getOwner() {
        if (owner == null) {
            this.setOwner(
                    Owner.builder()
                            .ownerType(
                                    this.getOwnerType()
                            )
                            .ownerId(
                                    this.getOwnerId()
                            )
                            .build()
            );
        }
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
                this.setOwnerType(Constants.OWNER_TYPE_API);
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
    public ActionResult doSaveOjbectTags(
            long id,
            List<String> tags,
            Owner owner
    ) throws ModelException {
        return model.saveOjbectTags(
                id,
                tags,
                owner
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
    public ActionResult doSaveOjbectTags(
            long id,
            List<String> tags
    ) throws ModelException {
        return model.saveOjbectTags(
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
    public List<String> doGetOjbectTags(
            long id,
            Owner owner
    ) throws ModelException {
        return model.getOjbectTags(id, owner);
    }

    /**
     * 获取数据标签
     *
     * @param id
     * @return
     * @throws ModelException
     */
    @Override
    public List<String> doGetOjbectTags(
            long id
    ) throws ModelException {
        return model.getOjbectTags(id, this.getOwner());
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
    public List<TagInfo> doGetObjectTagList(
            long id,
            Owner owner
    ) throws ModelException {
        return model.getObjectTagList(
                id, owner
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
    public List<TagInfo> doGetObjectTagList(
            long id
    ) throws ModelException {
        return model.getObjectTagList(
                id, this.getOwner()
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
        return model.getObjects(tags, owner);
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
        return model.getObjects(tags, this.getOwner());
    }
}
