package com.example.Proiect1.repositories;

import com.example.Proiect1.domain.Favourite;
import com.example.Proiect1.domain.Listener;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
class FavouriteRepositoryTest {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private ListenerRepository listenerRepository;

    @Autowired
    private SongRepository songRepository;

    @Test
    @Order(5)
    public void addFavourite() {

        Favourite favourite = new Favourite();
        Optional<Listener> listener = listenerRepository.findById(1L);
        favourite.setListener(listener.get());
        Optional<Song> song = songRepository.findById(1L);
        favourite.setSong(song.get());
        favouriteRepository.save(favourite);

    }

    @Test
    @Order(1)
    public void findById() {

        Optional<Favourite> favourite = favouriteRepository.findById(1L);
        assertTrue(favourite.isPresent());
        log.info("Find favourite by listener id:" + favourite.get().getListener().getName() + " - " + favourite.get().getSong().getName());

    }

    @Test
    @Order(2)
    public void findByListenerId() {

        List<Favourite> favourites = favouriteRepository.findByListenerId(1L);
        assertTrue(favourites.size() >= 1);
        log.info("Find favourite by listener id:");
        favourites.forEach(favourite -> log.info(favourite.getListener().getName() + " - " + favourite.getSong().getName()));

    }

    @Test
    @Order(3)
    public void findBySongId() {

        List<Favourite> favourites = favouriteRepository.findBySongId(1L);
        assertTrue(favourites.size() >= 1);
        log.info("Find favourite by song id:");
        favourites.forEach(favourite -> log.info(favourite.getSong().getName() + " - " + favourite.getListener().getName()));

    }

    @Test
    @Order(4)
    public void deleteById() {

        Optional<Favourite> favourite = favouriteRepository.findById(1L);
        assertTrue(favourite.isPresent());
        favouriteRepository.deleteById(1L);
        Optional<Favourite> favouriteDeleted = favouriteRepository.findById(1L);
        assertFalse(favouriteDeleted.isPresent());

    }

    @Test
    @Order(6)
    public void deleteByListenerId() {

        List<Favourite> favourites = favouriteRepository.findByListenerId(1L);
        assertFalse(favourites.isEmpty());
        favouriteRepository.deleteByListenerId(1L);
        Optional<Favourite> favouriteDeleted = favouriteRepository.findById(1L);
        assertFalse(favouriteDeleted.isPresent());

    }

    @Test
    @Order(7)
    public void deleteBySongId() {

        List<Favourite> favourites = favouriteRepository.findBySongId(1L);
        if(favourites.isEmpty()) {
            Favourite favourite = new Favourite();
            Optional<Listener> listener = listenerRepository.findById(1L);
            favourite.setListener(listener.get());
            Optional<Song> song = songRepository.findById(1L);
            favourite.setSong(song.get());
            favouriteRepository.save(favourite);
        }
        else assertFalse(favourites.isEmpty());
        favouriteRepository.deleteBySongId(1L);
        Optional<Favourite> favouriteDeleted = favouriteRepository.findById(1L);
        assertFalse(favouriteDeleted.isPresent());

    }

    @Test
    @Order(8)

    public void findPage() {

        Pageable firstPage = PageRequest.of(0, 2);
        Page<Favourite> allFavourites = favouriteRepository.findAll(firstPage);
        if (allFavourites.getNumberOfElements() < 2) {

            Favourite favourite = new Favourite();
            Optional<Listener> listener = listenerRepository.findById(1L);
            favourite.setListener(listener.get());
            Optional<Song> song = songRepository.findById(1L);
            favourite.setSong(song.get());
            favouriteRepository.save(favourite);

        }
        allFavourites = favouriteRepository.findAll(firstPage);
        assertTrue(allFavourites.getNumberOfElements() == 2);

    }
    
}