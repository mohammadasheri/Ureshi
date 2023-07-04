package com.ureshii.demo.artist;

import com.ureshii.demo.exception.NotFoundException;
import com.ureshii.demo.song.CreateSongDTO;
import com.ureshii.demo.song.Song;

public interface ArtistService {
    Artist createArtist(CreateArtistDTO dto);
    Artist getArtistById(Long id) throws NotFoundException;
}
