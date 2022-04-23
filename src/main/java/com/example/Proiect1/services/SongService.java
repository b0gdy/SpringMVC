package com.example.Proiect1.services;

import com.example.Proiect1.domain.Song;

import java.util.List;

public interface SongService {

    Song save(Song song);

    List<Song> findAll();

    Song findById(Long id);

    List<Song> findByName(String name);

    List<Song> findByArtistId(Long artistId);

    Song update(Song song);

    void deleteById(Long id);

    void deleteByArtistId(Long artistId);
    
}
