package com.supsp.springboot.core.service.impl;


import com.supsp.springboot.core.base.ActionResult;
import com.supsp.springboot.core.exceptions.ModelException;
import com.supsp.springboot.core.service.ITagActionService;
import com.supsp.springboot.core.vo.SysObject;
import com.supsp.springboot.core.vo.TagInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagActionServiceImpl implements ITagActionService {

    @Override
    public ActionResult saveOjbectTags(long objectId, SysObject<List<String>> data) throws ModelException {
        return ActionResult.success(true);
    }

    @Override
    public List<String> getOjbectTags(long objectId, SysObject<Object> data) throws ModelException {
        return null;
    }

    @Override
    public List<TagInfo> getObjectTagList(long objectId, SysObject<Object> data) throws ModelException {
        return null;
    }

    @Override
    public List<Long> getObjects(SysObject<List<String>> data) throws ModelException {
        return null;
    }
}
