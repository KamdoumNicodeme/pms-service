package dev.hexa.pmsservice.application.dto;

import java.util.List;

public record AuthResponseDto(
        String accessToken,
        String userName,
        List<String> roles
) {
}
