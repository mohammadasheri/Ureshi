package com.ureshii.demo.artist;

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

@RestController
@RequestMapping("/artist")
@Slf4j
public record ArtistController(ArtistService artistService) {

    @PostMapping("/create")
    ResponseEntity<ArtistResponseDTO> createArtist(@RequestParam @NotBlank String name,
                                        @NotBlank @RequestParam String countryOfOrigin,
                                        @RequestParam MultipartFile pictureFile) throws IOException {
        log.info("Artist controller: create artist");
        CreateArtistDTO dto = new CreateArtistDTO(name, countryOfOrigin,
                FilenameUtils.getExtension(pictureFile.getOriginalFilename()), pictureFile.getBytes());
        Artist artist = artistService.createArtist(dto);
        return new ResponseEntity<>(convertArtist(artist), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<ArtistResponseDTO> getArtistById(@PathVariable @NotNull Long id) throws NotFoundException {
        log.info("Artist controller: create artist");
        Artist artist = artistService.getArtistById(id);
        return new ResponseEntity<>(convertArtist(artist), HttpStatus.OK);
    }

    private ArtistResponseDTO convertArtist(Artist artist) {
        return new ArtistResponseDTO(artist.getId(),artist.getName());
    }
}
