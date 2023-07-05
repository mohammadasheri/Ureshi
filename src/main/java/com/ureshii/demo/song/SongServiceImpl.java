package com.ureshii.demo.song;

import com.ureshii.demo.artist.Artist;
import com.ureshii.demo.artist.ArtistService;
import com.ureshii.demo.config.file.ImageWriterGateway;
import com.ureshii.demo.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
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
        String songFileAddress = generateNewFileAddress(dto.songFileType());
        String pictureFileAddress = generateNewFileAddress(dto.pictureFileType());
        gateway.saveFile(songFileAddress, dto.songBytes());
        gateway.saveFile(pictureFileAddress, dto.pictureBytes());
        Artist artist = artistService.getArtistById(dto.artistId());
        Song song = Song.builder().name(dto.name()).language(dto.language()).bitrate(dto.bitrate())
                .fileAddress(songFileAddress).pictureAddress(pictureFileAddress).artists(Set.of(artist)).build();
        return repository.save(song);
    }

    @Override
    public Base64FileDTO downloadFileById(Long id) throws IOException, NotFoundException {
        Song song = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no file available with this parameters."));
        String fileAddress = baseDirectory + song.getFileAddress();
        File data = ResourceUtils.getFile(fileAddress);
        byte[] bytes = FileUtils.readFileToByteArray(data);
        return new Base64FileDTO(id, Base64.getEncoder().encodeToString(bytes), song.getName());
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
