package com.supsp.springboot.core.vo.auth.admin;

import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.base.BaseAccountStore;
import com.supsp.springboot.core.enums.AuthMemberType;
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
public class AdminAccountStore extends BaseAccountStore implements IAccountStore {
    @Serial
    private static final long serialVersionUID = -1601893407495568174L;

    @Override
    public Boolean getDefault() {
        return null;
    }

    @Override
    public void setDefault(Boolean aDefault) {

    }

    @Override
    public AuthMemberType getMemberType() {
        return AuthMemberType.admin;
    }

    @Override
    public void setMemberType(AuthMemberType memberType) {
        this.memberType = AuthMemberType.admin;
    }
}
