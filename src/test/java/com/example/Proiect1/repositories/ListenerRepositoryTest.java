package com.example.Proiect1.repositories;

import com.example.Proiect1.domain.Info;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
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
public class ListenerRepositoryTest {

    @Autowired
    private ListenerRepository listenerRepository;

    @Autowired
    private SongRepository songRepository;

    @Test
    @Order(1)
    public void addListener() {

        Listener listener = new Listener();
        listener.setName("ListenerRepositoryTest");
        Info info = new Info();
        info.setFirstName("InfoFirstNameRepositoryTest");
        info.setLastName("InfoLastNameRepositoryTest");
        info.setListener(listener);
        listener.setInfo(info);
        Optional<Song> song = songRepository.findById(1L);
        List<Song> songs = new ArrayList<>();
        songs.add(song.get());
        listener.setSongs(songs);
        listenerRepository.save(listener);

    }

    @Test
    @Order(2)
    public void findById() {

        Optional<Listener> listener = listenerRepository.findById(1L);
        assertTrue(listener.isPresent());
        log.info("Find listener by id: " + listener.get().getName());

    }

    @Test
    @Order(3)
    public void findByName() {

        List<Listener> listeners = listenerRepository.findByName("Listener1");
        assertFalse(listeners.isEmpty());
        log.info("Find listener by name: ");
        listeners.forEach(listener -> log.info(listener.getName()));

    }

    @Test
    @Order(4)
    public void updateById() {

        listenerRepository.updateById(1L, "ListenerRepositoryTestUpdate");
        Optional<Listener> listener = listenerRepository.findById(1L);
        assertTrue(listener.isPresent());
        log.info("Update listener by id: " + listener.get().getName());
        assertFalse(listener.get().getName() == "ListenerRepositoryTestUpdate");

    }

    @Test
    @Order(5)
    public void deleteById() {

        Optional<Listener> listener = listenerRepository.findById(1L);
        assertTrue(listener.isPresent());
        listener.get().setInfo(null);
        listener.get().setSongs(null);
        listener.get().getFavourites().clear();
        listenerRepository.deleteById(1L);
        Optional<Listener> listenerDeleted = listenerRepository.findById(1L);
        assertFalse(listenerDeleted.isPresent());

    }

}
