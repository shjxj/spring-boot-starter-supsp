package com.supsp.springboot.core.vo.auth;

import com.supsp.springboot.core.base.BaseAccountRole;
import com.supsp.springboot.core.enums.AuthMemberType;
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
public class AccountRole extends BaseAccountRole implements IAccountRole {
    @Serial
    private static final long serialVersionUID = 8051818851338646098L;

    @Override
    public AuthMemberType getMemberType(){
        return AuthMemberType.admin;
    }

    @Override
    public void setMemberType(AuthMemberType memberType){

    }
}
