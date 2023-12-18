package com.ureshii.demo.playlist;

import com.ureshii.demo.song.ProjectedSong;

import java.util.List;

public interface ProjectedPlaylist {
    Long getId();

    String getName();

    List<ProjectedSong> getSongs();
}
