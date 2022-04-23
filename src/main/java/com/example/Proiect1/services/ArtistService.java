package com.example.Proiect1.services;

import com.example.Proiect1.domain.Artist;

import java.util.List;

public interface ArtistService {

    Artist save(Artist artist);

    List<Artist> findAll();

    Artist findById(Long id);

    List<Artist> findByName(String name);

    Artist update(Artist artist);

    void deleteById(Long id);
    
}
