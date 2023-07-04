package com.ureshii.demo.user.dto;


import jakarta.validation.constraints.NotBlank;

public record UserStatisticsDTO(@NotBlank String phone, @NotBlank long userAcceptRecordCount,
                                @NotBlank long userSkipRecordCount
) {
}
