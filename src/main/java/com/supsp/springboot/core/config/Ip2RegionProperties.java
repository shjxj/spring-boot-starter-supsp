package com.supsp.springboot.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = Ip2RegionProperties.PREFIX)
public class Ip2RegionProperties {
    public static final String PREFIX = "ip2region";

    /**
     * 是否开启自动配置
     */
    private boolean enabled = false;

    /**
     * db数据文件位置
     * <p>
     * ClassPath目录下
     * </p>
     */
    private String dbFile = "ip2region.xdb";

}
