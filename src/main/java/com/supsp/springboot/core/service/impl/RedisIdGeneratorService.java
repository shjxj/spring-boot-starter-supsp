package com.supsp.springboot.core.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.supsp.springboot.core.consts.Constants;
import com.supsp.springboot.core.service.IIdGeneratorService;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.RedisUtils;
import com.supsp.springboot.core.utils.StrUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service("redisID")
public class RedisIdGeneratorService implements IIdGeneratorService {

    public static final Integer DEFAULT_LEN = 4;
    public static final Integer DEFAULT_PREFIX = null;

    public String DateFormat(String type) {
        switch (type) {
            case Constants.TYPE_DAY -> {
                return "yyyyMMdd";
            }
            case Constants.TYPE_SECONDS -> {
                return "yyyyMMddHHmmss";
            }
            default -> {
                return "yyyyMMddHHmm";
            }
        }
    }

    public String keyName(String module, String name, String type) {
        if (StrUtils.isBlank(module)) {
            module = Constants.DEFAULT_MODULE;
        }
        if (StrUtils.isBlank(name)) {
            name = Constants.DEFAULT_NAME;
        }
        return "ID::" + module + "::" + name + "::" + DateUtil.format(DateUtil.date(), DateFormat(type));
    }

    public String keyName(String module, String name) {
        return keyName(module, name, Constants.TYPE_MINUTE);
    }

    public String keyName() {
        return keyName(Constants.DEFAULT_MODULE, Constants.DEFAULT_NAME, Constants.TYPE_MINUTE);
    }

    public long idValue(String module, String name, String type) {
        String key = keyName(module, name, type);
        long expire = Constants.MINUTE_TIME_OUT;
        switch (type) {
            case Constants.TYPE_DAY -> expire = Constants.DAY_TIME_OUT;
            case Constants.TYPE_SECONDS -> expire = Constants.SECONDS_TIME_OUT;
        }
        return RedisUtils.getIncr(key, expire);
    }

    @Override
    public Long id(String module, String name, Integer len, Integer prefix, String type) {
        long id = idValue(module, name, type);
        if (id < 1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (prefix != null && prefix.compareTo(0) > 0) {
            sb.append(prefix);
        }
        DateTime date = DateUtil.date();
        sb.append(CommonUtils.timestamp());
//        if (len == null || len.compareTo(0) <= 0) {
//            len = DEFAULT_LEN;
//        }
        if (len != null && len.compareTo(0) > 0) {
            sb.append(StrUtils.leftPad(String.valueOf(id), len, '0'));
        } else {
            sb.append(id);
        }
        // sb.append(DateUtil.format(date, "HHmmss"));
        // sb.append(RandomStringUtils.randomNumeric(2));
        sb.append(RandomUtil.randomNumbers(2));
        return Long.parseLong(sb.toString());
    }

    @Override
    public Long id(String module, String name, Integer len, Integer prefix) {
        return id(module, name, len, prefix, Constants.TYPE_MINUTE);
    }

    @Override
    public Long id(String module, String name, String type) {
        return id(module, name, DEFAULT_LEN, DEFAULT_PREFIX, type);
    }

    @Override
    public Long id(String module, String name) {
        return id(module, name, DEFAULT_LEN, DEFAULT_PREFIX, Constants.TYPE_MINUTE);
    }

    @Override
    public Long id(Integer len, Integer prefix) {
        return id(Constants.DEFAULT_MODULE, Constants.DEFAULT_NAME, len, prefix, Constants.TYPE_MINUTE);
    }

    @Override
    public Long id() {
        return id(Constants.DEFAULT_MODULE, Constants.DEFAULT_NAME, DEFAULT_LEN, DEFAULT_PREFIX, Constants.TYPE_MINUTE);
    }

}
