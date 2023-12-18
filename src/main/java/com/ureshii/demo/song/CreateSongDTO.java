package com.ureshii.demo.song;

import jakarta.validation.constraints.Size;

import java.util.Optional;

public record CreateSongDTO(String name, Optional<String> language, Long duration, Optional<String> bitrate,
                            Optional<Long> artistId, String pictureFileType, String songMediaType,

                            @Size(min = 20000, max = 1000000, message = "You should upload image with size in range" +
                                                                        "(10" +
                                                                        "K-1M)")
                            byte[] pictureBytes,
                            @Size(min = 20000, max = 10000000, message = "You should upload image with size in range" +
                                                                         "(10" +
                                                                         "K-10M)")
                            byte[] songBytes) {

}
