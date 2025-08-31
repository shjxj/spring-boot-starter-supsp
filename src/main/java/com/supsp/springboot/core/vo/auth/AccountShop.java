package com.supsp.springboot.core.vo.auth;

import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.base.BaseAccountShop;
import com.supsp.springboot.core.interfaces.IAccountShop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@SuperBuilder
@Accessors(chain = true)
@SensitiveData
public class AccountShop extends BaseAccountShop implements IAccountShop {

    @Serial
    private static final long serialVersionUID = -5981860296237318221L;
}
