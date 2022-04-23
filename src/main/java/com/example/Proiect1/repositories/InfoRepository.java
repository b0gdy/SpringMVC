package com.example.Proiect1.repositories;

import com.example.Proiect1.domain.Info;
import com.example.Proiect1.domain.Listener;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface InfoRepository extends CrudRepository<Info, Long> {

    @Query("SELECT i FROM Info i WHERE i.id = :id")
    Optional<Info> findById(Long id);

    @Query("SELECT i FROM Info i WHERE i.listener.id = :listenerId")
    Optional<Info> findByListenerId(Long listenerId);

    @Modifying
    @Query(value = "UPDATE Info i SET i.firstName = :firstName, i.lastName = :lastName, i.listener = :listener WHERE i.id = :id")
    void updateById(Long id, String firstName, String lastName, Listener listener);

    @Modifying
    @Query(value = "DELETE FROM Info i WHERE i.id = :id")
    void deleteById(Long id);

    @Modifying
    @Query(value = "DELETE FROM Info i WHERE i.listener.id = :listenerId")
    void deleteByListenerId(Long listenerId);

}
