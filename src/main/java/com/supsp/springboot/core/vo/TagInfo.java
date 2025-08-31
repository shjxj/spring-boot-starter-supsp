package com.supsp.springboot.core.vo;

import com.supsp.springboot.core.interfaces.IVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "TagInfo", description = "标签信息")
public class TagInfo implements IVo {

    @Serial
    private static final long serialVersionUID = 2082242117781276661L;

    @Schema(title = "名称")
    private String tagName;

    @Schema(title = "是否已选")
    private Boolean isSelected;
}
