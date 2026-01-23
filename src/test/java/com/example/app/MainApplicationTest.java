package com.example.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Main Application Tests")
class MainApplicationTest {
    
    @Test
    @DisplayName("Test application output")
    void testApplicationOutput() {
        // Redirect System.out to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        try {
            MainApplication.main(new String[]{});
            String output = outputStream.toString();
            
            // Verify expected output
            assertTrue(output.contains("Jenkins CI/CD Pipeline Demo"));
            assertTrue(output.contains("Application Version: 1.0.0"));
            assertTrue(output.contains("Java Version"));
            
        } finally {
            System.setOut(originalOut);
        }
    }
    
    @Test
    @DisplayName("Test health check argument")
    void testHealthCheck() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        try {
            MainApplication.main(new String[]{"--health"});
            String output = outputStream.toString();
            
            assertTrue(output.contains("Health Check: OK"));
            assertTrue(output.contains("Memory Usage"));
            
        } finally {
            System.setOut(originalOut);
        }
    }
    
    @Test
    @DisplayName("Simple assertion test")
    void testSimpleAssertion() {
        assertEquals(2 + 2, 4);
        assertTrue("hello".contains("ell"));
    }
}
