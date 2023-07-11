package com.ureshii.demo.song;

import jakarta.validation.constraints.Size;

public record CreateSongDTO(String name, String language, String bitrate, Long artistId,
                            String pictureFileType,
                            String songMediaType,
                            Long duration,
                            @Size(min = 20000, max = 6000000, message = "You should upload image with size in range(10" +
                                    "K-6M)")
                            byte[] pictureBytes,
                            @Size(min = 20000, max = 6000000, message = "You should upload image with size in range(10" +
                                    "K-6M)")
                            byte[] songBytes) {

}
