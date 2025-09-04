package com.supsp.springboot.core.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.io.Serial;

@Setter
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException implements com.supsp.springboot.core.interfaces.IException {
    @Serial
    private static final long serialVersionUID = 885015426250507125L;

    private int code = com.supsp.springboot.core.exceptions.ExceptionCodes.OPERATION_FAILURE.getCode();

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, int code) {
        this(message);
        this.setCode(code);
    }

    public BaseException(ExceptionCodes exceptionCode) {
        this(exceptionCode.getMessage(), exceptionCode.getCode());
    }

    public BaseException(String message, ExceptionCodes exceptionCode) {
        this(message, exceptionCode.getCode());
    }

    public BaseException(ExceptionCodes exceptionCode, int code) {
        this(exceptionCode.getMessage(), code);
    }

}
