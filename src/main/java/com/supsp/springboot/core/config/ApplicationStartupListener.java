package com.supsp.springboot.core.config;

import cn.hutool.core.net.NetUtil;
import com.supsp.springboot.core.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

@Component
@Slf4j
public class ApplicationStartupListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    @EventListener
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        Environment environment = event.getEnvironment();

        // 获取端口号
        String port = environment.getProperty(
                "server.port",
                "8080"
        );

        String contextPath = environment.getProperty(
                "server.servlet.context-path",
                ""
        );

        String systemName = environment.getProperty(
                "system.app.name",
                "base"
        );

        String docUrl = environment.getProperty(
                "system.doc.url",
                "https://www.supsp.com"
        );

        String appName = environment.getProperty(
                "spring.application.name",
                ""
        );

        String swaggerPath = environment.getProperty(
                "springdoc.swagger-ui.path",
                "/swagger-ui.html"
        );

        System.out.println(" ");
        System.out.println("■ " + systemName.toUpperCase() + " " + appName.toUpperCase() + " 启动 " + docUrl);
        LinkedHashSet<String> ips = NetUtil.localIpv4s();
        if (!ips.isEmpty()) {
            for (String ip : ips) {
                if (StrUtils.isNotBlank(ip)) {
                    System.out.println("■ http://" + ip + ":" + port + contextPath + "/doc.html");
                    System.out.println("■ http://" + ip + ":" + port + contextPath + swaggerPath);
                }
            }
        }

        System.out.println(" ");
    }
}
