package com.ureshii.demo.song;

import com.ureshii.demo.artist.Artist;
import com.ureshii.demo.base.BaseEntity;
import com.ureshii.demo.role.Role;
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
public class Song extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    private String mediaType;
    private String language;
    private String bitrate;
    @Column(nullable = false, unique = true)
    private String fileAddress;
    private String pictureAddress;
    private Long likes;
    private Long play_count;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "song_artist",
            joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id")
    )
    private Set<Artist> artists = new HashSet<>();
}
