package com.example.Proiect1.repositories;

import com.example.Proiect1.domain.*;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class InfoRepositoryTest {

    @Autowired
    private InfoRepository infoRepository;

    @Autowired
    private ListenerRepository listenerRepository;


    @Test
    @Order(5)
    public void addInfo() {

        Info info = new Info();
        info.setId(1L);
        info.setFirstName("InfoFirstNameRepositoryTest");
        info.setLastName("InfoLastNameRepositoryTest");
        Optional<Listener> listener = listenerRepository.findById(1L);
        assertTrue(listener.isPresent());
//        listener.get().setInfo(null);
//        assertTrue(listener.get().getInfo() == null);
        info.setListener(listener.get());
//        listener.get().setInfo(info);
        infoRepository.save(info);

    }

    @Test
    @Order(1)
    public void findById() {

        Optional<Info> info = infoRepository.findById(1L);
        assertTrue(info.isPresent());
        log.info("Find info by id: " + info.get().getFirstName() + " " + info.get().getLastName());

    }


    @Test
    @Order(2)
    public void findByListenerId() {

        Optional<Info> info = infoRepository.findByListenerId(1L);
        assertTrue(info.isPresent());
        log.info("Find info by listener id:");
        log.info(info.get().getFirstName() + " " + info.get().getLastName());

    }

    @Test
    @Order(3)
    public void updateById() {

        infoRepository.updateById(1L, "InfoFirstNameRepositoryTest", "InfoLastNameRepositoryTest", listenerRepository.findById(1L).get());
        Optional<Info> info = infoRepository.findById(1L);
        assertTrue(info.isPresent());
        log.info("Update info by id: " + info.get().getFirstName() + " " + info.get().getLastName());
        assertFalse((info.get().getFirstName() == "InfoFirstNameRepositoryTest") && (info.get().getLastName() == "InfoLastNameRepositoryTest"));

    }

    @Test
    @Order(4)
    public void deleteById() {

        Optional<Info> info = infoRepository.findById(1L);
        assertTrue(info.isPresent());
        infoRepository.deleteById(1L);
        Optional<Info> infoDeleted = infoRepository.findById(1L);
        assertFalse(infoDeleted.isPresent());

    }

    @Test
    @Order(6)
    public void deleteByListenerId() {

        Optional<Info> info = infoRepository.findByListenerId(1L);
        assertFalse(info.isEmpty());
        infoRepository.deleteByListenerId(1L);
        Optional<Info> infoDeleted = infoRepository.findById(1L);
        assertFalse(infoDeleted.isPresent());

    }

}