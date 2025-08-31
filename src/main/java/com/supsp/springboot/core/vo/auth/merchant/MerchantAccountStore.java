package com.supsp.springboot.core.vo.auth.merchant;

import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.base.BaseAccountStore;
import com.supsp.springboot.core.interfaces.IAccountStore;
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
public class MerchantAccountStore extends BaseAccountStore implements IAccountStore {

    @Serial
    private static final long serialVersionUID = 4832533379682628933L;

}
