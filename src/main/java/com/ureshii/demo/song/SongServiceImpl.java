package com.ureshii.demo.song;

import com.ureshii.demo.artist.Artist;
import com.ureshii.demo.artist.ArtistService;
import com.ureshii.demo.config.file.ImageWriterGateway;
import com.ureshii.demo.exception.NotFoundException;
import com.ureshii.demo.playlist.ProjectedPlaylist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class SongServiceImpl implements SongService {

    private final SongRepository repository;
    private final ArtistService artistService;
    private final ImageWriterGateway gateway;

    @Value("${app.baseDirectory}")
    private String baseDirectory;

    public SongServiceImpl(SongRepository repository, ArtistService artistService, ImageWriterGateway gateway) {
        this.repository = repository;
        this.artistService = artistService;
        this.gateway = gateway;
    }

    @Override
    public Song createSong(CreateSongDTO dto) throws NotFoundException {
        log.info("Song service: create song");
        String songFileAddress = generateNewFileAddress(dto.songMediaType());
        String pictureFileAddress = generateNewFileAddress(dto.pictureFileType());
        gateway.saveFile(songFileAddress, dto.songBytes());
        gateway.saveFile(pictureFileAddress, dto.pictureBytes());
        Song song = Song.builder().name(dto.name()).duration(dto.duration()).songMediaType(dto.songMediaType())
                .pictureMediaType(dto.pictureFileType()).fileAddress(songFileAddress).pictureAddress(pictureFileAddress)
                .build();

        Set<Artist> artists = new HashSet<>(Collections.emptySet());
        if (dto.artistId().isPresent()) {
            artists.add(artistService.getArtistById(dto.artistId().get()));
        }
        song.setArtists(artists);
        dto.language().ifPresent(song::setLanguage);
        dto.bitrate().ifPresent(song::setBitrate);
        return repository.save(song);
    }

    @Override
    public List<Song> getAllSongs() {
        return repository.findAll();
    }

    @Override
    public Song getSongById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no song available with this parameters."));
    }

    @Override public List<ProjectedSong> getSongsLike(String query) {
        return repository.findAllByNameIgnoreCaseContainingOrderByCreatedDesc(query);
    }

    private String generateNewFileAddress(String fileType) {
        log.debug("generate new file address");
        SimpleDateFormat formatter = new SimpleDateFormat("/yyyy/MM/dd/HH/mm/");
        Date date = new Date();
        String address = formatter.format(date) + UUID.randomUUID() + "." + fileType;
        log.debug("generated address {}", address);
        return address;
    }
}
