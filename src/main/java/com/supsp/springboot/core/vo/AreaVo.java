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
@Schema(name = "AreaVo", description = "地区信息")
public class AreaVo implements IVo {
    @Serial
    private static final long serialVersionUID = 5056120725390974891L;

    @Schema(title = "地区")
    private String area;

    @Schema(title = "省ID")
    private Long province;

    @Schema(title = "市ID")
    private Long city;

    @Schema(title = "区ID")
    private Long district;

    @Schema(title = "街道ID")
    private Long street;

    @Schema(title = "社区ID")
    private Long community;

    @Schema(title = "省名称")
    private String provinceName;

    @Schema(title = "市名称")
    private String cityName;

    @Schema(title = "区名称")
    private String districtName;

    @Schema(title = "街道名称")
    private String streetName;

    @Schema(title = "社区名称")
    private String communityName;

    @Schema(title = "最终地区ID")
    private Long regionId;
}
