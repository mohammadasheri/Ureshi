package com.ureshii.demo.song;

import com.ureshii.demo.exception.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface SongService {
    Song createSong(CreateSongDTO dto) throws NotFoundException;

    List<Song> getAllSongs();

    Base64FileDTO downloadFileById(Long id) throws IOException, NotFoundException;
}
