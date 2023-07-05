package com.ureshii.demo.song;

import com.ureshii.demo.exception.NotFoundException;

import java.io.IOException;

public interface SongService {
    Song createSong(CreateSongDTO dto) throws NotFoundException;

    Base64FileDTO downloadFileById(Long id) throws IOException, NotFoundException;
}
