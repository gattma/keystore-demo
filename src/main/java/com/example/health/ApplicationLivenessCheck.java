package com.example.health;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class ApplicationLivenessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        // Check if the application is running and not deadlocked
        // For this demo, we'll just check if the current thread is alive
        // In a real application, you might check database connections, 
        // critical services, etc.
        
        // Simple check to ensure the application is responsive
        // For this demo, we'll always return UP since the thread is running if we get here
        return HealthCheckResponse.up("Application");
    }
}