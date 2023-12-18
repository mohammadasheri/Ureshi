package com.ureshii.demo.song;

import com.ureshii.demo.artist.ArtistService;
import com.ureshii.demo.config.file.ImageWriterGateway;
import com.ureshii.demo.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

        if (dto.artistId().isPresent()) {
            song.setArtists(Set.of(artistService.getArtistById(dto.artistId().get())));
        }
        dto.language().ifPresent(song::setLanguage);
        dto.bitrate().ifPresent(song::setBitrate);
        return repository.save(song);
    }

    @Override
    public List<Song> getAllSongs() {
        return repository.findAll();
    }

    @Override
    public Song downloadFileById(Long id) throws IOException, NotFoundException {
        Song song = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no file available with this parameters."));

        return song;
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
