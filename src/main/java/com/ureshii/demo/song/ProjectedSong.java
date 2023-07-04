package com.ureshii.demo.song;

import com.ureshii.demo.artist.ProjectedArtist;

import java.time.LocalDateTime;

public interface ProjectedSong {
    Long getId();

    LocalDateTime getCreated();

    LocalDateTime getModified();

    String getName();

    ProjectedArtist getResolver();
}
