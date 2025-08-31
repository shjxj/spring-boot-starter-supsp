package com.supsp.springboot.core.vo.auth.merchant;

import com.supsp.springboot.core.base.BaseAccountRole;
import com.supsp.springboot.core.interfaces.IAccountRole;
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
public class MerchantAccountRole extends BaseAccountRole implements IAccountRole {
    @Serial
    private static final long serialVersionUID = 4081295385080406965L;

}
