package com.ureshii.demo.search;

import com.ureshii.demo.artist.Artist;
import com.ureshii.demo.artist.ArtistResponseDTO;
import com.ureshii.demo.exception.NotFoundException;
import com.ureshii.demo.playlist.*;
import com.ureshii.demo.song.ProjectedSong;
import com.ureshii.demo.song.Song;
import com.ureshii.demo.song.SongResponseDTO;
import com.ureshii.demo.song.SongService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/search")
@Slf4j
public record SearchController(PlaylistService playlistService, SongService songService) {

    @GetMapping()
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<SearchResponseDTO> createPlaylist(@RequestParam @NotBlank String queryString) {
        log.info("Search controller: search by query string");
        List<ProjectedPlaylist> playlists = playlistService.getPlaylistsLike(queryString);
        List<ProjectedSong> songs = songService.getSongsLike(queryString);
        SearchResponseDTO searchResponseDTO = new SearchResponseDTO(playlists, songs);
        return new ResponseEntity<>(searchResponseDTO, HttpStatus.OK);
    }
}
