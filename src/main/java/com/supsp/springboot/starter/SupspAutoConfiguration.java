package com.supsp.springboot.starter;

import com.supsp.springboot.core.config.CoreProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Auto-configuration for Supsp Service
 * This class handles the automatic configuration of the Supsp starter
 */
@Configuration
@ConditionalOnProperty(prefix = "supsp", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(CoreProperties.class)
public class SupspAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public SupspService supspService(@Qualifier("coreProperties") CoreProperties properties) {
        return new DefaultSupspService(properties);
    }
    
    /**
     * Configures the async thread pool executor based on the provided properties.
     * This bean will be created only if async thread pool properties are configured.
     */
    /**
     * Configures the async thread pool executor based on the provided properties.
     * This bean will be created only if async thread pool properties are configured.
     */
    @Bean(name = "supspAsyncExecutor")
    @ConditionalOnProperty(prefix = "supsp.async.threadPool", name = "corePoolSize")
    @ConditionalOnMissingBean(name = "supspAsyncExecutor")
    public Executor supspAsyncExecutor(@Qualifier("coreProperties") CoreProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        
        executor.setCorePoolSize(properties.getAsyncCorePoolSize());
        executor.setMaxPoolSize(properties.getAsyncMaxPoolSize());
        executor.setQueueCapacity(properties.getAsyncQueueCapacity());
        executor.setKeepAliveSeconds(properties.getAsyncKeepAliveSeconds());
        executor.setThreadNamePrefix(properties.getAsyncThreadNamePrefix() != null ? 
                properties.getAsyncThreadNamePrefix() : "supsp-async-");
        
        executor.initialize();
        return executor;
    }
    
    /**
     * Creates a bean for the application properties to be used throughout the application.
     * Using CoreProperties directly instead of SupspProperties.App
     */
    @Bean
    @ConditionalOnMissingBean(name = "appProperties")
    public CoreProperties appProperties(@Qualifier("coreProperties") CoreProperties properties) {
        return properties;
    }
    
    /**
     * Creates a bean for encode-related properties from CoreProperties
     */
    @Bean
    @ConditionalOnMissingBean(name = "encodeProperties")
    public CoreProperties encodeProperties(@Qualifier("coreProperties") CoreProperties properties) {
        return properties;
    }
    
    /**
     * Creates a bean for cache-related properties from CoreProperties
     */
    @Bean
    @ConditionalOnMissingBean(name = "cacheProperties")
    public CoreProperties cacheProperties(@Qualifier("coreProperties") CoreProperties properties) {
        return properties;
    }
    
    /**
     * Creates a bean for async-related properties from CoreProperties
     */
    @Bean
    @ConditionalOnMissingBean(name = "asyncProperties")
    public CoreProperties asyncProperties(@Qualifier("coreProperties") CoreProperties properties) {
        return properties;
    }
    
    /**
     * Creates a bean for doc-related properties from CoreProperties
     */
    @Bean
    @ConditionalOnMissingBean(name = "docProperties")
    public CoreProperties docProperties(@Qualifier("coreProperties") CoreProperties properties) {
        return properties;
    }
}