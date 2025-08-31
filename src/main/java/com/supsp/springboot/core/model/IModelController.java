package com.supsp.springboot.core.model;


import com.supsp.springboot.core.base.ActionResult;
import com.supsp.springboot.core.exceptions.ModelException;
import com.supsp.springboot.core.vo.Owner;
import com.supsp.springboot.core.vo.TagInfo;

import java.util.List;

public interface IModelController<O extends BaseEntityModel<S, M, T>, S extends BaseEntityServiceImpl<M, T>, M extends IEntityMapper<T>, T extends BaseModelEntity<T>> extends IEntityController<T> {

    O getModel();

    void setModel(O model);

    S getService();

    void setService(S service);

    M getMapper();

    void setMapper(M mapper);

    Owner getOwner();

    void setOwner(Owner owner);

    String getOwnerType();

    void setOwnerType(String ownerType);

    Long getOwnerId();

    void setOwnerId(Long ownerId);

    ActionResult doSaveOjbectTags(
            long id,
            List<String> tags,
            Owner owner
    ) throws ModelException;

    ActionResult doSaveOjbectTags(
            long id,
            List<String> tags
    ) throws ModelException;

    List<String> doGetOjbectTags(
            long id,
            Owner owner
    ) throws ModelException;

    List<String> doGetOjbectTags(
            long id
    ) throws ModelException;

    List<TagInfo> doGetObjectTagList(
            long id,
            Owner owner
    ) throws ModelException;

    List<TagInfo> doGetObjectTagList(
            long id
    ) throws ModelException;

    List<Long> getObjects(
            List<String> tags,
            Owner owner
    ) throws ModelException;

    List<Long> getObjects(
            List<String> tags
    ) throws ModelException;
}
