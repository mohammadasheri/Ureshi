package com.ureshii.demo.artist;

import com.ureshii.demo.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Entity
public class Artist extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    private String countryOfOrigin;
    private String playCount;
    @Column(nullable = false, unique = true)
    private String pictureAddress;
    private boolean enabled;
}
