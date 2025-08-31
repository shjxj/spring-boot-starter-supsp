package com.supsp.springboot.core.vo.auth.api;

import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.base.BaseAccountOrg;
import com.supsp.springboot.core.interfaces.IAccountOrg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@SensitiveData
public class ApiAccountOrg extends BaseAccountOrg implements IAccountOrg {
    @Serial
    private static final long serialVersionUID = 7417471829218389318L;
}
