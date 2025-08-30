package com.supsp.springboot.starter;

import com.supsp.springboot.core.config.CoreProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the SupspService
 */
class SupspServiceTest {
    
    private SupspService supspService;
    private CoreProperties properties;
    
    @BeforeEach
    void setUp() {
        properties = new CoreProperties();
        
        // Set test application properties
        properties.setSystemName("test-app");
        properties.setSystemVersion("1.0.0");
        
        supspService = new DefaultSupspService(properties);
        
        // Manually initialize the service
        try {
            ((DefaultSupspService) supspService).afterPropertiesSet();
        } catch (Exception e) {
            fail("Failed to initialize SupspService: " + e.getMessage());
        }
    }
    
    @Test
    void testIsReady() {
        assertTrue(supspService.isReady(), "SupspService should be ready");
    }
    
    @Test
    void testProcessData() {
        String input = "test data";
        String result = supspService.processData(input);
        assertNotNull(result);
        assertTrue(result.contains(input));
        assertTrue(result.contains("Processed by Supsp"));
    }
    
    @Test
    void testGetStatus() {
        String status = supspService.getStatus();
        assertNotNull(status);
        assertTrue(status.contains("Supsp Service Status"));
        assertTrue(status.contains("Enabled: true"));
        // Check for application information in status
        assertTrue(status.contains("Application Info"));
        assertTrue(status.contains("test-app"));
        assertTrue(status.contains("1.0.0"));
    }
}