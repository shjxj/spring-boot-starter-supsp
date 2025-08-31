package com.supsp.springboot.core.exceptions;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthException extends BaseException {
    @Serial
    private static final long serialVersionUID = -9172932977365928358L;

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, int code) {
        super(message, code);
    }

    public AuthException(ExceptionCodes exceptionCode) {
        super(exceptionCode);
    }

    public AuthException(String message, ExceptionCodes exceptionCode) {
        super(message, exceptionCode);
    }

    public AuthException(ExceptionCodes exceptionCode, int code) {
        super(exceptionCode, code);
    }
}
