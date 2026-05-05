package dev.hexa.pmsservice.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDto(
        String userName,
        String userEmail,
        String password,
        List<RoleDto> roles,
        String status
) {
}
