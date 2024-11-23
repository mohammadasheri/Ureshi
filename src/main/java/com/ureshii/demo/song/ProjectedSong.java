package com.ureshii.demo.song;

import com.ureshii.demo.artist.ProjectedArtist;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface ProjectedSong {
    Long getId();

    String getName();

    Set<ProjectedArtist> getArtists();

    LocalDateTime getCreated();
}
