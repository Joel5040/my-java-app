package com.example.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainApplication {
    
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("🚀 Jenkins CI/CD Pipeline Demo Application");
        System.out.println("==========================================");
        System.out.println("✅ Application Version: 1.0.0");
        System.out.println("✅ Java Version: " + System.getProperty("java.version"));
        System.out.println("✅ Current Time: " + getCurrentTime());
        
        // Check if running in Docker
        if (isRunningInDocker()) {
            System.out.println("✅ Running in: Docker Container");
        } else {
            System.out.println("✅ Running in: Local Environment");
        }
        
        System.out.println("==========================================");
        
        // Handle health check argument
        if (args.length > 0 && args[0].equals("--health")) {
            printHealthCheck();
        }
    }
    
    private static String getCurrentTime() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    private static boolean isRunningInDocker() {
        String dockerEnv = System.getenv("DOCKER_CONTAINER");
        return dockerEnv != null && dockerEnv.equals("true");
    }
    
    private static void printHealthCheck() {
        System.out.println("🔍 Health Check: OK");
        
        // Calculate memory usage
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long usedMemoryMB = usedMemory / (1024 * 1024);
        
        System.out.println("📊 Memory Usage: " + usedMemoryMB + " MB");
        System.out.println("📊 Total Memory: " + (totalMemory / (1024 * 1024)) + " MB");
        System.out.println("📊 Free Memory: " + (freeMemory / (1024 * 1024)) + " MB");
    }
}
