package com.supsp.springboot.core.model;

import com.supsp.springboot.core.interfaces.IData;
import lombok.Data;

import java.io.Serial;

@Data
public abstract class BaseData implements IData {
    @Serial
    private static final long serialVersionUID = -6890895335506240232L;
}
