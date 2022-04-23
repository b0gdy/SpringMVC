package com.example.Proiect1.repositories;

import com.example.Proiect1.domain.Artist;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends CrudRepository<Artist, Long> {

    @Query("SELECT a FROM Artist a WHERE a.id = :id")
    Optional<Artist> findById(Long id);

    @Query("SELECT a FROM Artist a WHERE a.name = :name")
    List<Artist> findByName(String name);

    @Modifying
    @Query(value = "UPDATE Artist a SET a.name = :name WHERE a.id = :id")
    void updateById(Long id, String name);

    @Modifying
    @Query(value = "DELETE FROM Artist a WHERE a.id = :id")
    void deleteById(Long id);
    
}
