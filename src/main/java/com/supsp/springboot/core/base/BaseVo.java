package com.supsp.springboot.core.base;

import com.supsp.springboot.core.interfaces.IVo;
import lombok.Data;

import java.io.Serial;

@Data
public abstract class BaseVo implements IVo {
    @Serial
    private static final long serialVersionUID = -4612372138990550140L;
}
