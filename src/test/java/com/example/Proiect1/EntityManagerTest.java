package com.example.Proiect1;

import com.example.Proiect1.domain.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntityManagerTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Order(1)
    public void findListener() {

        Listener listenerFound = entityManager.find(Listener.class, 1L);
        assertEquals(listenerFound.getName(), "Listener1");

    }

    @Test
    @Order(2)
    public void findInfo() {

        Info infoFound = entityManager.find(Info.class, 1L);
        assertEquals(infoFound.getFirstName(), "InfoFirstName1");
        assertEquals(infoFound.getLastName(), "InfoLastName1");
        assertEquals(infoFound.getListener().getId(), 1L);

    }

    @Test
    @Order(3)
    public void findArtist() {

        Artist artistFound = entityManager.find(Artist.class, 1L);
        assertEquals(artistFound.getName(), "Artist1");

    }

    @Test
    @Order(4)
    public void findSong() {

        Song songFound = entityManager.find(Song.class, 1L);
        assertEquals(songFound.getName(), "Song1");
        assertEquals(songFound.getGenre(), Genre.Pop);
        assertEquals(songFound.getArtist().getId(), 1L);

    }

    @Test
    @Order(5)
    public void findFavourite() {

        Favourite favouriteFound = entityManager.find(Favourite.class, 1L);
        assertEquals(favouriteFound.getListener().getId(), 1L);
        assertEquals(favouriteFound.getSong().getId(), 1L);

    }

    @Test
    @Order(6)
    public void updateListener() {

        Listener listenerUpdated = entityManager.find(Listener.class, 1L);
        listenerUpdated.setName("ListenerEntityManagerTestUpdate");
        entityManager.persist(listenerUpdated);
        entityManager.flush();

    }

    @Test
    @Order(7)
    public void updateInfo() {

        Info infoUpdated = entityManager.find(Info.class, 1L);
        infoUpdated.setFirstName("InfoFirstNameEntityManagerTestUpdate");
        infoUpdated.setLastName("InfoLastNameEntityManagerTestUpdate");
        entityManager.persist(infoUpdated);
        entityManager.flush();

    }

    @Test
    @Order(8)
    public void updateArtist() {

        Artist artistUpdated = entityManager.find(Artist.class, 1L);
        artistUpdated.setName("ArtistEntityManagerTestUpdate");
        entityManager.persist(artistUpdated);
        entityManager.flush();

    }

    @Test
    @Order(9)
    public void updateSong() {

        Song songUpdated = entityManager.find(Song.class, 1L);
        songUpdated.setName("SongEntityManagerTestUpdate");
        songUpdated.setGenre(Genre.Rock);
        entityManager.persist(songUpdated);
        entityManager.flush();

    }

}
