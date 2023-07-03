package com.ureshii.demo.user;


import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDTO(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
