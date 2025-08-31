package com.supsp.springboot.core.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class ModelConfig implements InitializingBean {

    @Value("#{'${model.tenant.ignore}'.split(',')}")
    private String[] modelIgnoreTenant;

    @Value("#{'${model.merchant.ignore}'.split(',')}")
    private String[] modelIgnoreMerchant;

    public static String[] MODEL_IGNORE_TENANT;

    public static String[] MODEL_IGNORE_MERCHANT;

    @Override
    public void afterPropertiesSet() throws Exception {
//        log.debug(
//                "\n■■■■■ ModelConfig \nmodelIgnoreTenant: {}\nmodelIgnoreMerchant: {}",
//                modelIgnoreTenant,
//                modelIgnoreMerchant
//        );
        MODEL_IGNORE_TENANT = modelIgnoreTenant;
        MODEL_IGNORE_MERCHANT = modelIgnoreMerchant;
    }
}
