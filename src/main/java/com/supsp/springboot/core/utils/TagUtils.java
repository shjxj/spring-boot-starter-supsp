package com.supsp.springboot.core.utils;

import com.supsp.springboot.core.base.ActionResult;
import com.supsp.springboot.core.exceptions.ModelException;
import com.supsp.springboot.core.service.ITagActionService;
import com.supsp.springboot.core.vo.SysObject;
import com.supsp.springboot.core.vo.TagInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagUtils {
    @Resource
    private ITagActionService tagActionService;

    /**
     * 保存数据标签
     *
     * @param objectId
     * @param data
     * @return
     * @throws ModelException
     */
    public ActionResult saveOjbectTags(
            long objectId,
            SysObject<List<String>> data
    ) throws ModelException {
        return tagActionService.saveOjbectTags(
                objectId,
                data
        );
    }

    /**
     * 获取数据标签
     *
     * @param objectId
     * @param data
     * @return
     * @throws ModelException
     */
    public List<String> getOjbectTags(
            long objectId,
            SysObject<Object> data
    ) throws ModelException {
        return tagActionService.getOjbectTags(
                objectId,
                data
        );
    }

    /**
     * 获取数据可用标签
     *
     * @param objectId
     * @param data
     * @return
     * @throws ModelException
     */
    public List<TagInfo> getObjectTagList(
            long objectId,
            SysObject<Object> data
    ) throws ModelException {
        return tagActionService.getObjectTagList(
                objectId,
                data
        );
    }

    /**
     * 获取标签下数据
     *
     * @param data
     * @return
     * @throws ModelException
     */
    public List<Long> getObjects(
            SysObject<List<String>> data
    ) throws ModelException {
        return tagActionService.getObjects(
                data
        );
    }
}
