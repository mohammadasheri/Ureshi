package com.ureshii.demo.song;


import com.ureshii.demo.artist.ArtistResponseDTO;

import java.util.List;

public record SongResponseDTO(Long id, String name, String language, String bitrate, Long likes, Long play_count,
                              List<ArtistResponseDTO> artist) {
}
