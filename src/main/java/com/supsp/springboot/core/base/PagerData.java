package com.supsp.springboot.core.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supsp.springboot.core.consts.Constants;
import com.supsp.springboot.core.interfaces.IPagerData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Accessors(chain = true)
//@Schema(name = "PagerData", description = "分页列表")
public class PagerData<T> implements IPagerData<T> {
    @Serial
    private static final long serialVersionUID = -6365534719863528307L;

    @Schema(title = "success")
    private boolean success = true;

    @Schema(title = "当前页数", defaultValue = "1")
    @JsonProperty("current")
    private long currentPage = 1;

    @Schema(title = "每页数量", defaultValue = "10")
    private long pageSize = Constants.DEFAULT_PAGE_SIZE;

    @Schema(title = "当前记录总量")
    private long total = 0;

    @Schema(title = "总页数")
    private long pageTotal = 1;

    @Schema(title = "数据结果")
    private List<T> data;

    @Schema(title = "扩展数据")
    private Object ext;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public List<T> getData() {
        return data;
    }

    @Override
    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public long getCurrentPage() {
        return currentPage;
    }

    @Override
    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public long getPageSize() {
        return pageSize;
    }

    @Override
    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public long getPageTotal() {
        return pageTotal;
    }

    @Override
    public void setPageTotal(long pageTotal) {
        this.pageTotal = pageTotal;
    }

    @Override
    public Object getExt() {
        return ext;
    }

    @Override
    public void setExt(Object ext) {
        this.ext = ext;
    }
}
