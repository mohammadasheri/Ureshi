package com.ureshii.demo.song;

import com.ureshii.demo.config.file.ImageWriterGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public record SongServiceImpl(SongRepository repository, ImageWriterGateway gateway) implements SongService {

    @Override
    public Song createSong(CreateSongDTO dto) {
        String songFileAddress = generateNewFileAddress(dto.songFileType());
        String pictureFileAddress = generateNewFileAddress(dto.pictureFileType());
        gateway.saveFile(songFileAddress, dto.songBytes());
        gateway.saveFile(pictureFileAddress, dto.pictureBytes());
        Song song = Song.builder().name(dto.name()).language(dto.language()).bitrate(dto.bitrate())
                .fileAddress(songFileAddress).pictureAddress(pictureFileAddress).build();
        return repository.save(song);
    }

    private String generateNewFileAddress(String fileType) {
        SimpleDateFormat formatter = new SimpleDateFormat("/yyyy/MM/dd/HH/mm/");
        Date date = new Date();
        String address = formatter.format(date);
        return address + UUID.randomUUID() + "." + fileType;
    }
}
