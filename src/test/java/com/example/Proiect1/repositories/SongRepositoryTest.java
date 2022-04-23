package com.example.Proiect1.repositories;

import com.example.Proiect1.domain.Artist;
import com.example.Proiect1.domain.Genre;
import com.example.Proiect1.domain.Info;
import com.example.Proiect1.domain.Song;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class SongRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Test
    @Order(1)
    public void addSong() {

        Song song = new Song();
        song.setName("SongRepositoryTest");
        song.setGenre(Genre.Pop);
        Optional<Artist> artist = artistRepository.findById(1L);
        song.setArtist(artist.get());
        songRepository.save(song);

    }

    @Test
    @Order(2)
    public void findById() {

        Optional<Song> song = songRepository.findById(1L);
        assertTrue(song.isPresent());
        log.info("Find song by id: " + song.get().getName());

    }

    @Test
    @Order(3)
    public void findByName() {

        List<Song> songs = songRepository.findByName("Song1");
        assertFalse(songs.isEmpty());
        log.info("Find song by name: ");
        songs.forEach(song -> log.info(song.getName()));

    }

    @Test
    @Order(4)
    public void findByArtistId() {

        List<Song> songs = songRepository.findByArtistId(1L);
        assertTrue(songs.size() >= 1);
        log.info("Find song by artist id:");
        songs.forEach(song -> log.info(song.getName()));

    }


    @Test
    @Order(5)
    public void updateById() {

        songRepository.updateById(1L, "SongRepositoryTest", Genre.Rock, artistRepository.findById(1L).get());
        Optional<Song> song = songRepository.findById(1L);
        assertTrue(song.isPresent());
        log.info("Update song by id: " + song.get().getName());
        assertFalse((song.get().getName() == "SongRepositoryTest") && (song.get().getGenre() == Genre.Rock));

    }

    @Test
    @Order(6)
    public void deleteById() {

        Optional<Song> song = songRepository.findById(1L);
        assertTrue(song.isPresent());
        song.get().getListeners().forEach(l -> {
            l.getSongs().remove(song);
        });
        song.get().getFavourites().clear();
//        favouriteRepository.findBySongId(1L);
        favouriteRepository.deleteBySongId(1L);
        songRepository.deleteById(1L);
        Optional<Song> songDeleted = songRepository.findById(1L);
        assertFalse(songDeleted.isPresent());

    }

    @Test
    @Order(7)
    public void deleteByArtistId() {

        List<Song> songs = songRepository.findByArtistId(1L);
        assertFalse(songs.isEmpty());
        songs.forEach(s -> {
            s.getListeners().forEach(l -> {
                l.getSongs().remove(s);
            });
            s.getFavourites().clear();
            favouriteRepository.deleteBySongId(s.getId());
        });
        songRepository.deleteByArtistId(1L);
        Optional<Song> songDeleted = songRepository.findById(1L);
        assertFalse(songDeleted.isPresent());

    }

    @Test
    @Order(8)

    public void findPage() {

        Pageable firstPage = PageRequest.of(0, 2);
        Page<Song> allSongs = songRepository.findAll(firstPage);
        assertTrue(allSongs.getNumberOfElements() == 2);

    }

}
