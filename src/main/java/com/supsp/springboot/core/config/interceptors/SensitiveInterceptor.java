package com.supsp.springboot.core.config.interceptors;

import com.baomidou.mybatisplus.annotation.TableId;
import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.exceptions.ExceptionCodes;
import com.supsp.springboot.core.exceptions.ModelException;
import com.supsp.springboot.core.service.ISensitiveCryptService;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.StrUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Intercepts({
        @Signature(
                type = Executor.class,
                method = "query",
                args = {
                        MappedStatement.class,
                        Object.class,
                        RowBounds.class,
                        ResultHandler.class
                }
        ),
        @Signature(
                type = Executor.class,
                method = "query",
                args = {
                        MappedStatement.class,
                        Object.class,
                        RowBounds.class,
                        ResultHandler.class,
                        CacheKey.class,
                        BoundSql.class
                }
        ),
        @Signature(
                type = Executor.class,
                method = "queryCursor",
                args = {
                        MappedStatement.class,
                        Object.class,
                        RowBounds.class
                }
        ),
        @Signature(
                type = Executor.class,
                method = "update",
                args = {
                        MappedStatement.class,
                        Object.class
                }
        )
})
@Component
@Slf4j
public class SensitiveInterceptor implements Interceptor {

    @Resource
    private ISensitiveCryptService sensitiveCryptService;

    private static final String PARAM_ET = "et";

    public boolean needToCrypt(Object object) {
        if (object == null) {
            return false;
        }
        Class<?> clazz = object.getClass();
        if (clazz.isPrimitive() || object instanceof String) {
            return false;
        }
        return clazz.isAnnotationPresent(SensitiveData.class);
    }

    private Object proceed(Invocation invocation, MappedStatement mappedStatement, Object parameter) throws ModelException {
        Object newParameter = sensitiveCryptService.encrypt(parameter);
        try {
            Object result = invocation.proceed();
            reductionParameter(mappedStatement, newParameter, parameter);
            return result;
        } catch (InvocationTargetException | IllegalAccessException e) {
//            log.error(
//                    "reductionParameter error: {} " + e ,
//                    parameter
//            );
//            e.printStackTrace();
            throw new ModelException(ExceptionCodes.OPERATION_FAILURE);
        }
    }

    private void reductionParameter(MappedStatement mappedStatement, Object newParameter, Object parameter) throws ModelException {
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (SqlCommandType.INSERT == sqlCommandType) {
            Field[] parameterFields = parameter.getClass().getDeclaredFields();
            Optional<Field> optional = Arrays.stream(parameterFields)
                    .filter(field -> field.isAnnotationPresent(TableId.class))
                    .findAny();
            if (optional.isPresent()) {
                try {
                    Field field = optional.get();
                    field.setAccessible(true);
                    Object id = field.get(parameter);
                    BeanUtils.copyProperties(newParameter, parameter);
                    field.set(parameter, id);
                } catch (IllegalAccessException e) {
//                    log.error(
//                            "reductionParameter error: " + e
//                    );
//                    e.printStackTrace();
                    throw new ModelException(ExceptionCodes.OPERATION_FAILURE);
                }
            } else {
                BeanUtils.copyProperties(newParameter, parameter);
            }
        } else {
            BeanUtils.copyProperties(newParameter, parameter);
        }
    }

    private Object updateIntercept(Invocation invocation) throws Throwable {
//        log.debug(
//                "■■■■ UPDATE INTERCEPT: {} {}",
//                invocation.getTarget().getClass(),
//                invocation.getTarget() instanceof ParameterHandler
//        );
//        if (!(invocation.getTarget() instanceof ParameterHandler)){
//            return invocation.proceed();
//        }
        Object[] args = invocation.getArgs();
        Object parameter = args[1];
        MappedStatement mappedStatement = (MappedStatement) args[0];
        if (ObjectUtils.isEmpty(parameter)) {
            return invocation.proceed();
        }

        if (parameter instanceof MapperMethod.ParamMap) {
            @SuppressWarnings("unchecked")
            Map<String, Object> paramMap = ((Map<String, Object>) parameter);
            if (
                    ObjectUtils.isEmpty(paramMap)
                            || !paramMap.containsKey(PARAM_ET)
            ) {
                return invocation.proceed();
            }
            Object updateParameter = paramMap.get(PARAM_ET);
            if (needToCrypt(updateParameter)) {
                return proceed(invocation, mappedStatement, updateParameter);
            }
        } else if (needToCrypt(parameter)) {
            return proceed(invocation, mappedStatement, parameter);
        }
        return invocation.proceed();
    }

    private Object queryIntercept(Invocation invocation) throws Throwable {
        //取出查询的结果
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }
        if (resultObject instanceof ArrayList) {
            @SuppressWarnings("unchecked")
            ArrayList<Objects> resultList = (ArrayList<Objects>) resultObject;
            if (
                    CommonUtils.isNotEmpty(resultList)
                            && needToCrypt(resultList.getFirst())
            ) {
                for (Object result : resultList) {
                    sensitiveCryptService.decrypt(result);
                }
            }
        } else {
            if (needToCrypt(resultObject)) {
                sensitiveCryptService.decrypt(resultObject);
            }
        }
        return resultObject;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
//        Object[] args = invocation.getArgs();
//        Object parameter = args[1];
//        MappedStatement mappedStatement = (MappedStatement) args[0];
        String methodName = invocation.getMethod().getName();
        // BoundSql sql = mappedStatement.getBoundSql(parameter);
        switch (methodName) {
            case "insert", "update" -> {
                return updateIntercept(invocation);
            }
            default -> {
                if (StrUtils.startsWithIgnoreCase(methodName, "query")) {
                    return queryIntercept(invocation);
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // return Interceptor.super.plugin(target);
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
