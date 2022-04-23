package com.example.Proiect1.repositories;

import com.example.Proiect1.domain.Listener;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ListenerRepository extends CrudRepository<Listener, Long> {

    @Query("SELECT l FROM Listener l WHERE l.id = :id")
    Optional<Listener> findById(Long id);

    @Query("SELECT l FROM Listener l WHERE l.name = :name")
    List<Listener> findByName(String name);

    @Modifying
    @Query(value = "UPDATE Listener l SET l.name = :name WHERE l.id = :id")
    void updateById(Long id, String name);

    @Modifying
    @Query(value = "DELETE FROM Listener l WHERE l.id = :id")
    void deleteById(Long id);


}
