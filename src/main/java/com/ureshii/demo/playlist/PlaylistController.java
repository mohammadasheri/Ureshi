package com.ureshii.demo.playlist;

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
@RequestMapping("/playlist")
@Slf4j
public record PlaylistController(PlaylistService artistService) {

    @PostMapping("/create")
    ResponseEntity<PlaylistResponseDTO> createPlaylist(@RequestParam @NotBlank String name, @RequestParam MultipartFile pictureFile) throws IOException {
        log.info("Playlist controller: create playlist");
        CreatePlaylistDTO dto = new CreatePlaylistDTO(name,
                FilenameUtils.getExtension(pictureFile.getOriginalFilename()), pictureFile.getBytes());
        Playlist playlist = artistService.createPlaylist(dto);
        return new ResponseEntity<>(convertPlaylist(playlist), HttpStatus.CREATED);
    }

    @PostMapping("/addSong")
    ResponseEntity<PlaylistResponseDTO> createPlaylist(@RequestBody PlaylistAddSongDTO dto)
            throws IOException, NotFoundException {
        log.info("Playlist controller: create playlist");
        Playlist playlist = artistService.addSong(dto);
        return new ResponseEntity<>(convertPlaylist(playlist), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<PlaylistResponseDTO> getPlaylistById(@PathVariable @NotNull Long id) throws NotFoundException {
        log.info("Playlist controller: find playlist by id");
        Playlist playlist = artistService.getPlaylistById(id);
        return new ResponseEntity<>(convertPlaylist(playlist), HttpStatus.OK);
    }

    @GetMapping(path = "/list")
    ResponseEntity<List<ProjectedPlaylist>> getAllPlaylists() {
        log.info("Playlist controller: list playlist");
        List<ProjectedPlaylist> playlists = artistService.getAllPlaylists();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping(path = "/findByName")
    ResponseEntity<PlaylistResponseDTO> getPlaylistByName(@RequestParam @NotNull String artistName)
            throws NotFoundException {
        log.info("Playlist controller: find playlist by name");
        Playlist playlist = artistService.getPlaylistByName(artistName);
        return new ResponseEntity<>(convertPlaylist(playlist), HttpStatus.OK);
    }

    private PlaylistResponseDTO convertPlaylist(Playlist playlist) {
        return new PlaylistResponseDTO(playlist.getId(), playlist.getName());
    }
}
