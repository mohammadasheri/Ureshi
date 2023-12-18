package com.ureshii.demo.song;

import com.ureshii.demo.exception.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface SongService {
    Song createSong(CreateSongDTO dto) throws NotFoundException;

    List<Song> getAllSongs();

    Song getSongById(Long id) throws NotFoundException;
}
