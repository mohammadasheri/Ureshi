package com.ureshii.demo.playlist;

import com.ureshii.demo.config.file.ImageWriterGateway;
import com.ureshii.demo.exception.NotFoundException;
import com.ureshii.demo.song.Song;
import com.ureshii.demo.song.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public record PlaylistServiceImpl(PlaylistRepository repository, SongService songService, ImageWriterGateway gateway)
        implements PlaylistService {

    @Override
    public Playlist createPlaylist(CreatePlaylistDTO dto) {
        log.info("Playlist service: create Playlist");
        String pictureFileAddress = generateNewFileAddress(dto.pictureFileType());
        gateway.saveFile(pictureFileAddress, dto.pictureBytes());
        Playlist playlist = repository.findByName(dto.name()).orElseGet(
                () -> Playlist.builder().name(dto.name())
                        .pictureAddress(pictureFileAddress).pictureMediaType(dto.pictureFileType()).build());
        return repository.save(playlist);
    }

    @Override
    public Playlist addSong(PlaylistAddSongDTO dto) throws NotFoundException {
        Playlist playlist = getPlaylistById(dto.playlistId());
        Song song = songService.getSongById(dto.songId());
        playlist.getSongs().add(song);
        return repository.save(playlist);
    }

    @Override
    public Playlist getPlaylistById(Long id) throws NotFoundException {
        Optional<Playlist> PlaylistOptional = repository.findById(id);
        return PlaylistOptional.orElseThrow(() -> new NotFoundException("Playlist not found."));
    }

    @Override
    public Playlist getPlaylistByName(String PlaylistName) throws NotFoundException {
        Optional<Playlist> PlaylistOptional = repository.findByName(PlaylistName);
        return PlaylistOptional.orElseThrow(() -> new NotFoundException("Playlist not found."));
    }

    @Override public List<ProjectedPlaylist> getAllPlaylists() {
        return repository.findAllByOrderByCreatedDesc();
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
