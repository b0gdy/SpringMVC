package com.example.Proiect1.repositories;

import com.example.Proiect1.domain.Artist;
import com.example.Proiect1.domain.Genre;
import com.example.Proiect1.domain.Song;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends PagingAndSortingRepository<Song, Long> {

    @Query("SELECT s FROM Song s WHERE s.id = :id")
    Optional<Song> findById(Long id);

    @Query("SELECT s FROM Song s WHERE s.name = :name")
    List<Song> findByName(String name);

    @Query("SELECT s FROM Song s WHERE s.artist.id = :id")
    List<Song> findByArtistId(Long id);

    @Modifying
    @Query(value = "UPDATE Song s SET s.name = :name, s.genre = :genre, s.artist = :artist WHERE s.id = :id")
    void updateById(Long id, String name, Genre genre, Artist artist);

    @Modifying
    @Query(value = "DELETE FROM Song s WHERE s.id = :id")
    void deleteById(Long id);

    @Modifying
    @Query(value = "DELETE FROM Song s WHERE s.artist.id = :artistId")
    void deleteByArtistId(Long artistId);

}
