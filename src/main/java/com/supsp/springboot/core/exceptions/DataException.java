package com.supsp.springboot.core.exceptions;

import lombok.EqualsAndHashCode;
import org.springframework.dao.DataAccessException;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
public class DataException extends DataAccessException {
    @Serial
    private static final long serialVersionUID = -7744179530497441352L;

    public DataException(String msg) {
        super(msg);
    }

    public DataException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
