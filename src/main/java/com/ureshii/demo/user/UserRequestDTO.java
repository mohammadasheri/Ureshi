package com.ureshii.demo.user;


import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String role
) {
}
