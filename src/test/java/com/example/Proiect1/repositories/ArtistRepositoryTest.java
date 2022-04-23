package com.example.Proiect1.repositories;

import com.example.Proiect1.domain.Artist;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class ArtistRepositoryTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    @Test
    @Order(1)
    public void addArtist() {

        Artist artist = new Artist();
        artist.setName("ArtistRepositoryTest");
        artistRepository.save(artist);

    }

    @Test
    @Order(2)
    public void findById() {

        Optional<Artist> artist = artistRepository.findById(1L);
        assertTrue(artist.isPresent());
        log.info("Find artist by id: " + artist.get().getName());

    }

    @Test
    @Order(3)
    public void findByName() {

        List<Artist> artists = artistRepository.findByName("Artist1");
        assertFalse(artists.isEmpty());
        log.info("Find artist by name: ");
        artists.forEach(artist -> log.info(artist.getName()));

    }

    @Test
    @Order(4)
    public void updateById() {

        artistRepository.updateById(1L, "ArtistRepositoryTestUpdate");
        Optional<Artist> artist = artistRepository.findById(1L);
        log.info("Update artist by id: " + artist.get().getName());
        assertTrue(artist.isPresent());
        assertFalse(artist.get().getName() == "ArtistRepositoryTestUpdate");

    }

    @Test
    @Order(5)
    public void deleteById() {

        Optional<Artist> artist = artistRepository.findById(1L);
        assertTrue(artist.isPresent());
        artist.get().getSongs().forEach(s -> {
            s.getListeners().forEach(l -> {
                l.getSongs().remove(s);
            });
            s.getFavourites().clear();
        });
        artist.get().getSongs().clear();
        songRepository.deleteByArtistId(1L);
//        log.info("Songs:");
//        artist.get().getSongs().forEach(s -> log.info(s.getName()));
        artistRepository.deleteById(1L);
        Optional<Artist> artistDeleted = artistRepository.findById(1L);
        assertFalse(artistDeleted.isPresent());

    }
    
}