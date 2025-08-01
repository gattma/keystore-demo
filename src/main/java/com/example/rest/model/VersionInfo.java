package com.example.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Version information model containing application version details
 */
public record VersionInfo(
    @JsonProperty("version") String version,
    @JsonProperty("artifact_id") String artifactId,
    @JsonProperty("group_id") String groupId,
    @JsonProperty("build_time") String buildTime
) {
}
