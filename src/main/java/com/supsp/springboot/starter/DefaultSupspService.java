package com.supsp.springboot.starter;

import com.supsp.springboot.core.config.CoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Default implementation of SupspService
 */
public class DefaultSupspService implements SupspService, InitializingBean {
    
    private static final Logger logger = LoggerFactory.getLogger(DefaultSupspService.class);
    
    private final CoreProperties properties;
    private boolean initialized = false;
    
    public DefaultSupspService(CoreProperties properties) {
        this.properties = properties;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // CoreProperties doesn't have an isEnabled() method, assuming it's always enabled
        initialize();
    }
    
    private void initialize() {
        // Log application information
        if (properties.getSystemName() != null) {
            logger.info("Initializing Supsp service for application: {}-{}", 
                    properties.getSystemName(), 
                    properties.getSystemVersion());
        } else {
            logger.info("Initializing Supsp service");
        }
        
        // Check for encode configuration
        boolean hasSecret = properties.getSecret() != null && !properties.getSecret().isEmpty();
        boolean hasKey = properties.getEncodeKey() != null && !properties.getEncodeKey().isEmpty();
        boolean hasIv = properties.getEncodeIv() != null && !properties.getEncodeIv().isEmpty();
        
        if (hasSecret || hasKey || hasIv) {
            logger.debug("Encode configuration provided");
        }
        
        // Async thread pool configuration is now handled elsewhere
        
        // Check cache configuration
        if (properties.isCacheEnable()) {
            logger.info("Cache enabled with name: {}", properties.getCacheName());
        }
        
        this.initialized = true;
        logger.info("Supsp service initialized successfully");
    }
    
    @Override
    public String processData(String data) {
        if (!isReady()) {
            throw new IllegalStateException("Supsp service is not ready");
        }
        
        logger.debug("Processing data with Supsp service");
        // Here would be the actual processing logic
        // For demonstration purposes, we'll just return a modified string
        return "Processed by Supsp: " + data;
    }
    
    @Override
     public boolean isReady() {
        // CoreProperties doesn't have an isEnabled() method, assuming it's always enabled
        return initialized;
    }
    
    @Override
    public String getStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Supsp Service Status:\n");
        // CoreProperties doesn't have an isEnabled() method, assuming it's always enabled
        status.append("  Enabled: true").append("\n");
        status.append("  Initialized: " + initialized).append("\n");
        
        // Application information
        status.append("\nApplication Info:\n");
        status.append("  Name: " + properties.getSystemName()).append("\n");
        status.append("  Version: " + properties.getSystemVersion()).append("\n");
        status.append("  Project: " + properties.getSystemProject()).append("\n");
        status.append("  Active Profile: " + properties.getProfileActive()).append("\n");
        
        // Encode configuration status
        status.append("\nEncode Configuration:\n");
        status.append("  Has Secret: " + (properties.getSecret() != null && !properties.getSecret().isEmpty())).append("\n");
        status.append("  Has Key: " + (properties.getEncodeKey() != null && !properties.getEncodeKey().isEmpty())).append("\n");
        status.append("  Has IV: " + (properties.getEncodeIv() != null && !properties.getEncodeIv().isEmpty())).append("\n");
        
        // Async thread pool status is now handled elsewhere
        
        // Cache status
        status.append("\nCache Configuration:\n");
        status.append("  Enabled: " + properties.isCacheEnable()).append("\n");
        status.append("  Name: " + properties.getCacheName()).append("\n");
        status.append("  Version: " + properties.getCacheVersion()).append("\n");
        if (properties.isCacheEnable()) {
            status.append("  Expiration: " + properties.getCacheExpire()).append("\n");
        }
        
        return status.toString();
    }
}