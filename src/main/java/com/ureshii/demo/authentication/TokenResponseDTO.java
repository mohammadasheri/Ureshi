package com.ureshii.demo.authentication;

public record TokenResponseDTO(
        String token,
        String refreshToken
) {
}