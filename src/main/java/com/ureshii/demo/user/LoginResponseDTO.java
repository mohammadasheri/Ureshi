package com.ureshii.demo.user;

public record LoginResponseDTO(
        String username,
        String type,
        String token
) {
}