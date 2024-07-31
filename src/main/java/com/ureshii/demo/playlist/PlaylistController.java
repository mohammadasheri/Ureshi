package com.ureshii.demo.playlist;

import com.ureshii.demo.artist.Artist;
import com.ureshii.demo.artist.ArtistResponseDTO;
import com.ureshii.demo.exception.NotFoundException;
import com.ureshii.demo.song.Song;
import com.ureshii.demo.song.SongResponseDTO;
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
@RequestMapping("/playlist")
@Slf4j
public record PlaylistController(PlaylistService service, @Value("${app.baseDirectory}") String baseDirectory) {

    @PostMapping("/create")
    ResponseEntity<PlaylistResponseDTO> createPlaylist(@RequestParam @NotBlank String name, @RequestParam MultipartFile pictureFile) throws IOException {
        log.info("Playlist controller: create playlist");
        CreatePlaylistDTO dto = new CreatePlaylistDTO(name,
                FilenameUtils.getExtension(pictureFile.getOriginalFilename()), pictureFile.getBytes());
        Playlist playlist = service.createPlaylist(dto);
        return new ResponseEntity<>(convertPlaylist(playlist), HttpStatus.CREATED);
    }

    @PostMapping("/addSong")
    ResponseEntity<PlaylistResponseDTO> createPlaylist(@RequestBody PlaylistAddSongDTO dto)
            throws IOException, NotFoundException {
        log.info("Playlist controller: create playlist");
        Playlist playlist = service.addSong(dto);
        return new ResponseEntity<>(convertPlaylist(playlist), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<PlaylistResponseDTO> getPlaylistById(@PathVariable @NotNull Long id) throws NotFoundException {
        log.info("Playlist controller: find playlist by id");
        Playlist playlist = service.getPlaylistById(id);
        return new ResponseEntity<>(convertPlaylist(playlist), HttpStatus.OK);
    }

    @GetMapping(path = "/list")
    ResponseEntity<List<ProjectedPlaylist>> getAllPlaylists() {
        log.info("Playlist controller: list playlist");
        List<ProjectedPlaylist> playlists = service.getAllPlaylists();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping(path = "/listSongs/{id}")
    ResponseEntity<List<SongResponseDTO>> getPlaylistSongs(@PathVariable @NotNull Long id) throws NotFoundException {
        log.info("Playlist controller: list playlist songs");
        Playlist playlist = service.getPlaylistById(id);
        return new ResponseEntity<>(playlist.getSongs().stream().map(this::convertSong).toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/findByName")
    ResponseEntity<PlaylistResponseDTO> getPlaylistByName(@RequestParam @NotNull String artistName)
            throws NotFoundException {
        log.info("Playlist controller: find playlist by name");
        Playlist playlist = service.getPlaylistByName(artistName);
        return new ResponseEntity<>(convertPlaylist(playlist), HttpStatus.OK);
    }


    @GetMapping("/picture/download/{id}")
    ResponseEntity<ByteArrayResource> downloadPicture(@PathVariable @NotNull Long id)
            throws IOException, NotFoundException {
        Playlist playlist = service.getPlaylistById(id);
        String fileAddress = baseDirectory + playlist.getPictureAddress();
        File data = ResourceUtils.getFile(fileAddress);
        byte[] dataBytes = FileUtils.readFileToByteArray(data);
        ByteArrayResource resource = new ByteArrayResource(dataBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-disposition", "attachment; filename=\"" + id + "." + playlist.getPictureMediaType() + "\"");
        return ResponseEntity.ok().headers(headers).contentLength(dataBytes.length)
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }

    private PlaylistResponseDTO convertPlaylist(Playlist playlist) {
        return new PlaylistResponseDTO(playlist.getId(), playlist.getName());
    }

    private SongResponseDTO convertSong(Song song) {
        return new SongResponseDTO(song.getId(), song.getName(), song.getSongMediaType(), song.getPictureMediaType(),
                song.getLanguage(),
                song.getDuration(),
                song.getLikes(),
                song.getPlayCount(),
                song.getArtists().stream().map(this::convertArtist).toList());
    }

    private ArtistResponseDTO convertArtist(Artist artist) {
        return new ArtistResponseDTO(artist.getId(), artist.getName());
    }
}
