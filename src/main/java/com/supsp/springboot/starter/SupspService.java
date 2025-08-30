package com.supsp.springboot.starter;

/**
 * Supsp Service Interface
 * Main interface for the Supsp starter functionality
 */
public interface SupspService {
    
    /**
     * Process data using Supsp functionality
     * @param data The data to process
     * @return Processed result
     */
    String processData(String data);
    
    /**
     * Check if the service is initialized and ready to use
     * @return True if the service is ready, false otherwise
     */
    boolean isReady();
    
    /**
     * Get the current configuration status
     * @return Configuration status information
     */
    String getStatus();
}