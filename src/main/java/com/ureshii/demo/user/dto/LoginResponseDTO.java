package com.ureshii.demo.user.dto;

public record LoginResponseDTO(
        String username,
        String type,
        String token
) {
}