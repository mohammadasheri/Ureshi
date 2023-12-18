package com.ureshii.demo.playlist;

import com.ureshii.demo.exception.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface PlaylistService {
    Playlist createPlaylist(CreatePlaylistDTO dto);

    Playlist addSong(PlaylistAddSongDTO dto) throws NotFoundException, IOException;

    Playlist getPlaylistById(Long id) throws NotFoundException;

    Playlist getPlaylistByName(String name) throws NotFoundException;

    List<ProjectedPlaylist> getAllPlaylists();
}
