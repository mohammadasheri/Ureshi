package com.ureshii.demo.user.dto;


import jakarta.validation.constraints.NotBlank;

public record EnableUserRequestDTO(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
