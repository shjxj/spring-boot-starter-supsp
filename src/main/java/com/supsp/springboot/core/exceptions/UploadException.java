package com.supsp.springboot.core.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class UploadException extends BaseException {
    @Serial
    private static final long serialVersionUID = 5859561324871457559L;

    public UploadException() {
        super();
    }

    public UploadException(String message) {
        super(message);
    }

    public UploadException(String message, int code) {
        super(message, code);
    }

    public UploadException(ExceptionCodes exceptionCode) {
        super(exceptionCode);
    }

    public UploadException(String message, ExceptionCodes exceptionCode) {
        super(message, exceptionCode);
    }

    public UploadException(ExceptionCodes exceptionCode, int code) {
        super(exceptionCode, code);
    }
}
