package com.example.Proiect1.repositories;

import com.example.Proiect1.domain.Favourite;
import com.example.Proiect1.domain.Listener;
import com.example.Proiect1.domain.Song;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends PagingAndSortingRepository<Favourite, Long> {

    @Query("SELECT f FROM Favourite f WHERE f.id = :id")
    Optional<Favourite> findById(Long id);

    @Query("SELECT f FROM Favourite f WHERE f.listener.id = :listenerId")
    List<Favourite> findByListenerId(Long listenerId);

    @Query("SELECT f FROM Favourite f WHERE f.song.id = :songId")
    List<Favourite> findBySongId(Long songId);

    @Modifying
    @Query(value = "UPDATE Favourite f SET f.listener = :listener, f.song = :song WHERE f.id = :id")
    void updateById(Long id, Listener listener, Song song);

    @Modifying
    @Query(value = "DELETE FROM Favourite f WHERE f.id = :id")
    void deleteById(Long id);

    @Modifying
    @Query(value = "DELETE FROM Favourite f WHERE f.listener.id = :listenerId")
    void deleteByListenerId(Long listenerId);

    @Modifying
    @Query(value = "DELETE FROM Favourite f WHERE f.song.id = :songId")
    void deleteBySongId(Long songId);
    
}
