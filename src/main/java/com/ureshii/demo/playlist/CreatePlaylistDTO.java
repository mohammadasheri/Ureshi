package com.ureshii.demo.playlist;

import jakarta.validation.constraints.Size;

public record CreatePlaylistDTO(String name,
                                String pictureFileType,
                                @Size(min = 0, max = 6000000, message = "You should upload image with size in range(0" +
                                    "K-6M)")
                              byte[] pictureBytes) {

}
