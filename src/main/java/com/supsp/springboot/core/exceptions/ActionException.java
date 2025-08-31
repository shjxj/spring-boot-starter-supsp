package com.supsp.springboot.core.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActionException extends BaseException {
    @Serial
    private static final long serialVersionUID = -7808423040901326019L;

    public ActionException() {
        super();
    }

    public ActionException(String message) {
        super(message);
    }

    public ActionException(String message, int code) {
        super(message, code);
    }

    public ActionException(ExceptionCodes exceptionCode) {
        super(exceptionCode);
    }

    public ActionException(String message, ExceptionCodes exceptionCode) {
        super(message, exceptionCode);
    }

    public ActionException(ExceptionCodes exceptionCode, int code) {
        super(exceptionCode, code);
    }
}
