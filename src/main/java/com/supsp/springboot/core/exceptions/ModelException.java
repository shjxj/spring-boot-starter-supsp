package com.supsp.springboot.core.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class ModelException extends BaseException {
    @Serial
    private static final long serialVersionUID = -3170056867688990875L;

    public ModelException() {
        super();
    }

    public ModelException(String message) {
        super(message);
    }

    public ModelException(String message, int code) {
        super(message, code);
    }

    public ModelException(ExceptionCodes exceptionCode) {
        super(exceptionCode);
    }

    public ModelException(String message, ExceptionCodes exceptionCode) {
        super(message, exceptionCode);
    }

    public ModelException(ExceptionCodes exceptionCode, int code) {
        super(exceptionCode, code);
    }
}
