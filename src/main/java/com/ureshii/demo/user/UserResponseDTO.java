package com.ureshii.demo.user;


import jakarta.validation.constraints.NotBlank;

public record UserResponseDTO(
        @NotBlank
        Long id,
        @NotBlank
        String username
) {
}
