package com.supsp.springboot.core.vo.auth.tenant;

import com.supsp.springboot.core.base.BaseAccountPost;
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
public class TenantAccountPost extends BaseAccountPost implements IAccountPost {
    @Serial
    private static final long serialVersionUID = 500745404444094566L;

}
