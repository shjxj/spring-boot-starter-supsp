package com.supsp.springboot.core.model;

import lombok.Data;

import java.io.Serial;

@Data
public abstract class BaseFilter implements IFilter {
    @Serial
    private static final long serialVersionUID = 7997283590011273179L;
}
