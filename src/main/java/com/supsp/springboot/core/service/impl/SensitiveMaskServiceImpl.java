package com.supsp.springboot.core.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import com.supsp.springboot.core.service.ISensitiveMaskService;
import org.springframework.stereotype.Service;

@Service
public class SensitiveMaskServiceImpl implements ISensitiveMaskService {

    @Override
    public String maskData(String data) {
        return DesensitizedUtil.firstMask(data);
    }
}
