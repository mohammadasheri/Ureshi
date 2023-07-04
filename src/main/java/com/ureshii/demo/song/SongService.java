package com.ureshii.demo.song;

import com.ureshii.demo.exception.NotFoundException;

public interface SongService {
    Song createSong(CreateSongDTO dto) throws NotFoundException;
}
