package com.ureshii.demo.artist;

import com.ureshii.demo.exception.NotFoundException;

import java.util.List;

public interface ArtistService {
    Artist createArtist(CreateArtistDTO dto);

    Artist getArtistById(Long id) throws NotFoundException;
    Artist getArtistByName(String name) throws NotFoundException;

    List<Artist> getAllArtists();
}
