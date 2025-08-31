package com.supsp.springboot.core.vo.auth.consumer;

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
@Accessors(chain = true)
@SuperBuilder
@SensitiveData
public class ConsumerAccountShop extends BaseAccountShop implements IAccountShop {
    @Serial
    private static final long serialVersionUID = 7417471829218389318L;
}
