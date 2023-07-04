package com.ureshii.demo.song;

import com.ureshii.demo.artist.Artist;
import com.ureshii.demo.artist.ArtistService;
import com.ureshii.demo.config.file.ImageWriterGateway;
import com.ureshii.demo.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public record SongServiceImpl(SongRepository repository, ArtistService artistService, ImageWriterGateway gateway) implements SongService {

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

    private String generateNewFileAddress(String fileType) {
        log.debug("generate new file address");
        SimpleDateFormat formatter = new SimpleDateFormat("/yyyy/MM/dd/HH/mm/");
        Date date = new Date();
        String address = formatter.format(date)+ UUID.randomUUID() + "." + fileType;
        log.debug("generated address {}",address);
        return address;
    }
}
