package dev.hexa.pmsservice.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthRequestDto(
        String login,
        String password
) {
}
