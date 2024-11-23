package com.ureshii.demo.search;


import com.ureshii.demo.playlist.ProjectedPlaylist;
import com.ureshii.demo.song.ProjectedSong;

import java.util.List;

public record SearchResponseDTO(List<ProjectedPlaylist> playlists, List<ProjectedSong> songs) {
}
