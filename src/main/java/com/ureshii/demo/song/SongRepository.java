package com.ureshii.demo.song;

import com.ureshii.demo.playlist.ProjectedPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findByName(String name);
    List<ProjectedSong> findAllByNameIgnoreCaseContainingOrderByCreatedDesc(String queryString);
}
