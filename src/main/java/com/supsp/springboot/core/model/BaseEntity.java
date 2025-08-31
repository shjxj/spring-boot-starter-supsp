package com.supsp.springboot.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseEntity extends BaseData implements IEntity {
    @Serial
    private static final long serialVersionUID = 4150255761840772275L;
    
}
