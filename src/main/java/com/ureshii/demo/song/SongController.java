package com.ureshii.demo.song;

import com.ureshii.demo.artist.ArtistResponseDTO;
import com.ureshii.demo.exception.NotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/song")
@Slf4j
public record SongController(SongService songService, @Value("${app.baseDirectory}") String baseDirectory) {

    @PostMapping("/create")
    ResponseEntity<SongResponseDTO> createSong(@RequestParam @NotBlank String name, @RequestParam String language,
                                               @RequestParam Long duration, @RequestParam String bitrate,
                                               @RequestParam @NotNull Long artistId,
                                               @RequestParam MultipartFile pictureFile,
                                               @RequestParam MultipartFile songFile)
            throws IOException, NotFoundException {
        log.info("Song controller: create song");
        CreateSongDTO dto = new CreateSongDTO(name, language, bitrate, artistId,
                FilenameUtils.getExtension(pictureFile.getOriginalFilename()),
                FilenameUtils.getExtension(songFile.getOriginalFilename()), duration, pictureFile.getBytes(),
                songFile.getBytes());
        Song song = songService.createSong(dto);
        return new ResponseEntity<>(convertSong(song), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    ResponseEntity<List<SongResponseDTO>> getAllSongs() {
        return new ResponseEntity<>(songService.getAllSongs().stream().map(this::convertSong).toList(), HttpStatus.OK);
    }

    @GetMapping("/list/home")
    ResponseEntity<List<SongResponseDTO>> getHomeSongs() {
        return new ResponseEntity<>(songService.getAllSongs().stream().map(this::convertSong).toList(), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    ResponseEntity<ByteArrayResource> downloadSong(@PathVariable @NotNull Long id)
            throws IOException, NotFoundException {
        Song song = songService.downloadFileById(id);
        String fileAddress = baseDirectory + song.getFileAddress();
        File data = ResourceUtils.getFile(fileAddress);
        byte[] dataBytes = FileUtils.readFileToByteArray(data);
        ByteArrayResource resource = new ByteArrayResource(dataBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-disposition", "attachment; filename=\"" + id + "." + song.getSongMediaType() + "\"");
        return ResponseEntity.ok().headers(headers).contentLength(dataBytes.length)
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }

    @GetMapping("/picture/download/{id}")
    ResponseEntity<ByteArrayResource> downloadPicture(@PathVariable @NotNull Long id)
            throws IOException, NotFoundException {
        Song song = songService.downloadFileById(id);
        String fileAddress = baseDirectory + song.getPictureAddress();
        File data = ResourceUtils.getFile(fileAddress);
        byte[] dataBytes = FileUtils.readFileToByteArray(data);
        ByteArrayResource resource = new ByteArrayResource(dataBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-disposition", "attachment; filename=\"" + id + "." + song.getPictureMediaType() + "\"");
        return ResponseEntity.ok().headers(headers).contentLength(dataBytes.length)
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }

    private SongResponseDTO convertSong(Song song) {
//        return new SongResponseDTO(song.getId(), song.getName(), song.getMediaType(), song.getLanguage(),
//                song.getBitrate(),
//                song.getLikes(),
//                song.getplayCount(),
//                song.getArtists().stream().map(artist -> new ArtistResponseDTO(artist.getId(), artist.getName()))
//                        .toList());
        return new SongResponseDTO(song.getId(), song.getName(), song.getSongMediaType(),song.getPictureMediaType(),
                song.getLanguage(),
                song.getDuration(),
                song.getLikes(),
                song.getPlayCount(),
                null);
    }

}
