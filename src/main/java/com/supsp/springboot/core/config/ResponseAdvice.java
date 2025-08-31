package com.supsp.springboot.core.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supsp.springboot.core.base.ActionResult;
import com.supsp.springboot.core.base.Result;
import com.supsp.springboot.core.utils.CommonTools;
import com.supsp.springboot.core.utils.StrUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
@Hidden
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(
            MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType
    ) {
//        if (!CommonTools.isEnvProduct()) {
//            log.debug(
//                    "\n■■■■ ResponseAdvice supports\n{}\n{}",
//                    returnType.getClass().getName(),
//                    converterType.getName()
//            );
//        }
        return converterType.equals(MappingJackson2HttpMessageConverter.class) || converterType.equals(StringHttpMessageConverter.class);
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
//        if (!CommonTools.isEnvProduct()) {
//            log.debug(
//                    "\n■■■■ ResponseAdvice beforeBodyWrite\n{}\n{}\n{}\n{}\n{}",
//                    request.getURI().getPath(),
//                    returnType,
//                    selectedContentType,
//                    selectedConverterType.getName(),
//                    JsonUtil.toJSONString(body)
//            );
//        }
        String urlPath = request.getURI().getPath();
        if (
                StrUtils.isNotBlank(urlPath)
        ) {
            if (
                    urlPath.startsWith("/swagger-ui")
                            || urlPath.startsWith("/v2/api-docs/")
                            || urlPath.startsWith("/v3/api-docs/")
                            || urlPath.startsWith("/doc.html")
                            || urlPath.startsWith("/webjars")
            ) {
                return body;
            }
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (body != null) {
            if (
                    body.getClass().equals(Result.class)
                            || body.getClass().equals(ActionResult.class)
            ) {
                return body;
            }
            if (body instanceof String) {
                if (CommonTools.isJSONString((String) body)) {
                    return body;
                }
                return toStringResult(body);
            }
        }
        return Result.success(body);
    }

    private Object toStringResult(Object body) {
        try {
            return new ObjectMapper().writeValueAsString(Result.success(body));
        } catch (JsonProcessingException e) {
            if (!CommonTools.isEnvProduct()) {
                log.error(
                        "\n■■■■ 无法转发json格式\n{}\n",
                        body.getClass(),
                        e
                );
            }
            // throw new RuntimeException("无法转发json格式", e);
        }
        return null;
    }
}
