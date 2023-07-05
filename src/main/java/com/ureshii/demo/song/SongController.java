package com.ureshii.demo.song;

import com.ureshii.demo.artist.ArtistResponseDTO;
import com.ureshii.demo.exception.NotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/song")
@Slf4j
public record SongController(SongService songService) {

    @PostMapping("/create")
    ResponseEntity<SongResponseDTO> createSong(@RequestParam @NotBlank String name, @RequestParam String language,
                                               @RequestParam String bitrate, @RequestParam @NotNull Long artistId,
                                               @RequestParam MultipartFile pictureFile,
                                               @RequestParam MultipartFile songFile)
            throws IOException, NotFoundException {
        log.info("Song controller: create song");
        CreateSongDTO dto = new CreateSongDTO(name, language, bitrate, artistId,
                FilenameUtils.getExtension(pictureFile.getOriginalFilename()),
                FilenameUtils.getExtension(songFile.getOriginalFilename()), pictureFile.getBytes(),
                songFile.getBytes());
        Song song = songService.createSong(dto);
        return new ResponseEntity<>(convertSong(song), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    ResponseEntity<List<SongResponseDTO>> downloadSong() {
        return new ResponseEntity<>(songService.getAllSongs().stream().map(this::convertSong).toList(), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    ResponseEntity<Base64FileDTO> downloadSong(@PathVariable @NotNull Long id) throws IOException, NotFoundException {
        return new ResponseEntity<>(songService.downloadFileById(id), HttpStatus.OK);
    }

    private SongResponseDTO convertSong(Song song) {
        return new SongResponseDTO(song.getId(), song.getName(), song.getLanguage(), song.getBitrate(), song.getLikes(),
                song.getPlay_count(),
                song.getArtists().stream().map(artist -> new ArtistResponseDTO(artist.getId(), artist.getName()))
                        .toList());
    }

}
