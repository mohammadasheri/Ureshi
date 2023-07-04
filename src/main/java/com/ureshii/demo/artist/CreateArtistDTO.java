package com.ureshii.demo.artist;

import jakarta.validation.constraints.Size;

public record CreateArtistDTO(String name, String countryOfOrigin,
                              String pictureFileType,
                              @Size(min = 20000, max = 6000000, message = "You should upload image with size in range(10" +
                                    "K-6M)")
                            byte[] pictureBytes) {

}
