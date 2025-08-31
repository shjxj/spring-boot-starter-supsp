package com.supsp.springboot.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supsp.springboot.core.consts.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
//@Schema(name = "BaseParams", description = "请求参数")
public abstract class BaseParams implements IParams {
    @Serial
    private static final long serialVersionUID = -1887076800415914426L;

    @Schema(title = "当前页数", defaultValue = "1")
    @JsonProperty("current")
    protected long currentPage = Constants.DEFAULT_CURRENT_PAGE;

    @Schema(title = "每页数量", defaultValue = "20")
    protected long pageSize = Constants.DEFAULT_PAGE_SIZE;

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
}
