package com.supsp.springboot.core.config;

import com.supsp.springboot.core.base.Result;
import com.supsp.springboot.core.exceptions.ActionException;
import com.supsp.springboot.core.exceptions.BaseException;
import com.supsp.springboot.core.exceptions.ExceptionCodes;
import com.supsp.springboot.core.exceptions.ModelException;
import com.supsp.springboot.core.utils.CommonTools;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLException;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
@Hidden
public class WebExceptionHandler {

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        if (!CommonTools.isEnvProduct()) {
            log.error("HttpRequestMethodNotSupportedException exception: ", e);
        }
        return Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统错误");
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException exception", e);
        return Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请稍后再试");
    }

    @ExceptionHandler({BaseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> badRequestExceptionHandler(BaseException e) {
        if (!CommonTools.isEnvProduct()) {
            log.error("BaseException exception: ", e);
        }
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({ActionException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public Result<String> methodFailureExceptionHandler(ActionException e) {
        if (!CommonTools.isEnvProduct()) {
            log.error("ActionException exception: ", e);
        }
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        if (!CommonTools.isEnvProduct()) {
            log.error("MethodArgumentNotValidException exception: ", e);
        }
        FieldError fieldError = e.getBindingResult()
                .getFieldErrors()
                .getFirst();
        // fieldError.getField() +
        return Result.fail(HttpStatus.BAD_REQUEST.value(), fieldError.getDefaultMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        if (!CommonTools.isEnvProduct()) {
            log.error("ConstraintViolationException exception: ", e);
        }
        return Result.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleBusinessException(MaxUploadSizeExceededException e) {
        if (!CommonTools.isEnvProduct()) {
            log.error("MaxUploadSizeExceededException exception: ", e);
        }
        String msg;
        if (e.getCause().getCause() instanceof FileSizeLimitExceededException) {
            // log.error(ex.getMessage());
            msg = "单文件大小不得超过20M";
        } else if (e.getCause().getCause() instanceof SizeLimitExceededException) {
            // log.error(ex.getMessage());
            msg = "总上传文件大小不得超过200M";
        } else {
            msg = "请检查文件类型及大小是否符合规范";
        }
        return Result.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), msg);
    }

    @ExceptionHandler({ModelException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> ServerErrorExceptionHandler(BaseException e) {
        if (!CommonTools.isEnvProduct()) {
            log.error("ServerErrorExceptionHandler exception: ", e);
        }
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({SQLException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> SQLExceptionHandler(SQLException e) {
        if (!CommonTools.isEnvProduct()) {
            log.error("SQLException exception: ", e);
        }
        return Result.fail(ExceptionCodes.SYSTEM_ERROR.getCode(), ExceptionCodes.SYSTEM_ERROR.getMessage());
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> bindExceptionHandler(BindException e) {
        if (!CommonTools.isEnvProduct()) {
            log.error("BindException exception: ", e);
        }
        return Result.fail(ExceptionCodes.SYSTEM_ERROR.getCode(), e.getMessage());
    }
    
    /**
     * 处理JSON解析错误，例如类型不匹配的情况
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        if (!CommonTools.isEnvProduct()) {
            log.error("HttpMessageNotReadableException exception: ", e);
        }
        // 返回友好的错误信息，告知客户端请求参数格式不正确
        return Result.fail(HttpStatus.BAD_REQUEST.value(), "请求参数格式不正确，请检查参数类型和格式");
    }
}
