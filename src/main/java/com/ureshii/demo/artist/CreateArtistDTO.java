package com.ureshii.demo.artist;

import jakarta.validation.constraints.Size;

public record CreateArtistDTO(String name,
                              String countryOfOrigin,
                              String pictureFileType,
                              @Size(min = 0, max = 6000000, message = "You should upload image with size in range(0" +
                                    "K-6M)")
                              byte[] pictureBytes) {

}
