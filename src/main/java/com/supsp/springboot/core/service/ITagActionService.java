package com.supsp.springboot.core.service;



import com.supsp.springboot.core.base.ActionResult;
import com.supsp.springboot.core.exceptions.ModelException;
import com.supsp.springboot.core.vo.SysObject;
import com.supsp.springboot.core.vo.TagInfo;

import java.util.List;

public interface ITagActionService {
    ActionResult saveOjbectTags(
            long objectId,
            SysObject<List<String>> data
    ) throws ModelException;

    List<String> getOjbectTags(
            long objectId,
            SysObject<Object> data
    ) throws ModelException;

    List<TagInfo> getObjectTagList(
            long objectId,
            SysObject<Object> data
    ) throws ModelException;

    List<Long> getObjects(
            SysObject<List<String>> data
    ) throws ModelException;
}
