package dev.hexa.pmsservice.application.dto;

public record UserCreateResponseDto(
        String status,
        UserDto user
) {
}
