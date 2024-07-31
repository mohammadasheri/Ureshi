package com.ureshii.demo.playlist;

import com.ureshii.demo.base.BaseEntity;
import com.ureshii.demo.song.Song;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Entity
public class Playlist extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    private String playCount;
    @Column(nullable = false, unique = true)
    private String pictureAddress;
    private String pictureMediaType;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "playlist_song",
            joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id")
    )
    private Set<Song> songs = new HashSet<>();
}
