package com.ureshii.demo.song;


import jakarta.validation.constraints.NotBlank;

public record Base64FileDTO(@NotBlank Long id, @NotBlank String base64File, @NotBlank String fileName) {
}
