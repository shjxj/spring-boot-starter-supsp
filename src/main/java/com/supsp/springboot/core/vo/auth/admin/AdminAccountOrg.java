package com.supsp.springboot.core.vo.auth.admin;

import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.base.BaseAccountOrg;
import com.supsp.springboot.core.enums.AuthMemberType;
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
@SuperBuilder
@Accessors(chain = true)
@SensitiveData
public class AdminAccountOrg extends BaseAccountOrg implements IAccountOrg {

    @Serial
    private static final long serialVersionUID = -5981860296237318221L;

    @Override
    public AuthMemberType getMemberType(){
        return AuthMemberType.admin;
    }

    @Override
    public void setMemberType(AuthMemberType memberType){

    }
}
