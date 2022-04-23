package com.example.Proiect1;

import com.example.Proiect1.domain.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CascadeTypeTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Order(1)
    public void saveListener() {

        Listener listener = new Listener();
        listener.setName("ListenerCascadeTypeTest");
        List<Listener> listeners = new ArrayList<>();
        listeners.add(listener);

        Info info = new Info();
        info.setFirstName("InfoFirstCascadeTypeNameTest");
        info.setLastName("InfoLastCascadeTypeNameTest");
        info.setListener(listener);
        listener.setInfo(info);

        Artist artist = new Artist();
        artist.setName("ArtistCascadeTypeTest");
        entityManager.persist(artist);

        Song song = new Song();
        song.setName("SongCascadeTypeTest");
        song.setGenre(Genre.Pop);
        song.setArtist(artist);
        song.setListeners(listeners);
        List<Song> songs = new ArrayList<>();
        songs.add(song);
        listener.setSongs(songs);

        Favourite favourite = new Favourite();
        favourite.setListener(listener);
        favourite.setSong(song);
        List<Favourite> favourites = new ArrayList<>();
        favourites.add(favourite);
        listener.setFavourites(favourites);

        entityManager.persist(listener);
        entityManager.flush();
        entityManager.clear();

    }

    @Test
    @Order(2)
    public void updateArtist() {

        Song song = entityManager.find(Song.class, 1L);
        Artist artist = song.getArtist();
        artist.setName("ArtistCascadeTypeTestUpdate");
        artist.getSongs().forEach(s -> {s.setGenre(Genre.Rap);});
        entityManager.merge(artist);
        entityManager.flush();

    }

    @ParameterizedTest
    @Order(3)
    @ValueSource(longs = {1})
    public void orphanRemovalListener(long id) {

        Listener listener = entityManager.find(Listener.class, id);
        listener.setInfo(null);
        listener.setSongs(null);
//        listener.getSongs().clear();
//        listener.setFavourites(null);
        listener.getFavourites().clear();
        entityManager.persist(listener);
        entityManager.flush();

    }

    @ParameterizedTest
    @Order(4)
    @ValueSource(longs = {1})
    public void orphanRemovalSong(long id) {

        Song song = entityManager.find(Song.class, id);
//        song.setListeners(null);
//        song.getListeners().clear();
        song.getListeners().forEach(l -> {
            l.getSongs().remove(song);
        });
        song.getFavourites().clear();
        entityManager.persist(song);
        entityManager.flush();

    }

    @ParameterizedTest
    @Order(5)
    @ValueSource(longs = {1})
    public void orphanRemovalArtist(long id) {

        Artist artist = entityManager.find(Artist.class, id);
        artist.getSongs().forEach(s -> {
            s.getListeners().forEach(l -> {
                l.getSongs().remove(s);
            });
            s.getFavourites().clear();
        });
        artist.getSongs().clear();
        entityManager.persist(artist);
        entityManager.flush();

    }

}
