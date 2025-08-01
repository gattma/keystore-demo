package com.example.rest;

import com.example.rest.model.VersionInfo;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Configuration endpoints for application metadata
 */
@Path("/config")
public class ConfigResource {

    @Inject 
    @ConfigProperty(name = "quarkus.application.version", defaultValue = "1.0.0-SNAPSHOT")
    String applicationVersion;

    @Inject
    @ConfigProperty(name = "quarkus.application.name", defaultValue = "keystore-demo")
    String applicationName;

    /**
     * Returns version information of the application
     * @return VersionInfo containing version details
     */
    @GET
    @Path("/version")
    @Produces(MediaType.APPLICATION_JSON)
    public VersionInfo getVersion() {
        return new VersionInfo(
            applicationVersion,
            applicationName,
            "at.gattma",
            DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        );
    }
}
