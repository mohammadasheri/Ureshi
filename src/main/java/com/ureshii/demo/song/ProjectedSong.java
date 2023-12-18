package com.ureshii.demo.song;

import com.ureshii.demo.artist.ProjectedArtist;

import java.time.LocalDateTime;
import java.util.List;

public interface ProjectedSong {
    Long getId();

    String getName();

    List<ProjectedArtist> getArtists();

    LocalDateTime getCreated();
}
