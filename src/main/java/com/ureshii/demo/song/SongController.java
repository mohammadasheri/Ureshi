package com.ureshii.demo.song;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/song")
public record SongController(SongService songService) {

    @PostMapping("/create")
    ResponseEntity<Song> createSong(@RequestParam @NotBlank String name, @RequestParam String language,
                                    @RequestParam String bitrate, @RequestParam @NotNull Long artistId,
                                    @RequestParam MultipartFile pictureFile, @RequestParam MultipartFile songFile)
            throws IOException {
        CreateSongDTO dto = new CreateSongDTO(name, language, bitrate, artistId,
                FilenameUtils.getExtension(pictureFile.getOriginalFilename()),
                FilenameUtils.getExtension(songFile.getOriginalFilename()),
                pictureFile.getBytes(), songFile.getBytes());
        Song song = songService.createSong(dto);
        return new ResponseEntity<>(song, HttpStatus.CREATED);
    }
}
