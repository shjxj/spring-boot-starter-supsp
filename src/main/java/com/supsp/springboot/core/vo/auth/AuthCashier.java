package com.supsp.springboot.core.vo.auth;

import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.interfaces.IData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@SensitiveData
public class AuthCashier implements IData {
    @Serial
    private static final long serialVersionUID = 7210185945136309807L;

    @Schema(title = "收银员ID")
    private Long id;

    @Schema(title = "收银员姓名")
    private String name;

    @Schema(title = "收银员手机")
    private String mobile;
}
