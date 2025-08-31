package com.supsp.springboot.core.vo.auth;

import com.supsp.springboot.core.base.BaseAccountPost;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.interfaces.IAccountPost;
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
public class AccountPost extends BaseAccountPost implements IAccountPost {
    @Serial
    private static final long serialVersionUID = 3764628638683580313L;

    @Override
    public AuthMemberType getMemberType(){
        return AuthMemberType.admin;
    }

    @Override
    public void setMemberType(AuthMemberType memberType){

    }
}
