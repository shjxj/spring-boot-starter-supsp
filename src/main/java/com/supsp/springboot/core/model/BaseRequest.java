package com.supsp.springboot.core.model;

import lombok.Data;

import java.io.Serial;

@Data
public abstract class BaseRequest implements IRequest {
    @Serial
    private static final long serialVersionUID = 6494661098544891779L;
}
